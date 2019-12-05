/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

//import java.io.StreamCorruptedException;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     *         获取推文所涵盖的时间段，即求最大区间
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        int total = tweets.size();
        Instant min = tweets.get(0).getTimestamp();
        Instant max = tweets.get(0).getTimestamp();
        for(int i = 0; i<total ;i++) {
        	Instant now = tweets.get(i).getTimestamp();
        	if(min.isAfter(now)) {
        		min = now;
        	}
        	if(max.isBefore(now)){
        		max = now;
        	}
        }
        Timespan timespan = new Timespan(min, max);
        return timespan;
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     *         获取推文列表中提到的用户名
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> users = new HashSet<String>();
        int total = tweets.size();
        Pattern pattern =Pattern.compile("(@[A-Za-z0-9_-]+)");   //构造正则表达式
        for ( int i = 0; i< total ;i++) {
        	String text = tweets.get(i).getText();
        	Matcher mentioned_users = pattern.matcher(text);  //匹配正则表达式
        	while(mentioned_users.find()) {
        		String match_user = new String(mentioned_users.group().toString().toLowerCase());
        		users.add(match_user);
        	}
        }
        return users;
    }

}
