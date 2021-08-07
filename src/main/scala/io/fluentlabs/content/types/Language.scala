package io.fluentlabs.content.types

import cats.syntax.all._
import play.api.libs.json.{Reads, Writes}

object Language extends Enumeration {
  type Language = Value
  val CHINESE: Value = Value("CHINESE")
  val CHINESE_TRADITIONAL: Language.Value = Value("CHINESE_TRADITIONAL")
  val ENGLISH: Value = Value("ENGLISH")
  val SPANISH: Value = Value("SPANISH")
  val UNKNOWN: Value = Value("UNKNOWN")

  def fromString(s: String): Option[Language] =
    Language.values.find(_.toString === s)

  implicit val reads: Reads[Language] = Reads.enumNameReads(Language)
  implicit val writes: Writes[Language] = Writes.enumNameWrites
}
