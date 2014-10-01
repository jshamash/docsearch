package com.docsearch.search

import scala.collection.mutable

object InvertedIndexSearch extends Search {

  //  term -> (doc, # occurrences, position)
  val index: mutable.Map[String, List[(String, Int, Int)]] = mutable.Map()
  var numDocs = 0

  def indexDocument(documentName: String, doc: String) = {
    val words = doc.split("[^\\w]+")
    val wordMap = words.groupBy(_.toLowerCase).map {
      case (k, v) => (k, (v.size, s"\\b(?i)$k\\b".r.findFirstMatchIn(doc).get.start + 1))
    }
    wordMap foreach { case (term, (occurrences, position)) =>
      val list = index.getOrElse(term, List())
      index += (term -> ((documentName, occurrences, position) :: list))
    }
    numDocs += 1
  }

  def search(searchTerms: String): List[SearchResult] = {
    // Vector of search terms
    val terms = searchTerms.split("[^\\w]+").toList
    // Replace each search term by its appearance in the index
    val q = terms map { term => index.getOrElse(term.toLowerCase, List())}

    val sumSquaredWeights = q.foldLeft(0.0)((sum, terms) => math.pow(sum + numDocs / (1 + terms.size), 2))
    val queryNorm = 1 / math.sqrt(sumSquaredWeights)

    // For each term, t, represented by the vector, and for each document, d, that t appears in, calculate
    // tfidf(t, d, D) = tf(t, d) * idf(t, D)
    // where D is the document corpus.
    val results = (q map { termAppearances =>
      val idf = numDocs / (1 + termAppearances.size)
      termAppearances map { case (docName, occurrences, position) =>
        val tf = 1 + math.log(occurrences)
        val tfidf = tf * idf
        (docName, tfidf, position)
      }
    }).flatten.groupBy(_._1).map {
      case (k, v) =>
        val x = v map { case (x1, x2, x3) => (x2, x3)}
        val (score, pos) = x reduceLeft { (t1, t2) => (t1._1 + t2._1, math.min(t1._2, t2._2))}
        SearchResult(k, pos, (score * queryNorm * 10).toInt)
    }

    results.toList.sortBy(- _.score)
  }
}
