# Concurrent Museum Simulation
This repo contains my solutions for Assignment 1 of SWEN90004 2022S1. The project is broken down into two parts: 1a and 1b.

### 1a
The goal of 1a is to simulate a museum where tour groups are escorted into and out of rooms by guides, seeking to observe any potential concurrency issues in the process. The implementation is in Java and it utilises threads and basic monitors to implement concurrent objects trying to access shared resources. Details on the assignment specifications are available in [`1a/specs.pdf`](./1a/specs.pdf), and a reflection on my observation of the system behaviour is in [`1a/reflection.txt`](./1a/reflection.txt). Source files can be found in `1a/src/`.

### 1b
1b, on the other hand, also models the same museum, but in LTS (Labelled Transition System) to allow automatic verification of safety and liveness properties of the system. Details on assignment specifications are available in [`1b/specs.pdf`](./1b/specs.pdf), and a discussion on the concurrency issues exposed by LTSA is in [`1b/discussion.txt`](./1b/discussion.txt).


## How to Run
```sh
# To run 1a
$ cd 1a/src/
$ javac *.java
$ java Main

# To run 1b
# Open the .lts files in 1b/ with LTSA (download from https://www.doc.ic.ac.uk/ltsa/).
```
