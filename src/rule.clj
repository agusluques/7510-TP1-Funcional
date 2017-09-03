(ns rule
	(:require [clojure.string :as str]
		[clean :refer :all]
		[resolver :refer :all])
)


(defrecord Rule [rule-name rule-args listof-facts]
	Resolver
	(resolve-query [this query]
		(let [query-name (clean (subs query 0 (str/index-of query "(")))]
			(if (= query-name (:rule-name this))
				true
				false
			)
		)
	)
)