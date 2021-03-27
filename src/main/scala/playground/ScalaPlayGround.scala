package playground

import twstock.Stock

object ScalaPlayGround extends App {

  val z = Stock.fetchFrom(2021, 2, 2330)
  println(z.price)
//  print(z.movingAverage(z.price, 60))


}

