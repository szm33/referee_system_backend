//package pl.lodz.p.it.referee_system.filter;
//
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
////public class LoggerInterceptor extends HandlerInterceptorAdapter {
//public class LoggerInterceptor implements MethodInterceptor {
//
//    private static Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);
//
//    @Override
//    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
//        return null;
//    }
//
////    @Override
////    public boolean preHandle(
////            HttpServletRequest request,
////            HttpServletResponse response,
////            Object handler) throws Exception {
////        StringBuilder parameters = new StringBuilder("[Parameters]( ");
////        request.getParameterMap().forEach((name,value) -> parameters.append(name + ":" + value + " "));
////        parameters.append(")");
////
////        log.info("[preHandle][" + request + "]" + "[" + request.getMethod()
////                + "]" + " with identity [" + request.getRemoteUser() + "] " + request.getRequestURI()
////        + parameters);
////
////        return true;
////    }
//}
