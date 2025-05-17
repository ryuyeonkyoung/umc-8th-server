# Member.email 제약조건 해제
ALTER TABLE Member
    MODIFY email VARCHAR(50);

# 확인
SHOW COLUMNS FROM Member LIKE 'email';