package twstock
import analytics.Analytics
import fetch.TWESFetcher

import java.time.{ZoneId, ZonedDateTime}



class Stock(val sid: Int, val initial_fetch: Boolean) extends Analytics {

  private var _data : List[Map[String, Any]] = initial_fetch match {
    case true => {
      val date = ZonedDateTime.now(ZoneId.of("UTC")).minusMonths(1)
      fetch(date.getYear, date.getMonthValue)
    }
    case false => List()
  }

  def data: List[Map[String, Any]] = _data

  def date: List[String] = _data.map(data => data.get("date")).filter(_ != None).map(_.get.toString)
  def capacity: List[Double] = _data.map(data => data.get("capacity")).filter(_ != None).map(_.get.toString.toDouble)
  def turnover: List[Double] = _data.map(data => data.get("turnover")).filter(_ != None).map(_.get.toString.toDouble)
  def open: List[Double] = _data.map(data => data.get("open")).filter(_ != None).map(_.get.toString.toDouble)
  def high: List[Double] = _data.map(data => data.get("high")).filter(_ != None).map(_.get.toString.toDouble)
  def low: List[Double] = _data.map(data => data.get("low")).filter(_ != None).map(_.get.toString.toDouble)
  def price: List[Double] = _data.map(data => data.get("close")).filter(_ != None).map(_.get.toString.toDouble)
  def transaction: List[Double] = _data.map(data => data.get("transaction")).filter(_ != None).map(_.get.toString.toDouble)
  def fetch(year: Int, month: Int): List[Map[String, Any]] = TWESFetcher.fetch(year, month, sid)

}

object Stock {
  def apply(sid: Int, initial_fetch: Boolean=true): Stock = new Stock(sid, initial_fetch)

  def fetchFrom(year: Int, month: Int, sid: Int, initial_fetch: Boolean=false): Stock = {
    val stock = new Stock(sid, initial_fetch)
    val data = stock.fetch(year, month)
    stock._data = data
    stock
  }
}
