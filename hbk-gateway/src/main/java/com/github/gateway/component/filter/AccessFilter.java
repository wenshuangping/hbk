package com.github.gateway.component.filter;

import com.github.common.constant.SecurityConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER;


/**
 * @author wsp
 * @date 2017/11/20 在RateLimitPreFilter 之前执行，不然又空指针问题
 */
@Slf4j
@Component
public class AccessFilter extends ZuulFilter {


    private final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FORM_BODY_WRAPPER_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime", System.currentTimeMillis());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && (!ANONYMOUS_USER.equals(authentication.getName()))) {
            RequestContext requestContext = RequestContext.getCurrentContext();
            requestContext.addZuulRequestHeader(SecurityConstants.USER_HEADER, authentication.getName());

            try {
                String roleHeaderStr=URLEncoder.encode(CollectionUtil.join(authentication.getAuthorities(), ","), "UTF-8");
                requestContext.addZuulRequestHeader(SecurityConstants.ROLE_HEADER, roleHeaderStr);

            } catch (UnsupportedEncodingException e) {
                log.error("AccessFilter::run", e);
            }



        }
        return null;
    }
}
