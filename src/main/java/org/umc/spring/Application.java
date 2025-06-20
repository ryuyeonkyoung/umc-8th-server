package org.umc.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

//    @Bean
//    public CommandLineRunner run(ApplicationContext context) {
//        return args -> {
//            MissionQueryService missionQueryService = context.getBean(MissionQueryService.class);
//            ReviewCommandService reviewQueryService = context.getBean(ReviewCommandService.class);
//            MemberQueryService memberQueryService = context.getBean(MemberQueryService.class);
//
//            // loadHomeMissions 테스트
//            Long testMemberId = 1L;
//            System.out.println("Executing loadHomeMissions with testMemberId: " + testMemberId);
//            missionQueryService.loadHomeMissions(testMemberId)
//                    .forEach(System.out::println);
//
//            // setStoreReviews 테스트
//            Long testStoreId = 1L;
//            String testContext = "테스트 리뷰 내용";
//            Float testRating = 4.5f;
//
//            System.out.println("Executing setStoreReviews with parameters:");
//            System.out.println("Store ID: " + testStoreId);
//            System.out.println("Member ID: " + testMemberId);
//            System.out.println("Context: " + testContext);
//            System.out.println("Rating: " + testRating);
//
//            boolean result = reviewQueryService.setStoreReviews(testStoreId, testMemberId, testContext, testRating);
//            System.out.println("Review saved successfully: " + result);
//
//            // loadMemberProfile 테스트
//            try {
//                System.out.println("Executing loadMemberProfile with testMemberId: " + testMemberId);
//                MemberProfileResponseDTO memberProfile = memberQueryService.loadMemberProfile(testMemberId);
//                System.out.println("Member Profile: " + memberProfile);
//            } catch (IllegalArgumentException e) {
//                System.err.println("Error: " + e.getMessage());
//            }
//
//            // loadCompletedMissions 테스트
//            Long testLastMissionId = 0L; // 마지막 미션 ID (초기값은 0)
//            MissionStatus testMissionStatus = MissionStatus.COMPLETE; // 테스트할 미션 상태
//
//            try {
//                System.out.println("Executing loadCompletedMissions with parameters:");
//                System.out.println("Member ID: " + testMemberId);
//                System.out.println("Last Mission ID: " + testLastMissionId);
//                System.out.println("Mission Status: " + testMissionStatus);
//
//                memberQueryService.loadCompletedMissions(testMemberId, testLastMissionId, testMissionStatus)
//                        .forEach(System.out::println);
//            } catch (IllegalArgumentException e) {
//                System.err.println("Error: " + e.getMessage());
//            }
//
//        };
//    }
}
