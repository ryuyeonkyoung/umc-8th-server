ALTER TABLE member_prefer RENAME COLUMN favorite_category_id TO food_category_id;

-- 외래 키 제약 조건 삭제
ALTER TABLE member_prefer DROP FOREIGN KEY FK3wj214uskly6gt6k1piqyjljt;

-- favorite_category 테이블 삭제
DROP TABLE favorite_category;