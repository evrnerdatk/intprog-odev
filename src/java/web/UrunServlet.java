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

//ilk önce dao packageından classını import ediyoruz
import dao.UrunDAO;
import model.Urun;


/**
 * Servlet implementation class AracServlet
 */
@WebServlet("/UrunServlet/*")
public class UrunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UrunDAO urunDAO;
	
	public void init() {
		urunDAO = new UrunDAO(); //urun daoyu yeni bir obje olarak yeniden oluşturuyoruz //
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
				insertUrun(request, response);
				break;
			case "/delete":
				deleteUrun(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUrun(request, response);
				break;
			default:
                                listUrun(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listUrun(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Urun> listUrun = urunDAO.selectAllUrun();
		request.setAttribute("listUrun", listUrun);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/urun-list.jsp");
		dispatcher.forward(request, response);
	}

        
        //yeni bir urun girişi olduğu zaman urun-add.jsp sayfasına yönlendirme iişlemi yapıyoruz
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/urun-add.jsp");
		dispatcher.forward(request, response);
	}

        
        //int tipinde id alıyoruz ardından urun classında urundao clasıında id üzerinden fonksiyonu çağırıyoruz
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Urun existingUrun = urunDAO.selectUrun(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/urun-add.jsp");
		request.setAttribute("urun", existingUrun);
		dispatcher.forward(request, response);

	}
        //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da urun class'ında newUrun isimli değişken oluştururuyoruz
        //oluşturduğumuz newUrun objesini urunDAO class'ındaki insertUrun fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz

	private void insertUrun(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String kategori = request.getParameter("kategori"); // jsp sayfasından gelen blgileri getparameter methoduyla üzerinde işlem yapabilmek için servlet içerisinde  bi değişken içerisine atıyoruz
		String renk = request.getParameter("renk");
		String marka = request.getParameter("marka");
		int stokAdedi =  Integer.parseInt(request.getParameter("stokAdedi"));
		Urun newUrun = new Urun(kategori,renk,marka,stokAdedi); 
		urunDAO.insertUrun(newUrun); //urundao üzerinde insert urun fonk çağırıyoruz ve newurunu çalıştırıyoruz //
		response.sendRedirect("UrunServlet/listUrun");
	}
        
        
                   //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da urun class'ında newUrun isimli değişken oluştururuyoruz
        //oluşturduğumuz newUrun objesini urunDAO class'ındaki updateUrun fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz

	private void updateUrun(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String kategori = request.getParameter("kategori");
		String renk = request.getParameter("renk");
		String marka = request.getParameter("marka");
		int stokAdedi =  Integer.parseInt(request.getParameter("stokAdedi"));
		Urun urun = new Urun(id, kategori, renk,marka,stokAdedi);
		urunDAO.updateUrun(urun);
		response.sendRedirect("UrunServlet/listUrun");
	}

        
                //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da urun class'ında newUrun isimli değişken oluştururuyoruz
        //oluşturduğumuz newUrun objesini urunDAO class'ındaki deleteUrun fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz
	private void deleteUrun(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		urunDAO.deleteUrun(id);
		response.sendRedirect("UrunServlet/listUrun");

	}


}