/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.cli;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import com.twodotsolutions.kafka.KafkaFileTailerConfiguration;
import com.twodotsolutions.kafka.api.Message;
import com.twodotsolutions.kafka.core.ConnectionException;
import com.twodotsolutions.kafka.core.MessageProducer;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import com.yammer.dropwizard.config.Bootstrap;

public class LoadDataCommand extends
		ConfiguredCommand<KafkaFileTailerConfiguration> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoadDataCommand.class);

	private static class FileAction implements ArgumentAction {

		public void run(ArgumentParser parser, Argument arg,
				Map<String, Object> attrs, String flag, Object value)
				throws ArgumentParserException {

			File f = new File((String) value);
			if (!f.exists()) {
				throw new ArgumentParserException("File: '" + value
						+ "' does not exist.", parser);
			}
			attrs.put(arg.getDest(), f);

		}

		public void onAttach(Argument arg) {
		}

		public boolean consumeArgument() {
			return true;
		}
	}

	private static class FileListener extends TailerListenerAdapter {

		private MessageProducer producer;

		public FileListener(MessageProducer producer) {
			this.producer = producer;
		}

		@Override
		public void handle(String line) {
			Message message = null;
			try {
				message = Message.parse(line);
			} catch (IOException e) {
				LOGGER.error("error parsing line: {}", line);
			}
			if (null != message) {
				producer.create(message);
			} else {
				LOGGER.warn("skipping event");
			}
		}
	}

	@Override
	public void configure(Subparser subparser) {
		super.configure(subparser);
		subparser.addArgument("-l", "--log-path").required(true)
				.metavar("LOGFILE").dest("log-file").action(new FileAction())
				.help("Path to the log to tail");
	}

	public LoadDataCommand() {
		super("load-data", "Load data by tailing log file");
	}

	@Override
	protected void run(Bootstrap<KafkaFileTailerConfiguration> bootstrap,
			Namespace namespace, KafkaFileTailerConfiguration config)
			throws Exception {

		File file = (File) namespace.get("log-file");

		LOGGER.info("running for file: {}", file);

		int delay = config.getTailerConfiguration().getDelay();
		int waitTime = config.getTailerConfiguration().getWaitTime();
		String brokerList = config.getKafkaConfiguration().getBrokerList();
		String topic = config.getKafkaConfiguration().getTopic();

		try (MessageProducer producer = new MessageProducer(brokerList, topic)) {

			Tailer tailer = new Tailer(file, new FileListener(producer), delay);
			tailer.run();

			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			LOGGER.info("closing");
			tailer.stop();

		} catch (ConnectionException ex) {
			LOGGER.error("could not connect to broker: {}:{}", ex.getHost(),
					ex.getPort());
			System.exit(0);
		}

	}

}
