package edu.knoldus.tweetsprocessor.application

import edu.knoldus.tweetsprocessor.modules.operation.Operations
import org.apache.log4j.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object MenuLauncher {

  def main(args: Array[String]): Unit = {
    val log: Logger = Logger.getLogger(this.getClass)
    val obj1 = new Operations
    val tweet = "#BellLetsTalk"
    val tweetObj = obj1.getTweets(tweet)
    tweetObj onComplete {
      case Success(s) => log.info(s"\n\n\n\n\n\n Tweets Are:\n\n\n\n  $s")
      case Failure(s) => log.info(s.getMessage)
    }
    val time = 2000
    Thread.sleep(time)

    val numTweetObj = obj1.getNumberOfTweets(tweet)
    numTweetObj onComplete {
      case Success(s) => log.info(s"\n\n\n\n\nNumber of Tweets\n $s")
      case Failure(s) => throw new Exception
    }


    val avgTweetObj = obj1.averageTweets(tweet)
    avgTweetObj onComplete {
      case Success(s) =>
        log.info(s"\n\n\n\n\nAverage Tweets\n $s")
      case Failure(s) => throw new Exception
    }
    val sleepTime = 1000
    val sleepTimeSecond = 5000

    Thread.sleep(sleepTime)

    val likeObj = obj1.averageLikesAndReTweets(tweet)
    likeObj onComplete {
      case Success(s) => log.info(s"\n\n\n\n\nAverage likes and reTweets are:\n $s")
      case Failure(s) => throw new Exception
    }
    Thread.sleep(sleepTimeSecond)
  }
}


