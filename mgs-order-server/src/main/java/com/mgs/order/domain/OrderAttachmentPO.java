package com.mgs.order.domain;

import com.mgs.common.BasePO;
import com.sun.xml.internal.rngom.parse.host.Base;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "o_order_attachment")
public class OrderAttachmentPO  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 附件名称
     */
    private String name;

    /**
     * url地址
     */
    private String url;

    /**
     * 实际地址(带后缀的文件名称)
     */
    private String realpath;
    private String createdBy;

    private String createdDt;
}