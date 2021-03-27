package fetch
import java.time.{ZoneId, ZonedDateTime}
import java.util.Date

abstract class BaseFetch {
  def fetch(year: Int, month: Int, sid: Int, retry: Int, sleepTime: Int): List[Map[String,Any]]
  def generateDateList(year: Int, month: Int): List[(Int, Int)] = {
      val nowDate = ZonedDateTime.now(ZoneId.of("UTC"))
      val nowM = nowDate.getMonthValue
      val nowY = nowDate.getYear
      val count = (nowY - year) * 12 + (nowM - month)
      val before = nowDate.minusMonths(count)
      val finalist = for (countValue <- 0 to count) yield {
        val directDate = before.plusMonths(countValue)
        (directDate.getYear, directDate.getMonthValue)
      }

      finalist.toList
  }
  def preProcessData(data: List[List[String]]): List[Map[String,Any]]
  def purify(originalData: List[Int]): List[Int]
}
