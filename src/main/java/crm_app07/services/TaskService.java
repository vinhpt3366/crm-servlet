package crm_app07.services;

import java.util.List;

import crm_app07.entity.TaskEntity;
import crm_app07.repository.TaskRepository;

public class TaskService {
	private TaskRepository taskRepository = new TaskRepository();

	public List<TaskEntity> getAllTasks() {
		return taskRepository.getAllTasks();
	}

	public List<TaskEntity> getTasksByUserID(int userID) {
		return taskRepository.getTasksByUserID(userID);
	}

	public List<TaskEntity> getTaskStatus() {
		System.out.println("getTaskStatus");
		return taskRepository.getTaskStatus();
	}

	public boolean deleteTaskByID(int id) {
		int hasDeleted = taskRepository.deleteTaskByID(id);
		return hasDeleted != -1;
	}

	public boolean insertTask(String taskName, String startDate, String endDate, int assigneeID, int projectID) {
		int result = taskRepository.insertTask(taskName, startDate, endDate, assigneeID, projectID);
		return result != -1;
	}

}
