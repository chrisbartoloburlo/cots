---
title: Repeatable tests
subtitle: Understanding the outputs of the test drivers.
tags: [results]
---


Part of the output of the test driver is also an offline representation of failed tests as sequences of `curl` commands, which can be executed from a terminal to reproduce faults of the SUT without re-executing the test driver.

As an aid to the testers, the test driver also attempts to *minimise* its output: if it detects that different sequences of requests/responses trigger the same fault (i.e., the SUT responds to the same request with the same violation of the status code, response body, or assertion oracle), then the test driver only picks (one of) the shortest request/response sequence(s) which trigger that fault, and it only saves the corresponding sequence of curl commands. When this minimisation is toggled off, the test driver saves a curl-based test for every discovered sequence of requests/responses that leads to a fault.