package org.nuaa.undefined.BigDataEveryWhere.mr.hero;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *  Title:HeroTopPlayer.java
 *  Description:
 *  需传参，根据设置的英雄，统计指定英雄胜率最高的3个用户,并输出用户id和胜率,（胜率降序，id升序）排序
 *  输入文件：/gamelog_heros/result/OftenUsedInfo/part-r-00000
 *  输出文件：/gamelog_heros/result/HeroTopPlayer
 *  @author Growarm
 *  @date 上午9:15:04
 *  version 1.0
 */
public class HeroTopPlayer {

	private static String hero;//传的参
	
	public static class HeroTopPlayerMapper extends Mapper<LongWritable,Text, Text,NullWritable>{

		private Text outputKey = new Text();
		//1	Alistar	2	100.00%
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
		
			String[] words = value.toString().split("\\s+");
			if (words[1].equals(hero)) {
				
				outputKey.set(words[1] + "==" + words[0] + "==" + words[3]);
				context.write(outputKey,NullWritable.get());
			}
		}		
	}
	
	
	public static class HeroTopPlayerReducer extends Reducer<Text,NullWritable, Text, NullWritable>{
		
		private Text outputKey = new Text();
		private int num	= 0;
		//Fiddlesticks == 98 == 50%		
		@Override
		protected void reduce(Text key, Iterable<NullWritable> values, Context context)
				throws IOException, InterruptedException {
		
			if (num++ < 3) {
				
				String[] words = key.toString().split("==");
				outputKey.set(words[0] + "\t" + words[1] + "\t" + words[2]);
				context.write(outputKey, NullWritable.get());		
			}
		}
	}
	
	public static class HeroTopPlayercompareter extends WritableComparator{
		
		public HeroTopPlayercompareter(){
			super(Text.class,true);
		}
		//Fiddlesticks == 98 == 50%
		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			
			String awords = ((Text)a).toString().split("==")[2];
			String bwords = ((Text)b).toString().split("==")[2];
			
			Integer aInt = Integer.parseInt(((Text)a).toString().split("==")[1]);
			Integer bInt = Integer.parseInt(((Text)b).toString().split("==")[1]);			

			if (bwords.equals(awords)) {
				
				return aInt.compareTo(bInt);
			}else {
				return bwords.compareTo(awords);
			}
		}
	}
	
	public static void main(String[] args) {
		
		try {
			hero = args[0];
			
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://master:9000");

			Job HeroTopPlayerJob;
			HeroTopPlayerJob=Job.getInstance(conf, "HeroTopPlayer");
			HeroTopPlayerJob.setJarByClass(HeroTopPlayer.class);

			HeroTopPlayerJob.setMapperClass(HeroTopPlayerMapper.class);
			HeroTopPlayerJob.setReducerClass(HeroTopPlayerReducer.class);

			HeroTopPlayerJob.setOutputKeyClass(Text.class);
			HeroTopPlayerJob.setOutputValueClass(NullWritable.class);
			
			HeroTopPlayerJob.setSortComparatorClass(HeroTopPlayercompareter.class);
			HeroTopPlayerJob.setGroupingComparatorClass(HeroTopPlayercompareter.class);	

			HeroTopPlayerJob.setInputFormatClass(TextInputFormat.class);	

			FileInputFormat.addInputPath(HeroTopPlayerJob, new Path("/gamelog_heros/result/OftenUsedInfo/part-r-00000"));
			Path outputPath = new Path("/gamelog_heros/result/HeroTopPlayer");

			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(HeroTopPlayerJob, outputPath);

			System.exit(HeroTopPlayerJob.waitForCompletion(true)?0:1);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
