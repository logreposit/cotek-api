# Cotek API Service

| branch | CI build | test coverage |
|--------|:--------:|--------------:|
| master  | [![CircleCI](https://circleci.com/gh/logreposit/cotek-api-service/tree/master.svg?style=shield)](https://circleci.com/gh/logreposit/cotek-api-service/tree/master)   | [![codecov.io](https://codecov.io/gh/logreposit/cotek-api-service/branch/master/graphs/badge.svg)](https://codecov.io/gh/logreposit/cotek-api-service/branch/master/graphs/badge.svg)   |
| develop | [![CircleCI](https://circleci.com/gh/logreposit/cotek-api-service/tree/develop.svg?style=shield)](https://circleci.com/gh/logreposit/cotek-api-service/tree/develop) | [![codecov.io](https://codecov.io/gh/logreposit/cotek-api-service/branch/develop/graphs/badge.svg)](https://codecov.io/gh/logreposit/cotek-api-service/branch/develop/graphs/badge.svg) |

Interface to communicate with a Cotek Inverter over HTTP.

## Description

This service is based on Spring-Boot and provides an HTTP interface to communicate with the Cotek Inverter.
The communication with the Inverter itself is done via RS232.

There is also an optional integration for the logreposit API.

## Configuration

The configuration is done via environment variables.

TODO: table with configuration options and default/example values.
