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
 * Job Name : ConsumeYearDistribution
 * input : /user/root/project/e-commerce-clean/part-r-00000
 * output : /user/root/project/e-commerce-consume-year-distribution
 * function : 按照年份统计信息，包括年交易额、年交易次数、年成功交易次数、年失败交易次数、以及按照性别的分布
 * overview :
 *          取时间的年份作为主键
 * @Date: Created in 2018/7/27 11:47
 */
public class ConsumeMonthDistributionJob {
    public static String inputPathString = "project/e-commerce-clean/part-r-00000";
    public static String outputPathString = "project/e-commerce-consume-month-distribution";
    public static String jobName = "consume-year-distribution";

    public static class ConsumeMonthDistributioMapper extends Mapper<LongWritable, Text, Text, Text> {
        private Text outputKey = new Text();
        private Text outputValue = new Text();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String [] data = value.toString().split("\\s+");
            outputKey.set(data[6].split("-")[1]);
            outputValue.set(data[4]+"|"+data[7]+"|"+data[8]);
            context.write(outputKey, outputValue);
        }
    }

    public static class ConsumeMonthDistributionReducer extends Reducer<Text, Text, Text, Text> {
        private Text outputValue = new Text();
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int sumConsumeCount = 0;
            int manConsumeCount = 0;
            int womanConsumeCount = 0;
            int successConsumeCount = 0;
            int failConsumeCount = 0;
            double sumConsumeMoney = 0;
            double manConsumeMoney = 0;
            double womanConsumeMoney = 0;
            for (Text in : values) {
                String [] data = in.toString().split("\\|");
                double money = Double.parseDouble(data[1]);
                int success = Integer.parseInt(data[2]);
                sumConsumeCount++;
                sumConsumeMoney += success * money;
                successConsumeCount += success;
                failConsumeCount += success == 1 ? 0 : 1;
                if (ConstCommerceValue.MAN_SEX.equals(data[0])) {
                    manConsumeCount++;
                    manConsumeMoney += success * money;
                } else {
                    womanConsumeCount++;
                    womanConsumeMoney += success * money;
                }
            }
            outputValue.set(sumConsumeCount+"\t"+manConsumeCount+"\t"+womanConsumeCount+"\t"+
                    sumConsumeMoney+"\t"+manConsumeMoney+"\t"+womanConsumeMoney+"\t"+
                    successConsumeCount+"\t"+failConsumeCount);
            context.write(key, outputValue);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(ConsumeMonthDistributioMapper.class);
        job.setReducerClass(ConsumeMonthDistributionReducer.class);
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
