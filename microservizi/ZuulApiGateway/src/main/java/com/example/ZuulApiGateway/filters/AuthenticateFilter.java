package com.example.ZuulApiGateway.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORWARD_TO_KEY;

public class AuthenticateFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AuthenticateFilter.class);

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

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String g = request.getRequestURL().toString();
        String token = request.getHeader("Authorization");
        if(token != null && !g.toLowerCase().contains("authenticationservice")){
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080").path("/StudentOne/authenticationservice/api/authenticateToken").build().toUriString();
            HttpGet r = new HttpGet(url);
            r.addHeader("Authorization", token);
            try (CloseableHttpResponse response = httpClient.execute(r)) {

                // Get HttpResponse Status
                if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                    HttpEntity entity = response.getEntity();
                    if(entity != null){
                        String user = EntityUtils.toString(entity);
                        Map<String, List<String>> params = ctx.getRequestQueryParams();
                        if (params == null) {
                            params = Maps.newHashMap();
                        }
                        params.put("user", Lists.newArrayList(user));

                        ctx.setRequestQueryParams(params);
                    }
                } else {
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseBody("not authorized");
                    ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
                    ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        request = ctx.getRequest();
        return null;
    }
}
