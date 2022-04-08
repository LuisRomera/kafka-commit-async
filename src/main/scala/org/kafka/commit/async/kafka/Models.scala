package org.kafka.commit.async.kafka

import java.util.Properties

case class Topic(name: String, partitions: Int)

case class KafakaConfig(name: String, properties: Properties, topic: Topic, topicList: List[Topic])
