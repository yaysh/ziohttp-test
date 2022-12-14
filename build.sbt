import com.typesafe.sbt.packager.docker.DockerPlugin

val zioQuillVersion = "4.6.0"
val zioVersion = "2.0.5" // "2.0.0-RC6"
val zioHttpVersion = "2.0.0-RC11" // "2.0.0-RC9"
val zioJsonVersion = "0.4.2"
val slf4jVersion = "2.0.5"
val zioLoggingVersion = "2.1.6"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.1.3"
ThisBuild / semanticdbEnabled := true

lazy val root = (project in file("."))
  .settings(
    name := "ziohttptest",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-test" % zioVersion % Test,
      "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
      "dev.zio" %% "zio-test-magnolia" % zioVersion % Test,
      "dev.zio" %% "zio-json" % zioJsonVersion,
      "io.d11" %% "zhttp" % zioHttpVersion,
      "dev.zio" %% "zio-logging-slf4j" % zioLoggingVersion,
      "org.slf4j" % "slf4j-api" % slf4jVersion,
      "org.slf4j" % "slf4j-simple" % slf4jVersion,
      /* "io.d11" %% "zhttp-test" % zioHttpVersion % Test, */
      "com.github.jwt-scala" %% "jwt-zio-json" % "9.1.2",
      "io.getquill" %% "quill-jdbc-zio" % zioQuillVersion,
      "org.postgresql" % "postgresql" % "42.5.1",
      "io.github.scottweaver" %% "zio-2-0-testcontainers-postgresql" % "0.9.0",
    ),
    Test / fork := true,
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )
  .enablePlugins(DockerPlugin)
