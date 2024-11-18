package crm_app07.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app07.entity.ProjectDetailsEntity;
import crm_app07.entity.ProjectEntity;
import crm_app07.entity.UserEntity;
import crm_app07.entity.UserTaskDetailsEntity;
import crm_app07.services.ProjectService;
import crm_app07.services.UserService;
import crm_app07.utils.NumberUtil;

@WebServlet(name = "projectController", urlPatterns = { "/projects", "/project-add", "/project-edit",
		"/project-details" })
public class ProjectController extends HttpServlet {
	private ProjectService projectService = new ProjectService();
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case "/projects":
			handleListProjects(req, resp);
			break;
		case "/project-add":
			showAddProjectForm(req, resp);
			break;
		case "/project-edit":
			showEditProjectForm(req, resp);
			break;
		case "/project-details":
			showProjectDetailsPage(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/projects");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		String id = req.getParameter("id");

		switch (action) {
		case "/project-add":
			addProject(req, resp);
			break;
		case "/project-edit":
			editProject(req, resp, id);
			break;

		default:
			resp.sendRedirect(req.getContextPath() + "/projects");
			break;
		}
	}

	private void handleListProjects(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String deleteID = req.getParameter("deleteID");

		if (NumberUtil.isPositiveInteger(deleteID) == false) {

//			HttpSession session = req.getSession(false);
//			UserEntity user = Utils.getUserFromSession(session);
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

			if (Integer.parseInt(roleId) == 1) {
				List<ProjectEntity> projects = projectService.getAllProjects();
				req.setAttribute("projects", projects);
				req.getRequestDispatcher("projects.jsp").forward(req, resp);
			} else if (Integer.parseInt(roleId) == 2) {
				List<ProjectEntity> projects = projectService.getMyProjects(user.getId());

				System.out.println("leader");
				for (ProjectEntity projectEntity : projects) {
					System.out.println(projectEntity.getName());
				}
				req.setAttribute("projects", projects);
				req.getRequestDispatcher("projects.jsp").forward(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath() + "/");
			}

		} else {
			projectService.deleteProjectByID(Integer.parseInt(deleteID));
			resp.sendRedirect(req.getContextPath() + "/projects");
		}
	}

	private void showAddProjectForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<UserEntity> leaders = new ArrayList<UserEntity>();
		leaders = userService.getAllLeaders();

		req.setAttribute("leaders", leaders);

		req.getRequestDispatcher("project-add.jsp").forward(req, resp);
	}

	private void showEditProjectForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String id = req.getParameter("id");
		if (NumberUtil.isPositiveInteger(id) == false) {
			resp.sendRedirect(req.getContextPath() + "/projects");
		} else {
			List<UserEntity> leaders = new ArrayList<UserEntity>();
			leaders = userService.getAllLeaders();

			req.setAttribute("leaders", leaders);

			ProjectEntity project = projectService.getProjectByID(Integer.parseInt(id));
			req.setAttribute("project", project);
			req.getRequestDispatcher("project-edit.jsp").forward(req, resp);
		}
	}

	private void showProjectDetailsPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		if (NumberUtil.isPositiveInteger(id) == false) {
			resp.sendRedirect(req.getContextPath() + "/projects");
		} else {
			List<ProjectDetailsEntity> list = projectService.getProjectDetailsByID(Integer.parseInt(id));
			int totalTasks = list.size();

			int notStartedTasks = 0;
			int inProgressTasks = 0;
			int completedTasks = 0;

			for (ProjectDetailsEntity projectDetailsEntity : list) {
				int statusID = projectDetailsEntity.getStatusID();
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
			}

			String formattedNotStartedPercentage = NumberUtil.calculatePercentage(notStartedTasks, totalTasks);
			String formattedInProgressPercentage = NumberUtil.calculatePercentage(inProgressTasks, totalTasks);
			String formattedCompletedPercentage = NumberUtil.calculatePercentage(completedTasks, totalTasks);

			req.setAttribute("notStartedPercentage", formattedNotStartedPercentage);
			req.setAttribute("inProgressPercentage", formattedInProgressPercentage);
			req.setAttribute("completedPercentage", formattedCompletedPercentage);

			System.out.println(id);

			List<UserTaskDetailsEntity> userTaskDetailsEntity = new ArrayList<UserTaskDetailsEntity>();
			userTaskDetailsEntity = projectService.getUserTaskDetails(Integer.parseInt(id));

			for (UserTaskDetailsEntity userTaskDetails : userTaskDetailsEntity) {
				System.out.println(userTaskDetails.getFullName());

				System.out.println(userTaskDetails.getTasksByStatus());
				System.out.println("-------------------------------");
			}

			req.setAttribute("userTaskDetailsEntity", userTaskDetailsEntity);

			req.getRequestDispatcher("project-details.jsp").forward(req, resp);
		}

	}

	private void addProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");

		String projectName = req.getParameter("projectName");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String leaderID = req.getParameter("leaderID");

		System.out.println("projectName: " + projectName);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);

		boolean result = projectService.insertProject(projectName, startDate, endDate, Integer.parseInt(leaderID));
		if (result) {
			resp.sendRedirect(req.getContextPath() + "/projects");
		} else {
			resp.sendRedirect(req.getContextPath() + "/project-add");
		}
	}

	private void editProject(HttpServletRequest req, HttpServletResponse resp, String id) throws IOException {
		req.setCharacterEncoding("UTF-8");

		if (NumberUtil.isPositiveInteger(id) == false) {
			resp.sendRedirect(req.getContextPath() + "/projects");
		}

		String projectName = req.getParameter("projectName");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String leaderID = req.getParameter("leaderID");

		System.out.println("projectName: " + projectName);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);

		boolean result = projectService.editProject(projectName, startDate, endDate, Integer.parseInt(id));
		if (result) {
			resp.sendRedirect(req.getContextPath() + "/projects");
		} else {
			resp.sendRedirect(req.getContextPath() + "/project-edit?id=" + id);
		}
	}

}
