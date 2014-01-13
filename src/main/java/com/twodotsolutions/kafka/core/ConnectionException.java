/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.core;


public class ConnectionException extends Exception {

	private String host;
	private int port;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3143800523629430212L;

	public ConnectionException(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}

}
