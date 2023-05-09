# SeTTS: Session Type Test Synthesis

## Compiling the sources

1. You will need:
    * a Java SE Development Kit (recommended: version 11 LTS)
    * [`sbt`](https://www.scala-sbt.org/download.html) 1.5.0
2. From the main folder containing this `README.md` file, execute the command:
    ```shell
      sbt compile
    ```

## Instructions

The remainder of this file explains:
  * how to [write a specification](#writing-a-specification)
  * how to [run the examples](#examples)

## Writing a Specification

```
T = Int(g) | String(g) | Bool(g) | ...
P = x:T, ..., x':T'
S = +{!l(P)<p>.S, ...} | &{?l(P)<A>.S, ...} | rex X.S | X | end
```
The variables `x,...,x'` are pairwise distinct and are categorised by the corresponding sort `T`, which is a data type annotated with an optional generator `g`. We assume a finite set of data types, including standard types (e.g., Int, String, etc. ) and user-defined types.

The communication units of our syntax are the output and input prefixes, respectively `!l(P)` and `?l(P)` with a message label `l` and message payload description `P`; the message payload `P`, in turn, assigns a variable name and a sort to the message contents. Assertions `A` are boolean expressions that may refer to the variables occurring in preceding payload descriptions. 

An internal choice allows the test driver to choose the continuation of the protocol; more precisely, in `+{!l(P)[A].S, ...}` the driver selects the k-th option with probability `p` for some k in the set, compute a payload value `v` satisfying the description `P`, and send the corresponding output message `!l(v)` to the other party; then, the protocol continues as specified in S.

In an external choice `&{?l(P)[A].S, ...}`, the test driver instead waits to receive some message `?l(P)`, for any k chosen by the other SUT; so, if the k-th input `?l(P)` is received, the variables in `P` are instantiated with the payload of the received message, and the assertion `A` is checked under such instantiation; if the assertion holds, the party continues according to `S`. Both external choices and internal choice bind the variables that appear in the assertions occurring on their branches and in their continuations.

## Examples

The source code of all examples is available under the [`examples/`]() directory.

To run the following examples make sure that the source code is compiled by running:
```shell
sbt examples/compile
```
This will automatically generate the test drivers together with other files required by the driver itself. 

### Pet-clinic
To start the [application](https://github.com/spring-petclinic/spring-petclinic-rest) run:
```shell
mvn spring-boot:run
```
To execute the generated tests, run:
```shell
sbt "project examples" "runMain examples.petclinic.Wrapper"
```
This will interact with the pet-clinic application to test the system. To increase the number of iterations go to the respective [wrapper](https://github.com/chrisbartoloburlo/rest-setts/blob/main/examples/src/main/scala/examples/petclinic/Wrapper.scala) and change the parameter (currently set to 3).

To change the session type you can do so [here](https://github.com/chrisbartoloburlo/rest-setts/blob/main/examples/src/main/scala/examples/petclinic/petclinic.st).