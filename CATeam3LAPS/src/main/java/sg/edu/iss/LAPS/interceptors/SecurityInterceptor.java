package sg.edu.iss.LAPS.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sg.edu.iss.LAPS.utility.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler) throws IOException {
        LOGGER.setLevel(Level.ALL);
        LOGGER.info("Interceptor: session :" + request.getSession().getAttribute("role"));
        LOGGER.info("Interceptor: path :" + request.getServletPath());

        if(request.getServletPath().contains("/admin/")){
            if(request.getSession().getAttribute("role")==null || request.getSession().getAttribute("role").toString() != Constants.ADMIN_ROLE_NAME){
                response.sendRedirect("/adminlogin/");
                return false;
            }
        }
        else if(request.getServletPath().contains("/staff/")){
            if(request.getSession().getAttribute("role")==null || request.getSession().getAttribute("role").toString() == Constants.ADMIN_ROLE_NAME){
                response.sendRedirect("/stafflogin/");
                return false;
            }
        }
        else if(request.getServletPath().contains("/manager/")){
            if(request.getSession().getAttribute("role")==null || request.getSession().getAttribute("role").toString() != Constants.MANAGER_ROLE_NAME){
                response.sendRedirect("/stafflogin/");
                return false;
            }
        }
        return true;
    }
}
