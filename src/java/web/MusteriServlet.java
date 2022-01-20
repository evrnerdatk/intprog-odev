package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.MusteriDAO;
import model.Musteri;


@WebServlet("/MusteriServlet/*")
public class MusteriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MusteriDAO musteriDAO;
	
	public void init() {
		musteriDAO = new MusteriDAO();
	}
  
        
        // do post metodunu do gete yönlendiriyoruz ve bu get içerisinde jsp sayfalarından gelen urlleri(href link formlar vs.) çekiyoruz//
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getRequestURI();
		action = action.substring(action.lastIndexOf("/")).toLowerCase();
		try {
			switch (action) { // actionu switch keys yapısıyla kontrol ediyoruz.
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertMusteri(request, response);
				break;
			case "/delete":
				deleteMusteri(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateMusteri(request, response);
				break;
			default:
				listMusteri(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listMusteri(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Musteri> listMusteri = musteriDAO.selectAllMusteri();
		request.setAttribute("listMusteri", listMusteri);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/musteri-list.jsp");
		dispatcher.forward(request, response);
	}
        
        

          //yeni bir musteri girişi olduğu zaman urun-add.jsp sayfasına yönlendirme iişlemi yapıyoruz
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/musteri-add.jsp");
		dispatcher.forward(request, response);
	}

        
         //int tipinde id alıyoruz ardından musteri classında musteridao clasıında id üzerinden fonksiyonu çağırıyoruz
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Musteri existingUser = musteriDAO.selectMusteri(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/musteri-add.jsp");
		request.setAttribute("musteri", existingUser);
		dispatcher.forward(request, response);

	}

        
              //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da musteri class'ında newMusteri isimli değişken oluştururuyoruz
        //oluşturduğumuz newMusteri objesini musteriDAO class'ındaki insertMusteri fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz

	private void insertMusteri(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String ad = request.getParameter("ad");
		String soyad = request.getParameter("soyad");
		String telefon = request.getParameter("telefon");
		String adres = request.getParameter("adres");
		Musteri newMusteri = new Musteri(ad,soyad,telefon,adres);
		musteriDAO.insertMusteri(newMusteri);
		response.sendRedirect("MusteriServlet/listMusteri");
	}
        
        

                  //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da musteri class'ında newMusteri isimli değişken oluştururuyoruz
        //oluşturduğumuz newMusteri objesini musteriDAO class'ındaki insertMusteri fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz

	private void updateMusteri(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String ad = request.getParameter("ad");
		String soyad = request.getParameter("soyad");
		String telefon = request.getParameter("telefon");
		String adres = request.getParameter("adres");
		Musteri musteri = new Musteri(id, ad, soyad,telefon,adres);
		musteriDAO.updateMusteri(musteri);
		response.sendRedirect("MusteriServlet/listMusteri");
	}

                  //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da musteri class'ında newMusteri isimli değişken oluştururuyoruz
        //oluşturduğumuz newMusteri objesini musteriDAO class'ındaki insertMusteri fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz

	private void deleteMusteri(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		musteriDAO.deleteMusteri(id);
		response.sendRedirect("MusteriServlet/listMusteri");

	}

}