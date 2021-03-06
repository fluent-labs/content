package io.fluentlabs.content.types.internal.definition

import io.fluentlabs.content.types.Language
import io.fluentlabs.content.types.internal.word.PartOfSpeech
import io.fluentlabs.dto.v1.definition.chinese.HSKLevel
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.Json

import scala.jdk.CollectionConverters._
import scala.compat.java8.OptionConverters._

class ChineseDefinitionTest extends AnyFunSpec {
  val example: ChineseDefinition = ChineseDefinition(
    subdefinitions = List("definition 1", "definition 2"),
    tag = PartOfSpeech.NOUN,
    examples = Some(List("example 1", "example 2")),
    inputPinyin = "ni3 hao3",
    inputSimplified = Some("你好"),
    inputTraditional = Some("你好"),
    definitionLanguage = Language.ENGLISH,
    source = DefinitionSource.MULTIPLE,
    token = "你好"
  )

  describe("A Chinese definition") {
    it("can properly generate an id") {
      assert(example.id == "CHINESE:你好:[ni] [xɑʊ̯]:Noun")
    }

    describe("when getting pronunciation") {
      it("can determine pronunciation from pinyin") {
        assert(example.pronunciation.pinyin == "ni hao")
        assert(example.pronunciation.ipa == "[ni] [xɑʊ̯]")
        assert(example.pronunciation.zhuyin == "ㄋㄧ ㄏㄠ")
        assert(example.pronunciation.wadeGiles == "ni hao")
      }
      it("does not break if invalid pinyin are provided") {
        val badPinyin = example.copy(inputPinyin = "invalid3")
        assert(badPinyin.pronunciation.pinyin == "")
        assert(badPinyin.pronunciation.ipa == "")
        assert(badPinyin.pronunciation.zhuyin == "")
        assert(badPinyin.pronunciation.wadeGiles == "")
        assert(badPinyin.pronunciation.tones.isEmpty)
      }
      it("does not break if no pinyin are provided") {
        val noPinyin = example.copy(inputPinyin = "")
        assert(noPinyin.pronunciation.pinyin == "")
        assert(noPinyin.pronunciation.ipa == "")
        assert(noPinyin.pronunciation.zhuyin == "")
        assert(noPinyin.pronunciation.wadeGiles == "")
      }
      it("does not accept invalid tones") {
        // Edge case warning! Pronunciation check is necessary
        // If you don't strip all tones when one tone is bad, then you might try to look up pinyin with tones included
        // That will come back empty, and is a bug.
        val badTone = example.copy(inputPinyin = "ni3 hao6")
        assert(badTone.pronunciation.pinyin == "ni hao")
        assert(badTone.pronunciation.ipa == "[ni] [xɑʊ̯]")
        assert(badTone.pronunciation.zhuyin == "ㄋㄧ ㄏㄠ")
        assert(badTone.pronunciation.wadeGiles == "ni hao")
        assert(badTone.pronunciation.tones.isEmpty)
      }

      it("can handle missing tones") {
        val missingTone = example.copy(inputPinyin = "ni hao")
        assert(missingTone.pronunciation.pinyin == "ni hao")
        assert(missingTone.pronunciation.ipa == "[ni] [xɑʊ̯]")
        assert(missingTone.pronunciation.zhuyin == "ㄋㄧ ㄏㄠ")
        assert(missingTone.pronunciation.wadeGiles == "ni hao")
        assert(missingTone.pronunciation.tones.isEmpty)
      }

      it("can handle partially missing tones") {
        val missingTone = example.copy(inputPinyin = "ni hao3")
        assert(missingTone.pronunciation.pinyin == "ni hao")
        assert(missingTone.pronunciation.ipa == "[ni] [xɑʊ̯]")
        assert(missingTone.pronunciation.zhuyin == "ㄋㄧ ㄏㄠ")
        assert(missingTone.pronunciation.wadeGiles == "ni hao")
        assert(missingTone.pronunciation.tones.isEmpty)
      }
    }

    describe("when getting HSK level") {
      it("can get HSK level") {
        val withHSK = example.copy(inputSimplified = Some("好"))
        assert(withHSK.hsk == HSKLevel.ONE)
      }

      it("does not break if there is no HSK level") {
        assert(example.hsk == HSKLevel.NONE)
      }
    }

    it("can convert itself to a DTO") {
      val converted = example.toDTO
      assert(example.id == converted.getId)
      assert(example.subdefinitions == converted.getSubdefinitions.asScala)
      assert(PartOfSpeech.toDTO(example.tag) == converted.getTag)
      assert(example.examples.contains(converted.getExamples.asScala))
      assert(example.simplified == converted.getSimplified.asScala)
      assert(
        example.traditional == converted.getTraditional.asScala.map(_.asScala)
      )
      assert(example.pronunciation.pinyin == converted.getPronunciation)
      assert(example.hsk == converted.getHsk)
    }

    it("can correctly serialize itself to JSON") {
      val json: String = Json.stringify(Json.toJson(example))
      // These matter for elasticsearch lookup to work
      assert(json.contains("\"definitionLanguage\":\"ENGLISH\""))
      assert(json.contains("\"source\":\"MULTIPLE\""))
      assert(json.contains("\"wordLanguage\":\"CHINESE\""))
      assert(json.contains("\"ipa\":\"[ni] [xɑʊ̯]\""))
    }
  }
}
