package applications;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {
  @Override
  public String format(LogRecord log) {
    // TODO Auto-generated method stub
    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss S");

    return log.getLevel() + ": " + format.format(log.getMillis()) + " " 
      + log.getSourceClassName() + " "
        + log.getSourceMethodName() + " " + log.getMessage() + "\n";
  }
}