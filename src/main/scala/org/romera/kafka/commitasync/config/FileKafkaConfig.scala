package org.romera.kafka.commitasync.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

import java.io.{File, FileReader}


@Component
class FileKafkaConfig @Autowired()(env: Environment) {

  case class FileskafkaConfig()()
  case class KafkaConfig()(bootstrapServer: String, properties: Map[String, Any])

  @Bean
  def fileskafkaConfig(): FileskafkaConfig ={



    case class Prop(country: String, state: List[String])

    val mapper: ObjectMapper = new ObjectMapper(new YAMLFactory())
    val files = new File(env.getProperty("kafka.path"))
    mapper.registerModule(DefaultScalaModule)
    val fileStream = getClass.getResourceAsStream(files.getPath + "/" +files.list()(0))
    val prop:Prop = mapper.readValue(fileStream, classOf[Prop])

    println(prop.country + ", " + prop.state)


    val reader = new FileReader(files.getPath + "/" +files.list()(0))
    val aa = new ObjectMapper(new YAMLFactory())
    aa.registerModule(DefaultScalaModule)
    val config: KafkaConfig = mapper.readValue(reader, classOf[KafkaConfig])
    new FileskafkaConfig()()
  }
}

