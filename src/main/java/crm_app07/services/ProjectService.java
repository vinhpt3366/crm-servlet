package crm_app07.services;

import java.util.List;

import crm_app07.entity.ProjectEntity;
import crm_app07.repository.ProjectRepository;

public class ProjectService {
	private ProjectRepository projectRepository = new ProjectRepository();

	public List<ProjectEntity> getAllProjects() {
		return projectRepository.getAllProjects();
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

	public boolean insertProject(String projectName, String startDate, String endDate) {
		int result = projectRepository.insertProject(projectName, startDate, endDate);
		return result != -1;
	}

}
