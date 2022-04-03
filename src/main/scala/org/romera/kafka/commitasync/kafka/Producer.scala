package org.romera.kafka.commitasync.kafka

import org.apache.kafka.clients.admin.{Admin, NewTopic}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.romera.kafka.commitasync.config.FileKafkaConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

import java.util.{Collections, Properties}

@Service
class Producer()(@Autowired fileKafkaConfig: FileKafkaConfig, environment: Environment ) {

  def createProducer(topic: String, nameBroker: String, message: String): Unit = {

  }

  // TODO:
  def creaateTopic(topic: String): Unit ={
//
//    val admin = Admin.create(properties)
//    val replicationFactor: Short = 1;
//
//    val newTopic = new NewTopic("topicName", 1, replicationFactor);
//    admin.createTopics(Collections.singleton(newTopic))
  }





}
