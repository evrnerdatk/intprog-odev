package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Urun;

public class UrunDAO {
	private final String jdbcURL ="jdbc:mysql://localhost:3306/proje?zeroDateTimeBehavior=convertToNull";
	private final String jdbcUsername = "root";
	private final String jdbcPassword = "wsxedcrfv99";
	private static final String INSERT_URUN_SQL = "INSERT INTO urun" + "  (kategori, renk,marka,stokAdedi) VALUES "
			+ " (?, ?,?,?);"; // veritabanımıza sorgu ifadelerimizi string ifadeler içerisinde tutuyruz //
	private static final String SELECT_URUN_BY_ID = "select id,kategori,renk,marka,stokAdedi from urun where id =?";
	private static final String SELECT_ALL_URUN = "select * from urun";
	private static final String DELETE_URUN_SQL = "delete from urun where id = ?;";
	private static final String UPDATE_URUN_SQL = "update urun set kategori = ?,renk= ?,marka= ?,stokAdedi= ? where id = ?;";
	public UrunDAO() {
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

	public void insertUrun(Urun urun) throws SQLException {
		System.out.println(INSERT_URUN_SQL);
		try (Connection connection = getConnection(); //veritabanı bağlantı işlemi 
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_URUN_SQL)) { //veritabanına sorgumuzu gönderiyoruz//
                    //yazma işlemleri yapıyoruz.
			preparedStatement.setString(1, urun.getKategori()); //statementın içinde olan bilgileri urun nesnesi içerisine eklendi
			preparedStatement.setString(2, urun.getRenk());
			preparedStatement.setString(3, urun.getMarka());
			preparedStatement.setInt(4, urun.getStokAdedi());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Urun selectUrun(int id) {
		Urun urun = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_URUN_BY_ID);) { //veritabanına sorgumuzu gönderiyoruz//
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();//preparedstatement içindeki veriler resultset classından oluşturduğum rs nesnesine aktarılıyor
			while (rs.next()) { //while döngüsü ile rs içindeki veriler değişkenler içerisinde tutuluyor
				String kategori = rs.getString("kategori");
				String renk = rs.getString("renk");
				String marka = rs.getString("marka");
				int stokAdedi = rs.getInt("stokAdedi");
				urun = new Urun(id, kategori, renk,marka,stokAdedi);//tutulan değişkenler urunclass'ında oluşturduğumuz urun objesi içinde tutuluyor
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return urun; //fonksiyon en son tutulan user objesini döndürüyor//
	}

	public List<Urun> selectAllUrun() {
		List<Urun> urunList = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_URUN);) { //veritabanına sorgumuzu gönderiyoruz//
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery(); //preparedstatement içindeki veriler resultset classından oluşturduğum rs nesnesine aktarılıyor
			while (rs.next()) { //while döngüsü ile rs içindeki veriler değişkenler içerisinde tutuluyor
				int id = rs.getInt("id");
				String kategori = rs.getString("kategori");
				String renk = rs.getString("renk");
				String marka = rs.getString("marka");
				int stokAdedi = rs.getInt("stokAdedi");
				urunList.add(new Urun(id, kategori, renk,marka,stokAdedi)); //tutulan değişkenler urun class'ında oluşturduğumuz user objesi içinde tutuluyor
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return urunList;
	}

	public boolean deleteUrun(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_URUN_SQL);) {
			statement.setInt(1, id); // id üzerinden yazma işlemini statament üzerinden yapıyoruz
			rowDeleted = statement.executeUpdate() > 0; // kaydetme işlemi
		}
		return rowDeleted;
	}

	public boolean updateUrun(Urun urun) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_URUN_SQL);) {
			statement.setString(1, urun.getKategori());
			statement.setString(2, urun.getRenk());
			statement.setString(3, urun.getMarka());
			statement.setInt(4, urun.getStokAdedi());
			statement.setInt(5, urun.getId());

			rowUpdated = statement.executeUpdate() > 0;
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