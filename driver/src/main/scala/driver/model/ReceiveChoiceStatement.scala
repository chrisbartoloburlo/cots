package driver.model

case class ReceiveChoiceStatement(label: String, choices: List[Statement]) extends Statement
