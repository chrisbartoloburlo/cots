package driver.util

import com.github.tototoshi.csv.CSVWriter

import java.io.{File, PrintWriter}

class csvLogger(pathname: String) {
  private val f = new File(f"${pathname}")
  private val writer: CSVWriter = CSVWriter.open(f)

  def log(info: String): Unit = {
    writer.writeRow(info.split(" "))
  }

  def log(info: List[String]): Unit ={
    for (i <- info){
      log(i)
    }
  }
}

class logger(pathname: String) {
  val writer = new PrintWriter(new File(pathname ))
  def log(info: String): Unit ={
    writer.write(info)
    writer.close()
  }
}
