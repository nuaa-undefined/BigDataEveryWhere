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
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ToMax
 * Job Name : ConsumeRegionDistribution
 * input : project/e-commerce-region/part-r-00000
 * output : project/e-commerce-consume-region-distribution
 * function : 获取各地区消费占比、各地区购弃率、各地区平均消费水平
 * overview : 在reduce层进行统计并在cleanup时输出
 * @Date: Created in 2018/7/27 9:26
 */
public class ConsumeRegionDistributionJob {
    public static String inputPathString = "project/e-commerce-region/part-r-00000";
    public static String outputPathString = "project/e-commerce-consume-region-distribution";
    public static String jobName = "consume-region-distribution";

    public static class ConsumeRegionDistributionMapper extends Mapper<Text, Text, Text, Text> {
        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            context.write(key,value);
        }
    }

    public static class ConsumeRegionDistributionReducer extends Reducer<Text, Text, Text, Text> {
        private Map<String, String> result = new HashMap<>();
        private double sumConsume = 0;
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            // 区域消费额占比
            // 区域购弃率
            // 平均消费水平
            for (Text in : values) {
                String [] data = in.toString().split("\\s+");
                String money = data[4];
                sumConsume += Double.parseDouble(money);
                result.put(key.toString(), data[0]+"|"+money+"|"+ data[8] + "|" + data[10] + "|" + data[1] + "|" + data[4]);
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            for (Map.Entry<String, String> entry : result.entrySet()) {
                String []data = entry.getValue().split("\\|");
                context.write(new Text(entry.getKey()+"\t"+data[0]),
                        new Text(JobUtil.polish(Double.parseDouble(data[1]), sumConsume) + "\t" +
                        JobUtil.polish(Double.parseDouble(data[3]), Double.parseDouble(data[2])) + "\t" +
                        Double.parseDouble(data[5]) / Double.parseDouble(data[4])));
            }
        }
    }

    public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        Job job = Job.getInstance(conf, jobName);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setMapperClass(ConsumeRegionDistributionMapper.class);
        job.setReducerClass(ConsumeRegionDistributionReducer.class);
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
