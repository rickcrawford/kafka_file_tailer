Kafka File Tailer
-----------------

This application tails a file into a Kafka Log.

I leveraged the following technologies for this:

1. [DropWizard](http://dropwizard.codahale.com/) - This is a great piece of software by Coda Hale that allows you to create self contained java applications. Your DevOps team will love you.
2. [Kafka](https://kafka.apache.org/) - Developed by LinkedIn - this is a great disk persistent message bus
3. [Apache Commons IO](http://commons.apache.org/proper/commons-io/) - If you haven't worked with the Tailer component, this project will offer a simple example.
4. [MessagePack](http://msgpack.org/) - Fast and effecient protocol buffer for objects. 


Setting up the application
==========================

Since this project is built using Maven, run the following commands to build the jar:

```
mvn build
```

You will of course need to [download](http://kafka.apache.org/downloads.html) the latest release of Apache Kafka to make this work. I prefer to use the binaries for ease of implementation.

When you download the binaries, you need to kick off Kafka and Zookeeper. For the sake of this application, use the default configurations in the `config/` directory.

Start Kafka:
```bash
bin/kafka-server-start.sh config/server.properties
```

Start Zookeeper:
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```
