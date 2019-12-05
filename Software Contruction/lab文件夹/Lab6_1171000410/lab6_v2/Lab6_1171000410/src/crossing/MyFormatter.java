package crossing;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {
  @Override
  public String format(LogRecord log) {
    // TODO Auto-generated method stub
    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss S");

    return format.format(log.getMillis()) + " ( " 
    	+ log.getMessage() + " )\n";
  }
}