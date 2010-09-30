(ns hozumi.test-embed
  (:use [hozumi.embed] :reload)
  (:use [clojure.test]))

(deftest test-embed
  (let [a 2]
    (are [expected input] (= expected (embed input))
	 "3"       "#{(+ 1 a)}"
	 "ab3"     "ab#{(+ 1 a)}"
	 "ab3cd"   "ab#{(+ 1 a)}cd"
	 "ab3cd4"  "ab#{(+ 1 a)}cd#{(+ 2 2)}"
	 "abcde"   "a#{(str \\b \\c \\d)}e"
	 "[:a 1]"  "#{[:a 1]}"
	 "{:a 1}"  "#{{:a 1}}"
	 "#{:a}"   "#{#{:a}}")))
