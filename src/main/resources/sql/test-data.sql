USE umc;

INSERT INTO region (id, name, created_at, updated_at)
VALUES (1, '서울', NOW(), NOW()),
       (2, '부산', NOW(), NOW()),
       (3, '인천', NOW(), NOW());

INSERT INTO store (id, name, address, score, region_id, created_at, updated_at)
VALUES (1, 'Store 1', '서울시 서대문구 이화여대길 52', 4.5, 1, NOW(), NOW()),
       (2, 'Store 2', '서울시 마포구 연남동', 3.8, 1, NOW(), NOW()),
       (3, 'Store 3', '서울시 동작구 흑석동', 2.2, 1, NOW(), NOW()),
       (4, '요아정', '서울시 용산구 이태원동', 4.0, 1, NOW(), NOW()),
       (5, '요아정', '서울시 서대문구 이화여대길 52', 3.2, 1, NOW(), NOW()),
       (6, '요아정', '서울시 강남구 대치동', 4.5, 1, NOW(), NOW());

INSERT INTO mission (id, mission_spec, store_id, created_at, updated_at, deadline, reward_points, address)
VALUES (1, 'Store 1-미션 1', 1, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), 6000, "주소1"),
       (2, 'Store 1-미션 2', 1, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), 5000, "주소2"),
       (3, 'Store 2-미션 1', 2, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), 1000, "주소3"),
       (4, 'Store 3-미션 1', 3, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), 3000, "주소4");

INSERT INTO review (id, context, rating, store_id, created_at, updated_at)
VALUES (1, '너무 좋아요!', 5.0, 1, NOW(), NOW()),
       (2, '분위기 짱~', 3.0, 1, NOW(), NOW()),
       (3, '서비스가 좋습니다', 4.8, 2, NOW(), NOW()),
       (4, '음식이 맛있고 사장님이 친절해요', 4.5, 3, NOW(), NOW());


-- 추가적으로 생성한 dml문
USE umc;
-- Region 데이터 추가
INSERT INTO region (id, name, created_at, updated_at)
VALUES (1, '서울', NOW(), NOW());

-- Store 데이터 추가
INSERT INTO store (id, region_id, name, address, score, status, closed_day, open_time, close_time, created_at,
                   updated_at)
VALUES (1, 1, '테스트 가게', '서울 강남구', 4.5, 'OPERATING', 'SUNDAY', '08:00:00', '23:59:00', NOW(), NOW());

-- Member 데이터 추가
INSERT INTO member (id, name, nickname, social_id, social_type, phone_number, phone_verified, gender, address, email,
                    point, status, created_at, updated_at)
VALUES (1, '테스트 회원', '테스터', 'social123', 'KAKAO', '010-1234-5678', TRUE, 'MALE', '서울 강남구', 'test@example.com', 100,
        'ACTIVE', NOW(), NOW());

-- Mission 데이터 추가
INSERT INTO mission (id, store_id, mission_spec, min_spend_money, reward_points, address, deadline, status, created_at,
                     updated_at)
VALUES (1, 1, '테스트 미션 설명', 10000, 500, '서울 강남구', '2023-12-31', 'CHALLENGING', NOW(), NOW());

-- Review 데이터 추가
INSERT INTO review (id, member_id, store_id, context, rating, status, created_at, updated_at)
VALUES (1, 1, 1, '좋은 가게입니다!', 4.5, 'ACTIVE', NOW(), NOW());

-- ReviewImage 데이터 추가 (리뷰 이미지가 필요한 경우)
INSERT INTO review_image (id, review_id, image_url, created_at, updated_at)
VALUES (1, 1, 'http://example.com/image1.jpg', NOW(), NOW());

-- MemberMission 데이터 추가 (getCompletedMissionsWithCursor 메서드 실행을 위해 필요)
INSERT INTO member_mission (id, member_id, mission_id, status, updated_at, created_at)
VALUES (1, 1, 1, 'COMPLETED', NOW(), NOW());