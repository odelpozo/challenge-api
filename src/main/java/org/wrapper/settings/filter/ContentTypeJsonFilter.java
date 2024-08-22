package org.wrapper.settings.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ContentTypeJsonFilter implements Filter {

    @Override
    public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if("POST".equalsIgnoreCase(req.getMethod()) || "PUT".equalsIgnoreCase(req.getMethod())) {
            if(!"application/json".equalsIgnoreCase(req.getContentType())) {
                res.sendError(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),"Content-Type must be application/json");
            }
        }

        if(req.getHeader("Accept") != null && req.getHeader("Accept").contains("application/json")) {
            res.sendError(HttpStatus.NOT_ACCEPTABLE.value(),"Accept header must be application/json");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
