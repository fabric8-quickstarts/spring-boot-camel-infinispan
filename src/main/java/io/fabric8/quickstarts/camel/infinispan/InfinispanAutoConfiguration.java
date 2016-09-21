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

@Configuration
@ConfigurationProperties(prefix = "infinispan")
public class InfinispanAutoConfiguration {

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

    private Logger logger = LoggerFactory.getLogger(getClass());

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
