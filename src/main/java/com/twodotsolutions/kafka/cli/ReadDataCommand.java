/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.cli;

import java.util.UUID;

import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twodotsolutions.kafka.KafkaFileTailerConfiguration;
import com.twodotsolutions.kafka.api.Message;
import com.twodotsolutions.kafka.core.MessageConsumer;
import com.twodotsolutions.kafka.core.MessageProcessor;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import com.yammer.dropwizard.config.Bootstrap;

public class ReadDataCommand extends
		ConfiguredCommand<KafkaFileTailerConfiguration> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadDataCommand.class);

	private static final class Processor implements MessageProcessor {

		@Override
		public void process(UUID key, Message value) throws Exception {
			LOGGER.debug("processing message:{} {}", key, value.toString());
		}
	}

	public ReadDataCommand() {
		super("read-data", "Read data from queue");
	}

	@Override
	protected void run(Bootstrap<KafkaFileTailerConfiguration> bootstrap,
			Namespace namespace, KafkaFileTailerConfiguration config)
			throws Exception {
		LOGGER.debug("run({},{},{})", bootstrap, namespace, config);

		String zookeeperHost = config.getZookeeperConfiguration().getHost();
		int zookeeperPort = config.getZookeeperConfiguration().getPort();
		int zookeeperSessionTimeout = config.getZookeeperConfiguration()
				.getSessionTimeout();
		int zookeeperSyncTime = config.getZookeeperConfiguration()
				.getSyncTime();
		String groupId = config.getKafkaConfiguration().getGroupId();
		String topic = config.getKafkaConfiguration().getTopic();
		String autoOffsetReset = config.getKafkaConfiguration()
				.getAutoOffsetReset();
		int autoCommitInterval = config.getKafkaConfiguration()
				.getAutoCommitInterval();
		int waitTime = config.getTailerConfiguration().getWaitTime();

		LOGGER.info("processing messages for topic:{}, groupId:{}", topic,
				groupId);

		try (MessageConsumer consumer = new MessageConsumer(zookeeperHost,
				zookeeperPort, zookeeperSessionTimeout, zookeeperSyncTime,
				groupId, autoOffsetReset, autoCommitInterval, topic)) {
			consumer.process(new Processor());
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

		}

	}
}
