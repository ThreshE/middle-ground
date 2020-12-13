package com.cloud.paymentcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableTransactionManagement
@SpringBootApplication
public class PaymentCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentCenterApplication.class, args);
	}

}
