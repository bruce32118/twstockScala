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
