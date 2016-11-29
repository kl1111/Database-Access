package jdbcExercises;

import java.sql.SQLException;
//import java.util.List;

public class Client {

	public static void main(String[] args) throws SQLException {

		UsersDAO usersDao = new UsersDAO();
		// User user = new User();
		// usersDao.addUsers(user);

		// List<User> userList = usersDao.listUsers();
		// for (User user: userList) {
		// System.out.println("User ID: " + user.getUserID() + " Username: " +
		// user.getUsername() + " Password: " + user.getPassword() + " First
		// name: " + user.getFirstName() + "Last name: "+ user.getLastName() + "
		// Email address: " + user.getEmail() + " Role: " + user.getRole() + "
		// Status: " + user.getStatus());
		// }

		// User user = usersDao.getUser("name");
		// System.out.println("User ID: " + user.getUserID() + " Username: " +
		// user.getUsername() + " Password: " + user.getPassword() + " First
		// name: " + user.getFirstName() + " Last name: "+ user.getLastName() +
		// " Email address: " + user.getEmail() + " Role: " + user.getRole() + "
		// Status: " + user.getStatus());

		//usersDao.removeUser("name");
		
		User user = new User();
		user.setUsername("Lewis");
		user.setLastName("Luong");
		user.setFirstName("Lewis");
		user.setEmail("taro-kun@...");
		user.setRole("Karen's brother");
		user.setStatus(3);
		user.setPassword("veggie");
		usersDao.updateUser(user);

	}

}
