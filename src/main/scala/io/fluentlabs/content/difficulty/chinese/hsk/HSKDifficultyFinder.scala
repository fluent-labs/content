package io.fluentlabs.content.difficulty.chinese.hsk

import io.fluentlabs.content.difficulty.CEFRDifficultyLevel.CEFRDifficultyLevel
import io.fluentlabs.content.difficulty.{CEFRDifficultyLevel, DifficultyFinder}
import io.fluentlabs.dto.v1.definition.chinese.HSKLevel
import io.fluentlabs.content.difficulty.{CEFRDifficultyLevel, DifficultyFinder}
import io.fluentlabs.content.util.ContentFileLoader

object HSKDifficultyFinder extends DifficultyFinder[HSKLevel] {
  private[this] val hsk: HskHolder = ContentFileLoader
    .loadJsonResourceFile[HskHolder]("/chinese/hsk.json")

  override def getDifficulty(token: String): HSKLevel = hsk.getLevel(token)
  override def convertDifficultyToCEFR(level: HSKLevel): CEFRDifficultyLevel =
    level match {
      case HSKLevel.ONE   => CEFRDifficultyLevel.A1_BEGINNING
      case HSKLevel.TWO   => CEFRDifficultyLevel.A2_ELEMENTARY
      case HSKLevel.THREE => CEFRDifficultyLevel.B1_INTERMEDIATE
      case HSKLevel.FOUR  => CEFRDifficultyLevel.B2_UPPER_INTERMEDIATE
      case HSKLevel.FIVE  => CEFRDifficultyLevel.C1_ADVANCED
      case HSKLevel.SIX   => CEFRDifficultyLevel.C2_PROFICIENCY
      case HSKLevel.NONE  => CEFRDifficultyLevel.C2_PROFICIENCY
    }
}
