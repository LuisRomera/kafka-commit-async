package org.romera.kafka.commitasync.config

import org.apache.kafka.clients.admin.{Admin, NewTopic}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.TimeoutException
import org.romera.kafka.commitasync.kafka.{KafakaConfig, Topic}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

import java.lang.String.format
import java.time.Duration
import java.util
import java.util.{Collections, Properties, ArrayList => JArrayList, List => JList, Map => JMap}
import scala.beans.BeanProperty
import scala.collection.convert.ImplicitConversions.{`collection AsScalaIterable`, `collection asJava`}



@Component
@ConfigurationProperties(prefix = "kafka")
class FileKafkaConfig @Autowired()(env: Environment) {

  private val log = LoggerFactory.getLogger(this.getClass)

  @BeanProperty
  var bootstrapServers: JList[JMap[String, String]] = new JArrayList[JMap[String, String]]


  var kafkaServer: List[KafakaConfig] = _

  def getBootStrapServer: JList[JMap[String, String]] = bootstrapServers

  def getKafkaProperties(bootstrapServer: JMap[String, String]): KafakaConfig = {
    val properties = new Properties()
    bootstrapServer.keySet().filter(k => !k.equals("name"))
      .forEach(k => properties.put(k, bootstrapServer.get(k)))
    properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.put("group.id", env.getProperty("app.name"))
    val consumer = new KafkaConsumer[String, String](properties)
    var topicList: List[Topic] = List()
    try {
      val mapTopic = consumer.listTopics(Duration.ofMillis(1000L))
      topicList = mapTopic.keySet().toArray.map(key => Topic(key.toString, consumer.listTopics().size())).toList
    } catch {
      case ex: TimeoutException => log.warn(s"No connection kafka ${bootstrapServer}")
    }
    val config = KafakaConfig(bootstrapServer.get("name"), properties, null, topicList)
    consumer.close()
    config
  }

  @Bean
  def filesKafkaConfig(): List[KafakaConfig] = {
    kafkaServer = bootstrapServers.toList.map(getKafkaProperties)
    kafkaServer
  }
}
