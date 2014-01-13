/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */

package com.twodotsolutions.kafka.core;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import kafka.common.FailedToSendMessageException;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twodotsolutions.kafka.api.Message;

public class MessageProducer implements AutoCloseable {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageProducer.class);

	private Producer<UUID, Message> producer;
	private String topic;

	public MessageProducer(String kafkaBrokerList, String topic)
			throws ConnectionException {

		LOGGER.debug("Creating Message Producer");

		Properties props = new Properties();
		props.put("serializer.class", MessageSerializer.class.getName());
		props.put("key.serializer.class", UUIDSerializer.class.getName());
		props.put("metadata.broker.list", kafkaBrokerList);

		List<String> brokers = Arrays.asList(kafkaBrokerList.split(","));
		for (String broker : brokers) {
			LOGGER.debug("checking host: {}", broker);
			String[] values = broker.split(":");
			String host = values[0];
			int port = 9092;
			if (values.length > 1) {
				port = Integer.parseInt(values[1]);
			}

			try (Socket socket = new Socket(host, port)) {
				continue;
			} catch (Exception e) {
				throw new ConnectionException(host, port);
			}
		}

		this.topic = topic;

		ProducerConfig pc = new ProducerConfig(props);
		producer = new Producer<UUID, Message>(pc);
	}

	public void create(List<Message> events)
			throws FailedToSendMessageException {
		LOGGER.debug("creating list:{}", events);

		List<KeyedMessage<UUID, Message>> messages = new ArrayList<KeyedMessage<UUID, Message>>();
		for (Message Message : events) {
			messages.add(new KeyedMessage<UUID, Message>(this.topic, UUID
					.fromString(Message.getUUID()), Message));
		}
		producer.send(messages);
	}

	public void create(Message Message) throws FailedToSendMessageException {
		create(Arrays.asList(Message));
	}

	public void close() {
		LOGGER.debug("closing producer...");

		producer.close();
	}

}
