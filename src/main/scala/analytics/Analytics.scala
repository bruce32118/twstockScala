package analytics
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

class Analytics {
  def movingAverage(priceList: List[Double], days: Double): List[Double] = {
    @tailrec
    def auxMoving(priceList: List[Double], remainNumber: Double, days: Double, result: ListBuffer[Double]): List[Double] = {
      if(remainNumber > 0) {
        val averageNumber = priceList.takeRight(days.toInt).sum / days
        result += BigDecimal(averageNumber)
          .setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
        auxMoving(priceList.dropRight(1), remainNumber-1, days, result)
      } else {
        result.toList.reverse
      }
    }
    auxMoving(priceList, priceList.length - days + 1, days, ListBuffer.empty)
  }


  def RSV(closePriceList: List[Double], highPriceList: List[Double], lowPriceList: List[Double], rsvDays: Int = 9): List[Double] = {
    @tailrec
    def auxRSV(closePriceList: List[Double], highPriceList: List[Double], lowPriceList: List[Double], RSVList: List[Double]): List[Double] = {
      if(closePriceList.isEmpty) RSVList
      else {
        val RSVvalue = (closePriceList.head - lowPriceList.slice(0, rsvDays).min) / (highPriceList.slice(0, rsvDays).max - lowPriceList.slice(0, rsvDays).min)
        auxRSV(closePriceList.tail, highPriceList.tail, lowPriceList.tail, RSVList :+ RSVvalue)
      }
    }
    auxRSV(closePriceList.slice(rsvDays - 1, closePriceList.length), highPriceList, lowPriceList, List())
  }

  def kValue(closePriceList: List[Double], highPriceList: List[Double], lowPriceList: List[Double]): List[Double] = {

    val kListBuffer = ListBuffer(0.0)
    val rsvValue = RSV(closePriceList, highPriceList, lowPriceList)

    val kList = for (rsv <- rsvValue) yield {
      kListBuffer += kListBuffer.takeRight(1).head * 2 / 3 + (rsv / 3)
      kListBuffer.takeRight(1).head
    }

    kList
  }

}
