package com.example.week01_project.domain.refund;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRefundItem is a Querydsl query type for RefundItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefundItem extends EntityPathBase<RefundItem> {

    private static final long serialVersionUID = 194592933L;

    public static final QRefundItem refundItem = new QRefundItem("refundItem");

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> orderItemId = createNumber("orderItemId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Long> refundId = createNumber("refundId", Long.class);

    public QRefundItem(String variable) {
        super(RefundItem.class, forVariable(variable));
    }

    public QRefundItem(Path<? extends RefundItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRefundItem(PathMetadata metadata) {
        super(RefundItem.class, metadata);
    }

}

