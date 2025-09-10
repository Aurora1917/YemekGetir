package com.yemekgetir.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    /**
     * Bu metot, RestTemplate sınıfından bir bean oluşturur ve
     * Spring bağlamına kaydeder.
     * Artık uygulamadaki herhangi bir sınıf, @Autowired veya
     * constructor injection kullanarak RestTemplate'i enjekte edebilir.
     * RestTemplate'in HTTP isteklerini yapması için bir örneği olması gerekir.
     * Bu konfigürasyon, uygulamanın başlatılması sırasında bu bean'i oluşturarak
     * "No qualifying bean" hatasını çözer.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
