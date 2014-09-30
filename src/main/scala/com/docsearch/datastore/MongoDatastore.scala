package com.docsearch.datastore

import com.mongodb.casbah.MongoURI
import com.mongodb.casbah.Imports._
import com.typesafe.config.ConfigFactory

object MongoDatastore {
  val config = ConfigFactory.load()
  val uri = config.getString("mongo.uri")
  val collectionName = config.getString("mongo.collection")

  val db = MongoURI(uri).connectDB match {
    case Left(e) => throw e
    case Right(db) => db
  }
  val collection = db(collectionName)

  def insertDocument(name: String, doc: String) = {
    collection.insert(MongoDBObject("name" -> name, "text" -> doc))
  }
}
