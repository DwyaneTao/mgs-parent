package com.mgs.config;

import com.mgs.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Set;

import static org.openxmlformats.schemas.presentationml.x2006.main.STSlideLayoutType.type;

/**
 * 简易拦截器
 */
@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String menuUrlDev = request.getRequestURI();
        String token = request.getHeader("Authorization");
        if(StringUtil.isEmpty(token)){
            log.error(request.getRequestURI() + "---接口缺失token");
            response.getWriter().print(ErrorCodeEnum.NOT_LOGIN.errorCode);
            return false;
        }
        Jwt jwt = JwtUtil.parseJWT(token);
        if(null == jwt || 0 == jwt.getResult()){
            log.error(request.getRequestURI() + jwt.getParseResCode());
            response.getWriter().print(ErrorCodeEnum.NOT_LOGIN.errorCode);
            return false;
        }
        String userAccount = jwt.getUserAccount();
        String userName = jwt.getUserName();
        String orgCode = jwt.getOrgCode();
        String domainName = jwt.getDomainName();
        Integer hotelInfoPermission = jwt.getHotelInfoPermission();

        boolean isLogin = false;
        //先判断登陆
        isLogin = SecurityCache.getLoginStatusByLoginName(userAccount);

        if(!isLogin){
            log.error(userAccount + "---该用户没有登录");
            response.getWriter().print(ErrorCodeEnum.NOT_LOGIN.errorCode);
            return false;
        }


        //注销用户
        if(menuUrlDev.equals("/user/logout")){
            //移除登录状态
            SecurityCache.removeLoginStatus(userAccount);
            log.error(userAccount + "该用户已经注销");
            response.getWriter().print(ErrorCodeEnum.PERMISSION_DENIED.errorCode);
            return false;
        }

        //再判断权限
        Set<String> oneTwoLevelMenuUrlList = SecurityCache.getMenuListByLoginName(SecurityCache.ONE_TWO_LEVEL_NENU_URL);

        if(CollectionUtils.isEmpty(oneTwoLevelMenuUrlList)){
            log.error("系统菜单url缓存初始化失败");
            response.getWriter().print(ErrorCodeEnum.PERMISSION_DENIED.errorCode);
            return false;
        }

        Set<String> menuUrlDevList = SecurityCache.getMenuListByLoginName(userAccount);
        if(CollectionUtils.isEmpty(menuUrlDevList)){
            response.getWriter().print(ErrorCodeEnum.PERMISSION_DENIED.errorCode);
            return false;
        }



        //只验证一级，二级菜单权限
        if(oneTwoLevelMenuUrlList.contains(menuUrlDev)){

            if(org.springframework.util.CollectionUtils.isEmpty(menuUrlDevList) || !menuUrlDevList.contains(menuUrlDev) ){
                log.error(userAccount + "该用户没有权限访问此链接" + request.getRequestURI());
                response.getWriter().print(ErrorCodeEnum.PERMISSION_DENIED.errorCode);
                return false;
            }
        }


        request.setAttribute("userAccount",userAccount);
        request.setAttribute("userName",userName);
        request.setAttribute("orgCode",orgCode);
        request.setAttribute("domainName",domainName);
        request.setAttribute("hotelInfoPermission",hotelInfoPermission);
        return super.preHandle(request,response,handler);
    }
}
