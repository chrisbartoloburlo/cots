---
title: Results
subtitle: Understanding the outputs of the test drivers.
tags: [results]
---

Every time the test driver receives a response from the SUT, it assesses its correctness by considering a series of test oracles:

1. *Response code*: first, the test driver checks the HTTP response status code. This is the part of an HTTP response indicating whether the request has been successfully fulfilled. In the model, one may specify any expected code for the response (as in M_clinic in Section III). If the SUT sends a response with an unexpected code, the SUT fails the test;

2. *Response body*: then, the test driver checks the HTTP response body. The schema describing the structure of the response body is part of the OpenAPI specification. The test driver parses the response body received from the SUT: if the body does not match the OpenAPI specification, the SUT fails the test; (note: this test oracle only considers the structure of the response body and not its contents)

3. *Assertions*: finally, the test driver checks whether the response contents satisfy the assertions (if any) specified in the model. Such assertions are Scala expression returning a boolean; if an assertion returns false, the SUT fails the test.