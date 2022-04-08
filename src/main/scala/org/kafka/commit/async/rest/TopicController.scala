package org.kafka.commit.async.rest

import org.kafka.commit.async.config.FileKafkaConfig
import org.kafka.commit.async.kafka.Constants.{CREATE_TOPIC, DELETE_TOPIC}
import org.kafka.commit.async.kafka.Producer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, RequestParam, RestController}

@RestController
class TopicController @Autowired()(environment: Environment, producer: Producer, fileKafkaConfig: FileKafkaConfig) {

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

  @RequestMapping(value = Array("/listTopic"), method = Array(RequestMethod.GET))
  def propertiesListTopic(@RequestParam("server") server: String): String = {
    producer.getTopicsList(server).toString()
  }

  @RequestMapping(value = Array("/listPrefixTopic"), method = Array(RequestMethod.GET))
  def propertiesListTopicPrefix(@RequestParam("server") server: String, @RequestParam("prefix") prefix: String): String = {
    producer.getTopicsList(server, prefix).toString()
  }
}