package org.umc.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.umc.spring.service.MissionService.MissionQueryService;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			MissionQueryService missionQueryService = context.getBean(MissionQueryService.class);

			// 테스트용 회원 ID
			Long testMemberId = 1L;

			// 쿼리 메서드 호출 및 결과 출력
			System.out.println("Executing loadHomeMissions with testMemberId: " + testMemberId);
			missionQueryService.loadHomeMissions(testMemberId)
					.forEach(System.out::println);
		};
	}


//	@Bean
//	public CommandLineRunner run(ApplicationContext context) {
//		return args -> {
//			StoreQueryService storeService = context.getBean(StoreQueryService.class);
//
//			// 파라미터 값 설정
//			String name = "요아정";
//			Float score = 4.0f;
//
//			// 쿼리 메서드 호출 및 쿼리 문자열과 파라미터 출력
//			System.out.println("Executing findStoresByNameAndScore with parameters:");
//			System.out.println("Name: " + name);
//			System.out.println("Score: " + score);
//
//			storeService.findStoresByNameAndScore(name, score)
//					.forEach(System.out::println);
//		};
//	}


}
