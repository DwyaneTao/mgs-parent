package com.mgs.dlt.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: Owen
 * @Date: 2019/7/30 16:56
 * @Description:
 */
@Data
@Table(name = "t_dis_dlt_interface_log")
public class DltInterfaceLogPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String channelCode;

    private String interfaceName;

    private Integer hotelId;

    private Integer roomId;

    private Integer productId;

    private Date startDate;

    private Date endDate;

    private String request;

    private String response;

    private String returnCode;

    private String returnMsg;
}
