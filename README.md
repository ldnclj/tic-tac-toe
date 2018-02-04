# Clojure Dojo Tic-Tac-Toe

Tic-Tac-Toe in ClojureScript with live reloading using Reagent.

## Overview

Project started at the 2018-01-30 Clojure Dojo at Thoughtworks to try to setup a Figwheel/Clojurescript project and make Tic-Tac-Toe.

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL.

## Contributors

A group of us at the Dojo sat down to ponder docs `assoc`, `update-in` and `flexbox`

TODO: Add names if you like here

## Further work

- display the current player
- customise the playing pieces?
- use SVG for the board/pieces?

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
