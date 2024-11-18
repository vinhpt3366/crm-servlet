package crm_app07.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTaskDetailsEntity {
	private int userId;
	private String fullName;
	private String email;
	private Map<String, List<TaskEntity>> tasksByStatus;

	public UserTaskDetailsEntity(int userId, String fullName, String email) {
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.tasksByStatus = new HashMap<>();
	}

	public UserTaskDetailsEntity() {

	}

	public void addTask(String status, TaskEntity task) {
		tasksByStatus.computeIfAbsent(status, k -> new ArrayList<>()).add(task);
	}

	public List<TaskEntity> getTasksByStatus(int i) {
		return tasksByStatus.getOrDefault(i, new ArrayList<>());
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, List<TaskEntity>> getTasksByStatus() {
		return tasksByStatus;
	}

	public void setTasksByStatus(Map<String, List<TaskEntity>> tasksByStatus) {
		this.tasksByStatus = tasksByStatus;
	}

}
