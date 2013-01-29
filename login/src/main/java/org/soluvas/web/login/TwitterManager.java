package org.soluvas.web.login;

import twitter4j.Twitter;

/**
 * Get Access ConsumerKey and ConsumerSecret and Get AccessToken and AccessSecrets
 * @author haidar
 *
 */
public interface TwitterManager {

	public abstract String getConsumerKey();

	public abstract String getConsumerSecret();

	Twitter getTwitter();

	Twitter createTwitter(String accessToken, String accessTokenSecret);
}
