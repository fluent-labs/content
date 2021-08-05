package io.fluentlabs.content.types.external.definition.webster.common

import io.fluentlabs.content.util.JsonSequenceHelper

case class WebsterDefinedRunOnPhrase(
    definedRunOnPhrase: String,
    definition: Seq[WebsterDefinition],
    labels: Option[Seq[String]],
    pronunciations: Option[Seq[WebsterPronunciation]],
    areaOfUsage: Option[String],
    subjectStatusLabels: Option[Seq[String]],
    variations: Option[Seq[WebsterVariant]]
)
object WebsterDefinedRunOnPhrase {
  implicit val readsString: Reads[List[String]] = Reads.list[String]
  implicit val reads: Reads[WebsterDefinedRunOnPhrase] = (
    (JsPath \ "drp").read[String] and (JsPath \ "def")
      .read[List[WebsterDefinition]](
        WebsterDefinition.helper.readsList
      ) and (JsPath \ "lbs")
      .readNullable[List[String]] and (JsPath \ "prs")
      .readNullable[List[WebsterPronunciation]](
        WebsterPronunciation.helper.readsList
      ) and (JsPath \ "psl")
      .readNullable[String] and (JsPath \ "sls")
      .readNullable[List[String]] and (JsPath \ "vrs")
      .readNullable[List[WebsterVariant]](WebsterVariant.helper.readsList)
  )(WebsterDefinedRunOnPhrase.apply _)
  implicit val writes: Writes[WebsterDefinedRunOnPhrase] =
    Json.writes[WebsterDefinedRunOnPhrase]
  implicit val helper: JsonSequenceHelper[WebsterDefinedRunOnPhrase] =
    new JsonSequenceHelper[WebsterDefinedRunOnPhrase]
}
