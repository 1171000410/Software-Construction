package exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import circularorbit.SocialNetworkCircle;
import fileinput.AllBufferedReader;
import fileinput.ReadStrategy;

import java.io.File;

import org.junit.jupiter.api.Test;


class TestSocialNetworkException {

  // Testing strategy
  // Enter an error file containing various exceptions
  // try catch statements to handle exception

  @Test
  void testSocialNetworkException() {
    SocialNetworkCircle s = new SocialNetworkCircle();
    String fileName = "txt/SocialNetWorkCircle_Exception.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    s.readFile(r.readFile(file));

    s.printException();
    assertEquals("SocialNetworkCircle", s.getSystemName());
  }

}
