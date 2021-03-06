package io.fluentlabs.content.types.internal.word

import cats.syntax.all._
import play.api.libs.json.{Reads, Writes}

object Count extends Enumeration {
  type Count = Value
  val SINGLE: Value = Value("Single")
  val PLURAL: Value = Value("Plural")
  val DUAL: Value = Value("Dual")

  implicit val reads: Reads[Count] = Reads.enumNameReads(Count)
  implicit val writes: Writes[Count] = Writes.enumNameWrites

  def fromString(s: String): Option[Count] =
    Count.values.find(_.toString === s)
}
