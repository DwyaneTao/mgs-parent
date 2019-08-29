package com.mgs.organization.service;

import com.mgs.common.Response;
import com.mgs.organization.remote.dto.ContactAddDTO;

/**
 * @author py
 * @date 2019/6/22 11:43
 **/
public interface ContactService {
    /**
     * 新增联系人
     * @param
     * @return
     */
    Response addContact(ContactAddDTO ContactAddDTO);
    /**
     * 修改联系人信息
     * @param
     * @return
     */
    Response modifyContact(ContactAddDTO ContactAddDTO);

    /**
     * 删除联系人信息
     * @param
     * @return
     */
    Response deleteContact(ContactAddDTO contactAddDTO);
}
