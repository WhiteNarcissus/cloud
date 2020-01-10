package zuul;


import io.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import zuul.filter.AccessFilter;

//import com.cardapprove.xshq.plugins.comptroller.ComptrollerInterceptor;
//import com.cardapprove.xshq.plugins.comptroller.strategy.impl.SnowFlakeIdGenerateInvoker;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"zuul","com.cardapprove.xshq"}, exclude = {DataSourceAutoConfiguration.class, SpringBootConfiguration.class})
@EnableZuulProxy
@EnableDiscoveryClient
@RefreshScope
@RestController
public class ExternalZuulApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ExternalZuulApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ExternalZuulApplication.class);
    }

    /**
     * CORS解决微服务跨域问题.
     *
     * @return COrsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();
        // 允许cookie跨域
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod(HttpMethod.HEAD);
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedMethod(HttpMethod.PATCH);
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

}
