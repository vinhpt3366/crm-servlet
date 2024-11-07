package crm_app07.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConfig {
	private final static String URL_DB = "jdbc:mysql://localhost:3307/crm";
	private final static String USERNAME_DB = "root";
	private final static String PASSWORD_DB = "abcd@356";

	public static Connection getConnection() {
		Connection connection = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL_DB, USERNAME_DB, PASSWORD_DB);
		} catch (ClassNotFoundException e) {
			System.out.println("Không tìm thấy driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Không tìm thấy db!");
			e.printStackTrace();
		}

		return connection;
	}
}
