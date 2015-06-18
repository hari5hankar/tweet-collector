package validate;

public class ValidateDriver {

	public static void main(String[] args){
		
		ValidateFollowers validateFollowers = new ValidateFollowers();
		long[] userIDsArray = {96951800L};
		for (Long mainUserID : userIDsArray) {
			validateFollowers.validate(mainUserID);

		}
	}
}
