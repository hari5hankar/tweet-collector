

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ConfigBuilder {

	private static Configuration configuration;
	
	/*
	 *Abhishek's keysz 
	 */
	private static final String consumerKeyStr = "6xApxD6P3xPGa6fGEqeezvBTh";
	private static final String consumerSecretStr = "sZQtcKOlTl9AtTg6ZvMcmuF31EoVk7d7tNqhU8LlwOIcMjMRyP";
	private static final String accessTokenStr = "2584378076-QaUNEp9CY144WwPJXYejRtdi5mjEQJEGeJyUeZ8";
	private static final String accessTokenSecretStr = "ZB8AIvzEJZvjkedlJMckwHZXabaD3vvh9PTUDa0uhxylQ";
	
/*	
 	//Harishankar's keys: 	
  	private static final String consumerKeyStr = "RCVjvrIN1nHF9UuddyWIw0GkO";
	private static final String consumerSecretStr = "0D0BeIdeVv8jp7pPBLrhmnas7dlHCEjxxlFLFinFtZWzkut3PQ";
	private static final String accessTokenStr = "2972666885-riSRlSUrmIOZH10msEa7bxOLj4BJVqKaI3GkUzC";
	private static final String accessTokenSecretStr = "pfHalw9HmwFtoVpongAFKod9C4oCzWCGCjeihbvUfiWXe";
*/	
	static {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true);
		configurationBuilder.setOAuthConsumerKey(consumerKeyStr);
		configurationBuilder.setOAuthConsumerSecret(consumerSecretStr);
		configurationBuilder.setOAuthAccessToken(accessTokenStr);
		configurationBuilder.setOAuthAccessTokenSecret(accessTokenSecretStr);
		configuration = configurationBuilder.build();
	}
	
	public static Configuration getConfig() {
		return configuration;
	}
}
