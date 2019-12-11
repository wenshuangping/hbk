package com.github.gateway.component.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wsp
 * @date 2017-12-25 17:53:38
 * 网关统一异常处理
 */
@Component
public class ErrorHandlerFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        return requestContext.getThrowable() != null;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        Map<String, String[]> map = request.getParameterMap();
        return null;
    }

}
