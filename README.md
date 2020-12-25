# simple-checkers-with-no-lists
This is my first Java project. It's a game of checkers with no use of lists (any iterable data structures) as that was the requirement given.

It's all just one file and a single class. I've never written any Java code before this (not counting few rather simple exercises).

Board is 8x8 tiles. Information about pawns is stored in two variables for each player, more specifically on 9 bit sets representing:

`figure: standard pawn or queen | status: is still in game or is dead | color: black or white | 3 bits for coordinates Y | 3 bits for coordinates X`

It's not pretty by any means but it wasn't the purpose. The purpose was to do something in Java and familarize myself with how to write it and with the bitwise operations. I was actually told by my school to use only the most basic parts of the language so I limited myself in most of my actions.
