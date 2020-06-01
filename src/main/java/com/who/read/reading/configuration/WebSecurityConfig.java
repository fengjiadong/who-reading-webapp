//package com.who.read.reading.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// * @Classname WebSecurityConfig 拦截器
// * @date 2020/3/26 5:47 PM
// * @Created by fengjiadong
// */
//@Configuration
//public class WebSecurityConfig implements WebMvcConfigurer {
//
//	/**
//	 * 登录session key
//	 */
//	public final static String SESSION_KEY = "user";
//
//	/**
//	 * 重写添加拦截器方法并添加配置拦截器
//	 * @param registry
//	 */
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(securityInterceptor)
//				.addPathPatterns("/api/**")
//				.addPathPatterns("/who/**")
//				.addPathPatterns("/menu/**")
//				.excludePathPatterns("/static")
//				.excludePathPatterns("/api/login/**","/hazelcast/**","/api/logged/**","/error/**");
//	}
//
//
//	@Autowired
//	SecurityInterceptor securityInterceptor;
//
//	@Component
//	public class SecurityInterceptor implements HandlerInterceptor {
//		@Override
//		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//				throws Exception {
//			String type = request.getHeader("X-Requested-With");
//			System.out.println("接收到请求"+request.getMethod()+":"+request.getRequestURI()+" X-Requested-With:"+type);
//			HttpSession session = request.getSession();
//			Object attribute = session.getAttribute(SESSION_KEY);
//			if (attribute != null) {
//				System.out.println("已登录,用户" + attribute);
//				return true;
//			}
//			System.out.println("未登录");
//			System.out.println("X-Requested-With:"+type);
//			// 跳转登录
//			if(type!=null && type.contains("XMLHttpRequest")){
//				String path = request.getContextPath();
//				String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
//				response.setHeader("SESSIONSTATUS", "TIMEOUT");
//				response.setHeader("CONTEXTPATH", basePath+"api/logged");
//				response.setStatus(HttpServletResponse.SC_FORBIDDEN);//403 禁止
//
//			}else{
//				request.getRequestDispatcher("/login").forward(request,response);
//			}
//			return false;
//		}
//		@Override
//		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//			System.out.println("请求响应完成");
//		}
//	}
//}
