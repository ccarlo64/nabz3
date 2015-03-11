package com.homeproject.nabz;

public class Stream {

	private String streamName;
	private String streamUrl;
	public void setStreamName( String s ) {
		this.streamName = s;
	}
    public Stream(String nome, String url) {
        this.streamName = nome;
        this.streamUrl = url;
    }
	public String getStreamName() {
		return this.streamName;
	}
	public String getStreamUrl() {
		return this.streamUrl;
	}
}
