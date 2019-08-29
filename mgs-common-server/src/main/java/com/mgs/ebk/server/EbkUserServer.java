package com.mgs.ebk.server;

import com.mgs.common.Response;
import com.mgs.ebk.service.EbkUserService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbkUserServer {

    @Autowired
    private EbkUserService ebkUserService;

    /**
     * 用户登陆
     * @param userDTO
     * @return
     */
    @PostMapping(value = "/ebk/user/login", produces = {"application/json;charset=UTF-8"})
    public Response login(@RequestBody UserDTO userDTO){
        Response response = new Response();
        try {
            UserDTO userDB = ebkUserService.login(userDTO);

            response.setResult(1);
            response.setModel(userDB);

        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(e.getMessage());
            response.setFailReason(ErrorCodeEnum.getDescByValue(e.getMessage()));
        }
        return response;
    }
}
