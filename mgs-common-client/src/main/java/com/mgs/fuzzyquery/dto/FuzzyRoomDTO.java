package com.mgs.fuzzyquery.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Owen
 * @Date: 2019/7/2 11:43
 * @Description:
 */
@Data
public class FuzzyRoomDTO implements Serializable{

    private Integer hotelId;

    private Integer roomId;

    private String roomName;

    private String bedTypes;
}
