package com.poly.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.poly.entity.Account;
import com.poly.service.SessionService;

@Service
public class AuthInterceptor implements HandlerInterceptor {
//	@Autowired
//	SessionService session;
//	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		String uri = request.getRequestURI();
//		Account user = session.getAttribute("user");
//		String error = "";
//		if(user == null) {
//			error = "Please login!";
//		}
//		//không phải admin hoặc uri tới admin ==> báo lỗi
//		else if( !user.getAdmin() && uri.startsWith("/admin/")) {
//			error = "Access denied!";
//		}
//		
//		if (error.length() > 0) {
//			session.setAttribute("security-uri", uri);
//			response.sendRedirect("/login.html?error="+error);
//			return false;
//		}
//		return true; //cho đi tip controller
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		
//		
//	}
	
}
