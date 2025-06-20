package org.umc.spring.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1051457008L;

    public static final QMember member = new QMember("member1");

    public final org.umc.spring.domain.common.QBaseEntity _super = new org.umc.spring.domain.common.QBaseEntity(this);

    public final StringPath address = createString("address");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final EnumPath<org.umc.spring.domain.enums.Gender> gender = createEnum("gender", org.umc.spring.domain.enums.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> inactiveDate = createDate("inactiveDate", java.time.LocalDate.class);

    public final SetPath<org.umc.spring.domain.mapping.MemberAgree, org.umc.spring.domain.mapping.QMemberAgree> memberAgrees = this.<org.umc.spring.domain.mapping.MemberAgree, org.umc.spring.domain.mapping.QMemberAgree>createSet("memberAgrees", org.umc.spring.domain.mapping.MemberAgree.class, org.umc.spring.domain.mapping.QMemberAgree.class, PathInits.DIRECT2);

    public final SetPath<org.umc.spring.domain.mapping.MemberMission, org.umc.spring.domain.mapping.QMemberMission> memberMissions = this.<org.umc.spring.domain.mapping.MemberMission, org.umc.spring.domain.mapping.QMemberMission>createSet("memberMissions", org.umc.spring.domain.mapping.MemberMission.class, org.umc.spring.domain.mapping.QMemberMission.class, PathInits.DIRECT2);

    public final SetPath<org.umc.spring.domain.mapping.MemberPrefer, org.umc.spring.domain.mapping.QMemberPrefer> memberPrefers = this.<org.umc.spring.domain.mapping.MemberPrefer, org.umc.spring.domain.mapping.QMemberPrefer>createSet("memberPrefers", org.umc.spring.domain.mapping.MemberPrefer.class, org.umc.spring.domain.mapping.QMemberPrefer.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final BooleanPath phoneVerified = createBoolean("phoneVerified");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final SetPath<Review, QReview> reviews = this.<Review, QReview>createSet("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public final EnumPath<org.umc.spring.domain.enums.Role> role = createEnum("role", org.umc.spring.domain.enums.Role.class);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<org.umc.spring.domain.enums.SocialType> socialType = createEnum("socialType", org.umc.spring.domain.enums.SocialType.class);

    public final EnumPath<org.umc.spring.domain.enums.MemberStatus> status = createEnum("status", org.umc.spring.domain.enums.MemberStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

