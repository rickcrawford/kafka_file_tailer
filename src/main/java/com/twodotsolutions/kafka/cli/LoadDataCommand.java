/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.argparse4j.inf.Namespace;

import com.twodotsolutions.kafka.KafkaFileTailerConfiguration;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import com.yammer.dropwizard.config.Bootstrap;

public class LoadDataCommand extends ConfiguredCommand<KafkaFileTailerConfiguration> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoadDataCommand.class);

	
	public LoadDataCommand() {
		super("load-data", "Load data into queue");
	}

	@Override
	protected void run(Bootstrap<KafkaFileTailerConfiguration> bootstrap,
			Namespace namespace, KafkaFileTailerConfiguration config) throws Exception {
		LOGGER.debug("run({},{},{})", bootstrap, namespace, config);

	}

}
