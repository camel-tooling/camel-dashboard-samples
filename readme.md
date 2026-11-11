# Camel runtimes applications

You can use the published applications provided in this repository to simulate some random execution and failure. The container images are tagged after the Camel version that they support.

The application controls three environment variables which you can use to throttle the number of messages, change the error percentage of simulated errors and make the health endpoint fail randomly:

```
MESSAGE_RATE_PER_MINUTE             # default, 60
ERROR_PERCENTAGE                    # default, 10
HEALTH_FAIL_PERCENTAGE              # default, 0 (health is always UP)
```

> NOTE: just replace the version with any existing released version.

## Camel Observability Services

The applications under `/obs-svc` directory are equipped with `camel-observability-services` in order to make it easy to work with [Camel Dashboard](https://camel-tooling.github.io/camel-dashboard/). They are best suited as they conform to the port convention used by the Camel Monitor Operator.

* docker run -it quay.io/camel-tooling/sample-db-app-main:4.22.0
* docker run -it quay.io/camel-tooling/sample-db-app-quarkus:4.22.0
* docker run -it quay.io/camel-tooling/sample-db-app-sb:4.22.0

## Regular Camel applications

The applications under `/regular` directory are equipped with `camel-health` and `camel-metrics` in order to make it still compatible to work with [Camel Dashboard](https://camel-tooling.github.io/camel-dashboard/). Camel Monitor operator is able to parse the default port and endpoints coming from regular applications.

* docker run -it quay.io/camel-tooling/sample-db-regular-app-main:4.22.0
* docker run -it quay.io/camel-tooling/sample-db-regular-app-quarkus:4.22.0
* docker run -it quay.io/camel-tooling/sample-db-regular-app-sb:4.22.0

## Run on Kubernetes and monitor with Camel Monitor

Any of the images above can be deployed as a regular `Deployment`. The [Camel Monitor Operator](https://github.com/camel-tooling/camel-monitor-operator) must be installed in the cluster.

```bash
kubectl create deployment camel-app --image=quay.io/camel-tooling/sample-db-app-main:4.22.0
```

To let the operator discover and monitor the application, label the `Deployment` with `camel.apache.org/monitor`. The value is the name of the monitor that will group the applications:

```bash
kubectl label deployment camel-app camel.apache.org/monitor=camel-sample
```

The operator creates the related `CamelMonitor` resource and starts to collect health and metrics from the application pods. Removing the label stops the monitoring:

```bash
kubectl label deployment camel-app camel.apache.org/monitor-
```

### Tune the application behavior

The behavior is controlled by the environment variables listed above. You can change them on a running `Deployment` (the pods are restarted to apply them):

```bash
# 120 messages per minute, 30% of them failing and the health endpoint failing 20% of the time
kubectl set env deployment/camel-app MESSAGE_RATE_PER_MINUTE=120 ERROR_PERCENTAGE=30 HEALTH_FAIL_PERCENTAGE=20

# Go back to the defaults
kubectl set env deployment/camel-app MESSAGE_RATE_PER_MINUTE- ERROR_PERCENTAGE- HEALTH_FAIL_PERCENTAGE-
```

> NOTE: with `HEALTH_FAIL_PERCENTAGE` greater than 0 the application randomly reports DOWN. If a liveness probe is configured, Kubernetes may restart the pod.

## Release process

### Automatic process

This repository has a release process that can be triggered manually using Github actions. It produces applications that are stored in https://quay.io/organization/camel-tooling repository.

### Manual process

It is convenient to bump each new release when all Camel runtimes are available to support (at least every minor version).

The new versions can be deployed on quay.io but running the github action workflow "Build and publish images".

To release on any other container registry, for each application, run the following:

```
./mvnw clean package
docker build -f src/main/docker/Dockerfile -t squakez/db-app-main:4.22.0 .
# You can also test it:
# docker run -it squakez/db-app-main:4.22.0
docker push squakez/db-app-main:4.22.0
```

You may replace the repository name, container name and version accordingly.
