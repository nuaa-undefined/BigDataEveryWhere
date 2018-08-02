package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * Job Name : OutputUserInfo
 * input : /user/root/project/e-commerce-clean/part-r-00000
 * output : /user/root/project/e-commerce-user
 * function : 将日志信息处理成用户表
 * overview :
 *          最终输出为用户id 、用户性别、用户购买次数、成交次数、未成交次数、交易总额、最大成交额
 *          用户归属地、用户最早使用记录
 * @Date: Created in 2018/7/26 20:32
 */
public class OutputUserInfoJob {
    public static String inputPathString = "project/e-commerce-clean/part-r-00000";
    public static String outputPathString = "project/e-commerce-user";
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

    public static class OutputUserInfoReducer extends Reducer<Text, Text, Text, Text> {
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
            outputValue.set(
                    sex + "\t" + buyCount + "\t" + successBuyCount + "\t" +
                    failBuyCount + "\t" + sumMoney + "\t" + maxMoney + "\t" +
                    place + "\t" + earliestTime
            );
            context.write(key, outputValue);
        }
    }
    public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(OutputUserInfoMapper.class);
        job.setReducerClass(OutputUserInfoReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(inputPathString));
        Path outputPath = new Path(outputPathString);
        FileSystem.get(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
