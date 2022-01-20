<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html >
<head>
  <title>Envanter Yönetim Sistemi</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <style type="text/css">
      
      
 
.dropdown-menu.show{
   width: auto;
    background-color:#e6e6fa;
    border-color: #f76c51;
    border:4px inset midnightblue;
    color: #fff;

 
    
}
.panel-danger.panel-colorful { 
  background-color:#87cefa;
  border-color: #f76c51;
  border: 11px inset mediumblue;
  color: #fff;
  margin:230px 30px 80px 40px;
  margin-right: -50px;
 


}


.panel-primary.panel-colorful {
  background-color:#da70d6;
  border-color: #5fa2dd;
  border: 11px inset mediumvioletred;
  color: #fff;
  margin:230px 30px 50px 40px;
  margin-right: -50px;
}
.panel-info.panel-colorful {
  background-color:#6a5acd;
  border-color: #4ebcda;
  border: 11px inset midnightblue;
  color: #fff;
  margin:230px 30px 50px 40px;
  margin-right: -50px;
  
}
.panel-body {
  padding: 1px 20px;
  
}
.panel hr {
  border-color: #191970;
}
.mar-btm {
  margin-bottom: 15px;
}
h2, .h2 {
  font-size: 30px;
}
.deneme ul  {
    float:left;
    position: relative;
    width: 100px;
    font-size: 20px;
   
}
.text-thin {
  font-weight: 800;
}



</style>
</head>
<body background="https://www.finishingline.co.uk/wp-content/uploads/2021/05/warehouse.png" >
    
    <nav class="navbar navbar-dark bg-dark"> 
  
    <a class="navbar-brand" href="#">
      <div class="nav navbar-nav  navbar-right" >
          <a class="navbar-brand" href="#">

         <a class="navbar-brand"  href="<%=request.getContextPath()%>/home">Envanter Yönetim Sistemi</a>
      </div>
    </a>
      <ul class="nav navbar-nav  navbar-right" >
          <div class="dropdown">
  <button type="button" class="btn btn-danger dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" href="#">${loggedUser.firstName} ${loggedUser.lastName}
 
  </button>
  <ul class="dropdown-menu">
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/profilim">Profilim</a></li>
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/cikisYap">Çıkış Yap</a></li>
    
  </ul>
</div>
       </ul>     
  
</nav>
  

        
        
        
        
   <div class="deneme">
        <ul class="nav navbar-nav ">
   <!admin ve user girdiği zamanda ikisi de home.jsp ye yönleniyor.if taglarını kullanma sebebim de sadece adminin görebileceği sayfalar oluşturmak>   
            <c:if test="${loggedUser.email == 'admin' && loggedUser.password == 'admin'}">  <%----%>
     <div class="btn-group dropend">
  <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/User_icon-cp.svg/1200px-User_icon-cp.svg.png" width="30" height="30" class="d-inline-block align-top" alt="">
    User İşlemleri
  </button>
       
  <ul class="dropdown-menu">
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/new">User Ekle</a></li>
     
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/listUser">User Listesi</a></li>
    
  </ul>
</div>
        </c:if>
        <div class="btn-group dropend">
  <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
  <img src="https://cdn.iconscout.com/icon/premium/png-256-thumb/product-175-1093872.png" width="30" height="30" class="d-inline-block align-top" alt="">
    Ürün İşlemleri
  </button>
       
  <ul class="dropdown-menu">
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/UrunServlet">Ürün Listesi</a></li>
    <c:if test="${loggedUser.email == 'admin' && loggedUser.password == 'admin'}"> 
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/UrunServlet/new">Ürün Ekle</a></li>
   
    </c:if>
  </ul>
</div>
  
    <c:if test="${loggedUser.email == 'admin' && loggedUser.password == 'admin'}">
   <div class="btn-group dropend">
  <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
  <img src="https://cullmanfumc.com/wp-content/uploads/2017/10/Customers_customer_user_male_man_people_light.png" width="30" height="30" class="d-inline-block align-top" alt="">
    Müşteri İşlemleri
  </button>
       
  <ul class="dropdown-menu">
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/MusteriServlet">Müşteri Listesi</a></li>
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/MusteriServlet/new">Müşteri Ekle</a></li>
  </ul>
</div>
    </c:if>
    <div class="btn-group dropend">
  <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
  <img src="https://cdn.iconscout.com/icon/premium/png-256-thumb/purchaser-2278840-1914575.png" width="30" height="30" class="d-inline-block align-top" alt="">
    Yüz Yüze Satış İşlemleri
  </button>
        
  <ul class="dropdown-menu">
      
      
    <li><a class="dropdown-item"  href="<%=request.getContextPath()%>/SatisServlet/list">Yüz Yüze Satş Listesi</a></li>
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/SatisServlet/new">Yüz Yüze Satış Yap</a></li>
      
  </ul>
    
</div>
    
    
    
          
           
    </ul>
</div>






<div class="container">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="container bootstrap snippets bootdey">
    <div class="row">
        <c:if test="${loggedUser.email == 'admin' && loggedUser.password == 'admin'}">
        <div class="col-md-3 col-sm-6 col-xs-12">
           <a href="<%=request.getContextPath()%>/UrunServlet/new"> 
               <div class="panel panel-info panel-colorful">
                   <div class="panel panel-info panel text-center">
                
                    <img src="https://cdn.iconscout.com/icon/premium/png-256-thumb/add-product-4-837051.png" width="120" height="120" class="d-inline-block align-top" alt="">

                	
                        <p class="h2 text-thin text-uppercase">Urun Ekle</p>
                   </div>          
            </div>
            </a>
        </div>
                
        <div class="col-md-3 col-sm-6 col-xs-12">
        	<a href="<%=request.getContextPath()%>/MusteriServlet/new">
                    <div class="panel panel-danger panel-colorful">
        		<div class="panel-body text-center">
        			                    <img src="https://wimwian.iima.ac.in/wp-content/uploads/2018/01/Couple-icon.png" width="120" height="120" class="d-inline-block align-top" alt="">
        			<p class="h2 text-thin text-uppercase">Müşteri Ekle</p>
        		</div>
        	</div></a>
        </div>
                        </c:if>
        <div class="col-md-3 col-sm-6 col-xs-12">
        	<a href="<%=request.getContextPath()%>/SatisServlet/new">
                    <div class="panel panel-primary panel-colorful">
        		<div class="panel-body text-center">
        		
        			<img src="https://cutewallpaper.org/24/sales-icon-png/sales-icon-download-in-colored-outline-style.png" width="120" height="120" class="d-inline-block align-top" alt="">
        			<p class="h2 text-thin text-uppercase">Urun Sat</p>
        		</div>
        	</div></a>
        </div>  
	</div>
</div>
</div>

     <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>


</html>