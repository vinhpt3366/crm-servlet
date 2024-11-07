package crm_app07.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app07.entity.RoleEntity;
import crm_app07.services.RoleService;
import crm_app07.utils.NumberUtil;

@WebServlet(name = "roleController", urlPatterns = { "/roles", "/role-add", "/role-edit" })
public class RoleController extends HttpServlet {
	private RoleService roleService = new RoleService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case "/roles":
			handleListRoles(req, resp);
			break;
		case "/role-add":
			showAddRoleForm(req, resp);
		case "/role-edit":
			showEditRoleForm(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/roles");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		String id = req.getParameter("id");

		switch (action) {
		case "/role-add":
			addRole(req, resp);
			break;
		case "/role-edit":
			editRole(req, resp, id);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/roles");
			break;
		}
	}

	private void handleListRoles(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String deleteID = req.getParameter("deleteID");

		if (NumberUtil.isPositiveInteger(deleteID) == false) {
			List<RoleEntity> roleEntities = roleService.getAllRoles();
			req.setAttribute("roles", roleEntities);
			req.getRequestDispatcher("role-table.jsp").forward(req, resp);
		} else {
			roleService.deleteRoleByID(Integer.parseInt(deleteID));
			resp.sendRedirect(req.getContextPath() + "/roles");
		}
	}

	private void showAddRoleForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<RoleEntity> roles = roleService.getAllRoles();
		req.setAttribute("roles", roles);
		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	}

	private void showEditRoleForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		if (NumberUtil.isPositiveInteger(id) == false) {
			resp.sendRedirect(req.getContextPath() + "/roles");
		} else {
			RoleEntity role = roleService.getRoleByID(Integer.parseInt(id));
			req.setAttribute("role", role);
			req.getRequestDispatcher("role-edit.jsp").forward(req, resp);
		}

	}

	private void addRole(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");

		String roleName = req.getParameter("roleName");
		String description = req.getParameter("description");

		System.out.println("roleName: " + roleName);
		System.out.println("description: " + description);

		boolean result = roleService.insertRole(roleName, description);
		if (result) {
			resp.sendRedirect(req.getContextPath() + "/roles");
		} else {
			resp.sendRedirect(req.getContextPath() + "/role-add");
		}
	}

	private void editRole(HttpServletRequest req, HttpServletResponse resp, String id) throws IOException {
		req.setCharacterEncoding("UTF-8");

		String roleName = req.getParameter("roleName");
		String description = req.getParameter("description");

		System.out.println("roleName: " + roleName);
		System.out.println("description: " + description);
		System.out.println(id);

		boolean result = roleService.editRole(roleName, description, Integer.parseInt(id));
		if (result) {
			resp.sendRedirect(req.getContextPath() + "/roles");
		} else {
			resp.sendRedirect(req.getContextPath() + "/role-edit");
		}
	}

}
