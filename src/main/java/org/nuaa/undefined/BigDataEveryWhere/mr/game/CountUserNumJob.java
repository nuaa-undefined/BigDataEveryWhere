package org.nuaa.undefined.BigDataEveryWhere.mr.game;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.CleanElectronicCommerceJob;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/4 11:44
 */
public class CountUserNumJob {
    public static String inputPathString = "project/game-2017-01-01-2017-01-07.log";
    public static String outputPathString = "project/game-count";
    public static String jobName = "game-user-count";
    public static class Map extends Mapper<Text, Text, Text, NullWritable> {

        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }

    public static class Reduce extends Reducer<Text, NullWritable, Text, NullWritable> {
        private int count = 0;
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            count++;
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            System.out.println(count);
            context.write(new Text(String.valueOf(count)), NullWritable.get());
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        FileInputFormat.addInputPath(job, new Path(inputPathString));
        Path outputPath = new Path(outputPathString);
        FileSystem.get(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
