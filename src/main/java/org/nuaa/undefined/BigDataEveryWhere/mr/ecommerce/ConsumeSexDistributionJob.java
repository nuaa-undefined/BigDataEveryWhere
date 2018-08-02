package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * Job Name : ConsumeSexDistribution
 * input : project/e-commerce-user/part-r-00000
 * output : project/e-commerce-consume-sex-distribution
 * function : 获取消费总额、男性消费总额、女性消费总额、男性女性消费占比、用户总数、男性用户总数、女性用户总数
 *          平均消费额、男性用户平均消费额、女性用户平均消费额
 * overview : 在reduce层进行统计并在cleanup时输出
 */
public class ConsumeSexDistributionJob {
    public static String inputPathString = "project/e-commerce-user/part-r-00000";
    public static String outputPathString = "project/e-commerce-consume-sex-distribution";
    public static String jobName = "consume-sex-distribution";

    public static class ConsumeSexDistributionMapper extends Mapper<Text, Text, Text, Text> {
        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            context.write(key,value);
        }
    }

    public static class ConsumeSexDistributionReducer extends Reducer<Text, Text, Text, Text> {
        // 总消费额
        private static double sumConsume = 0;
        // 男性用户总消费额
        private static double manSumConsume = 0;
        // 女性用户总消费额
        private static double womanSumConsume = 0;
        // 用户总人数
        private static double userNum = 0;
        // 男性用户数量
        private static int manNum = 0;
        // 女性用户数量
        private static int womanNum = 0;



        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text in : values) {
                String [] data = in.toString().split("\\s+");
                double money = Double.parseDouble(data[4]);
                sumConsume += money;
                userNum++;
                if ("1".equals(data[0])) {
                    manSumConsume += money;
                    manNum++;
                } else {
                    womanSumConsume += money;
                    womanNum++;
                }
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            context.write(new Text("用户消费总额"), new Text(String.valueOf(sumConsume)));
            context.write(new Text("男性消费总额"), new Text(String.valueOf(manSumConsume)));
            context.write(new Text("女性消费总额"), new Text(String.valueOf(womanSumConsume)));
            context.write(new Text("男性消费占比 : "), new Text(JobUtil.polish(manSumConsume, sumConsume)));
            context.write(new Text("女性消费占比 : "), new Text(JobUtil.polish(womanSumConsume, sumConsume)));
            context.write(new Text("用户总数 : "), new Text(String.valueOf(userNum)));
            context.write(new Text("男性用户总数 : "), new Text(String.valueOf(manNum)));
            context.write(new Text("女性性用户总数 : "), new Text(String.valueOf(womanNum)));
            context.write(new Text("用户平均消费额 : "), new Text(String.valueOf(userNum / sumConsume)));
            context.write(new Text("男性用户平均消费额 : "), new Text(String.valueOf(manSumConsume / manNum)));
            context.write(new Text("女性用户平均消费额 : "), new Text(String.valueOf(womanSumConsume / womanNum)));
        }
    }

    public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        Job job = Job.getInstance(conf, jobName);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setMapperClass(ConsumeSexDistributionMapper.class);
        job.setReducerClass(ConsumeSexDistributionReducer.class);
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
