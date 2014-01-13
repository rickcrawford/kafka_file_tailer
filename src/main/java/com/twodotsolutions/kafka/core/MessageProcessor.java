/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka.core;


import java.util.UUID;

import com.twodotsolutions.kafka.api.Message;


public interface MessageProcessor {

	void process(UUID key, Message message) throws Exception;

}
