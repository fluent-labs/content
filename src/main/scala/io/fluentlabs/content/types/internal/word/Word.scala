package io.fluentlabs.content.types.internal.word

import io.fluentlabs.content.types.Language.Language
import Count.Count
import GrammaticalGender.GrammaticalGender
import PartOfSpeech.PartOfSpeech
import Word.{numberFormat, punctuation}
import WordTense.WordTense
import io.fluentlabs.dto.v1.word.WordDTO

case class Word(
    language: Language,
    token: String,
    tag: PartOfSpeech,
    lemma: String,
    gender: Option[GrammaticalGender],
    number: Option[Count],
    proper: Option[Boolean],
    tense: Option[WordTense],
    processedToken: String
) {
  val isPunctuation: Boolean = punctuation.contains(token)
  val isNumber: Boolean = token.matches(numberFormat)

  lazy val toDTO: WordDTO =
    new WordDTO(
      token,
      processedToken,
      tag.toString,
      lemma,
      isPunctuation,
      isNumber
    )
}
object Word {
  val punctuation = ",.?;'[]()（）`~!@#$%^&*/+_-=<>{}:，。？！·；：‘“、\"”《》"
  val numberFormat = "[0-9]+(.)*[0-9]*"

  def fromToken(token: String, language: Language): Word =
    Word(
      token = token,
      language = language,
      tag = PartOfSpeech.UNKNOWN,
      lemma = token,
      gender = None,
      number = None,
      proper = None,
      tense = None,
      processedToken = Word.processToken(token)
    )

  def processToken(token: String): String = token.toLowerCase
}
