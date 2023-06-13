---
title: Driver generation
subtitle: How to generate test drivers online.
tags: [generation]
---

Follow the steps <a href="{{ site.baseurl }}/generator/index.html">here</a> to generate drivers online. Below, we describe what goes on in the background. 

<img src="{{ site.baseurl }}/assets/img/SeTTs-service.png">

Testing a REST API requires us to generate a test driver. To do so, we first need to write the specification tailored to the system under test. The specification includes:
1. the OpenAPI specification of the SUT, which is typically available as part of the REST APIs and documentation of the SUT itself;
2. the SeTTS model; and
3. optionally, a "preamble" Scala file containing auxiliary definitions (e.g., functions that check assertions, or custom generators to produce randomised inputs for the SUT).

SeTTs takes as input the SeTTs model and the optional preamble, and generates the Scala source code of an executable test driver that interacts with the SUT according to the model. The test driver, in turn, interacts with the REST API exposed by the SUT by using a Scala API which is auto-generated from the provided OpenAPI specification, using OpenAPI Generator. When the test driver runs, it invokes such Scala API methods to send HTTP requests to the SUT, and to receive and parse its responses; the model determines which requests are sent (and in what order) by the test driver, and which responses are expected.

<img src="{{ site.baseurl }}/assets/img/cloud.png">