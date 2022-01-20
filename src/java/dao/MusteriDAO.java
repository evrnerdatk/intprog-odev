package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Musteri;

public class MusteriDAO {
	private final String jdbcURL ="jdbc:mysql://localhost:3306/proje?zeroDateTimeBehavior=convertToNull";
	private final String jdbcUsername = "root";
	private final String jdbcPassword = "wsxedcrfv99";
	private static final String INSERT_MUSTERI_SQL = "INSERT INTO musteri" + "  (ad, soyad,telefon,adres) VALUES "
			+ " (?, ?,?,?);";
	private static final String SELECT_MUSTERI_BY_ID = "select id,ad,soyad,telefon,adres from musteri where id =?";
	private static final String SELECT_ALL_MUSTERI = "select * from musteri";
	private static final String DELETE_MUSTERI_SQL = "delete from musteri where id = ?;";
	private static final String UPDATE_MUSTERI_SQL = "update musteri set ad = ?,soyad= ?,telefon= ?,adres= ? where id = ?;";

	public MusteriDAO() {
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

	public void insertMusteri(Musteri musteri) throws SQLException {
		System.out.println(INSERT_MUSTERI_SQL);
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MUSTERI_SQL)) //veritabanına sorgumuzu gönderiyoruz//
                {
			preparedStatement.setString(1, musteri.getfName());  //statementın içinde olan bilgileri musteri nesnesi içerisine eklendi
			preparedStatement.setString(2, musteri.getLastName());
			preparedStatement.setString(3, musteri.getPhone());
			preparedStatement.setString(4, musteri.getAdress());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Musteri selectMusteri(int id) {
		Musteri musteri = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MUSTERI_BY_ID);) { //veritabanına sorgumuzu gönderiyoruz//
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) { //while döngüsü ile rs içindeki veriler değişkenler içerisinde tutuluyor
				String ad = rs.getString("ad");
				String soyad = rs.getString("soyad");
				String telefon = rs.getString("telefon");
				String adres = rs.getString("adres");

				musteri = new Musteri(id, ad, soyad,telefon,adres); //tutulan değişkenler musteri class'ında oluşturduğumuz user objesi içinde tutuluyor
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return musteri; //fonksiyon en son tutulan musteri objesini döndürüyor//
	}

	public List<Musteri> selectAllMusteri() {

		List<Musteri> musteriList = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MUSTERI);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) { //while döngüsü ile rs içindeki veriler değişkenler içerisinde tutuluyor
				int id = rs.getInt("id");
				String ad = rs.getString("ad");
				String soyad = rs.getString("soyad");
				String telefon = rs.getString("telefon");
				String adres = rs.getString("adres");
				musteriList.add(new Musteri(id, ad, soyad,telefon,adres));  //tutulan değişkenler musteri class'ında oluşturduğumuz user objesi içinde tutuluyor
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return musteriList;
	}

	public boolean deleteMusteri(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_MUSTERI_SQL);) { //veritabanına sorgumuzu gönderiyoruz
			statement.setInt(1, id); // id üzerinden yazma işlemini statament üzerinden yapıyoruz
			rowDeleted = statement.executeUpdate() > 0; //kaydetme işlemi 
		}
		return rowDeleted;
	}

	public boolean updateMusteri(Musteri musteri) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_MUSTERI_SQL);) { //veritabanına sorgumuzu gönderiyoruz
			statement.setString(1, musteri.getfName()); // id üzerinden yazma işlemini statament üzerinden yapıyoruz
			statement.setString(2, musteri.getLastName());
			statement.setString(3, musteri.getPhone());
			statement.setString(4, musteri.getAdress());
			statement.setInt(5, musteri.getId());

			rowUpdated = statement.executeUpdate() > 0; //kaydetme işlemi 
		}
		return rowUpdated;
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