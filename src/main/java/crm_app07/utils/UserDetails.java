package crm_app07.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import crm_app07.entity.TaskEntity;

public class UserDetails {
	private int id;
	private String email;
	private String fullName;
	private int totalTasks;
	private Map<Integer, List<TaskEntity>> tasksByStatus;

	public UserDetails() {
		this.tasksByStatus = new HashMap<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getTotalTasks() {
		return totalTasks;
	}

	public void setTotalTasks(int totalTasks) {
		this.totalTasks = totalTasks;
	}

	public Map<Integer, List<TaskEntity>> getTasksByStatus() {
		return tasksByStatus;
	}

	public void setTasksByStatus(Map<Integer, List<TaskEntity>> tasksByStatus) {
		this.tasksByStatus = tasksByStatus;
	}
}
