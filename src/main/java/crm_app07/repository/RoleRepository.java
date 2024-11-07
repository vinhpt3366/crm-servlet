package crm_app07.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_app07.config.MysqlConfig;
import crm_app07.entity.RoleEntity;

public class RoleRepository {
	public List<RoleEntity> getRoles() {
		List<RoleEntity> roles = new ArrayList<RoleEntity>();

		String queryString = "SELECT * FROM role";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				RoleEntity roleEntity = new RoleEntity();
				roleEntity.setId(res.getInt("role_id"));
				roleEntity.setName(res.getString("role_name"));
				roleEntity.setDescription(res.getString("description"));

				roles.add(roleEntity);
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
		return roles;
	}

	public RoleEntity getRoleByID(int id) {
		RoleEntity roleEntity = new RoleEntity();

		String queryString = "SELECT * FROM role WHERE role_id = ?";

		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				roleEntity.setId(res.getInt("role_id"));
				roleEntity.setName(res.getString("role_name"));
				roleEntity.setDescription(res.getString("description"));
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
		return roleEntity;
	}

	public int deleteRoleByID(int id) {
		int rowDeleted = -1;
		String queryString = "DELETE FROM `role` r WHERE r.role_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi deleteRoleByID!");
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

	public int insertRole(String roleName, String description) {
		int result = -1;

		String queryString = "INSERT INTO role (role_name, description) VALUES (?, ?)";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, roleName);
			statement.setString(2, description);
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

	public int editRole(String roleName, String description, int id) {
		int result = -1;
		String queryString = "UPDATE role r SET  r.role_name = ?, r.description = ? WHERE r.role_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, roleName);
			statement.setString(2, description);
			statement.setInt(3, id);

			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi editRole!");
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
