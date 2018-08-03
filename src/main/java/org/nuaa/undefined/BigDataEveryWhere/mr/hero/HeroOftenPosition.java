package org.nuaa.undefined.BigDataEveryWhere.mr.hero;

import java.io.IOException;

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

/**
 *  Title:HeroOftenPosition.java
 *  Description:
 *  找出各个英雄出现最多的分路及其比例
 *  输入文件：/gamelog_heros/result/HeroPosition/part-r-00000
 *  输出文件：/gamelog_heros/result/HeroOftenPosition
 *  @author Growarm
 *  @date 上午12:10:39
 *  version 1.0
 */
public class HeroOftenPosition {
	
	//Alistar	436	102	23.39%	70	16.06%	79	18.12%	140	32.11%	45	10.32%
	public static class HeroOftenPositionMapper extends Mapper<Text, Text, Text,Text> {
		
		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {

			context.write(key,value);
		}
	}
	
	public static class HeroOftenPositionReducer extends Reducer<Text, Text, Text, NullWritable>{
		
		private Text outputKey = new Text();
				
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

				for (Text value : values) {
					
					String[] words = value.toString().split("\\s+");
					int word = 0;
					int i = 1;
					
					for (int j = 1; j < words.length; j += 2) {
						int wordsInt = Integer.parseInt(words[j]);
						if (j == 1) {
							word = wordsInt;
						}
						if (word < wordsInt) {
							word = wordsInt;
							i = j;
						}
					}
					System.out.println(key + "\t" + (i + 1) / 2 +"\t" + words[i] + "\t" + words[i + 1]);
					outputKey.set(key + "\t" + (i + 1) / 2 +"\t" + words[i] + "\t" + words[i + 1]);
				}
				context.write(outputKey, NullWritable.get());		
		}
	}	
	
	public static void main(String[] args) {
		
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://master:9000");

			Job HeroOftenPositionJob;
			HeroOftenPositionJob=Job.getInstance(conf, "HeroOftenPosition");
			HeroOftenPositionJob.setJarByClass(HeroPosition.class);

			HeroOftenPositionJob.setMapperClass(HeroOftenPositionMapper.class);
			HeroOftenPositionJob.setReducerClass(HeroOftenPositionReducer.class);

			HeroOftenPositionJob.setMapOutputKeyClass(Text.class);
			HeroOftenPositionJob.setMapOutputValueClass(Text.class);

			HeroOftenPositionJob.setOutputKeyClass(Text.class);
			HeroOftenPositionJob.setOutputValueClass(NullWritable.class);
			
			HeroOftenPositionJob.setInputFormatClass(KeyValueTextInputFormat.class);	
			
			FileInputFormat.addInputPath(HeroOftenPositionJob, new Path("/gamelog_heros/result/HeroPosition/part-r-00000"));
			Path outputPath = new Path("/gamelog_heros/result/HeroOftenPosition");

			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(HeroOftenPositionJob, outputPath);

			System.exit(HeroOftenPositionJob.waitForCompletion(true)?0:1);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
