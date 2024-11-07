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

@WebServlet(name = "loginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService loginService = new LoginService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String email = "";
		String password = "";
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			String value = cookie.getValue();

			if (name.equals("email")) {
				email = value;
			}

			if (name.equals("password")) {
				password = value;
			}
		}
		req.setAttribute("email", email);
		req.setAttribute("password", password);
		req.getRequestDispatcher("login.jsp").forward(req, resp);
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
			if ("on".equals(remember)) {
				Cookie emailCookie = new Cookie("email", email);
				Cookie passwordCookie = new Cookie("password", password);
				emailCookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
				passwordCookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
				resp.addCookie(emailCookie);
				resp.addCookie(passwordCookie);
			} else {
				Cookie emailCookie = new Cookie("email", "");
				Cookie passwordCookie = new Cookie("password", "");
				emailCookie.setMaxAge(0);
				passwordCookie.setMaxAge(0);
				resp.addCookie(emailCookie);
				resp.addCookie(passwordCookie);
			}

			HttpSession oldSession = req.getSession(false);
			if (oldSession != null) {
				oldSession.invalidate();
			}

			HttpSession session = req.getSession();
			session.setMaxInactiveInterval(30 * 60); // 30 phút
			session.setAttribute("user", user);

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

}
