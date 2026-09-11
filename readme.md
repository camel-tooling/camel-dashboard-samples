# Camel runtimes applications

You can use the published applications provided in this repository to simulate some random execution and failure. The container images are tagged after the Camel version that they support.

> NOTE: just replace the version with any existing released version.

## Camel Observability Services

The applications under `/obs-svc` directory are equipped with `camel-observability-services` in order to make it easy to work with [Camel Dashboard](https://camel-tooling.github.io/camel-dashboard/). They are best suited as they conform to the port convention used by the Camel Monitor Operator.

* docker run -it squakez/db-app-main:4.22.0
* docker run -it squakez/db-app-quarkus:4.22.0
* docker run -it squakez/db-app-sb:4.22.0

## Regular Camel applications

The applications under `/regular` directory are equipped with `camel-health` and `camel-metrics` in order to make it still compatible to work with [Camel Dashboard](https://camel-tooling.github.io/camel-dashboard/). Camel Monitor operator is able to parse the default port and endpoints coming from regular applications.

* docker run -it squakez/db-regular-app-main:4.22.0
* docker run -it squakez/db-regular-app-quarkus:4.22.0
* docker run -it squakez/db-regular-app-sb:4.22.0

## Release process

It is convenient to bump each new release when all Camel runtimes are available to support (at least every minor version).

For each application, run the following:

```
./mvnw clean package
docker build -f src/main/docker/Dockerfile -t squakez/db-app-main:4.22.0 .
# You can also test it:
# docker run -it squakez/db-app-main:4.22.0
docker push squakez/db-app-main:4.22.0
```

You may replace the repository name, container name and version accordingly.
