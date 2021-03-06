package io.fluentlabs.content.formatters

object FormattingTags {
  // Emphasis
  val italic = "*"
  val bold = "**"
  val boldAndItalic = "***"
  val strikethrough = "~~"

  // Headings
  val levelOneHeading: String = "#".repeat(1)
  val levelTwoHeading: String = "#".repeat(2)
  val levelThreeHeading: String = "#".repeat(3)
  val levelFourHeading: String = "#".repeat(4)
  val levelFiveHeading: String = "#".repeat(5)
  val levelSixHeading: String = "#".repeat(6)

  val horizontalRule = "---"

  // HTML type tags
  val underlineOpen = "<ins>"
  val underlineClose = "</ins>"
}
