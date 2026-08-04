# Camel runtimes applications

You can use the published applications provided in this repository to simulate some random execution and failure. The container images are tagged after the Camel version that they support:

* docker run -it squakez/db-app-main:4.21.0
* docker run -it squakez/db-app-quarkus:4.21.0
* docker run -it squakez/db-app-sb:4.21.0

> NOTE: just replace the version with any existing released version.

The application is equipped with `camel-observability-services` in order to make it easy to work with [Camel Dashboard](https://camel-tooling.github.io/camel-dashboard/).

## Release process

It is convenient to bump each new release when all Camel runtimes are available to support (at least every minor version).

For each application, run the following:

```
./mvnw clean package
docker build -f src/main/docker/Dockerfile -t squakez/db-app-main:4.21.0 .
# You can also test it:
# docker run -it squakez/db-app-main:4.21.0
docker push squakez/db-app-main:4.21.0
```

You may replace the repository name, container name and version accordingly.
