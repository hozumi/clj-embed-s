(ns hozumi.embed
  (require [hozumi.det-enc :as enc])
  (:import [java.io PushbackReader StringReader File
	    InputStreamReader FileInputStream]))

(defn- embed* [pbr]
  (loop [sb (StringBuilder.)
	 acc []]
    (let [c (.read pbr)
	  c (if (= -1 c) -1 (char c))]
      (condp = c
	  -1 `(str ~@acc ~(.toString sb))
	  \# (let [c2 (.read pbr)
		   c2 (if (= -1 c) -1 (char c2))]
	       (condp = c2
		   -1 (recur (.append sb c) acc)
		   \{ (recur (StringBuilder.)
			     (conj acc
				   (.toString sb)
				   (let [r (read pbr)]
				     (.read pbr)
				     r)))
		   (recur (.append sb (str c c2)) acc)))
	  (recur (.append sb c) acc)))))

(defmacro embed
  [s]
  (if (string? s)
    (embed* (-> s StringReader. PushbackReader.))
    (let [f (eval s)]
      (if (instance? File f)
	(let [encode (enc/detect f :default)
	      pbr (-> f FileInputStream. (InputStreamReader. encode) PushbackReader.)]
	  (embed* pbr))))))