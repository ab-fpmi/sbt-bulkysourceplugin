version := "0.1"
scalaVersion := "2.12.1"

lazy val root = (project in file("."))
  .settings(
    version := version.value,
    scalaVersion := scalaVersion.value,
    bulkyThresholdInLines := 30,
    TaskKey[Unit]("check") := {
      val resultCompile = (Compile / bulkySources).value
      if (resultCompile.length != 2) {
        println(resultCompile)
        sys.error("Unexpected result")
      }

      val resultTest = (Test / bulkySources).value
      if (resultTest.length != 0) {
        println(resultTest)
        sys.error("Unexpected result")
      }

      ()
    }
  )
