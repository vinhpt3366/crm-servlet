package crm_app07.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app07.entity.ProjectEntity;
import crm_app07.services.ProjectService;
import crm_app07.utils.NumberUtil;

@WebServlet(name = "projectController", urlPatterns = { "/projects", "/project-add" })
public class ProjectController extends HttpServlet {
	private ProjectService projectService = new ProjectService();

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
		default:
			resp.sendRedirect(req.getContextPath() + "/projects");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case "/project-add":
			addProject(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/roles");
			break;
		}
	}

	private void handleListProjects(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String deleteID = req.getParameter("deleteID");
		if (NumberUtil.isPositiveInteger(deleteID) == false) {
			List<ProjectEntity> projects = projectService.getAllProjects();
			req.setAttribute("projects", projects);
			req.getRequestDispatcher("projects.jsp").forward(req, resp);
		} else {
			projectService.deleteProjectByID(Integer.parseInt(deleteID));
			resp.sendRedirect(req.getContextPath() + "/projects");
		}
	}

	private void showAddProjectForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("project-add.jsp").forward(req, resp);
	}

	private void addProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");

		String projectName = req.getParameter("projectName");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");

		System.out.println("projectName: " + projectName);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);

		boolean result = projectService.insertProject(projectName, startDate, endDate);
		if (result) {
			resp.sendRedirect(req.getContextPath() + "/projects");
		} else {
			resp.sendRedirect(req.getContextPath() + "/project-add");
		}
	}

}
