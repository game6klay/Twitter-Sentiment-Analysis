import java.util.ArrayList;

public class What2Think {

	public static void main(String[] args) throws Exception {
		String topic = ITwitterConstants.TWEET_TO_SEARCH;
		ArrayList<String> tweets = TweetManager.getTweets(topic);
		System.out.println("====================================================================================================================================");
		System.out.println("\n");
		NLP.init();
		int positive =0;
		int negetive=0;
		int neutral=0;
		int result;
		
		for (int i = 0; i < tweets.size(); i++) {
			result=NLP.findSentiment(tweets.get(i));
			System.out.println("Sentiment Anlaysis of tweet"+i+" :::::::::"+ NLP.findSentiment(tweets.get(i)));
			
			if(result==1)
				negetive++;
			else if(result==2)
				neutral++;
			else if(result==3)
				positive++;			
			
		}
		System.out.println("======================================");
		System.out.println("======Final Results================");
		System.out.println("======================================");
		System.out.println("|--- +Neutral Tweets----->>"+neutral+"|--- ");
		System.out.println("|--- +Negetive Tweets---->>"+negetive+"|--- ");
		System.out.println("|--- +Positive Tweets---->>"+positive+"|--- ");
		
		
		System.out.println("======================================");
		System.out.println("======================================");

	}

}