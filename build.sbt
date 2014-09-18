name := "st-workshop"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka"      %% "akka-stream-experimental" % "0.4",
  "com.typesafe.akka"      %% "akka-actor"               % "2.3.6",
  "com.typesafe.akka"      %% "akka-slf4j"               % "2.3.6",
  "org.scalanlp"           %% "breeze-natives"           % "0.9",
  "org.scalanlp"           %% "breeze"                   % "0.9",
  "org.scalanlp"           %% "nak"                      % "1.3",
  "com.typesafe.akka"      %% "akka-testkit"             % "2.3.6"        % "test"
)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)

parallelExecution in Test := false

fork in run := true

connectInput in run := true

outputStrategy in run := Some(StdoutOutput)