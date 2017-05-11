# logParser
Log Parser 

Description:
This tool is customized to parse and analyze data for the BMC logs. The steps below provide the details on setting up the various parts of the tool to insert the parsed data for further analysis.

Setting up and Running the Log Analyzer:

1.	The attached jar file has an implementation of the MapReduce algorithm that parses and generates 2 sets of data:
a.	A Map Reduced output in the form of Customer Id : Log Message
b.	A JSON doc with each log entry parsed and printed in the output file
2.	Use the sample attached log file and run the following command
bash> bin/Hadoop logAnalyzer.jar logParser.logRunner sampleBsm.log ./output

3.	Elastic Search Installation:
Download and Install elastic search with the default options
https://www.elastic.co/downloads/elasticsearch 
Start Elastic Search with the following command:
bash>bin/elasticsearch

4.	Kibana Installation:
Download and install kibana with default options
https://www.elastic.co/downloads/kibana
Start kibana with the following command:
bash>bin/kibana

5.	Populate elastic Search:
a.	Create ES mapping:
PUT dashboard
{
	"mappings": {
		"type": {
			"properties": {
				"classname": {
					"type": "text"
				},
				"task": {
					"type": "text"
				},
				"log level": {
					"type": "text"
				},
				"Id": {
					"type": "keyword"
				},
				"thread ID": {
					"type": "keyword"
				},
				"log Message": {
					"type": "text"
				},
				"@timestamp": {
					"type": "date",
					"format": "yyyy-MM-dd'T'HH:mm:ss"
				}
			}
		}
	}
}


b.	Use the generated json file to insert data into ES
curl -XPOST 'localhost:9200/dashboard/container/_bulk' --data-binary @sampleFile.json

6.	Open the Browser and open the kibana homepage: 
www.localhost:5601 
a.	Define the default index for the dashboard as: dashboard
(The one we created in ES above)
b.	Browse to the Discover tab and it should show all the log entries distributed by the time stamp.
c.	This data can further be used to analyze and create dashboards


Source code: https://github.com/kanishqbagri/logParser 

