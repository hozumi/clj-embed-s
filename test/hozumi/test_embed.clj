(ns hozumi.test-embed
  (:use [hozumi.embed] :reload)
  (:use [clojure.test])
  (:require [clojure.java.io :as io]))

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
	 "#{:a}"   "#{#{:a}}")
    (is (= "あいうえお3漢字" (embed (io/file "test/resources/utf_8.txt"))))
    (is (= "あいうえお3漢字" (embed (io/file "test/resources/euc_jp.txt"))))
    (is (= "あいうえお3漢字" (embed (io/file "test/resources/shift_jis.txt"))))))