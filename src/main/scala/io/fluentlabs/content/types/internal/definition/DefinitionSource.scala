package io.fluentlabs.content.types.internal.definition

import cats.syntax.all._
import io.fluentlabs.dto.v1.definition.DefinitionSourceDTO
import play.api.libs.json._

/*
 * An enum that defines where a definition came from
 */
object DefinitionSource extends Enumeration {
  type DefinitionSource = Value

  val CEDICT: Value = Value("CEDICT")
  val MIRRIAM_WEBSTER_LEARNERS: Value = Value("MIRRIAM_WEBSTER_LEARNERS")
  val MIRRIAM_WEBSTER_SPANISH: Value = Value("MIRRIAM_WEBSTER_SPANISH")
  val WIKTIONARY: Value = Value("WIKTIONARY")
  val WIKTIONARY_CHINESE: Value = Value("WIKTIONARY_CHINESE")
  val WIKTIONARY_DANISH: Value = Value("WIKTIONARY_DANISH")
  val WIKTIONARY_ENGLISH: Value = Value("WIKTIONARY_ENGLISH")
  val WIKTIONARY_SIMPLE_ENGLISH: Value = Value("WIKTIONARY_SIMPLE_ENGLISH")
  val WIKTIONARY_SPANISH: Value = Value("WIKTIONARY_SPANISH")
  val MULTIPLE: Value = Value("MULTIPLE")

  def fromString(source: String): Option[DefinitionSource] =
    DefinitionSource.values.find(_.toString === source)

  // Makes sure we can serialize and deserialize this to JSON
  implicit val sourceFormat: Format[DefinitionSource] =
    new Format[DefinitionSource] {
      def reads(json: JsValue): JsResult[DefinitionSource] =
        fromString(json.as[String]) match {
          case Some(source) => JsSuccess(source)
          case None         => JsError("Unknown definition source")
        }
      def writes(source: DefinitionSource.DefinitionSource): JsString =
        JsString(source.toString)
    }

  def toDTO(source: DefinitionSource): DefinitionSourceDTO =
    source match {
      case CEDICT => DefinitionSourceDTO.CEDICT
      case MIRRIAM_WEBSTER_LEARNERS =>
        DefinitionSourceDTO.MIRRIAM_WEBSTER_LEARNERS
      case MIRRIAM_WEBSTER_SPANISH =>
        DefinitionSourceDTO.MIRRIAM_WEBSTER_SPANISH
      case WIKTIONARY_SIMPLE_ENGLISH =>
        DefinitionSourceDTO.WIKTIONARY_SIMPLE_ENGLISH
      case _ => DefinitionSourceDTO.MULTIPLE
    }
}
