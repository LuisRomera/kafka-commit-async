package org.romera.kafka.commitasync.rest

import org.romera.kafka.commitasync.config.FileKafkaConfig
import org.romera.kafka.commitasync.kafka.Producer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, RestController}

@RestController
class Sender @Autowired()(env: Environment, producer: Producer, fileKafkaConfig: FileKafkaConfig) {

  @RequestMapping(value = Array("/sendString"), method = Array(RequestMethod.POST))
  def sendString(@PathVariable topic: String, @PathVariable message: String, @PathVariable nameBroker: String): Unit = {
    producer.createProducer(topic, nameBroker, message)
    LoggerFactory.getLogger(this.getClass).info(env.getProperty("app.name"))

  }
}
