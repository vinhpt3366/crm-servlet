package crm_app07.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_app07.config.MysqlConfig;
import crm_app07.entity.TaskEntity;

public class TaskRepository {
	public List<TaskEntity> getAllTasks() {
		List<TaskEntity> listTasks = new ArrayList<TaskEntity>();

		String queryString = "SELECT t.*, u.full_name, p.project_name, ts.status_name FROM task t JOIN user u ON t.assignee_id = u.user_id JOIN project p ON t.project_id = p.project_id JOIN task_status ts ON t.status_id = ts.status_id";

		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				TaskEntity item = new TaskEntity();
				item.setId(res.getInt("task_id"));
				item.setName(res.getString("task_name"));
				item.setDescription(res.getString("description"));
				item.setStartDate(res.getString("start_date"));
				item.setEndDate(res.getString("end_date"));
				item.setAssigneeName(res.getString("full_name"));
				item.setProjectName(res.getString("project_name"));
				item.setStatusName(res.getString("status_name"));

				listTasks.add(item);
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

		return listTasks;
	}

	public List<TaskEntity> getAllTasksByLeader(int leaderID) {
		List<TaskEntity> listTasks = new ArrayList<TaskEntity>();

		String queryString = "SELECT t.*, u.full_name, p.project_name, p.leader_id, ts.status_name FROM task t JOIN user u ON t.assignee_id = u.user_id JOIN project p ON t.project_id = p.project_id JOIN task_status ts ON t.status_id = ts.status_id WHERE p.leader_id = ?";

		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, leaderID);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				TaskEntity item = new TaskEntity();
				item.setId(res.getInt("task_id"));
				item.setName(res.getString("task_name"));
				item.setDescription(res.getString("description"));
				item.setStartDate(res.getString("start_date"));
				item.setEndDate(res.getString("end_date"));
				item.setAssigneeName(res.getString("full_name"));
				item.setProjectName(res.getString("project_name"));
				item.setStatusName(res.getString("status_name"));

				listTasks.add(item);
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

		return listTasks;
	}

	public TaskEntity getTaskByID(int id) {
		TaskEntity item = new TaskEntity();

		String queryString = "SELECT * FROM task t WHERE t.task_id = ?";

		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				item.setId(res.getInt("task_id"));
				item.setName(res.getString("task_name"));
				item.setDescription(res.getString("description"));
				item.setStartDate(res.getString("start_date"));
				item.setEndDate(res.getString("end_date"));
				item.setAssigneeID(res.getInt("assignee_id"));
				item.setProjectID(res.getInt("project_id"));
				item.setStatusID(res.getInt("status_id"));
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

	public List<TaskEntity> getTasksByUserID(int userID) {
		List<TaskEntity> listTasks = new ArrayList<TaskEntity>();

		String queryString = "SELECT t.*, u.email, u.full_name, ts.status_name, p.project_name FROM task t JOIN `user` u ON t.assignee_id = u.user_id  JOIN task_status ts ON t.status_id = ts.status_id JOIN project p ON p.project_id = t.project_id  WHERE u.user_id = ?";

		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, userID);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				TaskEntity item = new TaskEntity();
				item.setId(res.getInt("task_id"));
				item.setName(res.getString("task_name"));
				item.setDescription(res.getString("description"));
				item.setStartDate(res.getString("start_date"));
				item.setEndDate(res.getString("end_date"));
				item.setAssigneeName(res.getString("full_name"));
				item.setAssigneeEmail(res.getString("email"));
				item.setStatusName(res.getString("status_name"));
				item.setStatusID(res.getInt("status_id"));
				item.setProjectName(res.getString("project_name"));

				listTasks.add(item);
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

		return listTasks;
	}

	public List<TaskEntity> getTaskStatus() {
		List<TaskEntity> listTaskStatus = new ArrayList<TaskEntity>();

		String queryString = "SELECT * FROM task_status ts";

		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				TaskEntity item = new TaskEntity();
				item.setStatusID(res.getInt("status_id"));
				item.setStatusName(res.getString("status_name"));

				listTaskStatus.add(item);
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

		return listTaskStatus;
	}

	public int deleteTaskByID(int id) {
		int rowDeleted = -1;
		String queryString = "DELETE FROM task t WHERE t.task_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi deleteTaskByID!");
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

	public int insertTask(String taskName, String startDate, String endDate, int assigneeID, int projectID) {
		int result = -1;
		String queryString = "INSERT INTO task (task_name , description, start_date, end_date, assignee_id, project_id, status_id) VALUES (?, ?, ?, ?, ?, ?, 1)";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, taskName);
			statement.setString(2, taskName);
			statement.setString(3, startDate);
			statement.setString(4, endDate);
			statement.setInt(5, assigneeID);
			statement.setInt(6, projectID);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi insertRole!");
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

	public int editTask(String taskName, String startDate, String endDate, int assigneeID, int projectID, int id) {
		int result = -1;
		String queryString = "UPDATE `task` t SET  t.project_id = ?, t.task_name = ?, t.assignee_id = ?, t.start_date = ?, t.end_date = ? WHERE t.task_id = ?";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, projectID);
			statement.setString(2, taskName);
			statement.setInt(3, assigneeID);
			statement.setString(4, startDate);
			statement.setString(5, endDate);
			statement.setInt(6, id);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi editTask!");
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

	public int updateTaskStatus(int statusID, int taskID) {
		int result = -1;
		String queryString = "UPDATE task t SET t.status_id = ? WHERE t.task_id = ?";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, statusID);
			statement.setInt(2, taskID);

			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi updateTaskStatus!");
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
