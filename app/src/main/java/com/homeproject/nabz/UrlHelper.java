package com.homeproject.nabz;

public class UrlHelper {

	//private String token = "";
	private String serverToken = "token=";
	private String login = "/ojn_api/accounts/auth?login="; ///ojn_api/accounts/auth?login=" + tmp[0] + "&pass=

	private String pluginBunny               = "/ojn_api/bunny/";

	private String pluginBunnySendPacket     = "/packet/sendPacket?data=";
	private String pluginBunnySendText       = "/tts/say?text=";
	private String pluginBunnyColorBreathing = "/colorbreathing/setColor?name=";
	private String pluginBunnySleep          = "/sleep/sleep?";
	private String pluginBunnyWakeUp         = "/sleep/wakeup?";
    private String pluginBunnyWebradioPlay   = "/webradio/playurl="; 
	
	public String addToken( String url, String token ) {
		
		char c = url.charAt(url.length()-1);
		if (c=='?') {
			url = url + this.serverToken + token;
		}
		else {
			url = url + "&" + this.serverToken + token;
		}
		return url;
	}
	
	public String getAuthUrl( String server, String user, String password ){
		String url = server + this.login + user + "&pass=" + password;
		return url;
	}
	
	public String getBunnySendPacket( String server, String mac, String data) {
		String url = server + this.pluginBunny + mac + this.pluginBunnySendPacket + data;
		return url;
	}
	public String getBunnySendText( String server, String mac, String data) {
		String url = server + this.pluginBunny + mac + this.pluginBunnySendText + data;
		return url;
	}	
	public String getBunnySleep( String server, String mac) {
		String url = server + this.pluginBunny + mac + this.pluginBunnySleep;
		return url;
	}
	public String getBunnyWakeUp( String server, String mac) {
		String url = server + this.pluginBunny + mac + this.pluginBunnyWakeUp;
		return url;
	}
	public String getBunnyWebradioPlay( String server, String mac, String data) {
		String url = server + this.pluginBunny + mac + this.pluginBunnyWebradioPlay + data;
		return url;
	}
	public String getBunnyColorBreathing( String server, String mac, String data) {
		String url = server + this.pluginBunny + mac + this.pluginBunnyColorBreathing + data;
		return url;
	}

}
