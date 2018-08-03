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
 *  Title:HeroPosition.java
 *  Description:
 *  统计各个英雄在不同分路的次数及比例
 *  输入文件：/gamelog_heros/Info/part-r-00000
 *  输出文件：/gamelog_heros/result/HeroPosition
 *  输出：英雄	总次数	位置1次数	位置1比例	位置2次数......		
 *  @author Growarm
 *  @date 下午11:29:44
 *  version 1.0
 */
public class HeroPosition {
	
	//Fiddlesticks	0	98	1
	public static class HeroPositionMapper extends Mapper<Text, Text, Text,Text> {
		
		private Text outputValue = new Text();
		
		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {

			String word = value.toString().split("\\s+")[2];
			outputValue.set(word);
			context.write(key,outputValue);
		}
	}

	public static class HeroPositionReducer extends Reducer<Text, Text, Text, NullWritable>{
		//Fiddlesticks	1		
		private Text outputKey = new Text();
				
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			
				int sum = 0;
				int position1 = 0;
				int position2 = 0;
				int position3 = 0;
				int position4 = 0;
				int position5 = 0;
				
				for (Text value : values) {
					sum++;
					if (value.toString().equals("1")) {
						position1++;
					}else if (value.toString().equals("2")) {
						position2++;
					}else if (value.toString().equals("3")) {
						position3++;
					}else if (value.toString().equals("4")) {
						position4++;
					}else if (value.toString().equals("5")) {
						position5++;
					}
				}
				
				String positionRate1 = String.format("%.2f", 1.0 * position1 / sum * 100 ) + "%";
				String positionRate2 = String.format("%.2f", 1.0 * position2 / sum * 100 ) + "%";
				String positionRate3 = String.format("%.2f", 1.0 * position3 / sum * 100 ) + "%";
				String positionRate4 = String.format("%.2f", 1.0 * position4 / sum * 100 ) + "%";
				String positionRate5 = String.format("%.2f", 1.0 * position5 / sum * 100 ) + "%";
				outputKey.set(key + "\t" + sum +"\t" + position1 + "\t" + positionRate1 +"\t" + position2 + "\t" + positionRate2
						 +"\t" + position3 + "\t" + positionRate3 +"\t" + position4 + "\t" + positionRate4 +"\t" + position5 + "\t" + positionRate5);
				context.write(outputKey, NullWritable.get());		
		}
	}
	
	public static void main(String[] args) {
		
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://master:9000");

			Job HeroPositionJob;
			HeroPositionJob=Job.getInstance(conf, "HeroPosition");
			HeroPositionJob.setJarByClass(HeroPosition.class);

			HeroPositionJob.setMapperClass(HeroPositionMapper.class);
			HeroPositionJob.setReducerClass(HeroPositionReducer.class);

			HeroPositionJob.setMapOutputKeyClass(Text.class);
			HeroPositionJob.setMapOutputValueClass(Text.class);

			HeroPositionJob.setOutputKeyClass(Text.class);
			HeroPositionJob.setOutputValueClass(NullWritable.class);
			
			HeroPositionJob.setInputFormatClass(KeyValueTextInputFormat.class);	
			
			FileInputFormat.addInputPath(HeroPositionJob, new Path("/gamelog_heros/Info/part-r-00000"));
			Path outputPath = new Path("/gamelog_heros/result/HeroPosition");

			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(HeroPositionJob, outputPath);

			System.exit(HeroPositionJob.waitForCompletion(true)?0:1);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
