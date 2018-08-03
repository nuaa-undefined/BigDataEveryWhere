package org.nuaa.undefined.BigDataEveryWhere.mr.hero;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 *  Title:TotalWin.java
 *  Description:
 *  
 *  所有英雄总胜率
 *  
 *  @author Growarm
 *  @date 下午7:25:00
 *  version 1.0
 */
public class TotalWin {
	
	public static class TotalWinMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		
		//Fiddlesticks		0	98
		private final static IntWritable one = new IntWritable(1);
		private Text outputKey = new Text();
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			
			String word = value.toString().split("\\s+")[1];
			outputKey.set(word);
			context.write(outputKey, one);
		}
	}
	
	public static class TotalWinReducer extends Reducer<Text, IntWritable, Text,NullWritable> {
		
		private Text result = new Text();
		private int sum = 0;
		private int win = 0;		
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,//Iterable迭代器
				Context context) throws IOException, InterruptedException {
			
			for (IntWritable value : values) {
				sum++;
				if (key.toString().equals("1")) {
					win++;
				}
			}
		}
		
		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {
			
			String winRate = String.format("%.2f", 1.0 * win / sum * 100 ) + "%";
			result.set("所有英雄总胜率:" + '\t' + winRate);
			context.write(result,NullWritable.get());
		}
	}

	public static void main(String[] args) {
	
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://master:9000");

			Job TotalWinJob;
			TotalWinJob=Job.getInstance(conf, "TotalWin");
			TotalWinJob.setJarByClass(TotalWin.class);

			TotalWinJob.setMapperClass(TotalWinMapper.class);
			TotalWinJob.setReducerClass(TotalWinReducer.class);

			TotalWinJob.setMapOutputKeyClass(Text.class);
			TotalWinJob.setMapOutputValueClass(IntWritable.class);

			TotalWinJob.setOutputKeyClass(Text.class);
			TotalWinJob.setOutputValueClass(NullWritable.class);
			
			TotalWinJob.setInputFormatClass(TextInputFormat.class);
			
			FileInputFormat.addInputPath(TotalWinJob, new Path("/gamelog_heros/Info/part-r-00000"));
			Path outputPath = new Path("/gamelog_heros/result/TotalWinJob");

			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(TotalWinJob, outputPath);

			System.exit(TotalWinJob.waitForCompletion(true)?0:1);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
