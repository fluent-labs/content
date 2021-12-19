package io.fluentlabs.content.types.internal.definition

import io.fluentlabs.content.types.Language.Language
import DefinitionSource.DefinitionSource
import io.fluentlabs.content.types.internal.word.PartOfSpeech.PartOfSpeech
import io.fluentlabs.dto.v1.definition.DefinitionDTO
import io.fluentlabs.content.types.internal.word.PartOfSpeech

import scala.jdk.CollectionConverters._

case class EnglishDefinition(
    subdefinitions: List[String],
    ipa: String,
    tag: PartOfSpeech,
    examples: Option[List[String]],
    // These fields are needed for elasticsearch lookup
    // But do not need to be presented to the user.
    definitionLanguage: Language,
    wordLanguage: Language,
    source: DefinitionSource,
    token: String
) extends Definition {
  val id: String = generateId()

  override lazy val toDTO: DefinitionDTO =
    new DefinitionDTO(
      id,
      subdefinitions.asJava,
      PartOfSpeech.toDTO(tag),
      DefinitionSource.toDTO(source),
      examples.getOrElse(List()).asJava
    )
}
