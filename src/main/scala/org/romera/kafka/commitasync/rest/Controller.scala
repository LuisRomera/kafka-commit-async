package org.romera.kafka.commitasync.rest

import org.romera.kafka.commitasync.config.FileKafkaConfig
import org.romera.kafka.commitasync.kafka.Constants.{CREATE_TOPIC, DELETE_TOPIC}
import org.romera.kafka.commitasync.kafka.Producer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, RequestParam, RestController}

@RestController
class Controller @Autowired()(environment: Environment, producer: Producer, fileKafkaConfig: FileKafkaConfig) {

  @RequestMapping(value = Array("/createTopic"), method = Array(RequestMethod.GET))
  def createTopic(@RequestParam("server") server: String, @RequestParam("topic") topic: String,
                  @RequestParam("partition") partition: Int, @RequestParam("replicationFactor") replicationFactor: Short): Unit = {
    producer.managerTopic(server, topic, partition, replicationFactor, CREATE_TOPIC)
    LoggerFactory.getLogger(this.getClass).info(environment.getProperty("app.name"))
  }

  @RequestMapping(value = Array("/deleteTopic"), method = Array(RequestMethod.GET))
  def deleteTopic(@RequestParam("server") server: String, @RequestParam("topic") topic: String): Unit = {
    producer.managerTopic(server, topic, 0, 0, DELETE_TOPIC)
  }

  @RequestMapping(value = Array("/propertiesTopic"), method = Array(RequestMethod.GET))
  def propertiesTopic(@RequestParam("server") server: String, @RequestParam("topic") topic: String): String = {
    producer.propertiesTopic(server, topic)
  }
}
