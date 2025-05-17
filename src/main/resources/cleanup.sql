-- MySQL/PostgreSQL: FK 체크 잠시 끄기 (옵션)
-- MySQL: SET FOREIGN_KEY_CHECKS = 0;
-- PostgreSQL: ALTER TABLE ... DISABLE TRIGGER ALL;

-- 자식 테이블부터 데이터 비우기
TRUNCATE TABLE member_agree;
TRUNCATE TABLE member_prefer;
TRUNCATE TABLE review_image;
TRUNCATE TABLE comment;
TRUNCATE TABLE member_mission;

-- 리뷰, 스토어 비우기
TRUNCATE TABLE review;
TRUNCATE TABLE store;

-- mission, terms, favorite_category 비우기
TRUNCATE TABLE mission;
TRUNCATE TABLE terms;
TRUNCATE TABLE food_category;

-- member, region 비우기
TRUNCATE TABLE member;
TRUNCATE TABLE region;

-- FK 체크 다시 켜기
-- MySQL: SET FOREIGN_KEY_CHECKS = 1;
-- PostgreSQL: ALTER TABLE ... ENABLE TRIGGER ALL;