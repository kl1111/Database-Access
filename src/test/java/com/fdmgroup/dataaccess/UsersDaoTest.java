package com.fdmgroup.dataaccess;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import jdbcExercises.User;
import jdbcExercises.UsersDAO;

public class UsersDaoTest {

	private UsersDAO usersDao;

	@Before
	public void setUp() {
		usersDao = new UsersDAO();
		dropTable();
		tableSetup();
	}

	public void dropTable() {
		Connection connection = null;
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:trainee1/!QAZSE4@localhost:1521:XE");
			Statement statement = connection.createStatement();
			statement.execute("DROP TABLE TPUsers");
		} catch (SQLException e) {
			e.printStackTrace();
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

	// Separated Drop table and Create table as try catch would catch exception
	// if table does not exist while dropping
	public void tableSetup() {
		Connection connection = null;
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:trainee1/!QAZSE4@localhost:1521:XE");
			Statement statement = connection.createStatement();
			statement.execute(
					"create table TPUsers(user_id number(2) primary key, username VARCHAR2(20) unique, password VARCHAR2(20), firstName VARCHAR2(20), lastName VARCHAR2(20), email VARCHAR2(20), role VARCHAR2(20), status NUMBER(2))");
			statement.execute(
					"INSERT INTO TPUsers VALUES (1, 'chowchow', 'vegetables', 'Karen', 'Luong', 'karen.luong', 'developer', 1)");
			statement.execute(
					"INSERT INTO TPUsers VALUES (2, 'taro-kun', 'vegetables', 'Lewis', 'Luong', 'lewis.luong', 'developer', 1)");
			statement.execute(
					"INSERT INTO TPUsers VALUES (3, 'radish-kun', 'vegetables', 'Robert', 'Chung', 'robert.chung', 'developer', 1)");
		} catch (SQLException e) {
			e.printStackTrace();
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

	@Test
	public void test_ListUsers_CheckEmptyDatabase() {
		// ARRANGE
		List<User> userList = new ArrayList<User>();
		int expected = 0;
		// ACT
		usersDao.removeUser("chowchow");
		usersDao.removeUser("taro-kun");
		usersDao.removeUser("radish-kun");
		userList = usersDao.listUsers();
		// ASSERT
		assertEquals(expected, userList.size());
	}

	@Test
	public void test_ListUsers_RetrieveCorrectUsers() {
		// arrange
		List<User> userList = new ArrayList<User>();
		int expected = 3;
		// act
		userList = usersDao.listUsers();
		// assert
		assertEquals(expected, userList.size());
	}

	@Test
	public void test_ListUsers_ReturnsNull() {
		// arrange
		List<User> userList = new ArrayList<User>();
		// act
		dropTable();
		userList = usersDao.listUsers();
		// assert
		assertNull(userList);
	}

	@Test
	public void test_GetUser_RetrievesTaroKun() {
		// ARRANGE
		User user;
		int expectedID = 2;
		String expectedUsername = "taro-kun";
		String expectedPassword = "vegetables";
		String expectedFirstName = "Lewis";
		String expectedLastName = "Luong";
		String expectedEmail = "lewis.luong";
		String expectedRole = "developer";
		int expectedStatus = 1;
		// ACT
		user = usersDao.getUser("taro-kun");
		// ASSERT
		assertEquals(expectedID, user.getUserID());
		assertEquals(expectedUsername, user.getUsername());
		assertEquals(expectedPassword, user.getPassword());
		assertEquals(expectedFirstName, user.getFirstName());
		assertEquals(expectedLastName, user.getLastName());
		assertEquals(expectedEmail, user.getEmail());
		assertEquals(expectedRole, user.getRole());
		assertEquals(expectedStatus, user.getStatus());

	}
	
	@Test
	public void test_GetUser_ReturnsEmptyUserWithInvalidUsername(){
		User user;
		int expectedID = 0;
		int expectedStatus = 0;
		// ACT
		user = usersDao.getUser("Alien");
		// ASSERT
		assertEquals(expectedID, user.getUserID());
		assertNull(user.getUsername());
		assertNull(user.getPassword());
		assertNull(user.getFirstName());
		assertNull(user.getLastName());
		assertNull(user.getEmail());
		assertNull(user.getRole());
		assertEquals(expectedStatus, user.getStatus());
		
	}

	@Test
	public void test_getUsers_returnsNull() {
		// arrange
		User user;
		// act
		dropTable();
		user=usersDao.getUser("karen");
		// assert
		assertNull(user);
	}

	@Test
	public void test_removeUser_DeletesRadishKun(){
		//ARRANGE
		List<User> userList = new ArrayList<User>();
		int expected = 2;
		//ACT
		usersDao.removeUser("radish-kun");
		userList = usersDao.listUsers();
		// ASSERT
		assertEquals(expected, userList.size());
		
	}

	@Test
	public void test_removeUser_InvalidUserHasNoEffectOnArraySize(){
		List<User> userList = new ArrayList<User>();
		int expected = 3;
		//ACT
		usersDao.removeUser("Alien");
		userList = usersDao.listUsers();
		// ASSERT
		assertEquals(expected, userList.size());
	}

	@Test
	public void test_addUser_PineappleIncreasesArraySize(){
		//arrange
		List<User> userList = new ArrayList<User>();
		int expected = 4;
		User user = new User();
		user.setUsername("pineapple-kun");
		user.setFirstName("Max");
		user.setLastName("Zhong");
		user.setEmail("max.zhong");
		user.setPassword("fruit");
		user.setRole("app support");
		user.setStatus(1);
		//ACT
		usersDao.addUsers(user);
		userList = usersDao.listUsers();
		//ASSERT
		assertEquals(expected, userList.size());
	}
	
	@Test
	public void test_addUser_DupeUserChowChowLeavesArraySizeUnchanged(){
		//ARRANGE
		List<User> userList = new ArrayList<User>();
		int expected = 3;
		User user = new User();
		user.setUsername("chowchow");
		//ACT
		usersDao.addUsers(user);
		userList = usersDao.listUsers();
		//ASSERT
		assertEquals(expected, userList.size());
	}

	@Test
	public void test_updateUser_UpdateDetailsOfChowChow(){
		//ARRANGE
		User user = new User();
		user.setUsername("chowchow");
		user.setFirstName("Karen");
		user.setLastName("Luong");
		user.setEmail("karen.luong");
		user.setPassword("fruit");
		user.setRole("tech support");
		user.setStatus(1);
		//ACT
		usersDao.updateUser(user);
		user = usersDao.getUser("chowchow");
		//ASSERT
		assertEquals(1, user.getUserID());
		assertEquals("chowchow", user.getUsername());
		assertEquals("Karen", user.getFirstName());
		assertEquals("Luong", user.getLastName());
		assertEquals("karen.luong", user.getEmail());
		assertEquals("fruit", user.getPassword());
		assertEquals("tech support", user.getRole());
		assertEquals(1, user.getStatus());
	}

	@Test
	public void test_updateUser_UpdateInvalidUser(){
		User user = new User();
		user.setUsername("Alien");
		user.setFirstName("Karen");
		user.setLastName("Luong");
		user.setEmail("karen.luong");
		user.setPassword("fruit");
		user.setRole("tech support");
		user.setStatus(1);
		//ACT
		usersDao.updateUser(user);
		user = usersDao.getUser("Alien");
		//ASSERT
		assertEquals(0, user.getUserID());
		assertNull(user.getUsername());
		assertNull(user.getFirstName());
		assertNull(user.getLastName());
		assertNull(user.getEmail());
		assertNull(user.getPassword());
		assertNull(user.getRole());
		assertEquals(0, user.getStatus());
		
	}
}
