package crm_app07.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app07.entity.RoleEntity;
import crm_app07.entity.TaskEntity;
import crm_app07.entity.UserDetails;
import crm_app07.entity.UserEntity;
import crm_app07.services.RoleService;
import crm_app07.services.TaskService;
import crm_app07.services.UserService;
import crm_app07.utils.NumberUtil;
import crm_app07.utils.PasswordUtils;

@WebServlet(name = "userController", urlPatterns = { "/users", "/user-add", "/user-edit", "/user-details" })
public class UserController extends HttpServlet {
	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();
	private TaskService taskService = new TaskService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();

		switch (action) {
		case "/users":
			handleListUsers(req, resp);
			break;
		case "/user-add":
			showAddUserForm(req, resp);
			break;
		case "/user-details":
			showUserDetails(req, resp);
			break;
		case "/user-edit":
			showEditUserForm(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/users");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		String id = req.getParameter("id");

		switch (action) {
		case "/user-add":
			addUser(req, resp);
			break;
		case "/user-edit":
			editUser(req, resp, id);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/users");
			break;
		}
	}

	private void handleListUsers(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String deleteID = req.getParameter("deleteID");

		if (NumberUtil.isPositiveInteger(deleteID) == false) {
			List<UserEntity> listUsers = userService.getAllUsers();
			req.setAttribute("listUsers", listUsers);
			req.getRequestDispatcher("user-table.jsp").forward(req, resp);
		} else {
			userService.deleteUserByID(Integer.parseInt(deleteID));
			resp.sendRedirect(req.getContextPath() + "/users");
		}
	}

	private void showAddUserForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<RoleEntity> roles = roleService.getAllRoles();
		req.setAttribute("roles", roles);
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}

	private void showEditUserForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String id = req.getParameter("id");

		if (NumberUtil.isPositiveInteger(id) == false) {
			resp.sendRedirect(req.getContextPath() + "/users");
		} else {
			List<RoleEntity> roles = roleService.getAllRoles();
			req.setAttribute("roles", roles);
			UserEntity user = userService.getUserByID(Integer.parseInt(id));
			if (user.getEmail() == null || user.getEmail().isEmpty()) {
				System.out.println("not user");
				resp.sendRedirect(req.getContextPath() + "/users");
				return;
			}
			req.setAttribute("user", user);
			req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
		}

	}

	private void showUserDetails(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DecimalFormat df = new DecimalFormat("#.##");

		String userID = req.getParameter("id");
		if (NumberUtil.isPositiveInteger(userID) == false) {
			System.out.println("not int");
			resp.sendRedirect(req.getContextPath() + "/users");
			return;
		} else {
			List<TaskEntity> taskStatus = taskService.getTaskStatus();
			List<TaskEntity> taskDetailsEntities = taskService.getTasksByUserID(Integer.parseInt(userID));
			UserEntity user = userService.getUserByID(Integer.parseInt(userID));
			System.out.println(user);
			if (user.getEmail() == null || user.getEmail().isEmpty()) {
				System.out.println("not user");
				resp.sendRedirect(req.getContextPath() + "/users");
				return;
			}
			UserDetails userDetails = new UserDetails();
			userDetails.setEmail(user.getEmail());
			userDetails.setFullName(user.getFullName());
			userDetails.setTotalTasks(taskDetailsEntities.size());

			Map<Integer, List<TaskEntity>> tasksByStatus = new HashMap<>();
			for (TaskEntity taskEntity : taskDetailsEntities) {
				int statusID = taskEntity.getStatusID();
				List<TaskEntity> tasksForStatus = tasksByStatus.get(statusID);

				if (tasksForStatus == null) {
					tasksForStatus = new ArrayList<>();
					tasksByStatus.put(statusID, tasksForStatus);
				}

				tasksForStatus.add(taskEntity);
			}

			userDetails.setTasksByStatus(tasksByStatus);

			System.out.println(userDetails.getEmail());
			System.out.println(userDetails.getFullName());
			System.out.println(userDetails.getTotalTasks());
			int notStartedTasks = 0;
			int inProgressTasks = 0;
			int completedTasks = 0;

			for (Map.Entry<Integer, List<TaskEntity>> entry : userDetails.getTasksByStatus().entrySet()) {
				int statusID = entry.getKey();
				List<TaskEntity> tasks = entry.getValue();

				switch (statusID) {
				case 1:
					notStartedTasks += tasks.size();
					break;
				case 2:
					inProgressTasks += tasks.size();
					break;
				case 3:
					completedTasks += tasks.size();
					break;
				default:
					break;
				}
			}

			int totalTasks = userDetails.getTotalTasks() > 0 ? userDetails.getTotalTasks() : 1;
//			double notStartedPercentage = ((double) notStartedTasks / totalTasks) * 100;
//			double inProgressPercentage = ((double) inProgressTasks / totalTasks) * 100;
//			double completedPercentage = ((double) completedTasks / totalTasks) * 100;
//			String formattedNotStartedPercentage = df.format(notStartedPercentage);
			String formattedNotStartedPercentage = NumberUtil.calculatePercentage(notStartedTasks, totalTasks);
			String formattedInProgressPercentage = NumberUtil.calculatePercentage(inProgressTasks, totalTasks);
			String formattedCompletedPercentage = NumberUtil.calculatePercentage(completedTasks, totalTasks);
//			String formattedInProgressPercentage = df.format(inProgressPercentage);
//			String formattedCompletedPercentage = df.format(completedPercentage);

			req.setAttribute("notStartedPercentage", formattedNotStartedPercentage);
			req.setAttribute("inProgressPercentage", formattedInProgressPercentage);
			req.setAttribute("completedPercentage", formattedCompletedPercentage);

			req.setAttribute("tasksByStatus", tasksByStatus);
			req.setAttribute("userDetails", userDetails);

			req.getRequestDispatcher("user-details.jsp").forward(req, resp);
		}
	}

	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");

		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		String role = req.getParameter("role");
		String hashPassword = "";
		try {
			hashPassword = PasswordUtils.hashPassword(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		boolean result = userService.insertUser(email, hashPassword, fullname, phone, Integer.parseInt(role));
		if (result) {
			resp.sendRedirect(req.getContextPath() + "/users");
		} else {
			resp.sendRedirect(req.getContextPath() + "/user-add");
		}
	}

	private void editUser(HttpServletRequest req, HttpServletResponse resp, String id) throws IOException {
		req.setCharacterEncoding("UTF-8");

		if (NumberUtil.isPositiveInteger(id) == false) {
			resp.sendRedirect(req.getContextPath() + "/users");
			return;
		}

		UserEntity user = userService.getUserByID(Integer.parseInt(id));
		req.setAttribute("user", user);

		String fullname = req.getParameter("fullname");
//		String email = req.getParameter("email");
//		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		String role = req.getParameter("role");

		boolean result = userService.editUser(user.getEmail(), user.getPassword(), fullname, phone,
				Integer.parseInt(role), Integer.parseInt(id));
		if (result) {
			resp.sendRedirect(req.getContextPath() + "/users");
		} else {
			resp.sendRedirect(req.getContextPath() + "/user-edit?id=" + id);
		}
	}

}
