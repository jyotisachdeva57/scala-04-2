package edu.knoldus.tweetsprocessor.modules.TokenAccessor

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.log4j.Logger
import twitter4j._
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import scala.collection.JavaConverters._

class Connection {
  /**
    * function that sets up connection with twitter4j using authorization tokens and returns
    * list of tweets.
    *
    * @param str
    * @return
    */

  def getObject(str: String): List[Status] = {
    try {
      val parsedConfig: Config = ConfigFactory.parseFile(new File("src/main/resources/application.conf"))
      val conf: Config = ConfigFactory.load(parsedConfig)
      val consumerKey = conf.getString("consumerKey")
      val consumerSecretKey = conf.getString("consumerSecretKey")
      val accessToken = conf.getString("accessToken")
      val accessTokenSecret = conf.getString("accessTokenSecret")
      val configurationBuilder = new ConfigurationBuilder()
      configurationBuilder.setDebugEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecretKey)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)
      val twitter = new TwitterFactory(configurationBuilder.build).getInstance()
      val query = new Query(str)
      val timer=100
      query.setCount(timer)
      val twitterTweets = twitter.search(query).getTweets
      twitterTweets.asScala.toList
    } catch {
      case ex: Exception =>
        val log: Logger = Logger.getLogger(this.getClass)
        log.info(ex.getMessage)
        throw new Exception
    }
  }

}

