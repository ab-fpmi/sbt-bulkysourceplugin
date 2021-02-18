name := """sbt-bulkysourceplugin"""
organization := "ab-fpmi"
version := "0.1-SNAPSHOT"

sbtPlugin := true

// ScalaTest
//libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1" % "test"
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

bintrayPackageLabels := Seq("sbt","plugin")
bintrayVcsUrl := Some("""git@github.com:ab-fpmi/sbt-bulkysourceplugin.git""")

initialCommands in console := """import abfpmi.sbt._"""

enablePlugins(ScriptedPlugin)
scriptedBufferLog := false

// set up 'scripted; sbt plugin for testing sbt plugins
scriptedLaunchOpts ++=
  Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
