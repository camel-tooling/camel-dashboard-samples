# Camel runtimes applications

You can use the published applications provided in this repository to simulate some random execution and failure. The container images are tagged after the Camel version that they support:

* docker run -it squakez/db-app-main:4.20.0
* docker run -it squakez/db-app-quarkus:4.20.0
* docker run -it squakez/db-app-sb:4.20.0

> NOTE: just replace the version with any existing released version.

The application is equipped with `camel-observability-services` in order to make it easy to work with [Camel Dashboard](https://camel-tooling.github.io/camel-dashboard/).
