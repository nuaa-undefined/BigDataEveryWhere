package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
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
 * Job Name : CleanElectronicCommerce
 * input : /user/root/project/e-commerce.txt
 * output : /user/root/project/e-commerce-clean
 * function : clean the input file and create random city code
 * overview : map中将省级字段编为统一编码（随机经济较发达省占80%+，
 *           其他城市占20%-），同时去掉市级字段
 *
 * @Date: Created in 2018/7/26 20:14
 */
public class CleanElectronicCommerceJob {
    public static String inputPathString = "project/e-commerce.txt";
    public static String outputPathString = "project/e-commerce-clean";
    public static String jobName = "clean-electronic-commerce";
    public static class CleanElectronicCommerceMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String [] data = value.toString().split("\\s+");
            value.set(data[0] + "\t" + data[1] + "\t" + ConstCommerceValue.randomCityCode() +
                    "\t" + data[4] + "\t" + data[5] + "\t" + data[6] + "\t" + data[7] +
                    "\t" + data[8] + "\t" + data[9]);
            context.write(value, NullWritable.get());
        }
    }

    public static class CleanElectronicCommerceReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            for (NullWritable value : values) {
                context.write(key, value);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(CleanElectronicCommerceMapper.class);
        job.setReducerClass(CleanElectronicCommerceReducer.class);
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
