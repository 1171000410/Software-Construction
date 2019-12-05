package P4.twitter;
import static org.junit.Assert.assertTrue;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MySocialNetworkTest {
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T09:00:00Z");
	private static final Instant d4 = Instant.parse("2016-02-17T08:00:00Z");
	private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest #mit so much?",d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #mit", d2);
	private static final Tweet tweet3 = new Tweet(1, "cashkkl", "do you want to study here #cmu",d3);
	private static final Tweet tweet4 = new Tweet(2, "duuymv", "your lab #cmu your life", d4);
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testMyGuessFollowsGraphEmpty() {
		Map<String, Set<String>> followsGraph = SocialNetwork.MyguessFollowsGraph(new ArrayList<>());

		assertTrue("expected empty graph", followsGraph.isEmpty());
	}

	@Test
	public void testMyGuessFollowsGraph() {
		ArrayList<Tweet> test_tweets = new ArrayList<Tweet>();
		
		test_tweets.add(tweet1);
		test_tweets.add(tweet2);
		test_tweets.add(tweet3);
		test_tweets.add(tweet4);

		Map<String, Set<String>> followsGraph2 = SocialNetwork.MyguessFollowsGraph(test_tweets);
		
		assertTrue("expected follow", followsGraph2.get("alyssa").contains("bbitdiddle"));
		assertTrue("expected follow", followsGraph2.get("bbitdiddle").contains("alyssa"));
		assertTrue("expected follow", followsGraph2.get("cashkkl").contains("duuymv"));
		assertTrue("expected follow", followsGraph2.get("duuymv").contains("cashkkl"));
	}
}
