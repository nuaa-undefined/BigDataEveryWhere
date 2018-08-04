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
 * @author wyw
 * @Description 计算每一天的次均登录时长（折线图）
 */
public class AverageLoginTime {
	
      public static class  AverageLoginTimeMapper extends Mapper<Text, Text, Text, Text> {
		
		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			
			context.write(key, value);
		}
	  }
      
      public static class AverageLoginTimeRedecer extends Reducer<Text, Text, Text, NullWritable>{
  		
  		private Text outputKey = new Text();
  		private int[] totalPlay = {0,0,0,0,0,0,0};
   		private long[] totalTime = {0,0,0,0,0,0,0};
  		
  		@Override
  		protected void reduce(Text key, Iterable<Text> values, Context context)
  				throws IOException, InterruptedException {
  			
  			for (Text value : values) {
  				String beginTime = value.toString().split("\\s+")[2];
  				String beginDay = beginTime.split("T")[0];
  				String beginDate = beginDay.split("-")[2];
  				
  				for (int i = 1; i < 8; i++) {
					if (beginDate.equals("0"+i)) {
						totalPlay[i-1]++;
						totalTime[i-1] += Long.parseLong(value.toString().split("\\s+")[4]);
					}
				}
  		   }
  		}
  		
        @Override
        protected void cleanup(Context context)
          		throws IOException, InterruptedException {
          	
          	  outputKey.set("第一天的次均登陆时长：" + totalTime[0]/totalPlay[0] + "\n" +"第二天的次均登陆时长：" + totalTime[1]/totalPlay[1] + "\n" +"第三天的次均登陆时长：" + totalTime[2]/totalPlay[2] + "\n" +"第四天的次均登陆时长：" + totalTime[3]/totalPlay[3] + "\n" +"第五天的次均登陆时长：" + totalTime[4]/totalPlay[4] + "\n" +"第六天的次均登陆时长：" + totalTime[5]/totalPlay[5] + "\n" +"第七天的次均登陆时长：" + totalTime[6]/totalPlay[6]);
              context.write(outputKey, NullWritable.get());
          }		
  	}
    
    public static void main(String[] args) throws Exception {
    	Configuration conf = new Configuration();
		conf.set("fs.defaultFS","hdfs://master1:9000");
			
		Job averageLoginTimeJob;
		averageLoginTimeJob = Job.getInstance(conf, "AverageLoginTime");
		averageLoginTimeJob.setJarByClass(AverageLoginTime.class);
			
		averageLoginTimeJob.setMapperClass(AverageLoginTimeMapper.class);
		averageLoginTimeJob.setReducerClass(AverageLoginTimeRedecer.class);
		
		averageLoginTimeJob.setMapOutputKeyClass(Text.class);
		averageLoginTimeJob.setMapOutputValueClass(Text.class);
		
		averageLoginTimeJob.setOutputKeyClass(Text.class);
		averageLoginTimeJob.setOutputValueClass(NullWritable.class);
		
		averageLoginTimeJob.setInputFormatClass(KeyValueTextInputFormat.class);
		
		FileInputFormat.addInputPath(averageLoginTimeJob, new Path("/game-2017-01-01-2017-01-07.log"));
		Path outputPath = new Path("/gameAverageLoginTime");
		FileSystem.get(conf).delete(outputPath,true);
		FileOutputFormat.setOutputPath(averageLoginTimeJob, outputPath);
		
		System.exit(averageLoginTimeJob.waitForCompletion(true)?0:1);
	}

    

}
