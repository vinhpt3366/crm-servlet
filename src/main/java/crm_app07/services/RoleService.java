package crm_app07.services;

import java.util.List;

import crm_app07.entity.RoleEntity;
import crm_app07.repository.RoleRepository;

public class RoleService {
	private RoleRepository roleRepository = new RoleRepository();

	public List<RoleEntity> getAllRoles() {
		return roleRepository.getRoles();
	}

	public RoleEntity getRoleByID(int id) {
		return roleRepository.getRoleByID(id);
	}

	public boolean deleteRoleByID(int id) {
		int hasDeleted = roleRepository.deleteRoleByID(id);
		return hasDeleted != -1;
	}

	public boolean insertRole(String roleName, String description) {
		int result = roleRepository.insertRole(roleName, description);
		return result != -1;
	}

	public boolean editRole(String roleName, String description, int id) {
		int result = roleRepository.editRole(roleName, description, id);
		return result != -1;
	}

}
