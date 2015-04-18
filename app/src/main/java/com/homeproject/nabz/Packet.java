package com.homeproject.nabz;

public class Packet {

	private String packetName;
	private String packetDate;
	public void setStreamName( String s ) {
		this.packetName = s;
	}
    public Packet(String nome, String url) {
        this.packetName = nome;
        this.packetDate = url;
    }
	public String getPacketName() {
		return this.packetName;
	}
	public String getPacketDate() {
		return this.packetDate;
	}
}
