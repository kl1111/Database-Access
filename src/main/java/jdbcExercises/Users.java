package jdbcExercises;

import java.sql.SQLException;
import java.util.List;

public interface Users {
	
	void addUsers(User user) throws SQLException;
	
	User getUser(String username) throws SQLException;
	
	void removeUser(String username);
	
	void updateUser (User user);
	
	List<User> listUsers();
	
	
	
	

}
