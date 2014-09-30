package com.docsearch.search

import scala.collection.mutable

object InvertedIndexSearch extends Search {

  //  term -> (doc, # occurrences, position)
  val index: mutable.Map[String, List[(String, Int, Int)]] = mutable.Map()

  def indexDocument(documentName: String, doc: String) = {
    val words = doc.split("[^\\w]+")
    val wordMap = words.groupBy(_.toLowerCase).map{
      case (k, v) => (k, (v.size, s"\\b(?i)$k\\b".r.findFirstMatchIn(doc).get.start + 1))
    }
    wordMap foreach { case (term, (occurrences, position)) =>
      val list = index.getOrElse(term, List())
      index += (term -> ((documentName, occurrences, position) :: list))
    }
    println(index)

  }

  // coord(q,d) * sum(t in q)(tf(t,d)*idf(t))
  def search(searchTerms: String): SearchResult = ???
}
