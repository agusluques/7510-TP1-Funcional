(ns resolver)

(defprotocol Resolver (can-resolve-query? [this query]))