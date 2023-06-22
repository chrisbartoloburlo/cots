---
title: Invoking a test driver
subtitle: How to run tests via the test driver.
tags: [execution]
---


Once the driver is generated, we can package it by executing `sbt assembly` in a terminal located in the same directory as the generated code. This avoids compilation every time we want to execute the driver.

To run the driver itself, execute: 

`java -cp target/scala-2.13/$(package)-assembly-0.0.3.jar $(package).Wrapper $(iterations)`

Replace `$(package)` with the respective package name and `$(iterations)` with the number of tests to be executed. 