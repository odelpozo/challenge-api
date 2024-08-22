package org.wrapper.settings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AppMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        converters.clear();
        converters.add(jacksonMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        return new MappingJackson2HttpMessageConverter();
    }

}
