package io.fabric8.quickstarts.camel.infinispan;

import io.fabric8.spring.cloud.discovery.KubernetesDiscoveryClientConfiguration;

import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
@Import(KubernetesDiscoveryClientConfiguration.class)
public class Application extends FatJarRouter {

    public static void main(String[] args) {
        FatJarRouter.main(args);
    }

}
