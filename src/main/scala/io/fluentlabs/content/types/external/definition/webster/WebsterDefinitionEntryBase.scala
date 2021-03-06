package io.fluentlabs.content.types.external.definition.webster

import io.fluentlabs.content.types.external.definition.webster.common.WebsterPartOfSpeech.WebsterPartOfSpeech
import io.fluentlabs.content.formatters.WebsterFormatter
import io.fluentlabs.content.types.internal.word.PartOfSpeech.PartOfSpeech
import io.fluentlabs.content.types.external.definition.webster.common.{
  WebsterDefinition,
  WebsterHeadwordInfo,
  WebsterMeta,
  WebsterPartOfSpeech
}

trait WebsterDefinitionEntryBase {
  val meta: WebsterMeta
  val headwordInfo: WebsterHeadwordInfo
  val partOfSpeech: Option[WebsterPartOfSpeech]
  val definitions: Seq[WebsterDefinition]
  val shortDefinitions: Seq[String]

  // Here are opinionated choices about how these map to our domain

  // Lazy because sometimes these are parsed by subclasses.
  // And we want those to evaluate first.
  // Otherwise we'll get a fun NPE.
  lazy val tag: Option[PartOfSpeech] = partOfSpeech match {
    case Some(pos) => Some(WebsterPartOfSpeech.toDomain(pos))
    case None      => None
  }

  val subdefinitions: List[String] = {
    val d = WebsterFormatter.formatSeq(
      definitions
        // senseSequence: Option[Seq[Seq[WebsterSense]]]
        // remove the nones
        .flatMap(_.senseSequence)
        // Our data model needs them flattened to one list
        .flatten
        .flatten
        // definingText: WebsterDefiningText => examples: Option[Seq[WebsterVerbalIllustration]]
        .flatMap(_.definingText.text)
    )

    if (d.nonEmpty) d.toList
    else WebsterFormatter.formatList(shortDefinitions.toList)
  }

  val examples: Option[List[String]] = {
    // definitions: Seq[WebsterDefinition]
    val e = WebsterFormatter.formatSeq(
      definitions
        // senseSequence: Option[Seq[Seq[WebsterSense]]]
        // remove the nones
        .flatMap(_.senseSequence)
        // Our data model needs them flattened to one list
        .flatten
        .flatten
        // definingText: WebsterDefiningText => examples: Option[Seq[WebsterVerbalIllustration]]
        .flatMap(_.definingText.examples)
        .flatten
        // Verbal Illustration means examples, so we can just get the text.
        .map(_.text)
    )
    if (e.isEmpty) None else Some(e.toList)
  }

  // TODO - find out how frequent this is.
  val pronunciation: String = {
    val prons: Option[Seq[String]] = headwordInfo.pronunciations match {
      case Some(p) =>
        val ipas = p.flatMap(_.ipa)
        if (ipas.isEmpty) {
          val writtenPronunciations = p.flatMap(_.writtenPronunciation)
          if (writtenPronunciations.isEmpty) None
          else Some(writtenPronunciations)
        } else Some(ipas)
      case None => None
    }

    val finalPron = prons match {
      case Some(p) => p.head
      case None =>
        headwordInfo.alternatePronunciations match {
          case Some(a) =>
            val alt = a.flatMap(_.ipa)
            if (alt.isEmpty) "" else alt.head
          case None => ""
        }
    }

    WebsterFormatter.format(finalPron)
  }

  // Id is either the token, or token:n where n is the nth definition for the token.
  val token: String = meta.id.split(":")(0)
}
