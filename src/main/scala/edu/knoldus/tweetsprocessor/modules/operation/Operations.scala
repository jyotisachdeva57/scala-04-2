package edu.knoldus.tweetsprocessor.modules.operation

import edu.knoldus.tweetsprocessor.modules.TokenAccessor.Connection
import org.apache.log4j.Logger
import twitter4j.Query
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class Operations {
  val log: Logger = Logger.getLogger(this.getClass)

  /**
    * function that returns list of tweets having text and user name
    *
    * @param hashTag
    * @return
    */
  val connectionObj = new Connection
  def getTweets(hashTag: String): Future[List[Tweets]] = {

    Future {
      try {
        val tweets = connectionObj.getObject(hashTag)
        val allTweets = tweets.map {
          tweet =>
            Tweets(tweet.getText, tweet.getUser.getScreenName)
        }
        allTweets
      } catch {
        case ex: Exception => log.info(ex.getMessage)
          List[Tweets]()
      }
    }
  }


  /**
    * function that gives average number of tweets based on hashTag
    *
    * @param hashTag
    * @return
    */
  def averageTweets(hashTag: String): Future[Double] = {
    Future {
      val total = connectionObj.getObject(hashTag)
      val query = new Query(hashTag)
      query.setSince("2018-02-30")
      val feedsMapByDate = total.groupBy(_.getCreatedAt)
      feedsMapByDate.size / total.size.toDouble
    }
  }

  /**
    * function that returns total number of tweets based on hashTag
    *
    * @param tweetQuery
    * @return
    */

  def getNumberOfTweets(tweetQuery: String): Future[Int] = {
    Future {
      ConnectionObj.getObject(tweetQuery).size
    }
  }

  /**
    * function that returns average number of likes and reTweets based on hashTag
    *
    * @param tweetQuery
    * @return
    */

  def averageLikesAndReTweets(tweetQuery: String): Future[(Double, Int)] = {
    Future {
      try {
        val listTweets = ConnectionObj.getObject(tweetQuery)
        val listReTweets = listTweets.map(x => x.getRetweetCount).sum
        val listLikes = listTweets.map(x => x.getFavoriteCount).sum
        val totalTweets = listTweets.size
        (listLikes.toDouble / totalTweets.toDouble, listReTweets / totalTweets)
      }
      catch {
        case ex: Exception =>
          log.info(ex.getMessage)
          throw new Exception(ex)
          (0.0, 0)
      }
    }
  }
}

