# Concurrent Museum Simulation
This repo contains my solutions for Assignment 1a of SWEN90004 2022S1. The goal of the project is to simulate a museum where tour groups are escorted into and out of rooms by guides, exploring any potential concurrency issues in the process. The implementation is in Java and it utilises threads and basic monitors to implement concurrent objects trying to access shared resources.

More details on the assignment specifications are available in [`specs.pdf`](./specs.pdf), and a reflection on my observation of the system behaviour is in [`reflection.txt`](./reflection.txt). Source files can be found in `src/`.

## How to Run
```sh
$ cd src/
$ javac *.java
$ java Main
```
