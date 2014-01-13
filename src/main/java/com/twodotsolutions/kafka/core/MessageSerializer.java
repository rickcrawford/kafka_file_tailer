/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.core;

import java.io.IOException;

import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twodotsolutions.kafka.api.Message;

public class MessageSerializer implements Encoder<Message>, Decoder<Message> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageSerializer.class);

	private MessagePack msgpack = new MessagePack();

	public MessageSerializer() {

	}

	public MessageSerializer(VerifiableProperties props) {
		LOGGER.debug("properties:{}", props);
	}

	public byte[] toBytes(Message message) {
		LOGGER.debug("toBytes({})", message);
		try {
			return msgpack.write(message);
		} catch (IOException e) {
			LOGGER.error("error serializing message:{}", message, e);
		}
		return null;
	}

	public com.twodotsolutions.kafka.api.Message fromBytes(byte[] bytes) {
		try {
			return msgpack.read(bytes, Message.class);
		} catch (IOException e) {
			LOGGER.error("error deserializing message", e);
		}
		return null;
	}

}
