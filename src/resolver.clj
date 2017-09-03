(ns resolver)

(defprotocol Resolver (resolve-query [this query]))