import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class TweetManager {

    public static ArrayList<String> getTweets(String topic) throws Exception {

        Twitter twitter = new TwitterFactory().getInstance();
        ArrayList<String> tweetList = new ArrayList<String>();
        try {
        	
        	Connection conn = null; // connection object
            Statement stmt = null; // statement object
            ResultSet rs = null; // result set object
            conn = MySQLConnection.getConnection();
            stmt = (Statement) conn.createStatement();
            
            
            Query query = new Query(topic);
            query.setCount(ITwitterConstants.TWEET_RESULT_SIZE);
            QueryResult result;
            
            do {
                result = twitter.search(query);
                
                List<Status> tweets = result.getTweets();
                for(int i=0;i<tweets.size();i++){
                	System.out.println("User Name:::::::::::::::"+tweets.get(i).getUser().getName());
                	System.out.println("Tweet"+ i +" from User ::::::::"+tweets.get(i).getUser().getScreenName());                	
                	System.out.println("Tweet"+ i +"  ::::::::::::::::::"+tweets.get(i).getText());                	
                	System.out.println("Tweet"+ i +" from location:::::"+tweets.get(i).getUser().getLocation());
                	if(tweets.get(i).getGeoLocation()!=null)
                		System.out.println("Latitude:::::::::"+tweets.get(i).getGeoLocation().getLatitude());
                	if(tweets.get(i).getGeoLocation()!=null)
                    	System.out.println("Latitude:::::::::"+tweets.get(i).getGeoLocation().getLongitude());
                	String s="INSERT INTO TwitterMaster(user_id,name,tweet_id,tweet_text,tweet_time, country) VALUES(?,?,?,?,?,?)";
                	
                	PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement(s);	
                	preparedStatement.setString(1, tweets.get(i).getUser().getName());
                	preparedStatement.setString(2, tweets.get(i).getUser().getScreenName());
                	preparedStatement.setLong(3, tweets.get(i).getId());
                	preparedStatement.setString(4, tweets.get(i).getText().toString());
                	preparedStatement.setString(5, tweets.get(i).getCreatedAt().toString());
                	preparedStatement.setString(6, tweets.get(i).getUser().getLocation());
                	//stmt.executeUpdate("INSERT INTO TwitterMaster(user_id,name,tweet_id,tweet_text,tweet_time) VALUES('"+tweets.get(i).getUser().getName()+"', '"+tweets.get(i).getUser().getScreenName()+"','"+tweets.get(i).getId()+"', '"+tweets.get(i).getText()+"', '"+tweets.get(i).getCreatedAt().toString()+"')");
                	preparedStatement .executeUpdate();
                	preparedStatement.close();
                    tweetList.add(tweets.get(i).getText());
                    Thread.sleep(999);
                }
                break;
            } while ((query = result.nextQuery()) != null);
            
            conn.close();
            stmt.close();
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        return tweetList;
    }
}