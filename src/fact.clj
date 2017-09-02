(ns fact
	(:require [clojure.string :as str]
		[clean :refer :all]
		[resolver :refer :all])
)

(defrecord Fact [fact-name fact-args]
	Resolver
	(can-resolve-query? [this query]
		(let [query-name (clean (subs query 0 (str/index-of query "(")))]
			(if (= query-name (:fact-name this))
				true
				false
			)
		)
	)
)