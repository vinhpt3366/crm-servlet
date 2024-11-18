package crm_app07.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app07.repository.AuthRepository;

@WebFilter(filterName = "authenFilter", urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {
	AuthRepository authRepository = new AuthRepository();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String loginURI = httpRequest.getContextPath() + "/login";
		String homeURI = httpRequest.getContextPath() + "/";

		Cookie[] cookies = httpRequest.getCookies();
		String authToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					authToken = cookie.getValue();
					break;
				}
			}
		}

		boolean isLoggedIn = authToken != null && authRepository.isValidToken(authToken);
		boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
		if (isLoggedIn && isLoginRequest) {
			httpResponse.sendRedirect(homeURI);
		} else if (!isLoggedIn && !isLoginRequest) {
			httpResponse.sendRedirect(loginURI);
		} else {
			chain.doFilter(request, response);
		}

	}

}
