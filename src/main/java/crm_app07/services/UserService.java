package crm_app07.services;

import java.util.List;

import crm_app07.entity.UserEntity;
import crm_app07.repository.UserRepository;

public class UserService {
	private UserRepository userRepository = new UserRepository();

	public List<UserEntity> getAllUsers() {
		return userRepository.getAllUsersAndRole();
	}

	public List<UserEntity> getAllLeaders() {
		return userRepository.getAllLeaders();
	}

	public UserEntity getUserByID(int id) {
		return userRepository.getUserByID(id);
	}

	public UserEntity getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}

	public boolean deleteUserByID(int id) {
		try {
			int result = userRepository.deleteUserByID(id);
			return result != -1;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean insertUser(String email, String password, String fullName, String phone, int role) {
		if (userRepository.getUserByEmail(email) == null) {
			return false;
		}

		int result = userRepository.insertUser(email, password, fullName, phone, role);
		return result != -1;
	}

	public boolean editUser(String email, String password, String fullName, String phone, int role, int id) {
		int result = userRepository.editUser(email, password, fullName, phone, role, id);
		return result != -1;
	}

}
