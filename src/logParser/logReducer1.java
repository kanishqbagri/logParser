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


//		Pattern idPattern = Pattern.compile("^(.*)\\s+(\\[[A-Z]{4,5}\\])\\s+(.*)\\s+(\\[tenantID:[0-9]{2,3}\\])(.*)");
//		Pattern idPattern = Pattern.compile("^(.*)\\s+(\\[[A-Z]{4,5}\\])\\s+(BMC.[A-Z]{2,10}\\s+-)\\s+(\\[Thread=.*\\])\\s+(\\[Class=.*\\])\\s+(\\[tenantID:[0-9]{2,3}\\])(.*)");
		Pattern idPattern = Pattern.compile("^(.*)\\s(.*)(,.*)\\s+(\\[[A-Z]{4,5}\\])\\s+(BMC.[A-Z]{2,10}\\s+)(-)\\s+(\\[Thread=.*\\])\\s+(\\[Class=.*\\])\\s+(\\[tenantID:[0-9]{2,3}\\])(.*)");
		Matcher matcher = idPattern.matcher(message);

		int n=0;
		
		if (matcher.find()) {

			bsmLogPojo bsmLog= new bsmLogPojo();
			bsmLog.setDate(matcher.group(1));
			bsmLog.setTimestamp(matcher.group(2));
			bsmLog.setLogLevel(matcher.group(4));
			bsmLog.setBmcTask(matcher.group(5));
			bsmLog.setThreadID(matcher.group(7));
			bsmLog.setClassName(matcher.group(8));
			bsmLog.setTenantId(matcher.group(9));
			bsmLog.setLogMessage(matcher.group(10));
//			bsmLog.setOrigLog(message);
			
		
			
			JSONObject obj = new JSONObject();
			obj.put("@timestamp", bsmLog.getDate()+"T"+bsmLog.getTimestamp());
			obj.put("log level", bsmLog.getLogLevel());
			obj.put("BMC Task", bsmLog.getBmcTask());
			obj.put("thread ID ", bsmLog.getThreadID());
			obj.put("classname ", bsmLog.getClassName());
			obj.put("tenantId", bsmLog.getTenantId());
			obj.put("log Message", bsmLog.getLogMessage());
//			obj.put("origLog", bsmLog.getOrigLog());

//			//Setting up the index for bulk import of the output Json
			
			JSONObject indexJson= new JSONObject();
			indexJson.put("_index", "bmc_dashboard");
			
			JSONObject parentIndexJson= new JSONObject();
			parentIndexJson.put("index", indexJson);
			
//			indexJson.put("_id", n);
//			n++;
			try (FileWriter file = new FileWriter("/Users/kbagri/Lab/hadoop/hadoop-0.20.2/reduceJSONOutput.json", true)) {
				
				file.write(parentIndexJson.toJSONString() + "\n");
				file.write(obj.toJSONString() + "\n");
				
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}

	}

}
