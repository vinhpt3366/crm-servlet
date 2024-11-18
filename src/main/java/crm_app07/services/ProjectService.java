package crm_app07.services;

import java.util.List;

import crm_app07.entity.ProjectDetailsEntity;
import crm_app07.entity.ProjectEntity;
import crm_app07.entity.UserTaskDetailsEntity;
import crm_app07.repository.ProjectRepository;

public class ProjectService {
	private ProjectRepository projectRepository = new ProjectRepository();

	public List<ProjectEntity> getAllProjects() {
		return projectRepository.getAllProjects();
	}

	public List<ProjectEntity> getMyProjects(int userID) {
		return projectRepository.getMyProjects(userID);
	}

	public List<UserTaskDetailsEntity> getUserTaskDetails(int projectId) {
		return projectRepository.getUserTaskDetails(projectId);
	}

	public ProjectEntity getProjectByID(int id) {
		return projectRepository.getProjectByID(id);
	}

	public List<ProjectDetailsEntity> getProjectDetailsByID(int id) {
		return projectRepository.getProjectDetailsByID(id);
	}

	public boolean deleteProjectByID(int id) {
		if (id >= 0) {
			int hasDeleted = projectRepository.deleteProjectByID(id);
			return hasDeleted != -1;
		} else {
			System.out.println("ID không hợp lệ: " + id);
			return false;
		}

	}

	public boolean insertProject(String projectName, String startDate, String endDate, int leaderID) {
		int result = projectRepository.insertProject(projectName, startDate, endDate, leaderID);
		return result != -1;
	}

	public boolean editProject(String projectName, String startDate, String endDate, int id) {
		int result = projectRepository.editProject(projectName, startDate, endDate, id);
		return result != -1;
	}

}
