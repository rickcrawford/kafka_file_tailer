/***
 * Copyright 2014 - Rick Crawford - https://github.com/rickcrawford
 */
package com.twodotsolutions.kafka;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.yammer.dropwizard.config.Configuration;

public class KafkaFileTailerConfiguration extends Configuration {
	// tailer:
	// delay: 1000
	// waitTime: 60000
	public static class TailerConfiguration {

		@Min(1)
		@Max(1000)
		@JsonProperty
		private int delay = 1000;

		@Min(1)
		@Max(3600000)
		@JsonProperty
		private int waitTime = 60000;

		public int getDelay() {
			return delay;
		}

		public void setDelay(int delay) {
			this.delay = delay;
		}

		public int getWaitTime() {
			return waitTime;
		}

		public void setWaitTime(int waitTime) {
			this.waitTime = waitTime;
		}

		@Override
		public String toString() {
			return Objects.toStringHelper(this).add("delay", delay)
					.add("waitTime", waitTime).toString();
		}
	}

	//
	// kafka:
	// autoCommitInterval: 1000
	// autoOffsetReset: largest
	// serializerClass: kafka.serializer.StringEncoder
	// metadataBrokerList: localhost:9092
	// topic: messages
	//

	public static class KafkaConfiguration {

		@Min(1)
		@Max(5000)
		@JsonProperty
		private int autoCommitInterval = 1000;

		@Pattern(regexp = "(largest|smallest)")
		@JsonProperty
		private String autoOffsetReset = "largest";

		@JsonProperty
		private String serializerClass = "kafka.serilizer.StringEncoder";

		@NotNull
		@JsonProperty
		private String brokerList;

		@NotNull
		@JsonProperty
		private String topic;

		@NotNull
		@JsonProperty
		private String groupId;

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public int getAutoCommitInterval() {
			return autoCommitInterval;
		}

		public void setAutoCommitInterval(int autoCommitInterval) {
			this.autoCommitInterval = autoCommitInterval;
		}

		public String getAutoOffsetReset() {
			return autoOffsetReset;
		}

		public void setAutoOffsetReset(String autoOffsetReset) {
			this.autoOffsetReset = autoOffsetReset;
		}

		public String getSerializerClass() {
			return serializerClass;
		}

		public void setSerializerClass(String serializerClass) {
			this.serializerClass = serializerClass;
		}

		public String getBrokerList() {
			return brokerList;
		}

		public void setBrokerList(String brokerList) {
			this.brokerList = brokerList;
		}

		public String getTopic() {
			return topic;
		}

		public void setTopic(String topic) {
			this.topic = topic;
		}

		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("autoCommitInterval", autoCommitInterval)
					.add("autoOffsetReset", autoOffsetReset)
					.add("serializerClass", serializerClass)
					.add("brokerList", brokerList).add("topic", topic)
					.add("groupId", groupId).toString();
		}

	}

	//
	// zookeeper:
	// host: localhost
	// port: 2181
	// sessionTimeout: 400
	// syncTime: 200
	//
	//
	public static class ZookeeperConfiguration {

		@Min(1)
		@Max(5000)
		@JsonProperty
		private int sessionTimeout = 400;

		@Min(1)
		@Max(5000)
		@JsonProperty
		private int syncTime = 200;

		@NotNull
		@JsonProperty
		private String host;

		@NotNull
		@JsonProperty
		private int port;

		public int getSessionTimeout() {
			return sessionTimeout;
		}

		public void setSessionTimeout(int sessionTimeout) {
			this.sessionTimeout = sessionTimeout;
		}

		public int getSyncTime() {
			return syncTime;
		}

		public void setSyncTime(int syncTime) {
			this.syncTime = syncTime;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("sessionTimeout", sessionTimeout)
					.add("syncTime", syncTime).add("host", host)
					.add("port", port).toString();
		}
	}

	@Valid
	@NotNull
	@JsonProperty("tailer")
	private TailerConfiguration tailerConfiguration = new TailerConfiguration();

	@Valid
	@NotNull
	@JsonProperty("kafka")
	private KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();

	@Valid
	@NotNull
	@JsonProperty("zookeeper")
	private ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration();

	public KafkaConfiguration getKafkaConfiguration() {
		return kafkaConfiguration;
	}

	public ZookeeperConfiguration getZookeeperConfiguration() {
		return zookeeperConfiguration;
	}

	public TailerConfiguration getTailerConfiguration() {
		return tailerConfiguration;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("kafka", kafkaConfiguration)
				.add("tailer", tailerConfiguration)
				.add("zookeeper", zookeeperConfiguration).toString();
	}
}
