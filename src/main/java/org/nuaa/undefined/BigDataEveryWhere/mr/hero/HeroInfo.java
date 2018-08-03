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
 *  Title:HeroInfojava
 *  Description:
 *  计算各个英雄的使用次数及总胜率
 *  输入文件：/gamelog_heros/Info/part-r-00000
 *  输出文件：/gamelog_heros/result/HeroWinRate
 *  //输出：英雄	获胜次数	使用总次数	胜率
 *  @author Growarm
 *  @date 下午6:47:26
 *  version 1.0
 */
public class HeroInfo {
	
	public static class HeroInfoMapper extends Mapper<Text, Text, Text,Text> {
		
		private Text outputValue = new Text();
		
		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {

			String word = value.toString().split("\\s+")[0];
			outputValue.set(word);
			context.write(key,outputValue);
		}
	}
	//Fiddlesticks	0	98	1
	public static class HeroInfoReducer extends Reducer<Text, Text, Text, NullWritable>{
	
		private Text outputKey = new Text();
				
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			
				int sumNum = 0;
				int winNum = 0;
				
				for (Text value : values) {
					sumNum++;
					if (value.toString().equals("1")) {
						winNum++;
					}
				}
				
				String winRate = String.format("%.2f", 1.0 * winNum / sumNum * 100 ) + "%";
				outputKey.set(key + "\t" + winNum +"\t" + sumNum + "\t" + winRate);
				context.write(outputKey, NullWritable.get());		
		}
	}	

	public static void main(String[] args) {
	
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://master:9000");

			Job HeroInfoJob;
			HeroInfoJob=Job.getInstance(conf, "HeroInfo");
			HeroInfoJob.setJarByClass(HeroInfo.class);

			HeroInfoJob.setMapperClass(HeroInfoMapper.class);
			HeroInfoJob.setReducerClass(HeroInfoReducer.class);

			HeroInfoJob.setMapOutputKeyClass(Text.class);
			HeroInfoJob.setMapOutputValueClass(Text.class);

			HeroInfoJob.setOutputKeyClass(Text.class);
			HeroInfoJob.setOutputValueClass(NullWritable.class);
			
			HeroInfoJob.setInputFormatClass(KeyValueTextInputFormat.class);	
			
			FileInputFormat.addInputPath(HeroInfoJob, new Path("/gamelog_heros/Info/part-r-00000"));
			Path outputPath = new Path("/gamelog_heros/result/HeroInfo");

			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(HeroInfoJob, outputPath);

			System.exit(HeroInfoJob.waitForCompletion(true)?0:1);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
