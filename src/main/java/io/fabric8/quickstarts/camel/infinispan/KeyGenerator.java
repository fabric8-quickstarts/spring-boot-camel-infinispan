package io.fabric8.quickstarts.camel.infinispan;


import java.util.Random;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "quickstart.generator")
public class KeyGenerator {

    private String keyPrefix = "message-key-";

    private Integer min = 1;

    private Integer max = 15;

    private Random random = new Random();

    public String newRandomKey() {
        return generate(keyPrefix, min, max);
    }

    private String generate(String prefix, int min, int max) {
        int next = random.nextInt(max - min + 1) + min;
        return prefix + next;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}
