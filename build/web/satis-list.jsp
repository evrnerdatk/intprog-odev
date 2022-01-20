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
}
           .deneme ul  {
    float: left;
    position: relative;
    width: 150px;
    font-size: 20px;
}
</style>
</head>
<body background="https://listelist.com/wp-content/uploads/2019/05/e-commerce-online-shopping-1260x840.png" >

<nav class="navbar navbar-dark bg-dark">
  
    <a class="navbar-brand" href="#">
      <ul class="nav navbar-nav  navbar-right" >
      
         <a class="navbar-brand"  href="<%=request.getContextPath()%>/home">Envanter Yönetim Sistemi</a>
      </ul>
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
        <ul class="nav navbar-nav">
            
       <c:if test="${loggedUser.email == 'admin' && loggedUser.password == 'admin'}">     
     <div class="btn-group dropend">
  <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
    User islemleri
  </button>
       
  <ul class="dropdown-menu">
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/new">User Ekle</a></li>
     
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/listUser">User Listesi</a></li>
    
  </ul>
</div>
        </c:if>
        <div class="btn-group dropend">
  <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
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
    Musteri islemleri
  </button>
       
  <ul class="dropdown-menu">
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/MusteriServlet">Müşteri Listesi</a></li>
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/MusteriServlet/new">Müşteri Ekle</a></li>
  </ul>
</div>
    </c:if>
    <div class="btn-group dropend">
  <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
    Yüz Yüze Satış islemleri
  </button>
        
  <ul class="dropdown-menu">
      
      
    <li><a class="dropdown-item"  href="<%=request.getContextPath()%>/SatisServlet/list">Yüz Yüze Satiş Listesi</a></li>
    <li><a class="dropdown-item" href="<%=request.getContextPath()%>/SatisServlet/new">Yüz Yüze Satış Yap</a></li>
      
  </ul>
    
</div>
    
    
    
          
           
    </ul>
</div>
  
<div class="container">
	<div class="row">

		<div class="container">
			<h3 class="text-center">Urun Satis Listesi</h3>
			<hr>
			
			<br>
			<table class="table">
				<thead class="table-dark">
					<tr>
						<th>ID</th>
						<th>Urun Kategori</th>
						<th>Urun Marka</th>
						<th>Urun Renk</th>
						<th>Urun Stok Adedi</th>
						<th>Müşteri Ad Soyad</th>
                                                <c:if test="${loggedUser.email == 'admin' && loggedUser.password == 'admin'}">
						<th>İşlemler</th></c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="satis" items="${listSatis}">

						<tr>
							<td style="color: white"><c:out value="${satis.id}" /></td>
							<td style="color: white"><c:out value="${satis.urun.kategori}" /></td>
							<td style="color: white"><c:out value="${satis.urun.marka}" /></td>
							<td style="color: white"><c:out value="${satis.urun.renk}" /></td>
							<td style="color: white"><c:out value="${satis.urun.stokAdedi}" /></td>
							<td style="color: white"><c:out value="${satis.musteri.fName} ${satis.musteri.lastName}" /></td>					
							<td style="color: white"> 
                                                            <c:if test="${loggedUser.email == 'admin' && loggedUser.password == 'admin'}">
                                                            <a style="color:red" href="<%=request.getContextPath()%>/SatisServlet/delete?id=<c:out value='${satis.id}'/>">Sil</a>
								&nbsp;&nbsp;&nbsp;&nbsp; 
                                                                <a style="color:blue" href="<%=request.getContextPath()%>/SatisServlet/pdf?id=<c:out value='${satis.id}'/>">Faturalandır</a>
                                                               </td>
								</c:if>
								
						</tr>
					</c:forEach>
					
				</tbody>

			</table>
                        <div class="container text-left">

				<a href="<%=request.getContextPath()%>/SatisServlet/new" class="btn btn-danger">Urun Sat</a>
			</div>
		</div>
	</div>
</div>
     <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>