package web;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.UrunDAO;
import dao.SatisDAO;
import dao.MusteriDAO;
import java.io.OutputStream;


//ilk önce model packageından classını import ediyoruz
import model.Urun;
import model.Satis;
import model.Musteri;




@WebServlet("/SatisServlet/*")
public class SatisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SatisDAO satisDAO;
        private UrunDAO urunDAO;


      
	public void init() {
	
		satisDAO=new SatisDAO(); //satış daoyu yeni bir obje olarak yeniden oluşturuyoruz //
                urunDAO=new UrunDAO(); //urun daoyu yeni bir obje olarak yeniden oluşturuyoruz //
	
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
					showSatisNewForm(request, response);
					break;
				case "/insert":
					insertSatis(request, response);
					break;
				case "/delete":
					deleteSatis(request, response);
					break;
				case "/edit":
					showSatisEditForm(request, response);
					break;
				case "/update":
					updateSatis(request, response);
					break;
                                case "/pdf":
					pdf(request, response);
					break;
				
                                default:
					listSatis(request, response);
					break;
				
				}
			}
		 catch (SQLException ex) {
			throw new ServletException(ex);
		}
}
		
	


	
	private void listSatis(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		List<Satis> listSatis = satisDAO.selectAllSatis();
		request.setAttribute("listSatis", listSatis);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/satis-list.jsp");
		dispatcher.forward(request, response);
	}

        
           //yeni bir satış girişi olduğu zaman satış-add.jsp sayfasına yönlendirme iişlemi yapıyoruz
	private void showSatisNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
                
		List<Urun> urunListesi = new UrunDAO().selectAllUrun();
		List<Musteri> musteriListesi = new MusteriDAO().selectAllMusteri();
		request.setAttribute("listUrun", urunListesi);
		request.setAttribute("listMusteri", musteriListesi);
                
		RequestDispatcher dispatcher = request.getRequestDispatcher("/satis-add.jsp");
		dispatcher.forward(request, response);
	}

        
         //int tipinde id alıyoruz ardından satış classında urundao clasıında id üzerinden fonksiyonu çağırıyoruz
	private void showSatisEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		Satis existingUser = satisDAO.selectSatis(id);
		List<Urun> urunListesi = new UrunDAO().selectAllUrun();
		List<Musteri> musteriListesi = new MusteriDAO().selectAllMusteri();
		request.setAttribute("listUrun", urunListesi);
		request.setAttribute("listMusteri", musteriListesi);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/satis-add.jsp");
		request.setAttribute("satis", existingUser);
		dispatcher.forward(request, response);

	}
        
        
                //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da  claslarda  new... isimli değişken oluştururuyoruz
        //oluşturduğumuz new... objesini satışDAO class'ındaki insertUrun fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz
	private void insertSatis(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException,ServletException {
		request.setCharacterEncoding("UTF-8");
		Integer urunId = Integer.parseInt(request.getParameter("urun"));
		Integer musteriId = Integer.parseInt(request.getParameter("musteri"));
                Integer stokAdedi = Integer.parseInt(request.getParameter("stokAdedi"));
		Urun urun = new UrunDAO().selectUrun(urunId);
		Musteri musteri = new MusteriDAO().selectMusteri(musteriId);
		List<Satis> satislist=satisDAO.selectAllSatisUrun(urunId,stokAdedi);
		if(satislist.size()>0) {
			request.setAttribute("error", "Stokta yeteri kadar urun bulunmamaktadir. ");
			showSatisNewForm(request, response);
		}else {
                        
			Satis newSatis = new Satis(urun,musteri,stokAdedi);
                        satisDAO.insertSatis(newSatis);
                        int yenistok=urun.stokAdedi;
                        yenistok=yenistok-stokAdedi;
                        urun.stokAdedi=yenistok;
                        urunDAO.updateUrun(urun);
			response.sendRedirect("SatisServlet/listSatis");
		}
		
	}

        
                  //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da  claslarda  new... isimli değişken oluştururuyoruz
        //oluşturduğumuz new... objesini satışDAO class'ındaki insertUrun fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz
	private void updateSatis(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		Integer urunId = Integer.parseInt(request.getParameter("urun"));
		Integer musteriId = Integer.parseInt(request.getParameter("musteri"));
		Urun urun = new UrunDAO().selectUrun(urunId);
		Musteri musteri = new MusteriDAO().selectMusteri(musteriId);
		Satis satis = new Satis(urun,musteri);
		satisDAO.updateSatis(satis);
		response.sendRedirect("SatisServlet/listKiralama");
	}

        
                  //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da  claslarda  new... isimli değişken oluştururuyoruz
        //oluşturduğumuz new... objesini satışDAO class'ındaki insertUrun fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz
	private void deleteSatis(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		satisDAO.deleteSatis(id);
		response.sendRedirect("SatisServlet/listSatis");
	}
        
        //faturalandırma işlemlerini yaptığım yer
        private void pdf(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
                  
		int id = Integer.parseInt(request.getParameter("id"));
		Satis satisbastir = satisDAO.selectSatis(id);
                String fName = satisbastir.musteri.fName;
                String lastName = satisbastir.musteri.lastName;
                String adress = satisbastir.musteri.adress;
                String phone = satisbastir.musteri.phone;
                String kategori = satisbastir.urun.kategori;
                String marka = satisbastir.urun.marka;
                String renk = satisbastir.urun.renk;
                
                
                response.setContentType("application/pdf");
                OutputStream out=response.getOutputStream();
                  
                try {
                    try{
                        Document documento = new Document();
                        PdfWriter.getInstance(documento, out);
                        documento.open();
                        documento.add(new Paragraph("Musteri Bilgisi:"));
                                    documento.add(new Paragraph("-------------------------------------------------------------------------------------------------------"));
            documento.add(new Paragraph(fName));
            documento.add(new Paragraph(lastName));
            documento.add(new Paragraph(adress));
            documento.add(new Paragraph(phone));
            documento.add(new Paragraph("Urun Bilgisi:"));
             documento.add(new Paragraph("-------------------------------------------------------------------------------------------------------"));
            documento.add(new Paragraph(kategori));
            documento.add(new Paragraph(marka));
            documento.add(new Paragraph(renk));
            documento.add(new Paragraph("-------------------------------------------------------------------------------------------------------"));
            documento.add(new Paragraph("Ödeme:Alindi"));

            documento.close();
                        
                       
                        
                    }catch(Exception ex){ex.getMessage();}
           
                }
                finally {            
                out.close();
                 }
		response.sendRedirect("SatisServlet/listSatis");
                }
        
       
	

}