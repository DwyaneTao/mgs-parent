package com.mgs.organization.remote;

import com.mgs.common.Response;
import com.mgs.organization.remote.dto.ContactAddDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author py
 * @date 2019/7/2 17:30
 **/
@FeignClient(value = "mgs-common-server")
public interface ContactRemote {
    /**
     * 新增联系人信息
     */
    @PostMapping("/contact/addContact")
    Response addContact(@RequestBody ContactAddDTO request);

    /**
     * 修改联系人信息
     */
    @PostMapping("/contact/modifyContact")
    Response modifyContact(@RequestBody ContactAddDTO request);
    /**
     * 删除联系人信息
     */
    @PostMapping("/contact/deleteContact")
    Response deleteContact(@RequestBody ContactAddDTO request);

}
