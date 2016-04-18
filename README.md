# finite-state-emulator
Emulates a finite state automata

The fsa is defined in the fsa.txt file in the root, it has 5 lines:

1. comma-separated list of states
2. a comma separated list of input symbols
3. the starting state
4. a comma-separated list of states corresponding to the accepting states
5. a semi-colon delimited list of delta rules of the form "<state>,<sigma>-><state>;"

