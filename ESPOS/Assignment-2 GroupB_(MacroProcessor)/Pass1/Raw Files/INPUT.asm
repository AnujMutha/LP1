MACRO
INCR &X &Y &Z
MOVER &Z &X
ADD &Z &Y
MOVEM &Z &X
MEND
MACRO
DECR &A &B &C
MOVER &C &A
SUB &C &B
MOVEM &C &A
MEND
START 100
READ NI
READ N2
INCR N1 N2 AREG
DECR N1 N2 BREG
STOP
N1 DS 1
N2 DS 1
END