package crm_app07.utils;

import javax.servlet.http.HttpSession;

import crm_app07.entity.UserEntity;

public class Utils {
	public static UserEntity getUserFromSession(HttpSession session) {
		if (session == null) {
			return null;
		}
		return (UserEntity) session.getAttribute("user");
	}
}
