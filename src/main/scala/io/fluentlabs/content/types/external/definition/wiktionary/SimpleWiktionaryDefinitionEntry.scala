package io.fluentlabs.content.types.external.definition.wiktionary

import io.fluentlabs.content.types.Language.Language
import io.fluentlabs.content.types.Language
import io.fluentlabs.content.types.external.definition.DefinitionEntry
import io.fluentlabs.content.types.internal.definition.{
  Definition,
  DefinitionSource
}
import io.fluentlabs.content.types.internal.definition.DefinitionSource.DefinitionSource
import io.fluentlabs.content.types.internal.word.PartOfSpeech
import io.fluentlabs.content.types.internal.word.PartOfSpeech.PartOfSpeech

case class SimpleWiktionaryDefinitionEntry(
    // Required fields
    token: String,
    definition: String,
    tagRaw: String,
    ipa: String,
    override val subdefinitions: List[String],
    examplesRaw: List[String],
    // Nice extras
    antonyms: List[String],
    homonyms: List[String],
    homophones: List[String],
    notes: List[String],
    otherSpellings: List[String],
    pronunciationRaw: List[String],
    related: List[String],
    synonyms: List[String],
    usage: List[String]
) extends DefinitionEntry {
  override val source: DefinitionSource = SimpleWiktionaryDefinitionEntry.source
  override val definitionLanguage: Language =
    SimpleWiktionaryDefinitionEntry.definitionLanguage
  override val wordLanguage: Language =
    SimpleWiktionaryDefinitionEntry.wordLanguage
  override val pronunciation: String = pronunciationRaw.headOption.getOrElse("")
  override val tag: Option[PartOfSpeech] = Some(PartOfSpeech.withName(tagRaw))
  override val examples: Option[List[String]] = Some(examplesRaw)

  override def toDefinition(partOfSpeech: PartOfSpeech): Definition =
    DefinitionEntry.buildEnglishDefinition(this, partOfSpeech)
}

object SimpleWiktionaryDefinitionEntry {
  val source: DefinitionSource =
    DefinitionSource.WIKTIONARY_SIMPLE_ENGLISH
  val definitionLanguage: Language = Language.ENGLISH
  val wordLanguage: Language = Language.ENGLISH
}
