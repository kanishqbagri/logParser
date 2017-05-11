package logParser;

public class bsmLogPojo {
	public static String getDate() {
		return date;
	}
	public static void setDate(String date) {
		bsmLogPojo.date = date;
	}
	private static String date = null;
	private static String timestamp = null;
	private static String logLevel = null;
	private static String bmcTask = null;
	private static String threadID = null;
	private static String className = null;
//	private static String origLog=null;
//	
//	public static String getOrigLog() {
//		return origLog;
//	}
//	public static void setOrigLog(String origLog) {
//		bsmLogPojo.origLog = origLog;
//	}
	public static String getThreadID() {
		return threadID;
	}
	public static void setThreadID(String threadID) {
		bsmLogPojo.threadID = threadID;
	}
	public static String getClassName() {
		return className;
	}
	public static void setClassName(String className) {
		bsmLogPojo.className = className;
	}
	private static String TenantId = null;
	private static String logMessage = null;
	
	
	public static String getBmcTask() {
		return bmcTask;
	}
	public static void setBmcTask(String bmcTask) {
		bsmLogPojo.bmcTask = bmcTask;
	}
	public static String getTimestamp() {
		return timestamp;
	}
	public static void setTimestamp(String timestamp) {
		bsmLogPojo.timestamp = timestamp;
	}
	public static String getLogLevel() {
		return logLevel;
	}
	public static void setLogLevel(String logLevel) {
		bsmLogPojo.logLevel = logLevel;
	}
	public static String getTenantId() {
		return TenantId;
	}
	public static void setTenantId(String tenantId) {
		TenantId = tenantId;
	}
	public static String getLogMessage() {
		return logMessage;
	}
	public static void setLogMessage(String logMessage) {
		bsmLogPojo.logMessage = logMessage;
	}
	
	
	
	

}
