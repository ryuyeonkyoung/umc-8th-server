-- 자식 테이블부터 순서대로 DROP
DROP TABLE IF EXISTS member_agree;
DROP TABLE IF EXISTS member_prefer;
DROP TABLE IF EXISTS review_image;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS member_mission;

-- 리뷰가 스토어·멤버에 종속
DROP TABLE IF EXISTS review;

-- 스토어가 리전(region)에 종속
DROP TABLE IF EXISTS store;

-- mission, terms, favorite_category 은 독립적이지만
DROP TABLE IF EXISTS mission;
DROP TABLE IF EXISTS terms;
DROP TABLE IF EXISTS favorite_category;

-- member 가 마지막에
DROP TABLE IF EXISTS member;

-- 최상위 테이블
DROP TABLE IF EXISTS region;