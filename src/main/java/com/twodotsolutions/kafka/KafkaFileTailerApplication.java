/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twodotsolutions.kafka.cli.LoadDataCommand;
import com.twodotsolutions.kafka.cli.ReadDataCommand;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class KafkaFileTailerApplication  extends Service<KafkaFileTailerConfiguration> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(KafkaFileTailerApplication.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		LOGGER.debug("Running application with args:{}", Arrays.asList(args));
		
		new KafkaFileTailerApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<KafkaFileTailerConfiguration> bootstrap) {
		LOGGER.debug("initialize({})", bootstrap);
		
		bootstrap.setName("kafka-file-tailer");
		bootstrap.addCommand(new LoadDataCommand());
		bootstrap.addCommand(new ReadDataCommand());
	}

	@Override
	public void run(KafkaFileTailerConfiguration config, Environment environment)
			throws Exception {
		LOGGER.debug("run({},{})", config, environment);
		
	}
}
