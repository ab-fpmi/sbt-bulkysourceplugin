package abfpmi.sbt

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import sbt.IO._

object BulkysourcepluginPlugin extends AutoPlugin {

  override def trigger = allRequirements
  override def requires = JvmPlugin

  object autoImport {
    lazy val bulkyThresholdInLines = settingKey[Int]("Lines count which is considered bulky")
    lazy val bulkySources = taskKey[Seq[(Int, File)]]("Find bulky files")
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    bulkyThresholdInLines := 250,
    (Compile / bulkySources) := {
      val sources = (Compile / unmanagedSources).value
      val threshold = bulkyThresholdInLines.value
      doBulkySources(threshold, sources)
    },
    (Test / bulkySources) := {
      val sources = (Test / unmanagedSources).value
      val threshold = bulkyThresholdInLines.value
      doBulkySources(threshold, sources)
    }
  )

  override lazy val buildSettings = Seq()

  override lazy val globalSettings = Seq()

  def doBulkySources(threshold: Int, sources: Seq[File]): Seq[(Int, File)] = {
    sources.map(f => (readLines(f).length, f)).filter(_._1 > threshold)
  }
}
