package crm_app07.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_app07.config.MysqlConfig;
import crm_app07.entity.RoleEntity;
import crm_app07.entity.UserEntity;

public class UserRepository {
	public UserEntity getUserByEmail(String email) {
		UserEntity user = new UserEntity();
		String queryString = "SELECT * FROM user WHERE email = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, email);

			ResultSet res = statement.executeQuery();
			while (res.next()) {
				user.setId(res.getInt("user_id"));
				user.setEmail(res.getString("email"));
				user.setPassword(res.getString("password"));
				user.setRoleID(res.getInt("role_id"));
			}
		} catch (SQLException e) {
			System.out.println("Lỗi truy vấn!");
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				return user;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	public UserEntity getUserByID(int id) {
		UserEntity user = new UserEntity();
		String queryString = "SELECT * FROM user WHERE user_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);

			ResultSet res = statement.executeQuery();
			while (res.next()) {
				user.setId(res.getInt("user_id"));
				user.setEmail(res.getString("email"));
				user.setPassword(res.getString("password"));
				user.setRoleID(res.getInt("role_id"));
				user.setFullName(res.getString("full_name"));
				user.setPhoneNumber(res.getString("phone_number"));
			}
		} catch (SQLException e) {
			System.out.println("Lỗi truy vấn!");
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				return user;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	public List<UserEntity> getAllUsersAndRole() {
		List<UserEntity> listUsers = new ArrayList<UserEntity>();

		String queryString = "SELECT * FROM user u JOIN role r ON r.role_id = u.role_id";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);

			ResultSet res = statement.executeQuery();
			while (res.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.setId(res.getInt("user_id"));
				userEntity.setEmail(res.getString("email"));
				userEntity.setPassword(res.getString("password"));
				userEntity.setRoleID(res.getInt("role_id"));
				userEntity.setFullName(res.getString("full_name"));
				userEntity.setPhoneNumber(res.getString("phone_number"));

				RoleEntity roleEntity = new RoleEntity();
				roleEntity.setId(res.getInt("role_id"));
				roleEntity.setName(res.getString("role_name"));
				roleEntity.setDescription(res.getString("description"));

				userEntity.setRole(roleEntity);
				listUsers.add(userEntity);
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

		return listUsers;
	}

	public int deleteUserByID(int id) {
		int rowDeleted = -1;
		String queryString = "DELETE FROM `user` u WHERE u.user_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi deleteUserByID!");
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

	public int insertUser(String email, String password, String fullName, String phone, int role) {
		int result = -1;
		String queryString = "INSERT INTO user (email , password, full_name, phone_number, role_id) VALUES (?, ?, ?, ?, ?)";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, email);
			statement.setString(2, password);
			statement.setString(3, fullName);
			statement.setString(4, phone);
			statement.setInt(5, role);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi insertUser!");
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

	public int editUser(String email, String password, String fullName, String phone, int role, int id) {
		int result = -1;
		String queryString = "UPDATE `user` u SET  u.email = ?, u.password = ?, u.full_name = ?, u.phone_number = ?, u.role_id = ? WHERE u.user_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, email);
			statement.setString(2, password);
			statement.setString(3, fullName);
			statement.setString(4, phone);
			statement.setInt(5, role);
			statement.setInt(6, id);

			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi editUser!");
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
