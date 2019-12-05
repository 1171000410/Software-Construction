/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

	/**
	 * Find tweets written by a particular user.
	 * 
	 * @param tweets   a list of tweets with distinct ids, not modified by this
	 *                 method.
	 * @param username Twitter username, required to be a valid Twitter username as
	 *                 defined by Tweet.getAuthor()'s spec.
	 * @return all and only the tweets in the list whose author is username, in the
	 *         same order as in the input list.
	 */
	public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
		List<Tweet> user_tweets = new ArrayList<Tweet>(); // 顺序容器
		int total = tweets.size();
		for (int i = 0; i < total; i++) {
			if (tweets.get(i).getAuthor().equals(username)) { // 遍历tweets的作者
				user_tweets.add(tweets.get(i));
			}
		}
		return user_tweets;
	}

	/**
	 * Find tweets that were sent during a particular timespan.
	 * 
	 * @param tweets   a list of tweets with distinct ids, not modified by this
	 *                 method.
	 * @param timespan timespan
	 * @return all and only the tweets in the list that were sent during the
	 *         timespan, in the same order as in the input list.
	 */
	public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
		List<Tweet> time_tweets = new ArrayList<Tweet>(); // 顺序容器
		int total = tweets.size();
		Instant start = timespan.getStart();
		Instant end = timespan.getEnd();

		for (int i = 0; i < total; i++) {
			if (tweets.get(i).getTimestamp().isAfter(start) && tweets.get(i).getTimestamp().isBefore(end)) { // 遍历tweets时间
				time_tweets.add(tweets.get(i));
			}
		}
		return time_tweets;
	}

	/**
	 * Find tweets that contain certain words.
	 * 
	 * @param tweets a list of tweets with distinct ids, not modified by this
	 *               method.
	 * @param words  a list of words to search for in the tweets. A word is a
	 *               nonempty sequence of nonspace characters.
	 * @return all and only the tweets in the list such that the tweet text (when
	 *         represented as a sequence of nonempty words bounded by space
	 *         characters and the ends of the string) includes *at least one* of the
	 *         words found in the words list. Word comparison is not case-sensitive,
	 *         so "Obama" is the same as "obama". The returned tweets are in the
	 *         same order as in the input list.
	 */
	public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
		List<Tweet> word_tweets = new ArrayList<Tweet>(); // 顺序容器
		int total = tweets.size();
		for (int i = 0; i < total; i++) {
			String text = tweets.get(i).getText();
			for(int j = 0; j< words.size(); j++) {
				Pattern pattern =Pattern.compile(words.get(j));  //word的正则表达式
				Matcher mentioned_word = pattern.matcher(text);
				if(mentioned_word.find()) {     //由word找到tweet
					word_tweets.add(tweets.get(i));
				}
			}
		}
		return word_tweets;
	}
}
