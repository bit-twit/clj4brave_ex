;;; tests
(println "I'm a little teapot")

(def failingfn (fn []
  (take)))

(map * '(8 4))

(defn test-vec-conj []
  (let [vectest [1 2 3]]
    (conj vectest 4)
    ))

(defn test-list-conj []
  (let [listtest '(1 2 3)]
    (conj listtest 4)
    ))

(defn test-set []
  (let [settest (set ["hanna" "hanna" 3 3 5])]
    (println settest)
    ))

(defn test-eval-symbol []
    (nth (str 'symbolName) 6)
    )

(defn old-fart-shouting [victim]
  (str "Motherfucker " victim " you, you, skunk !"))

(defn shout-at [& people]
  (map old-fart-shouting people))

(defn favorite-things
  [name & things]
  (str "Hi " name ", here are my favourite things :" (clojure.string/join "," things)))

(defn show-coordinatinates
  "Simple function to test destructuring"
  [{lat :lat long :long}]
  (println (str "Latitude is: " lat))
  (println (str "Latitude is: " long)))

(defn show-coordinatinates-simpler
  "Simple function to test destructuring - use :keys keyword to destructure map"
  [{:keys [lat long]}]
  (println (str "Latitude is: " lat))
  (println (str "Latitude is: " long)))

(defn say-hi
  [& dudes]
  (map (fn [name] (str "Hi " name)) dudes))

(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))

(defn needs-matching-part?
  [part]
  (re-find #"^left-" (:name part)))
(needs-matching-part? {:name "left-eye"})
; => "left-" ; this is truthy
(needs-matching-part? {:name "neckbeard"})
; => nil ; this is falsey

;;; HOBBIT exercise
(def hobbit-left-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn make-matching-part
  [{:keys [name size] :or {size 1}}]
  {:name (clojure.string/replace name #"^left-" "right-")
   :size size
   })

(defn symmetrize-body-parts
  "Expects a sequence of maps representing initial asymmetric body parts"
  [initial-parts]
  (loop [remaining-body-parts initial-parts
         final-body-parts []]
    (if (empty? remaining-body-parts)
      final-body-parts
      (let [[part & remaining-parts] remaining-body-parts
            final-body-parts (conj final-body-parts part)]
         (if (needs-matching-part? part)
           (recur remaining-parts (conj final-body-parts (make-matching-part part)))
           (recur remaining-parts final-body-parts))
         ))))

(defn my-reduce
  ([f initial coll]
   (loop [result initial remaining-col coll]
     (if (empty? remaining-col)
       result
       (recur (f result (first remaining-col)) (rest remaining-col)))))
  ([f [head & tail]]
   (my-reduce f head tail)))

(defn symmetrize-body-parts-with-reduce
  [parts]
  (reduce (fn [final-parts part]
            (let [final-parts (conj final-parts part)]
              (if (needs-matching-part? part)
                (conj final-parts (make-matching-part part))
                final-parts)))
          []
          parts))

(defn hit-hobbit
  [asym-body-parts]
  (let [sym-parts (symmetrize-body-parts-with-reduce asym-body-parts)
        body-size-sum (reduce + 0 (map :size sym-parts))
        target-size (inc (rand body-size-sum))]
    (loop [[part & rest] sym-parts
           accum-size (:size part)]
      (if (> accum-size target-size)
        part
        (recur rest (+ accum-size (:size part)))))))

