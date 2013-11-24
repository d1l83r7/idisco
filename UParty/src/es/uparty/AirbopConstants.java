package es.uparty;

public class AirbopConstants {

	private static AirbopConstants airbopConstants;
	private String airbopAppKey = null;
	private String airbopAppSecret = null;
	private String googleProjectNumber = null;
	
	public static AirbopConstants getInstance(){
		if(airbopConstants ==null){
			airbopConstants = new AirbopConstants();
		}
		return airbopConstants;
	}
	
	public static void destroyIntance(){
		airbopConstants = null;
	}

	public String getAirbopAppKey() {
		return this.airbopAppKey;
	}

	public void setAirbopAppKey(String airbopAppKey) {
		this.airbopAppKey = airbopAppKey;
	}

	public String getAirbopAppSecret() {
		return this.airbopAppSecret;
	}

	public void setAirbopAppSecret(String airbopAppSecret) {
		this.airbopAppSecret = airbopAppSecret;
	}

	public String getGoogleProjectNumber() {
		return this.googleProjectNumber;
	}

	public void setGoogleProjectNumber(String googleProjectNumber) {
		this.googleProjectNumber = googleProjectNumber;
	}
}
