package org.nuaa.undefined.BigDataEveryWhere.mr.hero;

import java.io.IOException;
import java.util.ArrayList;

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
 *  Title:HeroPercentage.java
 *  Description:
 *  统计每个英雄在所有英雄中使用所占的百分比，并以降序输出
 *  输入文件：/gamelog_heros/result/HeroInfo/part-r-00000
 *  输出文件：/gamelog_heros/result/HeroPercentage
 *  @author Growarm
 *  @date 上午9:52:59
 *  version 1.0
 */
public class HeroPercentage {

	public static class HeroPercentageMapper extends Mapper<LongWritable, Text, Text, Text>{
		
		private Text outputKey = new Text();
		private Text outputValue = new Text();		
		//Alistar	221	436	50.69%
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			String[] words = value.toString().split("\\s+");//转换成string类型后，以n个空格的地方分割
			outputKey.set(words[0] + "==" + words[2]);
			outputValue.set(words[2]);
			context.write(outputKey, outputValue);
		}
	}
	
	public static class HeroPercentageReducer extends Reducer<Text,Text, Text, NullWritable>{
		
		private Text outputKey = new Text();
		private int sumNum	= 0;
		//记录每个英雄的数据
		private ArrayList<String> lists = new  ArrayList<String>(); 
		//Alistar==436	436	
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			String[] words = key.toString().split("==");
			lists.add(words[0] + "\t" + words[1]);
			for (Text value : values) {
				sumNum += Integer.parseInt(value.toString());
			}
		}
		
		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {
			
			for (String list : lists) {
				
				String[] word = list.toString().split("\\s+");
				int useNum = Integer.parseInt(word[1]);
				String useRate = String.format("%.2f", 1.0 * useNum / sumNum * 100 ) + "%";
				
				outputKey.set(word[0] + "\t" + useRate);
				context.write(outputKey, NullWritable.get());		
			}
		}
	}
	
	public static class HeroPercentagecompareter extends WritableComparator{
		
		public HeroPercentagecompareter(){
			super(Text.class,true);
		}
		//Alistar==436	436	
		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			
			Integer aInt = Integer.parseInt(((Text)a).toString().split("==")[1]);
			Integer bInt = Integer.parseInt(((Text)b).toString().split("==")[1]);
			
			if (bInt.equals(aInt)) {
				
				return 1;
			}else {
				return bInt.compareTo(aInt);
			}
		}
	}
	
	public static void main(String[] args) {
		
		try {
			
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://master:9000");

			Job HeroPercentageJob;
			HeroPercentageJob=Job.getInstance(conf, "HeroPercentage");
			HeroPercentageJob.setJarByClass(HeroPercentage.class);

			HeroPercentageJob.setMapperClass(HeroPercentageMapper.class);
			HeroPercentageJob.setReducerClass(HeroPercentageReducer.class);

			HeroPercentageJob.setMapOutputKeyClass(Text.class);
			HeroPercentageJob.setMapOutputValueClass(Text.class);

			HeroPercentageJob.setOutputKeyClass(Text.class);
			HeroPercentageJob.setOutputValueClass(NullWritable.class);
			
			HeroPercentageJob.setSortComparatorClass(HeroPercentagecompareter.class);
			HeroPercentageJob.setGroupingComparatorClass(HeroPercentagecompareter.class);	

			HeroPercentageJob.setInputFormatClass(TextInputFormat.class);	

			FileInputFormat.addInputPath(HeroPercentageJob, new Path("/gamelog_heros/result/HeroInfo/part-r-00000"));
			Path outputPath = new Path("/gamelog_heros/result/HeroPercentage");

			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(HeroPercentageJob, outputPath);

			System.exit(HeroPercentageJob.waitForCompletion(true)?0:1);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}	
}
