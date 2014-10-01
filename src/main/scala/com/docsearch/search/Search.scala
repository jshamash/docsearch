package com.docsearch.search

trait Search {
  def indexDocument(documentName: String, doc: String)
  def search(searchTerms: String): List[SearchResult]
}
