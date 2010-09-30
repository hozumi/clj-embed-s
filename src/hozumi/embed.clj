(ns hozumi.embed
  [:import [java.io PushbackReader StringReader]])

(defmacro embed
  [s]
  (let [s1 (PushbackReader. (StringReader. s))]
    (loop [sb (StringBuilder.)
	   acc []]
      (let [c (.read s1)
	    c (if (= -1 c) -1 (char c))]
	(condp = c
	    -1 `(str ~@acc ~(.toString sb))
	    \# (let [c2 (.read s1)
		     c2 (if (= -1 c) -1 (char c2))]
		 (condp = c2
		     -1 (recur (.append sb c) acc)
		     \{ (recur (StringBuilder.)
			       (conj acc
				     (.toString sb)
				     (let [r (read s1)]
				       (.read s1)
				       r)))
		     (recur (.append sb (str c c2)) acc)))
	    (recur (.append sb c) acc))))))
		   
	 