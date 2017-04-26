package logParser;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import java.io.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;

import org.apache.hadoop.mapred.*;
//import org.apache.hadoop.mapreduce.*;
public class logRunner {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception
  {
		
		String input = args[0];
		System.out.println("args[0]: " + args[0]);
		System.out.println("args[1]: " + args[1]);
		
		JobConf conf = new JobConf(logRunner.class);
		Job job = new Job(conf, "Factorial");
		
		conf.setNumReduceTasks(2);
		conf.setJobName("Factorial");
		conf.setMapperClass(logMapper1.class);
		
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);
		
		//conf.setPartitionerClass(FactPartitioner.class);
		conf.setReducerClass(logReducer1.class);
		
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		
		JobClient.runJob(conf);
	}

}
