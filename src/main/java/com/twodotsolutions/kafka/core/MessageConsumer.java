/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twodotsolutions.kafka.api.Message;


public class MessageConsumer implements AutoCloseable {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageConsumer.class);
	private String topic;
	private final ConsumerConnector consumer;

	public MessageConsumer(String zookeeperHost, int zookeeperPort,
			int zookeeperSessionTimeout, int zookeeperSyncTime, String groupId,
			String autoOffsetReset, int autoCommitInterval, String topic) {
		Properties props = new Properties();
		props.put("zookeeper.connect", zookeeperHost + ":" + zookeeperPort);
		props.put("zookeeper.session.timeout.ms",
				String.valueOf(zookeeperSessionTimeout));
		props.put("zookeeper.sync.time.ms", String.valueOf(zookeeperSyncTime));

		props.put("group.id", groupId);
		props.put("auto.offset.reset", autoOffsetReset);
		props.put("auto.commit.interval.ms", String.valueOf(autoCommitInterval));

		this.topic = topic;
		this.consumer = Consumer
				.createJavaConsumerConnector(new ConsumerConfig(props));
	}

	public void process(MessageProcessor processor) throws Exception {

		LOGGER.debug("process()");

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaStream<UUID, Message>>> consumerMap = consumer
				.createMessageStreams(topicCountMap, new UUIDSerializer(),
						new MessageSerializer());
		KafkaStream<UUID, Message> stream = consumerMap.get(topic).get(0);
		ConsumerIterator<UUID, Message> it = stream.iterator();
		while (it.hasNext()) {
			MessageAndMetadata<UUID, Message> message = it.next();
			Message Message = message.message();
			UUID key = message.key();
			processor.process(key, Message);
		}
	}

	@Override
	public void close() throws Exception {
		consumer.shutdown();
	}
}
