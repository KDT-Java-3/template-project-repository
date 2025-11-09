package com.example.week01_project.domain.orders;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderShipping is a Querydsl query type for OrderShipping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderShipping extends EntityPathBase<OrderShipping> {

    private static final long serialVersionUID = -1690324081L;

    public static final QOrderShipping orderShipping = new QOrderShipping("orderShipping");

    public final StringPath addressLine1 = createString("addressLine1");

    public final StringPath addressLine2 = createString("addressLine2");

    public final StringPath city = createString("city");

    public final StringPath country = createString("country");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath phone = createString("phone");

    public final StringPath postalCode = createString("postalCode");

    public final StringPath recipientName = createString("recipientName");

    public final StringPath state = createString("state");

    public QOrderShipping(String variable) {
        super(OrderShipping.class, forVariable(variable));
    }

    public QOrderShipping(Path<? extends OrderShipping> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderShipping(PathMetadata metadata) {
        super(OrderShipping.class, metadata);
    }

}

