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
 *  Title:OftenUsed.java
 *  Description:
 *  需传参，根据设置的id，统计指定用户最常用的3个英雄,并输出使用次数和胜率
 *  在map端过滤id不符的数据，同时将id与英雄连接，排序取前3个输出
 *  输入文件：/gamelog_heros/result/OftenUsedInfo/part-r-00000
 *  输出文件：/gamelog_heros/result/OftenUsed
 *  @author Growarm
 *  @date 下午8:09:58
 *  version 1.0
 */
public class OftenUsed {
	
	private static int userId;//传的参
	
	public static class OftenUsedMapper extends Mapper<LongWritable,Text, Text,NullWritable>{

		private Text outputKey = new Text();
		//1	Alistar	2	100.00%	1	0.00%	2	0.00%	3	100.00%	4	0.00%	5	0.00%
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
		
			String[] words = value.toString().split("\\s+");
			if (Integer.parseInt(words[0]) == userId) {
				
				outputKey.set(words[0] + "==" + words[1] + "==" + words[2]  + "==" + words[3]);
				context.write(outputKey,NullWritable.get());
			}
		}		
	}
	
	public static class OftenUsedReducer extends Reducer<Text,NullWritable, Text, NullWritable>{
		
		private Text outputKey = new Text();
		private int num	= 0;
		
		@Override
		protected void reduce(Text key, Iterable<NullWritable> values, Context context)
				throws IOException, InterruptedException {
		
			if (num++ < 3) {
				
				String[] words = key.toString().split("==");
				outputKey.set(words[0] + "\t" + words[1] + "\t" + words[2]  + "\t" + words[3]);
				context.write(outputKey, NullWritable.get());		
			}
		}
	}
	
	public static class OftenUsedcompareter extends WritableComparator{
		
		public OftenUsedcompareter(){
			super(Text.class,true);
		}
		//98 == Fiddlesticks == 5
		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			
			String awords = ((Text)a).toString().split("==")[2];
			String bwords = ((Text)b).toString().split("==")[2];
			
			Integer aNum = Integer.parseInt(awords);
			Integer bNum = Integer.parseInt(bwords);
			
			if (bNum.equals(aNum)) {
				return 1;
			}else {
				return bNum.compareTo(aNum);
			}
		}
	}
	
	public static void main(String[] args) {
		
		try {
			userId = Integer.parseInt(args[0]);
			
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://master:9000");

			Job OftenUsedJob;
			OftenUsedJob=Job.getInstance(conf, "OftenUsed");
			OftenUsedJob.setJarByClass(OftenUsed.class);

			OftenUsedJob.setMapperClass(OftenUsedMapper.class);
			OftenUsedJob.setReducerClass(OftenUsedReducer.class);

			OftenUsedJob.setOutputKeyClass(Text.class);
			OftenUsedJob.setOutputValueClass(NullWritable.class);
			
			OftenUsedJob.setSortComparatorClass(OftenUsedcompareter.class);
			OftenUsedJob.setGroupingComparatorClass(OftenUsedcompareter.class);	

			OftenUsedJob.setInputFormatClass(TextInputFormat.class);	

			FileInputFormat.addInputPath(OftenUsedJob, new Path("/gamelog_heros/result/OftenUsedInfo/part-r-00000"));
			Path outputPath = new Path("/gamelog_heros/result/OftenUsed");

			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(OftenUsedJob, outputPath);

			System.exit(OftenUsedJob.waitForCompletion(true)?0:1);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
