package org.romera.kafka.commitasync.kafka

import com.google.gson.{Gson, GsonBuilder}
import org.apache.kafka.clients.admin.{Admin, NewTopic}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.PartitionInfo
import org.romera.kafka.commitasync.config.FileKafkaConfig
import org.romera.kafka.commitasync.kafka.Constants.{CREATE_TOPIC, DELETE_TOPIC}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

import java.time.Duration
import java.util
import java.util.Collections

@Service
class Producer()(@Autowired fileKafkaConfig: FileKafkaConfig, environment: Environment) {

  val gson = new GsonBuilder().setPrettyPrinting().create()


  def managerTopic(env: String, topic: String, partition: Int, replicationFactor: Short, operation: String): Unit = {
    val kafkaEnv = getKafkaServerProperties(env)
    val admin = Admin.create(kafkaEnv.properties)
    val newTopic = new NewTopic(topic, partition, replicationFactor);
    operation match {
      case DELETE_TOPIC => admin.deleteTopics(Collections.singleton(topic))
      case CREATE_TOPIC => admin.createTopics(Collections.singleton(newTopic))
    }
    admin.close()
  }

  def propertiesTopic(env: String, topic: String): String = {
    val consumer = getConsumerStringString(env)
    val mapTopic = getTopics(consumer)
    consumer.close()
    mapTopic.get(topic).toString
  }

  case class Topic(name: String, partitions: Int)

  def getTopicsList(env: String): String = {
    val consumer = getConsumerStringString(env)
    val mapTopic = getTopics(consumer)
    consumer.close()
    gson.toJson(mapTopic.keySet().toArray)
  }

  def getTopicsList(env: String, prefix: String): String = {
    val consumer = getConsumerStringString(env)
    val mapTopic = getTopics(consumer)
    consumer.close()
    gson.toJson(mapTopic.keySet().toArray.toList
      .filter(t => t.toString.contains(prefix))
      .map(t => s"${t}"))
  }

  def getKafkaServerProperties(name: String): KafakaConfig =
    fileKafkaConfig.kafkaServer.filter(r => r.name.equals(name)).head

  def getConsumerStringString(name: String): KafkaConsumer[String, String] =
    new KafkaConsumer[String, String](getKafkaServerProperties(name).properties)

  def getTopics(consumer: KafkaConsumer[String, String]): util.Map[String, util.List[PartitionInfo]] =
    consumer.listTopics(Duration.ofMillis(1000L))
}
