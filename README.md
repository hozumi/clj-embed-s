# clj-embed-s

clj-embed-s enables you to embed Clojure expression into a string like Ruby.<br>
The trick is that when the macro is expanded, it divides a string into parts, which given to str function.

## Usage

    (use 'hozumi.embed)

    (embed "abc#{(+ 1 2)}def")
    => "abc3def"

    (let [gree "Hello"]
      (embed "#{gree} world!"))
    => "Hello world!"

## Installation
Leiningen
    [org.clojars.hozumi/clj-embed-s "1.0.0-SNAPSHOT"]