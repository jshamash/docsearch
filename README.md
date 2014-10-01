docsearch
=========

Scala application to index documents and query for search terms.

The index used is an [inverted index](http://en.wikipedia.org/wiki/Inverted_index). The index is a map
`word -> (docName, occurrences, position)`.
The search scoring algorithm is based on [tf-idf](http://en.wikipedia.org/wiki/Tf%E2%80%93idf) and is calculated as follows:
`score(q,d) = norm(q) * sum(t in q)(tf(t, d) * idf(t))`
where:
* `q` is the query vector (each component of the vector is one word of the query)
* `d` is a document
* `t` is a term
* `score(q, d)` is the score of document `d` on query `q`
* `tf(t,d) = 1 + log f(t,d)`, where `f(t,d)` is the number of occurences of `t` in `d`
* `idf(t,d) = log(N / (1 + |docsContaining(t)|))`
