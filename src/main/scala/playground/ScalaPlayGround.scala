package playground

import twstock.Stock

object ScalaPlayGround extends App {

  val z = Stock.fetchFrom(2021, 2, 2330)
  print(z.movingAverage(z.price, 60))


}

