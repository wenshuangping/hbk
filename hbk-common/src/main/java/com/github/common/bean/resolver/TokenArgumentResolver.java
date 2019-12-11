package com.github.common.bean.resolver;

import com.github.common.constant.SecurityConstants;
import com.github.common.util.UserVoUtil;
import com.github.common.vo.UserVO;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author wsp
 * @date 2017/12/21
 * Token转化UserVo
 */
@Slf4j
@Configuration
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 1. 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(UserVO.class);
    }

    /**
     * @param methodParameter       入参集合
     * @param modelAndViewContainer model 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     * @throws Exception exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        //请求从获取用户名
        String username = request.getHeader(SecurityConstants.USER_HEADER);
        //获取角色列表字符串
        String userVOStr = request.getHeader(SecurityConstants.ROLE_HEADER);
        try {
            userVOStr = URLDecoder.decode(userVOStr, "UTF-8");//解码
        } catch (UnsupportedEncodingException e) {
            log.error("TokenArgumentResolver::resolveArgument",e);
        }

        if (StrUtil.isBlank(username) || StrUtil.isBlank(userVOStr)) {
            log.warn("resolveArgument error username or role List Str is empty");
            return null;
        } else {
            log.info("resolveArgument username :{} roles:{}", username, userVOStr);
        }
        return UserVoUtil.str2UserVO(userVOStr);
    }

}
