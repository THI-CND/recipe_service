package de.benedikt_schwering.thicnd.adapters.out.redis;

import de.benedikt_schwering.thicnd.adapters.out.redis.dto.IngredientCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisCacheConfiguration {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Value("${spring.data.redis.database}")
    private int database;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        var configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setPassword(password);
        configuration.setDatabase(database);

        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<Long, IngredientCache> redisTemplate() {
        var template = new RedisTemplate<Long, IngredientCache>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setEnableDefaultSerializer(true);

        return template;
    }
}
