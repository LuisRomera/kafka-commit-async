package org.kafka.commit.async

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ApplicationInit

object ApplicationInit extends App {
  val app = SpringApplication.run(classOf[ApplicationInit], args: _*)
  val env = app.getEnvironment;
  LoggerFactory.getLogger(this.getClass).info("-------------------------------")
  LoggerFactory.getLogger(this.getClass).info(s"\t ${env.getProperty("app.name")}")
  LoggerFactory.getLogger(this.getClass).info(s"\t ${env.getProperty("swagger.url")}")
}

