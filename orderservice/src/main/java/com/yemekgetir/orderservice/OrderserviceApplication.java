package com.yemekgetir.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrderserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderserviceApplication.class, args);
	}

	/**
	 * Bu metot, RestTemplate sınıfından bir "bean" oluşturur ve
	 * Spring bağlamına kaydeder.
	 * Artık uygulamadaki herhangi bir sınıf, @Autowired veya
	 * constructor injection kullanarak RestTemplate'i enjekte edebilir.
	 * Bu konfigürasyon, uygulamanın başlatılması sırasında bu bean'i oluşturarak
	 * "No qualifying bean" hatasını çözer.
	 */

	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
