ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val kafkaVersion = "3.1.0"

val springVersion = "2.6.5"

val swaggerVersion = "3.0.0"

// kafka-clients

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % springVersion,
  "org.apache.kafka" % "kafka-clients" % kafkaVersion,
  "io.springfox" % "springfox-swagger-ui" % swaggerVersion,
  "io.springfox" % "springfox-swagger2" % swaggerVersion
)



lazy val root = (project in file("."))
  .settings(
    name := "kafka-commit-async"
  )
