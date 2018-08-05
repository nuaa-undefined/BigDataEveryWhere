package org.nuaa.undefined.BigDataEveryWhere.mr.game;

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
 * @author mll
 * Description: 计算从第二天开始的留存率
 * （Mapper端以KeyValue形式输入，不做任何操作）；Reducer端对用户每一天是否登录做标记，对老用户以及总用户做判断，计算出留存率）
 *
 */
public class RetentionRate {
	public static class RetentionMapper extends Mapper<Text, Text, Text, Text> {

		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			context.write(key, value);   			
		}	
	}

	//static class???
	public static class RetentionReducer extends Reducer<Text,Text, Text, NullWritable> {

		private Text outputkey = new Text();
		private float[] oldUser={0,0,0,0,0,0,0};
		private float[] totalUser={0,0,0,0,0,0,0};

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			int[] sign={0,0,0,0,0,0,0};
			
			for (Text value : values) {				        
				String word = value.toString().split("\\s+")[2];
				String data = word.split("T")[0];
				String day =data.split("-")[2];
				
				for(int i=1;i<8;i++)	{
	
					if(day.equals("0"+i)){
						sign[i-1]=1;
					}
				}				
			}
			for (int i = 0; i <7 ; i++) {
				if(sign[i]==1)
				{
					oldUser[i]++;		
				}
				else{
					break;
				}
			}
			for (int i = 0; i < 7; i++) {
				if(sign[i]==1) {
					
					for(int j=i;j<7;j++)
					{
						totalUser[j]++;
					}					
					break;
				}				
			}		
		}

		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {

			String data2Rate= String.format("%.2f",oldUser[1]/totalUser[1]*100); // "%.2f" 保留两位小数
			String data3Rate= String.format("%.2f",oldUser[2]/totalUser[2]*100);
			String data4Rate= String.format("%.2f",oldUser[3]/totalUser[3]*100); // "%.2f" 保留两位小数
			String data5Rate= String.format("%.2f",oldUser[4]/totalUser[4]*100);
			String data6Rate= String.format("%.2f",oldUser[5]/totalUser[5]*100);
			String data7Rate= String.format("%.2f",oldUser[6]/totalUser[6]*100);
			
			outputkey.set("次留存率：        "+data2Rate+"%\n"+"三日留存率：    "+data3Rate+"%\n"+"四日留存率：    "+data4Rate+"%\n"+"五日留存率：    "+data5Rate+"%\n"+"六日留存率：    "+data6Rate+"%\n"+"七日留存率：    "+data7Rate+"%\t");
			context.write(outputkey, NullWritable.get());
		}
	}

	public static void main(String[] args) throws Exception {

		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://master1:9000");

		Job retentionRateJob;
		retentionRateJob=Job.getInstance(conf,"RetentionRate");
		retentionRateJob.setJarByClass(RetentionRate.class);

		retentionRateJob.setMapperClass(RetentionMapper.class);
		retentionRateJob.setReducerClass(RetentionReducer.class);

		retentionRateJob.setMapOutputKeyClass(Text.class);
		retentionRateJob.setMapOutputValueClass(Text.class);

		retentionRateJob.setOutputKeyClass(Text.class);
		retentionRateJob.setOutputValueClass(NullWritable.class);

		retentionRateJob.setInputFormatClass(KeyValueTextInputFormat.class);

		FileInputFormat.addInputPath(retentionRateJob, new Path("/game1.log"));
		Path outputpath = new Path("/RetentionRate");

		FileSystem.get(conf).delete(outputpath,true);
		FileOutputFormat.setOutputPath(retentionRateJob, outputpath);

		System.exit(retentionRateJob.waitForCompletion(true)?0:1);	
	}
}
