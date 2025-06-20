package org.umc.spring.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFoodCategory is a Querydsl query type for FoodCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFoodCategory extends EntityPathBase<FoodCategory> {

    private static final long serialVersionUID = -407404942L;

    public static final QFoodCategory foodCategory = new QFoodCategory("foodCategory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<org.umc.spring.domain.mapping.MemberPrefer, org.umc.spring.domain.mapping.QMemberPrefer> memberPrefers = this.<org.umc.spring.domain.mapping.MemberPrefer, org.umc.spring.domain.mapping.QMemberPrefer>createSet("memberPrefers", org.umc.spring.domain.mapping.MemberPrefer.class, org.umc.spring.domain.mapping.QMemberPrefer.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QFoodCategory(String variable) {
        super(FoodCategory.class, forVariable(variable));
    }

    public QFoodCategory(Path<? extends FoodCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFoodCategory(PathMetadata metadata) {
        super(FoodCategory.class, metadata);
    }

}

