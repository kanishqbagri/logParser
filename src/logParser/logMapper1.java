package logParser;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import java.math.BigInteger;

/**
 * Mapper that takes a line from an input file containing values from 5000 to 1 and emits each number with a constant value for key/value pair.
 */
public class logMapper1 extends MapReduceBase 
                        implements Mapper<LongWritable, Text, Text, Text>
{

  // Regular expression to match the IP at the beginning of the line in an
  // Apache access log
	private static final Pattern tenantId = Pattern.compile("^*\\s+(\\[tenantID:[0-9]{2,3}\\])(.*)");

    
  public void map(LongWritable fileOffset, Text lineContents,
      OutputCollector<Text, Text> output, Reporter reporter)
      throws IOException {
    
    // apply the regex to the line of the access log
    Matcher matcher = tenantId.matcher(lineContents.toString());
    
    if(matcher.find())
    {
      // grab the IP
      String tenantID = matcher.group(1);
      String message= matcher.group(2);
      
      // output it with a count of 1
      output.collect(new Text(tenantID), new Text(message));
    }
  }

}
