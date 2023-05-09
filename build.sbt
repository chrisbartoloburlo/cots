import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

Keys.`package` := {
  (driver / Compile / Keys.`package`).value
  (examples / Compile / Keys.`package`).value
}

lazy val commonSettings = Seq(
  version := "0.0.3",
  scalaVersion := "2.13.5",
  scalacOptions ++= Seq(
    "-unchecked", "-feature"
  ),
  // ScalaDoc setup
  autoAPIMappings := true,
  Compile / doc / scalacOptions ++= Seq(
    "-no-link-warnings" // Workaround for ScalaDoc @throws links issues
  )
)

lazy val driver = (project in file("driver")).
  settings(commonSettings: _*).
  settings(
    name := "driver",

    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3",
      "com.github.tototoshi" %% "scala-csv" % "1.3.6"
    )
  )

lazy val examples = (project in file("examples")).
  dependsOn(driver).
  settings(commonSettings).
  settings(
    name := "examples",
    generateDrivers := (Def.taskDyn {
      val baseDir = sourceDirectory.value / "main" / "scala" / "examples"
      Def.task {
        generateSttpApi(baseDir, "featuresservice", "schema.yaml")
        generateDriver(baseDir, "featuresservice", "schema.yaml").value

        generateSttpApi(baseDir, "petclinic", "openapi.yml")
        generateDriver(baseDir, "petclinic", "openapi.yml").value

        generateSttpApi(baseDir, "usersregistry", "openapi.json")
        generateDriver(baseDir, "usersregistry", "openapi.json").value

        generateSttpApi(baseDir, "sockshop", "user-openapi.json")
        generateDriver(baseDir, "sockshop", "user-openapi.json").value

        generateSttpApi(baseDir, "restcountries", "swagger.yaml")
        generateDriver(baseDir, "restcountries", "swagger.yaml").value

        generateSttpApi(baseDir, "gestaohospital", "gestaohospital.json")
        generateDriver(baseDir, "gestaohospital", "gestaohospital.json").value

        generateSttpApi(baseDir, "languagetool", "swagger.json")
        generateDriver(baseDir, "languagetool", "swagger.json").value

        generateSttpApi(baseDir, "openverse", "openapi.json")
        generateDriver(baseDir, "openverse", "openapi.json").value

        generateSttpApi(baseDir, "petstore", "openapi.yaml")
        generateDriver(baseDir, "petstore", "openapi.yaml").value
      }
    }).value,

    (Compile / compile) := ((Compile / compile) dependsOn generateDrivers).value,

    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "core" % "3.3.18",
      "com.softwaremill.sttp.client3" %% "json4s" % "3.3.18",
      "org.json4s" %% "json4s-jackson" % "4.0.4"
    ),
    assemblyMergeStrategy in assembly := {
      case PathList("jackson-annotations-2.12.6.jar", xs @ _*) => MergeStrategy.rename
      case PathList("jackson-core-2.12.6.jar", xs @ _*) => MergeStrategy.rename
      case PathList("jackson-databind-2.12.6.jar", xs @ _*) => MergeStrategy.rename
      case PathList("jackson-dataformat-cbor-2.10.3.jar", xs @ _*) => MergeStrategy.last
      case PathList("jackson-datatype-jdk8-2.10.3.jar", xs @ _*) => MergeStrategy.last
      case PathList("jackson-datatype-jsr310-2.10.3.jar", xs @ _*) => MergeStrategy.last
      case PathList("jackson-module-parameter-names-2.10.3.jar", xs @ _*) => MergeStrategy.last
      case PathList("jackson-module-paranamer-2.10.3.jar", xs @ _*) => MergeStrategy.last
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case _ => MergeStrategy.first
    }
  )

val generateDrivers = taskKey[Unit]("Generate test drivers.")

def generateDriver(baseDir: File, name: String, openapi: String) = {
  val exampleDir = baseDir / name
  val stFile = exampleDir / (name ++ ".st")
  val preamble = exampleDir / "preamble.txt"

  (driver / Compile / runMain).toTask(f" driver.Generate ${exampleDir} ${stFile} ${preamble} false")
//  java -jar modules/openapi-generator-cli/target/openapi-generator-cliold.jar generate -i ~/Downloads/openapi.json -g scala-sttp --skip-validate-spec -o ~/Desktop/tmp2
}

def generateSttpApi(baseDir: File, name: String, openapi: String) = {
  val exampleDir = baseDir / name
  import scala.sys.process._
  s"rm -r $exampleDir/api/"!;
  s"rm -r $exampleDir/core/"!;
  s"rm -r $exampleDir/model/"!;
  s"java -jar openapi-generator-cli.jar generate -i $exampleDir/$openapi -g scala-sttp --skip-validate-spec -o $exampleDir --additional-properties modelPropertyNaming=original"!;
  s"rm -r $exampleDir/.openapi-generator/"!;
  s"rm $exampleDir/.openapi-generator-ignore"!;
  s"rm $exampleDir/build.sbt"!;
  s"rm $exampleDir/README.md"!;
  s"rm -r $exampleDir/project/"!;
  s"mv $exampleDir/src/main/scala/org/openapitools/client/api/ $exampleDir"!;
  val apiDir = new File(s"$exampleDir/api/")
  if (apiDir.exists && apiDir.isDirectory) {
    for(file <- apiDir.listFiles()){
      val apiFile = scala.io.Source.fromFile(file, "utf-8")
      var contents = ""
      try{
        contents = apiFile.mkString.replace("package org.openapitools.client.api", s"package examples.$name.api").replace("import org.openapitools.client", s"import examples.$name")
        contents = contents.replace(s"import examples.$name.model.AnyType\n", "")
      } finally apiFile.close()
      Files.write(Paths.get(file.getAbsolutePath), contents.getBytes(StandardCharsets.UTF_8))
    }
  }
  s"mv $exampleDir/src/main/scala/org/openapitools/client/core/ $exampleDir"!;
  val coreDir = new File(s"$exampleDir/core/")
  if (coreDir.exists && coreDir.isDirectory) {
    for(file <- coreDir.listFiles()){
      val coreFile = scala.io.Source.fromFile(file, "utf-8")
      var contents = ""
      try{
        contents = coreFile.mkString.replace("package org.openapitools.client.core", s"package examples.$name.core").replace("import org.openapitools.client", s"import examples.$name")
      } finally coreFile.close()
      Files.write(Paths.get(file.getAbsolutePath), contents.getBytes(StandardCharsets.UTF_8))
    }
  }
  s"mv $exampleDir/src/main/scala/org/openapitools/client/model/ $exampleDir"!;
  val modelDir = new File(s"$exampleDir/model/")
  if (modelDir.exists && modelDir.isDirectory) {
    for(file <- modelDir.listFiles()){
      val modelFile = scala.io.Source.fromFile(file, "utf-8")
      var contents = ""
      try{
        contents = modelFile.mkString.replace("package org.openapitools.client.model", s"package examples.$name.model").replace("import org.openapitools.client", s"import examples.$name")
      } finally modelFile.close()
      Files.write(Paths.get(file.getAbsolutePath), contents.getBytes(StandardCharsets.UTF_8))
    }
  }
  s"rm -r $exampleDir/src/"!;
}