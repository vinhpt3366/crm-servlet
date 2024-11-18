package crm_app07.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crm_app07.entity.UserEntity;
import crm_app07.services.LoginService;

@WebServlet(name = "loginController", urlPatterns = { "/login", "/logout" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService loginService = new LoginService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case "/login":
			login(req, resp);
			break;
		case "/logout":
			logout(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/login");
			break;
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");

		if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}

		UserEntity user = null;
		try {
			user = loginService.authenticateUser(email, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (user != null && user.getEmail() != null) {
			String token = loginService.insertToken(user.getId());

			if (!token.isEmpty()) {
				Cookie tokenCookie = new Cookie("token", token);
				tokenCookie.setMaxAge(1 * 24 * 60 * 60);
				resp.addCookie(tokenCookie);
			}

			Cookie emailCookie = new Cookie("email", email);
			emailCookie.setMaxAge(24 * 60 * 60); // 24 giờ
			resp.addCookie(emailCookie);

			if ("on".equals(remember)) {
				Cookie rememberCookie = new Cookie("remember", remember);
				rememberCookie.setMaxAge(24 * 60 * 60); // 24 giờ
				resp.addCookie(rememberCookie);
			}

			HttpSession session = req.getSession(true);
			session.setMaxInactiveInterval(24 * 60 * 60); // 24 giờ
			session.setAttribute("user", user);

			String roleId = String.valueOf(user.getRoleID());
			Cookie userCookie = new Cookie("roleID", roleId);
			userCookie.setMaxAge(24 * 60 * 60);
			userCookie.setPath("/");
			resp.addCookie(userCookie);

			switch (user.getRoleID()) {
			case 1:
			case 2:
			case 3:
			default:
				resp.sendRedirect(req.getContextPath() + "/");
				break;
			}
		} else {
			req.setAttribute("error", "Email hoặc mật khẩu không đúng");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = null;
		String password = null;
		Cookie[] cookies = req.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();

				if ("email".equals(name)) {
					email = value;
				}

				if ("password".equals(name)) {
					password = value;
				}
			}
		}

		if (email != null && !email.isEmpty()) {
			req.setAttribute("email", email);
		}
		if (password != null && !password.isEmpty()) {
			req.setAttribute("password", password);
		}

		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");

		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();
				if ("token".equals(name)) {
					loginService.deleteToken(value);

					Cookie tokenCookie = new Cookie("token", "");
					tokenCookie.setMaxAge(0);
					resp.addCookie(tokenCookie);
				}
			}
		}
		HttpSession oldSession = req.getSession(false);
		if (oldSession != null) {
			oldSession.invalidate();
		}
		resp.sendRedirect(req.getContextPath() + "/login");
	}

}
