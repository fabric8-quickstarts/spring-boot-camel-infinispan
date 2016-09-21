package io.fabric8.quickstarts.camel.infinispan;

import org.apache.camel.component.infinispan.processor.idempotent.InfinispanIdempotentRepository;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.commons.api.BasicCacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class defines two beans that are referenced in the camel routes.
 * Its properties can be changed using the application.properties file or environment variables.
 */
@Configuration
@ConfigurationProperties(prefix = "infinispan")
public class InfinispanAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The name of the Infinispan service.
     */
    private String service = "datagrid-app-hotrod";

    /**
     * The name of the Infinispan cache.
     */
    private String cacheName = "default";

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * Defines a bean named 'remoteCacheContainer' that points to the remote Infinispan cluster.
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public BasicCacheContainer remoteCacheContainer() {

        ConfigurationBuilder builder = new ConfigurationBuilder()
                .forceReturnValues(true);

        logger.info("Using Infinispan service {}", this.service);
        for (ServiceInstance service : discoveryClient.getInstances(this.service)) {
            String hostPort = service.getHost() + ":" + service.getPort();
            logger.info("Connecting to the Infinispan service at {}", hostPort);
            builder = builder.addServers(hostPort);
        }

        return new RemoteCacheManager(builder.create(), false);
    }

    /**
     * Defines a Camel idempotent repository based on the Infinispan cache container.
     */
    @Bean
    public InfinispanIdempotentRepository infinispanRepository(BasicCacheContainer cacheContainer) {
        return InfinispanIdempotentRepository.infinispanIdempotentRepository(cacheContainer, cacheName);
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
