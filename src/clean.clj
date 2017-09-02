(ns clean
	(:require [clojure.string :as str])
)

(defn clean [string]
	 (str/trim string)
)