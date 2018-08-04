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
 * Description: 计算从第二天开始每天的新用户
 * （Mapper以KeyValue形式输入，不做任何操作；
 *   在Reducer端，用数组对该用户每一天是否登录做标记，该用户第一次登录的日期时该用户是新用户，此日期的newUser++）
 *
 */
public class NewUser {
	
	public static class NewUserMapper extends Mapper<Text, Text, Text, Text> {

		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			context.write(key, value);   			
		}	
	}

	public static class NewUserReducer extends Reducer<Text,Text, Text, NullWritable> {

		private Text outputkey = new Text();
		private int[] newUser={0,0,0,0,0,0,0};

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
					newUser[i]++;
					break;
				}			
			}
		}
			
		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {			
			outputkey.set("第二天的新用户：    "+newUser[1]+"\n"+"第三天的新用户：    "+newUser[2]+"\n"+"第四天的新用户：    "+newUser[3]+"\n"+"第五天的新用户：    "+newUser[4]+"\n"+"第六天的新用户：    "+newUser[5]+"\n"+"第七日的新用户：    "+newUser[6]+"\t");
			context.write(outputkey, NullWritable.get());
		}
	}

	public static void main(String[] args) throws Exception {

		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://master1:9000");

		Job newUserJob;
		newUserJob=Job.getInstance(conf,"NewUser");
		newUserJob.setJarByClass(NewUser.class);

		newUserJob.setMapperClass(NewUserMapper.class);
		newUserJob.setReducerClass(NewUserReducer.class);

		newUserJob.setMapOutputKeyClass(Text.class);
		newUserJob.setMapOutputValueClass(Text.class);

		newUserJob.setOutputKeyClass(Text.class);
		newUserJob.setOutputValueClass(NullWritable.class);

		newUserJob.setInputFormatClass(KeyValueTextInputFormat.class);

		FileInputFormat.addInputPath(newUserJob, new Path("/game1.log"));
		Path outputpath = new Path("/NewUser");

		FileSystem.get(conf).delete(outputpath,true);
		FileOutputFormat.setOutputPath(newUserJob, outputpath);

		System.exit(newUserJob.waitForCompletion(true)?0:1);	
	}

}
