package org.kafka.commit.async.rest

import io.swagger.annotations.ApiParam
import org.kafka.commit.async.config.FileKafkaConfig
import org.kafka.commit.async.kafka.Producer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, RequestParam, RestController}

@RestController
class Sender @Autowired()(env: Environment, producer: Producer, fileKafkaConfig: FileKafkaConfig) {

  @RequestMapping(value = Array("/sendString"), method = Array(RequestMethod.POST))
  def sendString(@RequestParam topic: String, @RequestParam(required = false) key: String,
                 @RequestParam message: String, @RequestParam nameBroker: String,
                 @ApiParam(allowableValues = "STRING,LONG,AVRO_HORTONWORKS") @RequestParam keyS: String,
                 @ApiParam(allowableValues = "STRING,LONG,AVRO_HORTONWORKS") @RequestParam value: String): Unit = {
    producer.createProducer(nameBroker, topic, nameBroker, key, message, keyS, value)
    LoggerFactory.getLogger(this.getClass).info(env.getProperty("app.name"))

  }
}