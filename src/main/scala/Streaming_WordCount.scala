import org.apache.spark._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming._


object Streaming_WordCount {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a local StreamingContext with three working thread and batch interval of 10 secondz.
    // The master requires 2 cores to prevent a starvation scenario.

    val conf = new SparkConf().setMaster("local[2]").setAppName("Word Count in Streaming mode")
    val ssc = new StreamingContext(conf, Seconds(10))

    // Create a DStream that represents streaming data from a TCP source, specified as hostname (e.g. localhost) and port (e.g. 9999).
    val lines = ssc.socketTextStream("localhost", 9999)

    /*
    This lines DStream represents the stream of data that will be received from the data server. Each record in this DStream is a line of text.
    Next, we want to split the lines by space characters into words.
    */

    // Split each line into words
    val words = lines.flatMap(_.split(" "))

    // Count each word in each batch
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)

    // Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.print()

    /*
    Note that when these lines are executed, Spark Streaming only sets up the computation it will perform when it is started, and no real processing has started yet.
    To start the processing after all the transformations have been setup, we finally call
     */

    ssc.start()             // Start the computation
    ssc.awaitTermination()  // Wait for the computation to terminate
  }
}
