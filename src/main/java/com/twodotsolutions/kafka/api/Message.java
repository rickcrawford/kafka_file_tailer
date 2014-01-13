/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.api;

import java.io.IOException;
import java.util.Objects;

import org.msgpack.MessagePack;

import com.fasterxml.jackson.annotation.JsonProperty;

@org.msgpack.annotation.Message
public class Message {

	public Message() {
	}

	public Message(String uuid) {
		this.uuid = uuid;
	}

	@JsonProperty
	private String uuid;

	public String getUUID() {
		return uuid;
	}

	public static byte[] toBytes(Message message) throws IOException {
		return new MessagePack().write(message);
	}

	public String toString() {
		return Objects.toString(this);
	}
}
