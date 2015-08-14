(ns clj4brave-ex.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn is-palindrome
  [string]
  (every? #(= true %1)
          (map #(= %1 %2) string (reverse string))))
