package logParser;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper.Context;

import java.math.BigInteger;

public class logReducer1 extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException {

		String str = null;
		while (values.hasNext()) {

//			System.out.println(values.next());
			str = values.next().toString();
			output.collect(key, new Text(str));

		}
		
	}

}
