package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Urun;
import model.Satis;
import model.Musteri;

public class SatisDAO {
	private final String jdbcURL ="jdbc:mysql://localhost:3306/proje?zeroDateTimeBehavior=convertToNull";
	private final String jdbcUsername = "root";
	private final String jdbcPassword = "wsxedcrfv99";
	private static final String INSERT_SATIS_SQL = "INSERT INTO satis" + "  (urun,musteri,stokAdedi) VALUES " + " (?, ?,?);";
	private static final String SELECT_SATIS_BY_ID = "select id,urun,musteri from satis where id =?";
	private static final String SELECT_ALL_SATIS = "select * from satis";
	private static final String DELETE_SATIS_SQL = "delete from satis where id = ?;";
	private static final String UPDATE_SATIS_SQL = "update satis set urun = ?,musteri= ? where id = ?;";
	private static final String SELECT_SATIS_SQL ="select * from proje.satis where urun=? and stokAdedi>=? ;";

	public SatisDAO() {
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

	public void insertSatis(Satis satis) throws SQLException {
		System.out.println(INSERT_SATIS_SQL); 
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SATIS_SQL)) {//veritabanına sorgumuzu gönderiyoruz//
			preparedStatement.setInt(1, satis.getUrun().getId()); //statementın içinde olan bilgileri satıs nesnesi içerisine eklendi
			preparedStatement.setInt(2, satis.getMusteri().getId());
                        preparedStatement.setInt(3, satis.getStokAdedi());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Satis selectSatis(int id) {
		Satis satis = null;
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SATIS_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery(); //preparedstatement içindeki veriler resultset classından oluşturduğum rs nesnesine aktarılıyor
			while (rs.next()) { //while döngüsü ile rs içindeki veriler değişkenler içerisinde tutuluyor
			
				Integer urunId = rs.getInt("urun");
				Integer musteriId = rs.getInt("musteri");
				Urun urun = new UrunDAO().selectUrun(urunId);
				Musteri musteri = new MusteriDAO().selectMusteri(musteriId);
				satis = new Satis(id,urun, musteri);  //tutulan değişkenler satıs class'ında oluşturduğumuz satıs objesi içinde tutuluyor
			} 
		} catch (SQLException e) {
			printSQLException(e);
		}
		return satis;
	}

	public List<Satis> selectAllSatis() {
		List<Satis> satisList = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SATIS);) { //veritabanına sorgumuzu gönderiyoruz//
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) { //while döngüsü ile rs içindeki veriler değişkenler içerisinde tutuluyor
				int id = rs.getInt("id");
				Integer urunId = rs.getInt("urun");
				Integer musteriId = rs.getInt("musteri");
				Urun urun = new UrunDAO().selectUrun(urunId);
				Musteri musteri = new MusteriDAO().selectMusteri(musteriId);
				satisList.add(new Satis(id, urun, musteri));  //tutulan değişkenler musteri class'ında oluşturduğumuz user objesi içinde tutuluyor
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return satisList;
	}

	public boolean deleteSatis(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_SATIS_SQL);) { //veritabanına sorgumuzu gönderiyoruz
			statement.setInt(1, id); // id üzerinden yazma işlemini statament üzerinden yapıyoruz
			rowDeleted = statement.executeUpdate() > 0; //kaydetme işlemi
		}
		return rowDeleted;
	}

	public boolean updateSatis(Satis satis) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_SATIS_SQL);) {
			statement.setInt(1, satis.getUrun().getId()); // id üzerinden yazma işlemini statament üzerinden yapıyoruz
			statement.setInt(2, satis.getMusteri().getId());
			statement.setInt(5, satis.getId());
			System.out.println(satis.getUrun().getId());
			System.out.println("*********///////****************");
			rowUpdated = statement.executeUpdate() > 0; //kaydetme işlemi
		}
		return rowUpdated;
	}
	
        public List<Satis> selectAllSatisUrun(int uId, int sAdedi) {		
		List<Satis> satisList = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SATIS_SQL);) { //veritabanına sorgumuzu gönderiyoruz
			preparedStatement.setInt(1, uId);
			preparedStatement.setInt(2, sAdedi);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();  //preparedstatement içindeki veriler resultset classından oluşturduğum rs nesnesine aktarılıyor
			while (rs.next()) { //while döngüsü ile rs içindeki veriler değişkenler içerisinde tutuluyor
				int id = rs.getInt("id");
				Integer urunId = rs.getInt("urun");
				Integer musteriId = rs.getInt("musteri");
				int stokAdedi = rs.getInt("stokAdedi");
				
				Urun urun = new UrunDAO().selectUrun(urunId);
				Musteri musteri = new MusteriDAO().selectMusteri(musteriId);
				satisList.add(new Satis(id, urun, musteri,stokAdedi)); //tutulan değişkenler urun class'ında oluşturduğumuz user objesi içinde tutuluyor
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return satisList;
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