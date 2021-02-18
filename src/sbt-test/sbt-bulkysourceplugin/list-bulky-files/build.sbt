version := "0.1"
scalaVersion := "2.12.1"

lazy val root = (project in file("."))
  .settings(
    version := version.value,
    scalaVersion := scalaVersion.value,
    bulkyThresholdInLines := 30,
    TaskKey[Unit]("check") := {
      val result = (Compile / bulkySources).value
      if (result.length != 1) {
        println(result)
        sys.error("Unexpected result")
      }
      ()
    }
  )
