package challenge.leandrini.config;

import challenge.leandrini.common.WebRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class WebRequestConfig {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public WebRequest webRequest(HttpServletRequest httpServletRequest) {
        WebRequest webRequest = new WebRequest();
        webRequest.setPath(httpServletRequest.getRequestURI());
        return webRequest;
    }
}