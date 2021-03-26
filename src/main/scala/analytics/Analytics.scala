package analytics
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

class Analytics {
  def movingAverage(priceList: List[Double], days: Double): List[Double] = {
    @tailrec
    def auxMoving(priceList: List[Double], remainNumber: Double, days: Double, result: ListBuffer[Double]): List[Double] = {
      if(remainNumber > 0) {
        val averageNumber = priceList.takeRight(days.toInt).sum / days
        result += BigDecimal(averageNumber).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
        auxMoving(priceList.dropRight(1), remainNumber-1, days, result)
      } else {
        result.toList.reverse
      }
    }
    auxMoving(priceList, priceList.length - days + 1, days, ListBuffer.empty)
  }

}
