package com.projectbase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.projectbase.service.auditdb.AuditorAwareService;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistentConfig{

    @Bean
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareService();
    }
}
