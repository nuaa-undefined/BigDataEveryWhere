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
 * Job Name : OutputRegionInfo
 * input : /user/root/project/e-commerce-user/part-r-00000
 * output : /user/root/project/e-commerce-region
 * function : 将日志信息处理成区域表
 * overview :
 *          最终输出为区域编码、区域名、区域用户数、区域男性用户数、区域女性用户数、区域总消费额、
 *          区域男性用户总消费额、区域女性用户总消费额、区域最大成交额、区域交易总数、区域成功交易总数、
 *          区域失败交易总数、区域最早交易时间
 *
 * @Date: Created in 2018/7/27 9:58
 */
public class OutputRegionInfoJob {
    public static String inputPathString = "project/e-commerce-user/part-r-00000";
    public static String outputPathString = "project/e-commerce-region";
    public static String jobName = "output-region-info";

    public static class OutputRegionInfoMapper extends Mapper<LongWritable, Text, Text, Text> {
        private Text outputKey = new Text();
        private Text outputValue = new Text();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String [] data = value.toString().split("\\s+");
            outputKey.set(data[7]);
            outputValue.set(data[1] + "|" + data[2] + "|" + data[3] + "|" + data[4] + "|" + data[5] + "|" + data[6] + "|" + data[8]);
            context.write(outputKey, outputValue);
        }
    }

    public static class OutputRegionInfoReducer extends Reducer<Text, Text, Text, Text> {
        private Text outputKey = new Text();
        private Text outputValue = new Text();
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            // 区域编码
            String regionCode;
            // 区域名称
            String regionName;
            // 区域用户总数
            int userNum = 0;
            // 区域男性用户总数
            int manUserNum = 0;
            // 区域女性用户总数
            int womanUserNum = 0;
            // 区域消费总额
            double sumConsume = 0;
            // 区域男性消费总额
            double manConsume = 0;
            // 区域女性消费总额
            double womanConsume = 0;
            // 区域用户交易总数
            int userConsumeSum = 0;
            // 区域用户成功交易总数
            int userConsumeSuccessSum = 0;
            // 区域用户失败交易总数
            int userConsumeFailSum = 0;
            // 区域最高成交额
            double maxConsumeValue = 0;
            // 最早发生交易时间
            String earliestConsumeTime = null;
            regionCode = key.toString();
            regionName = ConstCommerceValue.CITY_CODE_NAME_MAP.get(regionCode);
            for (Text in : values) {
                String [] data = in.toString().split("\\|");
                Double money = Double.parseDouble(data[4]);
                userNum++;
                sumConsume += money;
                userConsumeSum += Integer.parseInt(data[1]);
                userConsumeSuccessSum += Integer.parseInt(data[2]);
                userConsumeFailSum += Integer.parseInt(data[3]);
                maxConsumeValue = Math.max(maxConsumeValue, Double.parseDouble(data[5]));
                earliestConsumeTime = JobUtil.compare(earliestConsumeTime, data[6]);
                if (ConstCommerceValue.MAN_SEX.equals(data[0])) {
                    manUserNum++;
                    manConsume += money;
                } else {
                    womanUserNum++;
                    womanConsume += money;
                }
            }
            outputKey.set(regionCode + "\t" + regionName + "\t" +
                    userNum + "\t" + manUserNum + "\t" +
                    womanUserNum + "\t" + sumConsume + "\t" +
                    manConsume + "\t" + womanConsume + "\t" + maxConsumeValue + "\t" +
                    userConsumeSum + "\t" + userConsumeSuccessSum + "\t" +
                    userConsumeFailSum + "\t" + earliestConsumeTime);
            context.write(outputKey, outputValue);
        }
    }

    public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(OutputRegionInfoMapper.class);
        job.setReducerClass(OutputRegionInfoReducer.class);
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
