package crm_app07.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import crm_app07.config.MysqlConfig;
import crm_app07.entity.UserEntity;

public class AuthRepository {

	public boolean getTokenByID(int id) {
		UserEntity user = new UserEntity();
		String queryString = "SELECT * FROM user_tokens WHERE user_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);

			ResultSet res = statement.executeQuery();
			while (res.next()) {
				user.setId(res.getInt("user_id"));
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
		if (user.getId() > 0) {
			return true;
		}
		return false;
	}

	public int insertToken(int userID, String token, Date time) {
		int result = -1;
		String queryString = "INSERT INTO user_tokens (user_id, auth_token, expiration_time) VALUES (?, ?, ?)";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, userID);
			statement.setString(2, token);
			System.out.println(time);
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(time.getTime());
			statement.setTimestamp(3, sqlDate);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi insertToken!");
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

	public boolean isValidToken(String token) {
		boolean result = false;
		String queryString = "SELECT expiration_time FROM user_tokens WHERE auth_token = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, token);

			ResultSet res = statement.executeQuery();
			if (res.next()) {
				Date expirationTime = res.getTimestamp("expiration_time");
				if (expirationTime.after(new Date())) {
					result = true;
				}
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
		return result;
	}

	public int deleteToken(String token) {
		int rowDeleted = -1;
		String queryString = "DELETE FROM user_tokens WHERE auth_token = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setString(1, token);
			rowDeleted = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi deleteToken!");
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

	public int deleteTokenByID(int id) {
		int rowDeleted = -1;
		String queryString = "DELETE FROM user_tokens WHERE user_id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi deleteTokenByID!");
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
}
