package com.firstfuel.fafi.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.Season.class.getName(), jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.Season.class.getName() + ".tournaments", jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.Player.class.getName(), jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.Franchise.class.getName(), jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.Franchise.class.getName() + ".players", jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.Tournament.class.getName(), jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.Tournament.class.getName() + ".matches", jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.Match.class.getName(), jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.Season.class.getName() + ".franchises", jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.TieTeam.class.getName(), jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.TieMatch.class.getName(), jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.TieMatch.class.getName() + ".ns", jcacheConfiguration);
            cm.createCache(com.firstfuel.fafi.domain.TieTeam.class.getName() + ".tiePlayers", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
