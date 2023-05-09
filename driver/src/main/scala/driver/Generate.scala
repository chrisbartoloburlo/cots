package driver

import driver.synth.Synth

import scala.util.Try

object Generate {
  def main(args: Array[String]): Unit = {
    val synth = new Synth()
    val preamble = Try(args(2))
    val rejectSameLabels = Try(args(3).toBoolean)
    synth.apply(args(0), args(1), preamble.getOrElse(""), rejectSameLabels.getOrElse(true), synthMonFile = true)
  }
}