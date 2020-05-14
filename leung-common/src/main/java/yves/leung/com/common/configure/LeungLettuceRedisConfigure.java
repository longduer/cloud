package yves.leung.com.common.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import yves.leung.com.common.service.RedisService;

public class LeungLettuceRedisConfigure {
    /**
     * 在往Redis里写数据时key及value的序列化方式（默认使用的JdkSerializationRedisSerializer这样的会导致我们通过Redis Desktop Manager
     * 显示的我们key跟value的时候为不可读字符串）。
     * 在febs-common模块的yves.leung.com.common.configure路径下新建LeungLettuceRedisConfigure
     *
     *
     * 自定义了一个泛型为<String, Object>的RedisTemplate，指定key序列化策略采用StringRedisSerializer，value序列化策略采用Jackson2JsonRedisSerializer，
     * 其内部采用ObjectMapper（不熟悉ObjectMapper的同学可以参考Spring Boot中的JSON技术）来序列化对象。@ConditionalOnClass(RedisOperations.class)表示只有当项目里存在RedisOperations类的时候（即引入了spring-boot-starter-data-redis依赖的时候），我们自定义的RedisTemplateBean才会被注册到IOC容器中；
     * 将上面定义的RedisService注册到IOC容器中，前提是IOC容器里存在名称为redisTemplate的Bean。
     */

    @Bean
    @ConditionalOnClass(RedisOperations.class)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用 String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的 key也采用 String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用 jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的 value序列化方式采用 jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    @ConditionalOnBean(name = "redisTemplate")
    public RedisService redisService() {
        return new RedisService();
    }

}
