package fis.pms.configuator;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fis.pms.configuator.argumentResolver.LoginWorkerArgumentResolver;
import fis.pms.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/error", "/login", "/*.ico", "/worker/signup", "/swagger-ui.html#/work-list-controller");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true)
                .allowedMethods("*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginWorkerArgumentResolver());
    }

    @Bean
    JPAQueryFactory JpaQueryFactory(EntityManager em){
        return new JPAQueryFactory(em);
    }
}
