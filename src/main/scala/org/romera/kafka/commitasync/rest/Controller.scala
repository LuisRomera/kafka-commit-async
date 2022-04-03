package org.romera.kafka.commitasync.rest
import org.romera.kafka.commitasync.config.FileKafkaConfig
import org.romera.kafka.commitasync.kafka.Producer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RestController}

@RestController
class Controller @Autowired()(env: Environment, producer: Producer, fileKafkaConfig: FileKafkaConfig) {

  @RequestMapping(value = Array("/greeting"), method = Array(RequestMethod.GET))
  def greeting: Unit = {
    LoggerFactory.getLogger(this.getClass).info(env.getProperty("app.name"))

  }
}
