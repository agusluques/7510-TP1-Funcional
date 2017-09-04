(ns rule
	(:require [clojure.string :as str]
		[clean :refer :all]
		[resolver :refer :all])
)


(defn replace-params [facts rule-args query-args]
  "dada una lista de variables y de valores, reemplaza en la sentencia la primer variable por el primer valor"
  (if (> (count rule-args) 0)
      (do
        (replace-params 
          (str/replace facts (last rule-args) (last query-args)) 
          (butlast rule-args) 
          (butlast query-args)
        )
      )
      facts
  )
)

(defrecord Rule [rule-name rule-args listof-facts]
	Resolver
	(resolve-query [this query]
		(let [query-name (clean (subs query 0 (str/index-of query "(")))
			query-args (clean (subs query (+ (str/index-of query "(") 1) (str/index-of query ")")))]
			(if (= query-name (:rule-name this))
				(let [list-queryargs (map clean (str/split query-args #","))
					list-ruleargs (map clean (str/split rule-args #","))]
					(map clean (str/split (replace-params (str/join "," listof-facts) list-ruleargs list-queryargs) #"(?<=\)),"))


				)
			)
		)
	)
)