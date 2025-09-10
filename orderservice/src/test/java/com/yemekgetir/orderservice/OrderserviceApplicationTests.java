package com.yemekgetir.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderserviceApplicationTests {

	/**
	 * Bu test, Orderservice uygulamasının Spring bağlamının (context)
	 * başarıyla yüklendiğini doğrulamak için kullanılır. Eğer bir hata varsa,
	 * bu, bir bağımlılığın eksik olduğu veya bir bean'in doğru
	 * şekilde yapılandırılmadığı anlamına gelir.
	 * <p>
	 * Hata mesajınızdaki "No qualifying bean of type 'org.springframework.web.client.RestTemplate'"
	 * sorunu, genellikle test sınıfı doğru şekilde yapılandırılmadığında veya
	 * RestTemplate bean'i uygulama ana sınıfında tanımlanmadığında ortaya çıkar.
	 * `@SpringBootTest` ek açıklaması, uygulamanın tüm Spring Boot
	 * özelliklerini ve bean'lerini test için kullanılabilir hale getirir.
	 */
	@Test
	void contextLoads() {
		// Bu metodun başarılı bir şekilde tamamlanması, Spring
		// bağlamının sorunsuz yüklendiğini gösterir.
	}
}
