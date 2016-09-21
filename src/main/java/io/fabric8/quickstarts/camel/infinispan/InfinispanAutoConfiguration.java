package io.fabric8.quickstarts.camel.infinispan;

import org.apache.camel.component.infinispan.processor.idempotent.InfinispanIdempotentRepository;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.commons.api.BasicCacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
     * The host name of the Infinispan service.
     */
    private String host;

    /**
     * The port of the Infinispan service.
     */
    private Integer port;

    /**
     * The name of the Infinispan cache.
     */
    private String cacheName = "default";

    /**
     * Defines a bean named 'remoteCacheContainer' that points to the remote Infinispan cluster.
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public BasicCacheContainer remoteCacheContainer() {

        String hostPort = host + ":" + port;
        logger.info("Connecting to the Infinispan service at {}", hostPort);

        return new RemoteCacheManager(
                new ConfigurationBuilder()
                        .addServers(hostPort)
                        .forceReturnValues(true)
                        .create(),
                false);
    }

    /**
     * Defines a Camel idempotent repository based on the Infinispan cache container.
     */
    @Bean
    public InfinispanIdempotentRepository infinispanRepository(BasicCacheContainer cacheContainer) {
        return InfinispanIdempotentRepository.infinispanIdempotentRepository(cacheContainer, cacheName);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
