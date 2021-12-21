package sg.edu.iss.LAPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sg.edu.iss.LAPS.interceptors.SecurityInterceptor;


@Component
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    SecurityInterceptor securityInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor);
    }

}