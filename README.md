# Spring Boot, Camel and Infinispan QuickStart

This quickstart demonstrates how to connect a Spring-Boot application to a JBoss Data Grid (or Infinispan) server using the Hot Rod protocol.
It requires that the data grid server (or cluster) has been deployed first.

In the example, a Camel route is using the Infinispan server as idempotent repository, filtering out messages that have already been processed.
Messages having a bounded random ID are created through a configurable generator.
Another Camel route shows how to lookup cache entries in the Infinispan server.

Both routes use the `default` cache of the data grid, although this can be changed in the `application.properties` file.
The default name for the target data grid cluster is `DATAGRID_APP_HOTROD`. It can be changed from the spring-boot configuration file or
using the environment variable `DATAGRID_SERVICE_NAME`.

### Building

The example can be built with

    mvn clean install


### Running the example locally

The example can be run locally using the following Maven goal:

    mvn spring-boot:run


### Running the example in Kubernetes

It is assumed a running Kubernetes platform is already running. If not you can find details how to [get started](http://fabric8.io/guide/getStarted/index.html).

Assuming your current shell is connected to Kubernetes or OpenShift so that you can type a command like

```
kubectl get pods
```

or for OpenShift

```
oc get pods
```

Then the following command will package your app and run it on Kubernetes:

```
mvn fabric8:run
```

To list all the running pods:

    oc get pods

Then find the name of the pod that runs this quickstart, and output the logs from the running pods with:

    oc logs <name of pod>

You can also use the [fabric8 developer console](http://fabric8.io/guide/console.html) to manage the running pods, and view logs and much more.

### More details

You can find more details about running this [quickstart](http://fabric8.io/guide/quickstarts/running.html) on the website. This also includes instructions how to change the Docker image user and registry.
