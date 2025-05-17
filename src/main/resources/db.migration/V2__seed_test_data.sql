USE umc;

USE umc;

-- Region 데이터 추가
INSERT INTO region (id, name, created_at, updated_at)
VALUES (1, '서울', NOW(), NOW()),
       (2, '부산', NOW(), NOW()),
       (3, '인천', NOW(), NOW());

-- Store 데이터 추가
INSERT INTO store (id, region_id, name, address, score, closed_day, open_time, close_time, version, created_at, updated_at)
VALUES (1, 1, 'Store 1', '서울시 서대문구 이화여대길 52', 4.5, 'SUNDAY', '08:00:00', '23:59:00', 0, NOW(), NOW()),
       (2, 1, 'Store 2', '서울시 마포구 연남동', 3.8, 'MONDAY', '08:00:00', '23:59:00', 0, NOW(), NOW()),
       (3, 1, 'Store 3', '서울시 동작구 흑석동', 2.2, 'TUESDAY', '08:00:00', '23:59:00', 0, NOW(), NOW()),
       (4, 1, '요아정', '서울시 용산구 이태원동', 4.0, 'WEDNESDAY', '08:00:00', '23:59:00', 0, NOW(), NOW()),
       (5, 1, '요아정', '서울시 서대문구 이화여대길 52', 3.2, 'THURSDAY', '08:00:00', '23:59:00', 0, NOW(), NOW()),
       (6, 1, '요아정', '서울시 강남구 대치동', 4.5, 'FRIDAY', '08:00:00', '23:59:00', 0, NOW(), NOW());

-- Member 데이터 추가
INSERT INTO member (id, name, nickname, social_id, social_type, phone_number, phone_verified, gender, address, email, point, status, version, created_at, updated_at)
VALUES (1, '테스트 회원', '테스터', 'social123', 'KAKAO', '010-1234-5678', TRUE, 'MALE', '서울 강남구', 'test@example.com', 100, 'ACTIVE', 0, NOW(), NOW());

-- Mission 데이터 추가
INSERT INTO mission (id, store_id, mission_spec, min_spend_money, reward_points, address, deadline, version, created_at, updated_at)
VALUES (1, 1, 'Store 1-미션 1', 1000, 6000, '주소1', DATE_ADD(NOW(), INTERVAL 7 DAY), 0, NOW(), NOW()),
       (2, 1, 'Store 1-미션 2', 2000, 5000, '주소2', DATE_ADD(NOW(), INTERVAL 7 DAY), 0, NOW(), NOW()),
       (3, 2, 'Store 2-미션 1', 3000, 1000, '주소3', DATE_ADD(NOW(), INTERVAL 7 DAY), 0, NOW(), NOW()),
       (4, 3, 'Store 3-미션 1', 4000, 3000, '주소4', DATE_ADD(NOW(), INTERVAL 7 DAY), 0, NOW(), NOW());

-- Review 데이터 추가
INSERT INTO review (id, member_id, store_id, context, rating, status, version, created_at, updated_at)
VALUES (1, 1, 1, '너무 좋아요!', 5.0, 'ACTIVE', 0, NOW(), NOW()),
       (2, 1, 1, '분위기 짱~', 3.0, 'ACTIVE', 0, NOW(), NOW()),
       (3, 1, 2, '서비스가 좋습니다', 4.8, 'ACTIVE', 0, NOW(), NOW()),
       (4, 1, 3, '음식이 맛있고 사장님이 친절해요', 4.5, 'ACTIVE', 0, NOW(), NOW());

-- ReviewImage 데이터 추가
INSERT INTO review_image (id, review_id, image_url, created_at, updated_at)
VALUES (1, 1, 'http://example.com/image1.jpg', NOW(), NOW()),
       (2, 2, 'http://example.com/image2.jpg', NOW(), NOW());

-- MemberMission 데이터 추가
INSERT INTO member_mission (id, member_id, mission_id, created_at, updated_at)
VALUES (1, 1, 1, NOW(), NOW()),
       (2, 1, 2, NOW(), NOW()),
       (3, 1, 3, NOW(), NOW());
