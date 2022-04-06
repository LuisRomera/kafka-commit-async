package org.romera.kafka.commitasync.kafka

import org.apache.kafka.clients.admin.{Admin, NewTopic}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.romera.kafka.commitasync.config.FileKafkaConfig
import org.romera.kafka.commitasync.kafka.Constants.{CREATE_TOPIC, DELETE_TOPIC}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

import java.time.Duration
import java.util.Collections

@Service
class Producer()(@Autowired fileKafkaConfig: FileKafkaConfig, environment: Environment) {

  def createProducer(topic: String, nameBroker: String, message: String): Unit = {

  }

  def managerTopic(env: String, topic: String, partition: Int, replicationFactor: Short, operation: String): Unit = {
    val kafkaEnv = fileKafkaConfig.kafkaServer.filter(r => r.name.equals(env)).head
    val admin = Admin.create(kafkaEnv.properties)
    val newTopic = new NewTopic(topic, partition, replicationFactor);
    operation match {
      case DELETE_TOPIC => admin.deleteTopics(Collections.singleton(topic))
      case CREATE_TOPIC => admin.createTopics(Collections.singleton(newTopic))
    }
    admin.close()
  }

  def propertiesTopic(env: String, topic: String): String = {
    val kafkaEnv = fileKafkaConfig.kafkaServer.filter(r => r.name.equals(env)).head
    val consumer = new KafkaConsumer[String, String](kafkaEnv.properties)
    val mapTopic = consumer.listTopics(Duration.ofMillis(1000L))
    consumer.close()
    mapTopic.get(topic).toString
  }

  case class Topic(name: String, partitions: Int)

  def getTopicsList(env: String): List[String] = {
    val kafkaEnv = fileKafkaConfig.kafkaServer.filter(r => r.name.equals(env)).head
    val consumer = new KafkaConsumer[String, String](kafkaEnv.properties)
    val mapTopic = consumer.listTopics(Duration.ofMillis(1000L))
    consumer.close()
    mapTopic.keySet().toArray.toList.map(t => s"topicName: ${t}, properties: ${mapTopic.get(t).toString}")
  }

  def getTopicsList(env: String, prefix: String): List[String] = {
    val kafkaEnv = fileKafkaConfig.kafkaServer.filter(r => r.name.equals(env)).head
    val consumer = new KafkaConsumer[String, String](kafkaEnv.properties)
    val mapTopic = consumer.listTopics(Duration.ofMillis(1000L))
    consumer.close()
    mapTopic.keySet().toArray.toList
      .filter(t => t.toString.contains(prefix))
      .map(t => s"${t}")
  }
}
