package fetch
import org.jsoup.Jsoup
import scala.collection.JavaConverters._


object TWESFetcher extends BaseFetch {

  val TWSE_BASE_URL = "http://www.twse.com.tw/"

  override def fetch(year: Int, month: Int, sid: Int, retry: Int = 5, sleepTime: Int = 5000): List[Map[String,Any]] = {

    val dateList = generateDateList(year, month)

    val fetchHtml = dateList
      .map { case (year, month) =>

        val document = Jsoup
          .connect(TWSE_BASE_URL + s"exchangeReport/STOCK_DAY?response=html&date=$year$month&stockNo=$sid")
          .get()

        Thread.sleep(sleepTime)

        val dataElement = document.body()
          .select("div")
          .select("table")
          .select("tbody")
          .select("tr")
          .select("td")
          .asScala

        dataElement
          .map(data => data.text())
          .toList
        }

    val preProcessedData = preProcessData(fetchHtml)

    preProcessedData
  }


  override def preProcessData(fetchHtml: List[List[String]]): List[Map[String,Any]] = {

    val typeCorrectList = fetchHtml
      .map(data => data
        .map(data => {
            if(data.contains(",")){
              data.replace(",", "").toDouble
            } else if (data.contains("/")){
              data
            } else {
              data.replace("X","").toDouble
            }
          })
      )

    val stockDataList = for(eles <- typeCorrectList; ele <- eles.grouped(9)) yield {
      Map("date"->ele(0),"capacity"->ele(1),"turnover"->ele(2),"open"->ele(3),"high"->ele(4),"low"->ele(5),"close"->ele(6),"change"->ele(7),"transaction"->ele(8))

    }

    stockDataList
  }

  override def purify(originalData: List[Int]): List[Int] = ???

}