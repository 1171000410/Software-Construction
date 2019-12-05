package debug;

///*
//
//This program is used for removing all the comments in a program code.
//
//Example 1:
//Input: 
//source = ["/*Test program */", "int main()", "{ ", "  
// variable declaration ", "int a, b, c;", "/* This is a test", "   
//multiline  ", "   comment for ", "   testing */", "a = b + c;", "}"]
//
//The line by line code is visualized as below:
//
///*Test program */
//int main()
//{ 
//  // variable declaration 
//int a, b, c;
///* This is a test
//   multiline  
//   comment for 
//   testing */
//a = b + c;
//}
//
//Output: ["int main()","{ ","  ","int a, b, c;","a = b + c;","}"]
//
//The line by line code is visualized as below:
//
//int main()
//{ 
//
//int a, b, c;
//a = b + c;
//}
//
//Explanation: 
//The string /* denotes a block comment, including line 1 and lines 6-9. 
//The string // denotes line 4 as comments.
//
//Example 2:
//
//Input: 
//source = ["a/*comment", "line", "more_comment*/b"]
//
//Output: ["ab"]
//
//Explanation: 
//The original source string is "a/*comment\nline\nmore_comment*/b", 
//where we have bolded the newline characters.  
//After deletion, the implicit newline characters are deleted, 
//leaving the string "ab", which when delimited by newline characters becomes ["ab"].
//
//
//Note:
//
//The length of source is in the range [1, 100].
//The length of source[i] is in the range [0, 80].
//Every open block comment is eventually closed.
//There are no single-quote, double-quote, or control characters in the source code.
//
//*/

import java.util.ArrayList;
import java.util.List;

class RemoveComments {
  public List<String> removeComments(String[] source) {
    boolean inBlock = false;
    StringBuilder newline = new StringBuilder();
    List<String> ans = new ArrayList<String>();
    for (String line : source) {
      int i = 0;
      char[] chars = line.toCharArray();
      if (!inBlock) { //不在block中
        newline = new StringBuilder();
      }
      while (i < line.length()) {
        //修改1:i+1 < line.length()
        //修改原因:防止数组越界
        if (!inBlock && i + 1 < line.length() && chars[i] == '/' && chars[i + 1] == '*') {
          inBlock = true;
          //修改2:i++
          //修改原因:跳过/*
          i++;
        } else if (inBlock && i + 1 < line.length() && chars[i] == '*' && chars[i + 1] == '/') {
          inBlock = false;
          //修改3: i++;
          //修改原因:跳过*/
          i++;
          //修改4:以下条件语句
          //修改原因:增加对不在block但以//开头的注释
        } else if (!inBlock && i + 1 < line.length() && chars[i] == '/' && chars[i + 1] == '/') {
          break;

        } else if (!inBlock) {
          newline.append(chars[i]);
        }
        i++;
      }
      //修改5:!inBlock
      //修改原因:确定已读到*/ ,不在block才添加
      if (!inBlock && newline.length() > 0) {
        ans.add(new String(newline));
      }
    }
    return ans;
  }
}