package crm_app07.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app07.entity.TaskEntity;
import crm_app07.entity.UserDetails;
import crm_app07.entity.UserEntity;
import crm_app07.services.TaskService;
import crm_app07.services.UserService;
import crm_app07.utils.NumberUtil;

@WebServlet(name = "profileController", urlPatterns = { "/profile", "/profile-task", "/profile-edit" })
public class ProfileController extends HttpServlet {
	private UserService userService = new UserService();
	private TaskService taskService = new TaskService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		String id = req.getParameter("id");

		switch (action) {
		case "/profile":
			showProfilePage(req, resp);
			break;
		case "/profile-task":
			showProfileTaskPage(req, resp);
			break;
		case "/profile-edit":
			List<TaskEntity> taskStatus = taskService.getTaskStatus();
			req.setAttribute("taskStatus", taskStatus);
			TaskEntity taskEntity = taskService.getTaskByID(Integer.parseInt(id));
			req.setAttribute("task", taskEntity);
			req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/profile");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		String id = req.getParameter("id");

		switch (action) {
		case "/profile-edit":
			if (NumberUtil.isPositiveInteger(id) == false) {
				resp.sendRedirect(req.getContextPath() + "/profile");
				return;
			}
			updateTaskStatus(req, resp, id);

			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/profile");
			break;
		}
	}

	private void showProfilePage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = "";
		Cookie[] cookies = req.getCookies();

		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			String value = cookie.getValue();
			if (name.equals("email")) {
				email = value;
			}
		}
		UserEntity user = userService.getUserByEmail(email);
		System.out.println(user.getEmail());
		System.out.println(user.getId());

		int id = user.getId();
		UserDetails userDetails = new UserDetails();
		userDetails.setEmail(user.getEmail());
		userDetails.setFullName(user.getFullName());
		userDetails.setId(id);

		req.setAttribute("userDetails", userDetails);

		req.getRequestDispatcher("profile.jsp").forward(req, resp);

	}

	private void showProfileTaskPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = "";
		Cookie[] cookies = req.getCookies();

		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			String value = cookie.getValue();
			if (name.equals("email")) {
				email = value;
			}
		}

		UserEntity user = userService.getUserByEmail(email);
		System.out.println(user.getEmail());
		System.out.println(user.getId());

		int id = user.getId();
		List<TaskEntity> taskDetailsEntities = taskService.getTasksByUserID(id);
		UserDetails userDetails = new UserDetails();
		userDetails.setEmail(user.getEmail());
		userDetails.setFullName(user.getFullName());
		userDetails.setId(id);
		userDetails.setTotalTasks(taskDetailsEntities.size());
		req.setAttribute("taskDetailsEntities", taskDetailsEntities);

		System.out.println(userDetails.getEmail());
		System.out.println(userDetails.getFullName());
		System.out.println(userDetails.getTotalTasks());
		int notStartedTasks = 0;
		int inProgressTasks = 0;
		int completedTasks = 0;

		for (TaskEntity taskEntity : taskDetailsEntities) {
			int statusID = taskEntity.getStatusID();
			switch (statusID) {
			case 1:
				notStartedTasks++;
				break;
			case 2:
				inProgressTasks++;
				break;
			case 3:
				completedTasks++;
				break;
			default:
				break;
			}
			System.out.println(taskEntity.getName());
			System.out.println(taskEntity.getStartDate());
			System.out.println(taskEntity.getEndDate());

		}

		int totalTasks = userDetails.getTotalTasks() > 0 ? userDetails.getTotalTasks() : 1;

		String formattedNotStartedPercentage = NumberUtil.calculatePercentage(notStartedTasks, totalTasks);
		String formattedInProgressPercentage = NumberUtil.calculatePercentage(inProgressTasks, totalTasks);
		String formattedCompletedPercentage = NumberUtil.calculatePercentage(completedTasks, totalTasks);

		req.setAttribute("notStartedPercentage", formattedNotStartedPercentage);
		req.setAttribute("inProgressPercentage", formattedInProgressPercentage);
		req.setAttribute("completedPercentage", formattedCompletedPercentage);

		req.setAttribute("userDetails", userDetails);

		req.getRequestDispatcher("profile-task.jsp").forward(req, resp);

	}

	private void updateTaskStatus(HttpServletRequest req, HttpServletResponse resp, String id)
			throws ServletException, IOException {

		if (NumberUtil.isPositiveInteger(id) == false) {
			resp.sendRedirect(req.getContextPath() + "/profile");
			return;
		}

		String statusID = req.getParameter("statusID");

		boolean result = taskService.updateTaskStatus(Integer.parseInt(statusID), Integer.parseInt(id));
		if (result) {
			resp.sendRedirect(req.getContextPath() + "/profile-task");
		} else {
			resp.sendRedirect(req.getContextPath() + "/profile-edit?id=" + id);
		}
	}

}
