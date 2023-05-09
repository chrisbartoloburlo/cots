package driver.model

case class SendStatement(label: String, responseCode: Int, statementID: String, types: Map[String, String], condition: String, continuation: Statement) extends Statement
