//package org.kafka.commit.async.kafka
//
//import org.apache.kafka.clients.consumer.KafkaConsumer
//import org.kafka.commit.async.config.FileKafkaConfig
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.core.env.Environment
//import org.springframework.stereotype.Service
//
//@Service
//class MessageService @Autowired() (fileKafkaConfig: FileKafkaConfig, environment: Environment) {
//  def getLastMessage(env: String, topic: String, partition: Int, replicationFactor: Short, operation: String): String = {
//    val kafkaProperties = fileKafkaConfig.kafkaServer.filter(r => r.name.equals(env))
//
////    val kafkaConsumer = new KafkaConsumer<>(kafkaProperties.properties)
//
//    "kafkaConsumer.seekToEnd()"
//  }
//
//
//}
