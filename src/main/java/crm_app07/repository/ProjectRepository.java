package crm_app07.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_app07.config.MysqlConfig;
import crm_app07.entity.ProjectEntity;

public class ProjectRepository {
	public List<ProjectEntity> getAllProjects() {
		List<ProjectEntity> projects = new ArrayList<ProjectEntity>();

		String queryString = "SELECT * FROM project";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				ProjectEntity item = new ProjectEntity();
				item.setId(res.getInt("project_id"));
				item.setName(res.getString("project_name"));
				item.setStartDate(res.getDate("start_date"));
				item.setEndDate(res.getDate("end_date"));

				projects.add(item);
			}
		} catch (SQLException e) {
			System.out.println("Lỗi truy vấn!");
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return projects;
	}

	public int deleteProjectByID(int id) {
		int rowDeleted = -1;
		String queryString = "DELETE FROM project p WHERE p.project_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi deleteProjectByID!");
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return rowDeleted;
	}

	public int insertProject(String projectName, String startDate, String endDate) {
		int result = -1;
		String queryString = "INSERT INTO project (project_name , description, start_date, end_date, creator_id) VALUES (?, ?, ?, ?, 1)";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, projectName);
			statement.setString(2, projectName);
			statement.setString(3, startDate);
			statement.setString(4, endDate);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi insertProject!");
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return result;
	}

}
