package se.pj.tbike.core.japi.impl;

import com.ank.japi.json.JsonTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JapiConf {

    @Bean
    public JsonTemplate jsonTemplate() {
        return new JsonTemplateImpl();
    }

}
