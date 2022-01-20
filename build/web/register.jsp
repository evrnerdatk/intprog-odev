<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Envanter Yönetim Sistemi</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <style type="text/css">
      :root {
  --input-padding-x: 1.5rem;
  --input-padding-y: .75rem;
}


.card-signin {
  border: 0;
  border-radius: 1rem;
  box-shadow: 0 0.5rem 1rem 0 rgba(0, 0, 0, 0.1);
    background: #007bff;
  background: linear-gradient(to right, #0062E6, #33AEFF);
}

.card-signin .card-title {
  margin-bottom: 2rem;
  font-weight: 300;
  font-size: 40px;
  font-family: Arial, Helvetica, sans-serif;
}
.card-body button{
   margin-left: 180px;
}
.card-body a{
    border-radius: 1rem;
    font-size: 15px;
}
.card-signin .card-body {
  padding: 2rem;
}

.form-signin {
  width: 100%;
}

.form-signin .btn {
  font-size: 20px;
  border-radius: 5rem;
  letter-spacing: .1rem;
  font-weight: bold;
  padding: 1rem;
  transition: all 0.2s;
}

.form-label-group {
  position: relative;
  margin-bottom: 1rem;
}

.form-label-group input {
  height: auto;
  border-radius: 2rem;
}

.form-label-group>input,
.form-label-group>label {
  padding: var(--input-padding-y) var(--input-padding-x);
}

.form-label-group>label {
  position: absolute;
  top: 0;
  left: 0;
  display: block;
  width: 100%;
  margin-bottom: 0;
  line-height: 1.5;
  color: #495057;
  border: 1px solid transparent;
  border-radius: .25rem;
  transition: all .1s ease-in-out;
}

.form-label-group input::-webkit-input-placeholder {
  color: transparent;
}

.form-label-group input:-ms-input-placeholder {
  color: transparent;
}

.form-label-group input::-ms-input-placeholder {
  color: transparent;
}

.form-label-group input::-moz-placeholder {
  color: transparent;
}

.form-label-group input::placeholder {
  color: transparent;
}

.form-label-group input:not(:placeholder-shown) {
  padding-top: calc(var(--input-padding-y) + var(--input-padding-y) * (2 / 3));
  padding-bottom: calc(var(--input-padding-y) / 3);
}

.form-label-group input:not(:placeholder-shown)~label {
  padding-top: calc(var(--input-padding-y) / 3);
  padding-bottom: calc(var(--input-padding-y) / 3);
  font-size: 12px;
  color: #777;
}

.btn-google {
  color: white;
  background-color: #ea4335;
}

.btn-facebook {
  color: white;
  background-color: #3b5998;
}


@supports (-ms-ime-align: auto) {
  .form-label-group>label {
    display: none;
  }
  .form-label-group input::-ms-input-placeholder {
    color: #777;
  }
}


@media all and (-ms-high-contrast: none),
(-ms-high-contrast: active) {
  .form-label-group>label {
    display: none;
  }
  .form-label-group input:-ms-input-placeholder {
    color: #777;
  }
}

  </style>

</head>
<body background="https://www.finishingline.co.uk/wp-content/uploads/2021/05/warehouse.png" >
    
    <nav  class="navbar navbar-dark bg-dark">
  
    <a class="navbar-brand" href="#">
      <ul class="nav navbar-nav  navbar-left" >
      
         <a class="navbar-brand"  href="<%=request.getContextPath()%>/home">Envanter Yönetim Sistemi</a>
      </ul>
    </a>
        
  
</nav>
	<br>
        
        
	<div class="container">
    <div class="row">
      <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card card-signin my-5">
          <div class="card-body">
		<form action="insertUyeOl" method="post">
                    <h3 class="card-title text-center"><strong><c:if test="${user == null}">ÜYE OL</c:if></strong></h3>
					
				

			<c:if test="${user != null}">
                            <input type="hidden" name="id" value="<c:out value='${user.id}' />" />
			</c:if>
			<div class="form-label-group">
                        <input type="text" id="inputfirstName" placeholder="Adi" value="<c:out value='${user.firstName}' />" class="form-control" name="firstName">
                        <label for="inputfirstName" style="cursor:text">Adı</label>
                        </div>
                        <div class="form-label-group">
                        <input type="text" id="inputlastName" placeholder="Adi" value="<c:out value='${user.lastName}' />" class="form-control" name="lastName">
			  <label for="inputlastName" style="cursor:text">Soyadı</label> 
                        </div>
				<div class="form-label-group">
                                        <input type="text" id="inputEmail" placeholder="Kullanici Adi" value="<c:out value='${user.email}' />" class="form-control" name="name" required="required">
                                        <label for="inputEmail" style="cursor:text">Kullanıcı Adı</label>
                                </div>
				<div class="form-label-group">
					 <input type="password" id="inputpassword" placeholder="Password" value="<c:out value='${user.password}' />" class="form-control" name="password">
				<label for="inputpassword" style="cursor:text">Şifre</label>
                                </div>
					<button type="submit" class="btn btn-lg btn-google btn-block text-uppercase">ÜYE OL</button>
					<hr class="my-4">
                                        <c:if test="${error != null}">
						<label class="text-danger">${error}</label>
					</c:if>
				</form>
<a class="btn btn-lg btn-facebook btn-block text-uppercase" href="<%=request.getContextPath()%>/login" style="cursor:pointer">Giriş Yap</a>

			</div>
		</div>
	</div>
</div>
</div>
</body>
</html>