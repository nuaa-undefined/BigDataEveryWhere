package org.nuaa.undefined.BigDataEveryWhere.mr.hero;

import java.io.IOException;

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
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *  Title:OftenUsedInfo.java
 *  Description:
 *  统计用户每个英雄的使用次数，及胜率
 *  输入文件：/gamelog_heros/Info/part-r-00000
 *  输出文件：/gamelog_heros/result/OftenUsedInfo
 *  输出：用户	英雄名	使用次数	胜率	分路1		分路1出现率		分路1胜率	分路2 ......
 *  把用户id与英雄连接绑定，做key，统计次数
 *  @author Growarm
 *  @date 下午8:48:15
 *  version 1.0
 */
public class OftenUsedInfo {
	
	public static class OftenUsedInfoMapper extends Mapper<LongWritable,Text, Text, Text>{

		private Text outputKey = new Text();
		private Text outputvalue = new Text();
		//Fiddlesticks		0	98	4
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			String[] words = value.toString().split("\\s+");
			outputKey.set(words[2] + "\t" + words[0]);
			outputvalue.set(words[1]+ "\t" + words[3]);
			context.write(outputKey,outputvalue);
		}		
	}
	
	public static class OftenUsedInfoReducer extends Reducer<Text,Text, Text,NullWritable>{
		
		private Text outputKey =  new Text();
		//98  Fiddlesticks	0	4		
		
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			int sum = 0;
			int winNum = 0;
			int position1 = 0;
			int position2 = 0;
			int position3 = 0;
			int position4 = 0;
			int position5 = 0;
			int positionWin1 = 0;
			int positionWin2 = 0;
			int positionWin3 = 0;
			int positionWin4 = 0;
			int positionWin5 = 0;
			
			for (Text value : values) {
				sum ++;
				String[] words = value.toString().split("\\s+");
				if (words[0].equals("1")) {
					winNum++;
				}
				if (words[1].equals("1")) {
					
					position1++;
					if (words[0].equals("1")) {
						positionWin1++;
					}
				}else if (words[1].equals("2")) {
					
					position2++;
					if (words[0].equals("1")) {
						positionWin2++;
					}
				}else if (words[1].equals("3")) {
					
					position3++;
					if (words[0].equals("1")) {
						positionWin3++;
					}
				}else if (words[1].equals("4")) {
					
					position4++;
					if (words[0].equals("1")) {
						positionWin4++;
					}
				}else if (words[1].equals("5")) {
					
					position5++;
					if (words[0].equals("1")) {
						positionWin5++;
					}
				}
			}
			String[] words = key.toString().split("\\s+");
			String word = String.valueOf(sum);

			String winRate = String.format("%.2f", 1.0 * winNum / sum * 100 ) + "%";
			
			String positionRate1 = String.format("%.2f", 1.0 * position1 / sum * 100 ) + "%";
			String positionRate2 = String.format("%.2f", 1.0 * position2 / sum * 100 ) + "%";
			String positionRate3 = String.format("%.2f", 1.0 * position3 / sum * 100 ) + "%";
			String positionRate4 = String.format("%.2f", 1.0 * position4 / sum * 100 ) + "%";
			String positionRate5 = String.format("%.2f", 1.0 * position5 / sum * 100 ) + "%";
			
			String positionWinRate1 = "0.00" + "%";
			String positionWinRate2 = "0.00" + "%";
			String positionWinRate3 = "0.00" + "%";
			String positionWinRate4 = "0.00" + "%";
			String positionWinRate5 = "0.00" + "%";
			if (position1 != 0) {
				positionWinRate1 = String.format("%.2f", 1.0 * positionWin1 / position1 * 100 ) + "%";
			}
			if (position2 != 0) {		
				positionWinRate2 = String.format("%.2f", 1.0 * positionWin2 / position2 * 100 ) + "%";
			}			
			if (position3 != 0) {
				positionWinRate3 = String.format("%.2f", 1.0 * positionWin3 / position3 * 100 ) + "%";
			}			
			if (position4 != 0) {
				positionWinRate4 = String.format("%.2f", 1.0 * positionWin4 / position4 * 100 ) + "%";
			}
			if (position5 != 0) {
				positionWinRate5 = String.format("%.2f", 1.0 * positionWin5 / position5 * 100 ) + "%";
			}

			outputKey.set(words[0] + "\t" + words[1] + "\t" + word  + "\t" + winRate + "\t" + "1"+ "\t" + positionRate1 + "\t" + positionWinRate1 +"\t" + 
			"2" + "\t" + positionRate2 + "\t" + positionWinRate2 +"\t" + "3" + "\t" + positionRate3  + "\t" + positionWinRate3 +"\t" + 
					"4" + "\t" + positionRate4  + "\t" + positionWinRate4 +"\t" + "5" + "\t" + positionRate5 + "\t" + positionWinRate5 );
			context.write(outputKey, NullWritable.get());		
		}
	}	
	
	public static void main(String[] args) {
		
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://master:9000");

			Job OftenUsedInfoJob;
			OftenUsedInfoJob=Job.getInstance(conf, "OftenUsedInfo");
			OftenUsedInfoJob.setJarByClass(UserInfo.class);

			OftenUsedInfoJob.setMapperClass(OftenUsedInfoMapper.class);
			OftenUsedInfoJob.setReducerClass(OftenUsedInfoReducer.class);
			
			OftenUsedInfoJob.setMapOutputKeyClass(Text.class);
			OftenUsedInfoJob.setMapOutputValueClass(Text.class);

			OftenUsedInfoJob.setOutputKeyClass(Text.class);
			OftenUsedInfoJob.setOutputValueClass(NullWritable.class);

			OftenUsedInfoJob.setInputFormatClass(TextInputFormat.class);	

			FileInputFormat.addInputPath(OftenUsedInfoJob, new Path("/gamelog_heros/Info/part-r-00000"));
			Path outputPath = new Path("/gamelog_heros/result/OftenUsedInfo");

			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(OftenUsedInfoJob, outputPath);

			System.exit(OftenUsedInfoJob.waitForCompletion(true)?0:1);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
}
