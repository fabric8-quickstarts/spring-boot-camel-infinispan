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

### Running the example in OpenShift

It is assumed that:
- OpenShift platform is already running, if not you can find details how to [Install OpenShift at your site](https://docs.openshift.com/container-platform/3.3/install_config/index.html).
- Your system is configured for Fabric8 Maven Workflow, if not you can find a [Get Started Guide](https://access.redhat.com/documentation/en/red-hat-jboss-middleware-for-openshift/3/single/red-hat-jboss-fuse-integration-services-20-for-openshift/)
- The Red Hat JDG xPaaS product should already be installed and running on your OpenShift installation, one simple way to run a JDG service is following the documentation of the JDG xPaaS image for OpenShift related to the `datagrid65-basic` template

The example can be built and run on OpenShift using a single goal:

    mvn fabric8:deploy

To list all the running pods:

    oc get pods

Then find the name of the pod that runs this quickstart, and output the logs from the running pods with:

    oc logs <name of pod>

You can also use the OpenShift [web console](https://docs.openshift.com/container-platform/3.3/getting_started/developers_console.html#developers-console-video) to manage the
running pods, and view logs and much more.

### Running via an S2I Application Template

Application templates allow you deploy applications to OpenShift by filling out a form in the OpenShift console that allows you to adjust deployment parameters.  This template uses an S2I source build so that it handle building and deploying the application for you.

First, import the Fuse image streams:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/fis-image-streams.json

Then create the quickstart template:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/quickstarts/spring-boot-camel-infinispan-template.json

Now when you use "Add to Project" button in the OpenShift console, you should see a template for this quickstart. 

### Integration Testing

The example includes a [fabric8 arquillian](https://github.com/fabric8io/fabric8/tree/v2.2.170.redhat/components/fabric8-arquillian) OpenShift Integration Test. 
Once the container image has been built and deployed in OpenShift, the integration test can be run with:

    mvn test -Dtest=*KT

The test is disabled by default and has to be enabled using `-Dtest`. Open Source Community documentation at [Integration Testing](https://fabric8.io/guide/testing.html) and [Fabric8 Arquillian Extension](https://fabric8.io/guide/arquillian.html) provide more information on writing full fledged black box integration tests for OpenShift. 

