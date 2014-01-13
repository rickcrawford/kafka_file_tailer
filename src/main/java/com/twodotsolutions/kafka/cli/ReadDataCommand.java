/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.cli;

import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twodotsolutions.kafka.KafkaFileTailerConfiguration;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import com.yammer.dropwizard.config.Bootstrap;

public class ReadDataCommand extends ConfiguredCommand<KafkaFileTailerConfiguration> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadDataCommand.class);

	
	public ReadDataCommand() {
		super("read-data", "Read data from queue");
	}

	@Override
	protected void run(Bootstrap<KafkaFileTailerConfiguration> bootstrap,
			Namespace namespace, KafkaFileTailerConfiguration config) throws Exception {
		LOGGER.debug("run({},{},{})", bootstrap, namespace, config);
		
		
		
	}
}
