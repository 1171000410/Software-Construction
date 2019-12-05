package exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import circularorbit.StellarSystem;
import fileinput.AllBufferedReader;
import fileinput.ReadStrategy;

import java.io.File;

import org.junit.jupiter.api.Test;


class TestStellarSystemException {

  // Testing strategy
  // Enter an error file containing various exceptions
  // try catch statements to handle exception

  @Test
  void testStellarSystemException() {
    StellarSystem s = new StellarSystem();
    String fileName = "txt/StellarSystem_Exception.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    s.readFile(r.readFile(file));

    assertEquals("StellarSystem", s.getSystemName());
  }

}
