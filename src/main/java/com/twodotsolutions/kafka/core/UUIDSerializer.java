/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.core;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UUIDSerializer implements Encoder<UUID>, Decoder<UUID> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UUIDSerializer.class);

	public UUIDSerializer() {

	}

	public UUIDSerializer(VerifiableProperties props) {
		LOGGER.debug("properties:{}", props);
	}

	public byte[] toBytes(UUID val) {
		LOGGER.debug("toBytes({})", val);
		return val.toString().getBytes();
	}

	public UUID fromBytes(byte[] bytes) {
		try {
			String name = new String(bytes, "UTF-8");
			return UUID.fromString(name);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("error parsing bytes", e);
		}
		return null;
	}

}
