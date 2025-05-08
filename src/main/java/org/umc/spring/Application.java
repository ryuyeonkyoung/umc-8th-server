package org.umc.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.umc.spring.service.MissionService.MissionQueryService;
import org.umc.spring.service.ReviewService.ReviewCommandService;

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
			ReviewCommandService reviewQueryService = context.getBean(ReviewCommandService.class);

			// loadHomeMissions 테스트
			Long testMemberId = 1L;
			System.out.println("Executing loadHomeMissions with testMemberId: " + testMemberId);
			missionQueryService.loadHomeMissions(testMemberId)
					.forEach(System.out::println);

			// setStoreReviews 테스트
			Long testStoreId = 1L;
			String testContext = "테스트 리뷰 내용";
			Float testRating = 4.5f;

			System.out.println("Executing setStoreReviews with parameters:");
			System.out.println("Store ID: " + testStoreId);
			System.out.println("Member ID: " + testMemberId);
			System.out.println("Context: " + testContext);
			System.out.println("Rating: " + testRating);

			boolean result = reviewQueryService.setStoreReviews(testStoreId, testMemberId, testContext, testRating);
			System.out.println("Review saved successfully: " + result);
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
