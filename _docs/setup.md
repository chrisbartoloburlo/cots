---
title: Understanding the syntax
subtitle: This covers the setup and options of SeTTS.
tags: [setup]
---

```shell
T = Int | String | Bool | ...               #data types
P = x:T, ..., x':T'                         #payloads
S = +{!operation_id(P:g)<f>.S, ...}         #internal choice 
    | &{?response_code(P)<f>.S, ...}        #external choice
    | rec X.S                               #recursive statement
    | X                                     #recursive variable
    | end                                   #termination
```
The variables `x,...,x'` in payloads `P` are pairwise distinct and are categorised by the corresponding data type `T`.
<!-- , which is a data type annotated with an optional generator `g`. -->
We assume a finite set of data types, including standard types (e.g., `Int`, `String`, etc. ) and user-defined types. 

The communication units of our syntax are the output and input prefixes, respectively `!l(P)` and `?l(P)` with a message label `l` and message payload description `P`; the message payload `P`, in turn, assigns a variable name and a type to the message contents. Functions `f` are boolean expressions that may refer to the variables occurring in preceding payload descriptions. They may be used to set assertions on the payloads received or on payloads being sent. 

An internal choice allows the test driver to choose the continuation of the protocol; more precisely, in `+{!l(P:g)[A].S, ...}` the driver selects the k-th option for some `k` in the set, generate a payload value `v` via the corresponding generator `g`, and send the output message `!l(v)` to the server; then, the protocol continues as specified in `S`.

In an external choice `&{?l(P)[A].S, ...}`, the test driver instead waits to receive some message `?l(P)`, for any `k` chosen by the other server; so, if the k-th input `?l(P)` is received, the variables in P are instantiated with the payload of the received message, and the function `f` is checked under such instantiation; if the assertion within the function holds, the party continues according to `S`. Both external choices and internal choice bind the variables that appear in the assertions occurring on their branches and in their continuations.


<style type="text/css" media="screen">
  #steditor { 
    position: relative;
    margin: auto;
    width: 700px;
    height: 35px;
  }
  #openapieditor { 
    position: relative;
    margin: auto;
    width: 700px;
    height: 180px;
  }
</style>

## Example
<div id="steditor">S_fs=+{!ProductApi.addProduct(productName: String(const "Product_1")).?C201(),
          !ProductApi.addProduct(productName: String(const "Product_1")).?C201()}</div>

The `ProductApi` from the specification above refers to the `tag` of the particular request (from the OpenAPI specification below) we would like to send. Similarly, the `addProduct` refers to the `operationId` below. 

<div id="openapieditor">/products/{productName}:
  post:
    tags: product
    operationId: addProduct
    parameters:
      - name: productName
        in: path
        type: string
    responses:
      '201': 
        description: successful operation</div>

<script type="text/javascript" src="../src/ace.js" charset="utf-8"></script>

<script type="text/javascript">
  var steditor = ace.edit("steditor");
  steditor.setTheme("ace/theme/dawn");
  steditor.session.setMode("ace/mode/st");
  steditor.setReadOnly(true);

  var openapieditor = ace.edit("openapieditor");
  openapieditor.setTheme("ace/theme/dawn");
  openapieditor.session.setMode("ace/mode/yaml");
  openapieditor.setReadOnly(true);
</script>




<!-- ### Update favicon

You can find the current favicon (favicon.png) inside the theme `/uploads/` directory, just replace it with your new favicon. -->