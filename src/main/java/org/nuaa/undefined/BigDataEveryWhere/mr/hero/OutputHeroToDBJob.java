package org.nuaa.undefined.BigDataEveryWhere.mr.hero;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.nuaa.undefined.BigDataEveryWhere.mr.hero.bean.HeroBean;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 16:48
 */
public class OutputHeroToDBJob {
    public static String inputPathString = "project/hero.txt";
    public static String outputPathString = "project/hero-user";
    public static String jobName = "output-hero";

    public static class HeroMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String [] data = value.toString().split("\\s+");
            context.write(new Text(data[0]), new Text(data[2] + "\t" + data[1] + "\t" + data[3]));
        }
    }

    public static class HeroReducer extends Reducer<Text, Text, HeroBean, NullWritable> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String name = key.toString();
            int winNum = 0;
            int failNum = 0;
            int userNum = 0;
            Set<String> heroSet = new HashSet<>();
            for (Text value : values) {
                String [] data = value.toString().split("\\s+");
                if ("1".equals(data[1])) {
                    winNum ++;
                } else {
                    failNum ++;
                }
                if (!heroSet.contains(data[0])) {
                    userNum ++;
                    heroSet.add(data[0]);
                }
            }
            context.write(new HeroBean(name, winNum, failNum, userNum), NullWritable.get());
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.163.101:3306/big_data??characterEncoding=utf8&useSSL=false",
                "root", "123456");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(HeroMapper.class);
        job.setReducerClass(HeroReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(HeroBean.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(job, new Path(inputPathString));
        DBOutputFormat.setOutput(job, "hero",
                "name","win_num","fail_num","user_num");
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
