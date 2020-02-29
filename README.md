In order to generate streaming input data --> Use Netcat (an utility) as a data server. 
  
## Streaming_WordCount.scala
- in terminal 1: > ./netcat/nc -l -p 9999  (windows)        or    ./netcat/nc -lk 9999   (unix like systmes)
- in terminal 2: > .spark/bin/spark_submit <path_to_jar> localhost 9999
