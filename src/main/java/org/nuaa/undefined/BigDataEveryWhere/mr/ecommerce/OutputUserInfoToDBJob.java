package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.bean.UserBean;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/30 10:03
 */
public class OutputUserInfoToDBJob {
    public static String inputPathString = "project/e-commerce-clean/part-r-00000";
    public static String outputPathString = "project/e-commerce-user-test";
    public static String jobName = "output-user-info";
    public static class OutputUserInfoMapper extends Mapper<LongWritable, Text, Text, Text> {
        private Text outputKey = new Text();
        private Text outputValue = new Text();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String [] data = value.toString().split("\\s+");
            outputKey.set(data[3]);
            outputValue.set(data[2] + "|" + data[4] + "|" + data[5] + "|" + data[7] + "|" + data[8]);
            context.write(outputKey, outputValue);
        }
    }

    public static class OutputUserInfoReducer extends Reducer<Text, Text, UserBean, NullWritable> {
        private Text outputValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String sex = null;
            int buyCount = 0;
            int successBuyCount = 0;
            int failBuyCount = 0;
            double sumMoney = 0;
            double maxMoney = 0;
            String place = null;
            String earliestTime = null;
            for (Text in : values) {
                String [] data = in.toString().split("\\|");
                buyCount ++;

                if (Integer.parseInt(data[4]) == 0) {
                    failBuyCount ++;
                } else {
                    double money = Double.parseDouble(data[3]);
                    sumMoney += money;
                    successBuyCount ++;
                    maxMoney = Math.max(maxMoney, money);
                }
                sex = sex != null ? sex : data[1];
                place = place != null ? place : data[0];
                earliestTime = JobUtil.compare(earliestTime, data[2]);
            }
            context.write(new UserBean(
                    key.toString(), sex, buyCount, successBuyCount, failBuyCount, sumMoney, maxMoney, place, earliestTime
            ), NullWritable.get());
        }
    }
    public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.163.101:3306/big_data??characterEncoding=utf8&useSSL=false",
                "root", "123456");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(OutputUserInfoMapper.class);
        job.setReducerClass(OutputUserInfoReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(UserBean.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(job, new Path(inputPathString));
        Path outputPath = new Path(outputPathString);
        FileSystem.get(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);
        DBOutputFormat.setOutput(job, "e_commerce_user",
                "id","sex","buy_count","success_count","fail_count",
                "sum_money","max_money","place","earliest_record");
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
