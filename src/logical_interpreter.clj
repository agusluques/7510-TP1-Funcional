(ns logical-interpreter
	(:require [clojure.string :as str]
		[clean :refer :all]
		[fact :refer :all]
		[rule :refer :all]
		[resolver :refer :all])
)

(defn save-fact [fact]
	(let [fact-key (clean (subs fact 0 (str/index-of fact "(")))
		fact-args (clean (subs fact (+ (str/index-of fact "(") 1) (str/index-of fact ")")))]

		(let [list-args  (map clean (str/split fact-args #","))]
			(->Fact fact-key list-args)
		)
	)	
)

(defn save-rule [rule] (->Rule "hola" "pep " '(1,2,3)) )

(defn define-fact-or-rule [fact-or-rule]
	(if (nil? (str/index-of fact-or-rule ":-"))
		(save-fact fact-or-rule)
		(save-rule fact-or-rule)
	)
)

(defn parse-database [database]
	(if (nil? (str/index-of database ") "))
		nil
		(map define-fact-or-rule (remove empty? (str/split database #"\n")))
	)
)

(defn exec-query-fact [database query]
	(println (map #(can-resolve-query? % query) database))

)

(defn exec-query-rule [database query]
	nil

)

(defn exec-query [database query]
	(let [rule-result (exec-query-rule database query)]
		(if (nil? rule-result)
			(let [fact-result (exec-query-fact database query)]
				(if (nil? fact-result)
					false
					true
				)
			)
		)
	)		
)

(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
	[database query]
	(let [parsed-database (parse-database database)]
		(if (nil? parsed-database)
			nil
			(exec-query parsed-database query)
		)
	)
)
