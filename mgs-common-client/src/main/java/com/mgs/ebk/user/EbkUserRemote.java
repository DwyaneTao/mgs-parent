package com.mgs.ebk.user;

import com.mgs.common.Response;
import com.mgs.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-common-server")
public interface EbkUserRemote {

    @PostMapping(value = "/ebk/user/login", produces = {"application/json;charset=UTF-8"})
    Response login(@RequestBody UserDTO userDTO);
}
