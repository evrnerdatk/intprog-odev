package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO {
	private final String jdbcURL ="jdbc:mysql://localhost:3306/proje?zeroDateTimeBehavior=convertToNull";
	private final String jdbcUsername = "root";
	private final String jdbcPassword = "wsxedcrfv99";
	private static final String INSERT_USERS_SQL = "INSERT INTO user" + "  (name, password,firstName,lastName) VALUES "
			+ " (?, ?,?,?);";
	private static final String SELECT_USER_BY_ID = "select id,name,password,firstName,lastName from user where id =?";
	private static final String SELECT_ALL_USERS = "select * from user";
	private static final String DELETE_USERS_SQL = "delete from user where id = ?;";
	private static final String UPDATE_USERS_SQL = "update user set name = ?,password= ?,firstName=?,lastName=? where id = ?;";
	private static final String LOGIN_USER_QUERY = "select * from user where name =? and password =?";
	public UserDAO() {
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void insertUser(User user) throws SQLException {
		System.out.println(INSERT_USERS_SQL);
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {//veritabanına sorgumuzu gönderiyoruz//
			preparedStatement.setString(1, user.getEmail()); //statementın içinde olan bilgileri user nesnesi içerisine eklendi
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public User selectUser(int id) {
		User user = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery(); //preparedstatement içindeki veriler resultset classından oluşturduğum rs nesnesine aktarılıyor
 
			while (rs.next()) { //while döngüsü ile rs içindeki veriler değişkenler içerisinde tutuluyor
				String name = rs.getString("name");
				String password = rs.getString("password");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");

				user = new User(id, name, password,firstName,lastName);//tutulan değişkenler user class'ında oluşturduğumuz user objesi içinde tutuluyor
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}

	public List<User> selectAllUsers() {

		List<User> users = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery(); //preparedstatement içindeki veriler resultset classından oluşturduğum rs nesnesine aktarılıyor

			while (rs.next()) { //while döngüsü ile rs içindeki veriler değişkenler içerisinde tutuluyor
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");

				users.add(new User(id, name, password,firstName,lastName));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;
	}

	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id); // id üzerinden yazma işlemini statament üzerinden yapıyoruz
			rowDeleted = statement.executeUpdate() > 0; // kaydetme işlemi
		}
		return rowDeleted;
	}

	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection(); //veritabanı bağlantısı yapıldı
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) { //veritabanına sorgu gönderilip statament değişkeni içinde gelen bilgi tutuldu
			statement.setString(1, user.getEmail()); //statementın içinde olan bilgileri user nesnesi içerisine eklendi
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setInt(5, user.getId());

			rowUpdated = statement.executeUpdate() > 0; 
		}
		return rowUpdated;
	}
	public User authtenticateUser(String e, String s) throws SQLException {
		User user = null;
		try (Connection connection = getConnection(); //veritabanı bağlantısı yapıldı
			PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_USER_QUERY)) {//veritabanına sorgu gönderilip statament değişkeni içinde gelen bilgi tutuldu
			preparedStatement.setString(1, e); //praparedstatement içindeki bilgileri set işlemleri gerçekleştirildi
			preparedStatement.setString(2, s);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery(); //preparedstatement içindeki veriler resultset classından oluşturduğum rs nesnesine aktarılıyor

			while (rs.next()) { //while döngüsü ile rs içindeki veriler değişkenler içerisinde tutuluyor
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String password = rs.getString("password");

				user = new User(id, name,password, firstName, lastName); //tutulan değişkenler user class'ında oluşturduğumuz user objesi içinde tutuluyor
			}
			return user; //fonksiyon en son tutulan user objesini döndürüyor
		} catch (SQLException ex) {
			printSQLException(ex);
			return user;
		}
	}
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}