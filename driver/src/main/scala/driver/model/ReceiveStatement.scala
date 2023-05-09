package driver.model

case class ReceiveStatement(api: String, label: String, statementID: String, types: Map[String, (String, (String, List[String]))], var probability: Double, condition: String, continuation: Statement) extends Statement