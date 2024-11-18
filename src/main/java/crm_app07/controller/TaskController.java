package crm_app07.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app07.entity.ProjectEntity;
import crm_app07.entity.TaskEntity;
import crm_app07.entity.UserEntity;
import crm_app07.services.ProjectService;
import crm_app07.services.TaskService;
import crm_app07.services.UserService;
import crm_app07.utils.NumberUtil;

@WebServlet(name = "taskController", urlPatterns = { "/tasks", "/task-add", "/task-edit" })
public class TaskController extends HttpServlet {
	private TaskService taskService = new TaskService();
	private ProjectService projectService = new ProjectService();
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();
		switch (action) {
		case "/tasks":
			handleListTasks(req, resp);
			break;
		case "/task-add":
			showAddTaskForm(req, resp);
			break;
		case "/task-edit":
			showEditTaskForm(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/tasks");
			break;
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		String id = req.getParameter("id");

		switch (action) {
		case "/task-add":
			addTask(req, resp);
			break;
		case "/task-edit":
			editTask(req, resp, id);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/tasks");
			break;
		}
	}

	private void handleListTasks(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String deleteID = req.getParameter("deleteID");

		if (NumberUtil.isPositiveInteger(deleteID) == false) {
			// admin
			Cookie[] cookies = req.getCookies();
			String roleId = null;
			String email = null;

			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("roleID".equals(cookie.getName())) {
						roleId = cookie.getValue();
					}
					if ("email".equals(cookie.getName())) {
						email = cookie.getValue();
					}
				}
			}

			int roleID = (roleId != null) ? Integer.parseInt(roleId) : -1;
			UserEntity user = userService.getUserByEmail(email);

			if (roleID == 1) {
				List<TaskEntity> tasks = taskService.getAllTasks();
				req.setAttribute("tasks", tasks);

			} else if (roleID == 2) {
				List<TaskEntity> tasks = taskService.getAllTasksByLeader(user.getId());
				req.setAttribute("tasks", tasks);
			} else {
				resp.sendRedirect(req.getContextPath() + "/");
				return;
			}

			req.getRequestDispatcher("task.jsp").forward(req, resp);
		} else {
			taskService.deleteTaskByID(Integer.parseInt(deleteID));
			resp.sendRedirect(req.getContextPath() + "/tasks");
		}
	}

	private void showAddTaskForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<UserEntity> users = userService.getAllUsers();

		Cookie[] cookies = req.getCookies();
		String roleId = null;
		String email = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("roleID".equals(cookie.getName())) {
					roleId = cookie.getValue();
				}
				if ("email".equals(cookie.getName())) {
					email = cookie.getValue();
				}
			}
		}

		int roleID = (roleId != null) ? Integer.parseInt(roleId) : -1;
		UserEntity user = userService.getUserByEmail(email);

		if (roleID == 1) {
			List<ProjectEntity> projects = projectService.getAllProjects();
			req.setAttribute("projects", projects);

		} else if (roleID == 2) {
			List<ProjectEntity> projects = projectService.getMyProjects(user.getId());
			req.setAttribute("projects", projects);

		} else {
			resp.sendRedirect(req.getContextPath() + "/");
			return;
		}

		req.setAttribute("users", users);

		req.getRequestDispatcher("task-add.jsp").forward(req, resp);
	}

	private void showEditTaskForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String id = req.getParameter("id");

		if (NumberUtil.isPositiveInteger(id) == false) {
			resp.sendRedirect(req.getContextPath() + "/tasks");
		} else {
			TaskEntity task = taskService.getTaskByID(Integer.parseInt(id));
			List<ProjectEntity> projects = projectService.getAllProjects();
			List<UserEntity> users = userService.getAllUsers();
			req.setAttribute("projects", projects);
			req.setAttribute("users", users);
			req.setAttribute("task", task);

			req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
		}
	}

	private void addTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");

		String projectID = req.getParameter("projectID");
		String taskName = req.getParameter("taskName");
		String assigneeID = req.getParameter("assigneeID");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");

		System.out.println("projectID: " + projectID);
		System.out.println("taskName: " + taskName);
		System.out.println("assigneeID: " + assigneeID);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);

		boolean result = taskService.insertTask(taskName, startDate, endDate, Integer.parseInt(assigneeID),
				Integer.parseInt(projectID));
		if (result) {
			resp.sendRedirect(req.getContextPath() + "/tasks");
		} else {
			resp.sendRedirect(req.getContextPath() + "/task-add");
		}
	}

	private void editTask(HttpServletRequest req, HttpServletResponse resp, String id) throws IOException {
		req.setCharacterEncoding("UTF-8");

		String projectID = req.getParameter("projectID");
		String taskName = req.getParameter("taskName");
		String assigneeID = req.getParameter("assigneeID");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");

		System.out.println("projectID: " + projectID);
		System.out.println("taskName: " + taskName);
		System.out.println("assigneeID: " + assigneeID);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);

		boolean result = taskService.editTask(taskName, startDate, endDate, Integer.parseInt(assigneeID),
				Integer.parseInt(projectID), Integer.parseInt(id));
		if (result) {
			resp.sendRedirect(req.getContextPath() + "/tasks");
		} else {
			resp.sendRedirect(req.getContextPath() + "/task-edit?id=" + id);
		}
	}

}
