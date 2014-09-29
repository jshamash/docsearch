package com.docsearch.datastore

import com.typesafe.config.ConfigFactory
import com.mongodb.casbah.Imports._

object MongoDatastore {
  val config = ConfigFactory.load()
  val mongoUri = config.getString("mongo-uri")
  val mongoClient = MongoClient(mongoUri)
}
