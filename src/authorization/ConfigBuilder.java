package authorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ConfigBuilder {

        private static Configuration configuration;

     /*  //Abhishek's keys
        public static final String consumerKeyStr = "6xApxD6P3xPGa6fGEqeezvBTh";
        public static final String consumerSecretStr ="sZQtcKOlTl9AtTg6ZvMcmuF31EoVk7d7tNqhU8LlwOIcMjMRyP";
        public static final String accessTokenStr ="2584378076-QaUNEp9CY144WwPJXYejRtdi5mjEQJEGeJyUeZ8";
        public static final String accessTokenSecretStr ="ZB8AIvzEJZvjkedlJMckwHZXabaD3vvh9PTUDa0uhxylQ";
*/

/*        //Omkar's keys
        public static final String consumerKeyStr = "6kRVFFfsFLfzPH4IUTlvgulbJ";
        public static final String consumerSecretStr = "VwzVBO5ygloqoyRMl9YWSFpiuV5arjO9HpYr648Ci3WEoGD7jl";
        public static final String accessTokenStr ="134179394-f7YpC2Pxd5QXucqoaWg3UAmH1Mc793bmYLhhlPxD";
        public static final String accessTokenSecretStr ="23jbLXADuXY2TA5TUGQkUayPtEqK3Dr5BelH00SKLG6Si";

*/
        
        //Harishankar's keys:
        public static final String consumerKeyStr = "RCVjvrIN1nHF9UuddyWIw0GkO";
        public static final String consumerSecretStr ="0D0BeIdeVv8jp7pPBLrhmnas7dlHCEjxxlFLFinFtZWzkut3PQ";
        public static final String accessTokenStr ="2972666885-riSRlSUrmIOZH10msEa7bxOLj4BJVqKaI3GkUzC";
        public static final String accessTokenSecretStr ="pfHalw9HmwFtoVpongAFKod9C4oCzWCGCjeihbvUfiWXe";

/*     
        //Harishankar's keys:
        public static final String consumerKeyStr = "Hh9V6RxrIrkgSI1lcFqwPEPAQ";
        public static final String consumerSecretStr ="3bUdrH8LgZ4I525u9rnf17UneKh8RFyu9nuv44r5LmIBFAD4Fx";
        public static final String accessTokenStr ="2972666885-RUy3lbSMgC6oIv6ZDqaL8k7YkAgYTt7PJYKfRnD";
        public static final String accessTokenSecretStr ="Cv7MDgd7UELtCnTJgsWjuQWELCvu8sNSY7fhy6RYoegIU";
        
  */      static {
        	//TODO ask for config number from stdin
        	
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