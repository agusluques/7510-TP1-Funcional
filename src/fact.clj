(ns fact
	(:require [clojure.string :as str]
		[clean :refer :all]
		[resolver :refer :all])
)

(defrecord Fact [fact-name fact-args]
	Resolver
	(resolve-query [this query]
		(let [query-name (clean (subs query 0 (str/index-of query "(")))
			query-args (clean (subs query (+ (str/index-of query "(") 1) (str/index-of query ")")))]
			(if (and (= query-name (:fact-name this)) (= (map clean (str/split query-args #",")) (:fact-args this)))
				true
				nil
			)
		)
	)
)