package jdbcExercises;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class UsersDAO implements Users {

	static Connection connection = null;
	Logger log = Logger.getLogger(UsersDAO.class);

	public void addUsers(User user) {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:trainee1/!QAZSE4@localhost:1521:XE");
			CallableStatement stmt = connection.prepareCall("{call ADD_USER(?,?,?,?,?,?,?)}");

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.setString(6, user.getRole());
			stmt.setInt(7, user.getStatus());
			stmt.execute();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Error adding a new user to database: " + sqle.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public User getUser(String username) {
		User user = new User();

		try {
			// registers oracle driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:trainee1/!QAZSE4@localhost:1521:XE");
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT user_id, username, password, firstName, lastName, email, role, status FROM TPUsers WHERE username=?");
			pstmt.setString(1, username);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				int userID = resultSet.getInt("user_ID");
				String usernames = resultSet.getString("username");
				String password = resultSet.getString("password");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				String role = resultSet.getString("role");
				int status = resultSet.getInt("status");
				user.setUserID(userID);
				user.setUsername(usernames);
				user.setPassword(password);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				user.setRole(role);
				user.setStatus(status);
			}

			// System.out.println("user_id: " + userID + "username: " +
			// usernames + "password: " + password + "first name: " + firstName
			// + "last name: "+ lastName + "email address: " + email + "role: "
			// + role + "status: " + status);

			return user;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Error adding a new user to database: " + sqle.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void removeUser(String username) {
		try {
			// registers oracle driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:trainee1/!QAZSE4@localhost:1521:XE");
			PreparedStatement pstmt = connection.prepareStatement("delete FROM TPUsers WHERE username=?");
			pstmt.setString(1, username);
			pstmt.execute();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Error deleting user from database: " + sqle.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateUser(User user) {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:trainee1/!QAZSE4@localhost:1521:XE");
			CallableStatement stmt = connection.prepareCall("{call UPDATE_USERS(?,?,?,?,?,?,?)}");

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.setString(6, user.getRole());
			stmt.setInt(7, user.getStatus());
			stmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Error updating user in database: " + sqle.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public List<User> listUsers() {
		List<User> userList = new ArrayList<User>();

		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:trainee1/!QAZSE4@localhost:1521:XE");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT user_id, username, password, firstName, lastName, email, role, status FROM TPUsers");

			while (resultSet.next()) {
				User user = new User();
				int userID = resultSet.getInt("user_ID");
				String usernames = resultSet.getString("username");
				String password = resultSet.getString("password");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				String role = resultSet.getString("role");
				int status = resultSet.getInt("status");
				user.setUserID(userID);
				user.setUsername(usernames);
				user.setPassword(password);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				user.setRole(role);
				user.setStatus(status);
				userList.add(user);
			}
			return userList;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

}
