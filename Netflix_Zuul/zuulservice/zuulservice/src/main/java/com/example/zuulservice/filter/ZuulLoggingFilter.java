package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {

    @Override
    public Object run() throws ZuulException {
        log.info("************ printing logs: ");

        /**
         * 사용자가 무슨 uri를 요청했는지 요청 정보를 찍기 위해 servlet을 통해 request 정보 이용
         */

        RequestContext ctx = RequestContext.getCurrentContext(); //request,response의 정보를 갖고 있는 최상위 객체가 Context 이므로 이걸로 request정보를 갖고 오자
        HttpServletRequest request=ctx.getRequest(); //이 filter안에는 request객체가 존재하지 않고 zuulfilter에서도 존재하지 않기 때문에 실제로 구해야함
        log.info("************"+request.getRequestURI());
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

}
