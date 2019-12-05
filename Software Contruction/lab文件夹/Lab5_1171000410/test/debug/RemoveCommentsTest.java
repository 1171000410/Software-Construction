package debug;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class RemoveCommentsTest {

  @Test
  void testRemoveComments() {
    RemoveComments r = new RemoveComments();
    String[] source = { "/*Test program */", "int main()", 
        "{ ", "  // variable declaration ", "int a, b, c;",
        "/* This is a test", "   multiline  ", "   comment for ", "  " 
        + " testing */", "a = b + c;", "}" };
    String[] output = { "int main()", "{ ", "  ", "int a, b, c;", "a = b + c;", "}" };

    assertEquals(Arrays.toString(output), r.removeComments(source).toString());

    source = new String[] { "a/*comment", "line", "more_comment*/b" };
    output = new String[] { "ab" };

    assertEquals(Arrays.toString(output), r.removeComments(source).toString());
  }

}
