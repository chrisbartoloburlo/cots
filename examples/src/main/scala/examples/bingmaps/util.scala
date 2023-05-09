package examples.bingmaps

import examples.openverse.model._

import scala.util.Random

object util {
  def getKey(rand: Random): String = {
    "ArS7O3iPEIPb2a_Ru91MVpfgDNFdajiwa1bgKWMBqTvAMX2AXzk6G_PH1-vcXxAh"
  }
  def getQuery(rand: Random): String = {
    "White House"
  }
  def constant(string: String, rand: Random): String = {
    string
  }
}
