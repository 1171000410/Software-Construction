package exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import circularorbit.AtomStructure;
import fileinput.AllBufferedReader;
import fileinput.ReadStrategy;

import java.io.File;

import org.junit.jupiter.api.Test;


class TestAtomStructureException {

  // Testing strategy
  // Enter an error file containing various exceptions
  // try catch statements to handle exception

  @Test
  void testAtomStructureException() {
    AtomStructure a = new AtomStructure();
    String fileName = "txt/AtomicStructure_Exception.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    a.readFile(r.readFile(file));

    assertEquals("AtomStructure", a.getSystemName());

    AtomStructure a2 = new AtomStructure();
    String fileName2 = "txt/AtomicStructure_Exception2.txt";
    File file2 = new File(fileName2);
    ReadStrategy r2 = new AllBufferedReader();
    a2.readFile(r2.readFile(file2));
    
  }

}
