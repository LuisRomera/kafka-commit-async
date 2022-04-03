ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val kafkaVersion = "3.1.0"

val springVersion = "2.6.5"

val swaggerVersion = "2.9.2"

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % springVersion,
  "org.apache.kafka" % "kafka-clients" % kafkaVersion,
  "io.springfox" % "springfox-swagger-ui" % swaggerVersion,
  "io.springfox" % "springfox-swagger2" % swaggerVersion,

  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.13.2",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.2"

)



lazy val root = (project in file("."))
  .settings(
    name := "kafka-commit-async"
  )
