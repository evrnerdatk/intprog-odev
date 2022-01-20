package web;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import dao.UserDAO;
import model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private static final String LOGGED_USER = "loggedUser";
       

      
	
	public void init() {
		userDAO = new UserDAO();  //user daoyu yeni bir obje olarak yeniden oluşturuyoruz //
           
	}
	protected boolean SessionController(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(LOGGED_USER);
		if(user!= null) {
			
				request.setAttribute("logged", user);
			return true;
		}
		else {
			return false;
		}
	}
        
          // do post metodunu do gete yönlendiriyoruz ve bu get içerisinde jsp sayfalarından gelen urlleri(href link formlar vs.) çekiyoruz//
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			if(SessionController(request) == false) {
				switch (action) { // actionu switch keys yapısıyla kontrol ediyoruz.
					case "/loginUser":
						loginAccount(request, response);
						break;
					case "/uyeOl":
						showUyeOl(request, response);
						break;
					case "/insertUyeOl":
						insertUyeOl(request, response);
						break;
					default:
						diplayLoginPage(request, response);
						break;
				}
			}
			else {
				switch (action) {
				case "/cikisYap":
					cikisYap(request, response);
					break;
				case "/new":
					showNewForm(request, response);
					break;
				case "/insert":
					insertUser(request, response);
					break;
				case "/delete":
					deleteUser(request, response);
					break;
				case "/edit":
					showEditForm(request, response);
					break;
				case "/update":
					updateUser(request, response);
					break;
				case "/home":
					showHome(request, response);
					break;
				case "/listUser":
					listUser(request, response);
					break;
				case "/profilim":
					showProfilimForm(request, response);
					break;
				case "/updateProfilim":
					updateProfilim(request, response);
					break;
				default:
                                        showHome(request, response);
                                        break;
				}
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		List<User> listUser = userDAO.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user-list.jsp");
		dispatcher.forward(request, response);
	}
        
        

        //yeni bir user girişi olduğu zaman user-add.jsp sayfasına yönlendirme iişlemi yapıyoruz
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user-add.jsp");
		dispatcher.forward(request, response);
	}

        
        //int tipinde id alıyoruz ardından user classında urundao clasıında id üzerinden fonksiyonu çağırıyoruz
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDAO.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user-add.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);

	}

           //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da user class'ında newUser isimli değişken oluştururuyoruz
        //oluşturduğumuz newUser objesini userDAO class'ındaki insertUser fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz
	private void insertUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		User newUser = new User(name, password,firstName,lastName);
		userDAO.insertUser(newUser);
		response.sendRedirect("listUser");
	}
	private void insertUyeOl(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
                Cookie cookie1 = new Cookie("cookie1", request.getParameter("name"));
                Cookie cookie2 = new Cookie("cookie2", request.getParameter("password"));
                response.addCookie( cookie1 );
                response.addCookie( cookie2 );
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		User newUser = new User(name, password,firstName,lastName);
		userDAO.insertUser(newUser);
		response.sendRedirect("loginUser");
	}
        
        
           //frontend'den gelen bilgilerimizi request.getparameter methodu ile değişkenlere aktarıyoruz daha sonrasın da urun class'ında newUrun isimli değişken oluştururuyoruz
        //oluşturduğumuz newUrun objesini urunDAO class'ındaki insertUrun fonksiyonu içerisine göndererek veritabanına insert işlemini gerçekleştiriyoruz
	private void updateUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		User user = new User(id,name, password,firstName,lastName);
		userDAO.updateUser(user);
		response.sendRedirect("listUser");
	}
	private void updateProfilim(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		User user = new User(id,name, password,firstName,lastName);
		userDAO.updateUser(user);
		response.sendRedirect("home");
	}
        
	private void showProfilimForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		User user=(User)request.getSession().getAttribute("loggedUser");
		int id = user.getId();
		User existingUser = userDAO.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/profil.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);

	}
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		userDAO.deleteUser(id);
		response.sendRedirect("listUser");

	}
        
        
	private void diplayLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp"); //gelen isteği başka bir servlete aktarmak için kullanılır
		dispatcher.forward(request, response);
	}

	private void loginAccount(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String sifre = request.getParameter("password");
                        
                        
			User user = new UserDAO().authtenticateUser(name, sifre);
			if(user == null) {
				request.setAttribute("error", "Email veya parola bilgisi yanlis girildi! ");
				diplayLoginPage(request, response);
			}
                        
			else {
				request.getSession().setAttribute(LOGGED_USER, user);
				request.removeAttribute("error");
				response.sendRedirect("home.jsp");
			}
	
       
        }
	private void cikisYap(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		request.setCharacterEncoding("UTF-8");

		request.getSession().setAttribute(LOGGED_USER, null);
		diplayLoginPage(request, response);
	}
	
	private void showHome(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);
	}
	private void showUyeOl(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
		dispatcher.forward(request, response);
	}
       
	
	
	
}