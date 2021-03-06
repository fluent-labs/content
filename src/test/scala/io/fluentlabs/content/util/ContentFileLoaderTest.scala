package io.fluentlabs.content.util

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import io.fluentlabs.content.difficulty.chinese.hsk.HskHolder
import io.fluentlabs.content.enrichers.chinese.ChinesePronunciationFromFile
import org.scalatest.funspec.AnyFunSpec

class ContentFileLoaderTest extends AnyFunSpec {
  describe("A content file loader") {
    it("can load files from json") {
      val goodFile = ContentFileLoader
        .loadJsonResourceFile[Seq[ChinesePronunciationFromFile]](
          "/chinese/pronunciation.json"
        )
      assert(goodFile.nonEmpty)
    }
    it("throws an exception if the file cannot be opened") {
      assertThrows[NullPointerException] {
        ContentFileLoader
          .loadJsonResourceFile[Seq[ChinesePronunciationFromFile]](
            "=/notfound.json"
          )
      }
    }
    it("throws an exception if the file is invalid") {
      assertThrows[IllegalStateException] {
        ContentFileLoader
          .loadJsonResourceFile[HskHolder](
            "/chinese/pronunciation.json"
          )
      }
    }
  }
}
