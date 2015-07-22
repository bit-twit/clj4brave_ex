(println "Just started")

(defn inc-map-values
  [map-to-inc]
  (into {} (map (fn [[key value]] [key (inc value)])
                map-to-inc)))

(defn filter-letters
  [expression letter]
  (filter
   (fn [element] (if (= element letter) false true))
   (seq expression)))
