package org.romera.kafka.commitasync.rest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RestController}

@RestController
class Controller @Autowired()(env: Environment) {

  @RequestMapping(value = Array("/greeting"), method = Array(RequestMethod.GET))
  def greeting: Unit = {

    LoggerFactory.getLogger(this.getClass).info(env.getProperty("app.name"))

  }
}
