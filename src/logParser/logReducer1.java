package logParser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper.Context;

import org.json.simple.JSONObject;

import java.math.BigInteger;

public class logReducer1 extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException {

		String str = null;
		while (values.hasNext()) {

			// System.out.println(values.next());
			str = values.next().toString();

			// Split the Message from the values into different components
			splitMessageAndCreateJSON(str);
			output.collect(key, new Text(str));

		}

	}

	public void splitMessageAndCreateJSON(String message) {

		String timestamp = null;
		String logLevel = null;
		String preIDMsg = null;
		String TenantId = null;
		String logMessage = null;

		Pattern idPattern = Pattern.compile("^(.*)\\s+(\\[[A-Z]{4,5}\\])\\s+(.*)\\s+(\\[tenantID:[0-9]{2,3}\\])(.*)");
		Matcher matcher = idPattern.matcher(message);

		if (matcher.find()) {

			timestamp = matcher.group(1);
			logLevel = matcher.group(2);
			preIDMsg = matcher.group(3);
			TenantId = matcher.group(4);
			logMessage = matcher.group(5);

			JSONObject obj = new JSONObject();
			obj.put("timestamp", matcher.group(1));
			obj.put("log level", matcher.group(2));
			obj.put("PreId logs", matcher.group(3));
			obj.put("TenantId", matcher.group(4));
			obj.put("Log Message", matcher.group(5));

			try (FileWriter file = new FileWriter("/Users/kbagri/Lab/hadoop/hadoop-0.20.2/reduceJSONOutput.json", true)) {

				file.write(obj.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}

			// System.out.println("Time stamp: "+ matcher.group(1));
			// System.out.println("Log Level: "+ matcher.group(2) );
			// System.out.println("PreId logs" + matcher.group(3));
			// System.out.println("Tenant Id: " + matcher.group(4));
			// System.out.println("Log Message: " + matcher.group(5));
		}

	}

}
