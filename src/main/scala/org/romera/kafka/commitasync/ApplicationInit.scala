package org.romera.kafka.commitasync

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ApplicationInit

object ApplicationInit extends App {
  val app = SpringApplication.run(classOf[ApplicationInit], args: _*)
  val env = app.getEnvironment;
  LoggerFactory.getLogger(this.getClass).info(env.getProperty("spring.name"))
  LoggerFactory.getLogger(this.getClass).info("http://localhost:9002/swagger-ui.html")
}