/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.api;

import java.io.IOException;

import org.msgpack.MessagePack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

@org.msgpack.annotation.Message
public class Message {

	public Message() {
	}

	public Message(String line) {
		this.line = line;
	}

	@JsonProperty
	private String line;

	public String getLine() {
		return line;
	}

	public static byte[] toBytes(Message message) throws IOException {
		return new MessagePack().write(message);
	}

	public static Message parse(String line) throws IOException {
		return new Message(line);
	}

	public String toString() {
		return Objects.toStringHelper(this).add("line", line).toString();
	}
}
