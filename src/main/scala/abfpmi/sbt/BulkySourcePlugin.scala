package abfpmi.sbt

import sbt.{Def, _}
import sbt.Keys._
import sbt.IO._

object BulkySourcePlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    lazy val bulkyThresholdInLines = settingKey[Int]("Lines count which is considered bulky")
    lazy val bulkySources = taskKey[Seq[(Int, File)]]("Find bulky files")
  }

  import autoImport._

  lazy val bulkySourceConfig: Seq[Def.Setting[_]] = Seq(
    bulkySources := doBulkySources(bulkyThresholdInLines.value, (bulkySources / sources).value),
    sources in bulkySources := sources.value
  )

  override lazy val globalSettings: Seq[Def.Setting[_]] = Seq(
    bulkyThresholdInLines := 250
  )

  override lazy val projectSettings: Seq[Def.Setting[_]] =
    inConfig(Compile)(bulkySourceConfig) ++ inConfig(Test)(bulkySourceConfig)

  def doBulkySources(threshold: Int, sources: Seq[File]): Seq[(Int, File)] = {
    sources.map(f => (readLines(f).length, f)).filter(_._1 > threshold).sortBy(_._1).reverse
  }
}
