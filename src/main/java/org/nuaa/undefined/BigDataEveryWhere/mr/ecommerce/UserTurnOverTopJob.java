package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * Job Name : UserTurnOverTop
 * input : project/e-commerce-user/part-r-00000
 * output : project/e-commerce-user-top-over-top
 * function : 获取总消费额前${TOP_NUM}的用户
 * overview : 按照消费额在map后的sort层进行降序排序
 * @Date: Created in 2018/7/26 21:26
 */
public class UserTurnOverTopJob {
    private final static int TOP_NUM = 20;
    public static String inputPathString = "project/e-commerce-user/part-r-00000";
    public static String outputPathString = "project/e-commerce-user-top-over-top";
    public static String jobName = "user-turn-over-top";

    public static class UserTurnOverMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            context.write(value, NullWritable.get());
        }
    }

    public static class UserTurnOverComparator extends WritableComparator {
        public UserTurnOverComparator(){
            super(Text.class, true);
        }

        @Override
        public int compare(WritableComparable a, WritableComparable b) {
             Double val1 = Double.valueOf(((Text) a).toString().split("\\s+")[5]);
             Double val2 = Double.valueOf(((Text) b).toString().split("\\s+")[5]);
             return val2.compareTo(val1);
        }
    }

    public static class UserTurnOverReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
        private static int outputCount = 0;

        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            if (outputCount < 20) {
                context.write(key, NullWritable.get());
                outputCount++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(UserTurnOverMapper.class);
        job.setSortComparatorClass(UserTurnOverComparator.class);
        job.setReducerClass(UserTurnOverReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(job, new Path(inputPathString));
        Path outputPath = new Path(outputPathString);
        FileSystem.get(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
