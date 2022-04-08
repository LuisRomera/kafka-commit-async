package org.kafka.commit.async.kafka

import org.apache.kafka.clients.admin.{Admin, NewTopic}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.kafka.commit.async.config.FileKafkaConfig
import org.kafka.commit.async.kafka.Constants.{CREATE_TOPIC, DELETE_TOPIC}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

import java.time.Duration
import java.util.Collections

@Service
class Producer()(@Autowired fileKafkaConfig: FileKafkaConfig, environment: Environment) {

  def createProducer(env: String, topic: String, nameBroker: String, key: String, message: String, keyS: String,
                     value: String): Unit = {
    val kafkaEnv = fileKafkaConfig.kafkaServer.filter(r => r.name.equals(env)).head

    val prop = kafkaEnv.properties
    val keySerde = getSerde(keyS)
    val valueSerde = getSerde(value)
    prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerde)
    prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerde)

    val producer = new KafkaProducer[String, String](prop)
    producer.send(new ProducerRecord(topic, key, message))
    producer.close()
  }
def getSerde(serde: String): String = serde match {
  case "STRING" => "org.apache.kafka.common.serialization.StringSerializer"
  case "LONG" => "org.apache.kafka.common.serialization.LongSerializer"
  case "AVRO_HORTONWORKS" => "org.apache.kafka.common.serialization.LongSerializer"
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