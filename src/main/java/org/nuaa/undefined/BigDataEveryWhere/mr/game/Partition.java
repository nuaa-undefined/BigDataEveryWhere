package org.nuaa.undefined.BigDataEveryWhere.mr.game;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author wyw
 * @description 对天数进行分区
 */
public class Partition {

		public static class PartitionMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
			
			@Override
			protected void map(LongWritable key, Text value, Context context)
					throws IOException, InterruptedException {
				
				context.write(value, NullWritable.get());
			}
		}
		
		public static class PartitionRedecer extends Reducer<Text, NullWritable, Text, NullWritable> {
			
			@Override
			protected void reduce(Text key, Iterable<NullWritable> values,
					Context context) throws IOException, InterruptedException {
				
				for (NullWritable value : values) {
					context.write(key, NullWritable.get());
				}
			}
		}
		
		public static class GetPart extends Partitioner<Text, NullWritable> {
			
			@Override
			public int getPartition(Text key, NullWritable value, int num) {
	                    
				String beginTime = key.toString().split("\\s+")[3];
				String date = beginTime.split("T")[0];
				String[] dates = date.split("-");
				for (int i = 1; i < 8; i++) {
					if(dates[2].equals("0"+i)){
						return (i-1)%num;
					}
				}
				return 0%num;
			}
		}
		
		public static void main(String[] args) throws Exception {

				Configuration conf = new Configuration();
				conf.set("fs.defaultFS","hdfs://master1:9000");
					
				Job partitionJob;
				partitionJob = Job.getInstance(conf, "userPartition");
				partitionJob.setJarByClass(Partition.class);
					
				partitionJob.setMapperClass(PartitionMapper.class);
				partitionJob.setReducerClass(PartitionRedecer.class);
				
				partitionJob.setOutputKeyClass(Text.class);
				partitionJob.setOutputValueClass(NullWritable.class);
				
				partitionJob.setInputFormatClass(TextInputFormat.class);
				
				FileInputFormat.addInputPath(partitionJob, new Path("/game-2017-01-01-2017-01-07.log"));
				Path outputPath = new Path("/gamePartition");
				FileSystem.get(conf).delete(outputPath,true);
				FileOutputFormat.setOutputPath(partitionJob, outputPath);
				
				partitionJob.setNumReduceTasks(7);
				partitionJob.setPartitionerClass(GetPart.class);
				
				System.exit(partitionJob.waitForCompletion(true)?0:1);
	}	
}
		
