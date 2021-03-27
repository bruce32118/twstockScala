# twstockScala

This project is rewrited by scala from twstock https://github.com/mlouielu/twstock.


# Example usage

### Easy usage
```scala
// The Stock class will fetch 2 month stock data from now in default.
val stockExample = Stock(2330)
stockExample.data
stockExample.price
stockExample.transaction
```


### If you want to fetch more than two months data, you can use Stock.fromfetch.
```scala
val stockExample = Stock.fromfetch(2020, 11, 2330)
stockExample.data
stockExample.price
stockExample.transaction
```

## MA value
```scala
val stockExample = Stock.fromfetch(2020, 11, 2330)
// 30 ma
stockExample.movingAverage(stockExample.price, days=30)
```

## KD Value
```scala
// K值D值的公式，都會參照前一天的值去運算，因此當你parser的天數較少時(預設KD值一開始都是0)，會跟你在其他網站上看到的會有些落差，
// 因此還是建議你將資料爬回自己的資料庫，然後在套入公式算出來，之後也會新增這些function
val stockExample = Stock.fromfetch(2020, 11, 2330)
val kValue = stockExample.kValue(stockExample.price, stockExample.high, stockExample.low)
val dValue = stockExample.dValue(kValue)
```