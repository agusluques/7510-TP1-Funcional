(ns logical-interpreter
	(:require [clojure.string :as str])
)

(defn clean [string]
	 (str/trim string)
)

(defn save-fact [fact]
	(let [fact-key (clean (subs fact 0 (str/index-of fact "(")))
		fact-args (clean (subs fact (+ (str/index-of fact "(") 1) (str/index-of fact ")")))]

		(let [list-args  (map clean (str/split fact-args #","))]
			(hash-map (keyword (clean fact-key))  list-args)
		)
	)	
)

(defn save-rule [rule] false )

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
	(let [query-key (clean (subs query 0 (str/index-of query "(")))
		query-args (clean (subs query (+ (str/index-of query "(") 1) (str/index-of query ")")))]
		(let [fact-args (map #((% query-key)) database)]
			(if (nil? fact-args) 
				nil
				true
			)
		)
		
	)

)

(defn exec-query-rule [database query] nil

)

(defn exec-query [database query]
	(let [result-fact (exec-query-fact database query)] 
		(if (nil? result-fact)
			(exec-query-rule database query)
			result-fact
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
