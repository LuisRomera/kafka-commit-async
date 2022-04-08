//package org.kafka.commit.async.rest
//
//import org.kafka.commit.async.config.FileKafkaConfig
//import org.kafka.commit.async.kafka.Constants.{CREATE_TOPIC, DELETE_TOPIC}
//import org.kafka.commit.async.kafka.MessageService
//import org.slf4j.LoggerFactory
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.core.env.Environment
//import org.springframework.web.bind.annotation.*
//
//@RestController
//class MessageController @Autowired()(environment: Environment, messageService: MessageService, fileKafkaConfig: FileKafkaConfig) {
//
//  @RequestMapping(value = Array("/message"), method = Array(RequestMethod.GET))
//  def createTopic(@RequestParam("server") server: String, @RequestParam("topic") topic: String,
//                  @RequestParam("partition") partition: Int, @RequestParam("replicationFactor") replicationFactor: Short): Unit = {
//    messageService.getLastMessage(server, topic, partition, replicationFactor, CREATE_TOPIC)
//    LoggerFactory.getLogger(this.getClass).info(environment.getProperty("app.name"))
//  }
//}