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
import org.nuaa.undefined.BigDataEveryWhere.mr.hero.bean.HeroLogBean;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 16:54
 */
public class OutputHeroLogToDBJob {
    public static String inputPathString = "project/hero.txt";
    public static String outputPathString = "project/hero-user";
    public static String jobName = "output-hero-log";

    public static class HeroLogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            context.write(value, NullWritable.get());
        }
    }

    public static class HeroLogReducer extends Reducer<Text, Text, HeroLogBean, NullWritable> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            context.write(new HeroLogBean(key.toString()), NullWritable.get());
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.163.101:3306/big_data??characterEncoding=utf8&useSSL=false",
                "root", "123456");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(HeroLogMapper.class);
        job.setReducerClass(HeroLogReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(HeroLogBean.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(job, new Path(inputPathString));
        DBOutputFormat.setOutput(job, "hero_log",
                "hero_name","user_id","status","label");
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
