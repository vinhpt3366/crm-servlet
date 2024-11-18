package crm_app07.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import crm_app07.config.MysqlConfig;
import crm_app07.entity.ProjectDetailsEntity;
import crm_app07.entity.ProjectEntity;
import crm_app07.entity.TaskEntity;
import crm_app07.entity.UserTaskDetailsEntity;

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

	public List<ProjectEntity> getMyProjects(int userID) {
		List<ProjectEntity> projects = new ArrayList<ProjectEntity>();

		String queryString = "SELECT * FROM project WHERE leader_id = ?";
		System.out.println(userID);
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, userID);
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

		for (ProjectEntity projectEntity : projects) {
			System.out.println(projectEntity.getName());
		}
		return projects;
	}

	public ProjectEntity getProjectByID(int id) {
		ProjectEntity item = new ProjectEntity();

		String queryString = "SELECT * FROM project p WHERE p.project_id = ?";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				item.setId(res.getInt("project_id"));
				item.setName(res.getString("project_name"));
				item.setStartDate(res.getDate("start_date"));
				item.setEndDate(res.getDate("end_date"));
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
		return item;
	}

	public List<ProjectDetailsEntity> getProjectDetailsByID(int id) {
		List<ProjectDetailsEntity> list = new ArrayList<ProjectDetailsEntity>();

		String queryString = "SELECT p.project_id, p.project_name, t.task_name, ts.status_id, ts.status_name, u.full_name FROM project p JOIN task t ON t.project_id = p.project_id  JOIN task_status ts ON ts.status_id = t.status_id  JOIN `user` u ON u.user_id = t.assignee_id  WHERE p.project_id = ?";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				ProjectDetailsEntity item = new ProjectDetailsEntity();
				item.setId(res.getInt("project_id"));
				item.setProjectName(res.getString("project_name"));
				item.setTaskName(res.getString("project_name"));
				item.setStatusID(res.getInt("status_id"));
				item.setStatusName(res.getString("status_name"));
				item.setAssigneeName(res.getString("full_name"));

				list.add(item);
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
		return list;
	}

	public List<UserTaskDetailsEntity> getUserTaskDetails(int projectId) {
		List<UserTaskDetailsEntity> userTaskDetailsList = new ArrayList<>();
		String queryString = "SELECT t.*, u.full_name, u.email, p.project_name FROM task t JOIN `user` u ON u.user_id = t.assignee_id JOIN project p ON p.project_id = t.project_id WHERE p.project_id = ?";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, projectId);
			ResultSet resultSet = statement.executeQuery();
			Map<Integer, UserTaskDetailsEntity> userTaskMap = new HashMap<>();

			while (resultSet.next()) {
				int userId = resultSet.getInt("assignee_id");
				String fullName = resultSet.getString("full_name");
				String email = resultSet.getString("email");
				System.out.println(email);

				if (!userTaskMap.containsKey(userId)) {
					userTaskMap.put(userId, new UserTaskDetailsEntity(userId, fullName, email));
				}

				int taskId = resultSet.getInt("task_id");
				String taskName = resultSet.getString("task_name");
				String startDate = resultSet.getDate("start_date").toString();
				String endDate = resultSet.getDate("end_date").toString();
				String status = resultSet.getString("status_id");

				TaskEntity task = new TaskEntity(taskId, taskName, startDate, endDate, projectId);

				userTaskMap.get(userId).addTask(status, task);
			}
			System.out.println(userTaskMap.values());
			for (Map.Entry<Integer, UserTaskDetailsEntity> entry : userTaskMap.entrySet()) {
				Integer userId = entry.getKey();
				UserTaskDetailsEntity userTaskDetails = entry.getValue();
				System.out.println("User ID: " + userId);
				System.out.println("User Details: " + userTaskDetails.toString());
				System.out.println("-------------------------------");
			}
			userTaskDetailsList.addAll(userTaskMap.values());

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

		for (UserTaskDetailsEntity userTaskDetailsEntity : userTaskDetailsList) {
			userTaskDetailsEntity.getEmail();

		}
		return userTaskDetailsList;
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

	public int insertProject(String projectName, String startDate, String endDate, int leaderID) {
		int result = -1;
		String queryString = "INSERT INTO project (project_name , description, start_date, end_date, leader_id) VALUES (?, ?, ?, ?, ?)";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, projectName);
			statement.setString(2, projectName);
			statement.setString(3, startDate);
			statement.setString(4, endDate);
			statement.setInt(5, leaderID);

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

	public int editProject(String projectName, String startDate, String endDate, int id) {
		int result = -1;
		String queryString = "UPDATE project p SET p.project_name = ?, p.start_date = ?, p.end_date = ? WHERE p.project_id = ?";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, projectName);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, id);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi editProject!");
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
