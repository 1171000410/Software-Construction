/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

	// Testing strategy
	// 给定一个input。从文件中读取poet，调用Graph.poem()后
	// 观察输出与预期是否相等
	// 最后测试toString的正确性

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	// TODO tests

	@Test
	public void testAll() {
		try {
			final GraphPoet nimoy = new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
			final String input = "Test the system.";
			assertEquals("Expected the same poem", "Test of the system.", nimoy.poem(input));
			assertEquals("Expected graph string",
					"this->is:1\n" + 
					"is->a:1\n" + 
					"a->test:1\n" + 
					"test->of:1\n" + 
					"of->the:1\n" + 
					"the->mugar:1\n" + 
					"mugar->omni:1\n" + 
					"omni->theater:1\n" + 
					"theater->sound:1\n" + 
					"sound->system.:1\n",nimoy.toString());
						
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	@Test
	public void testMyFile() {
		try {
			final GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/myTest.txt"));
			final String input = "stole the cat Hillary's villa";
			assertEquals("Expected the same poem", "stole the cat from Hillary's villa", nimoy.poem(input));
			assertEquals("Expected graph string",
					"i->heard:1\n"+
					"heard->trump:1\n"+
					"trump->stole:1\n"+
					"stole->the:1\n"+
					"the->cat:1\n"+
					"cat->from:1\n"+
					"from->hillary's:1\n"+
					"hillary's->villa.:1\n"
					,nimoy.toString());
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

}
