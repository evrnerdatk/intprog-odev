package model;

public class Musteri {    // oluşturacak olduğumu musterinin  özelliklerini yazıyoruz ve bu değişkenleri oluşturuyouz//
	public int id;
	public String fName;
	public String lastName;
	public String phone;
        public String adress;
        
           // iki adet constructor oluşturuyoruz.Bi tanesi yeni urun girişi için diğeri ise update işlemi için //
	public Musteri(String fName, String lastName, String phone, String adress) {
		super();
		this.fName = fName;
		this.lastName = lastName;
		this.phone = phone;
		this.adress = adress;
	}
        
	public Musteri(int id, String fName, String lastName, String phone, String adress) {
		super();
		this.id = id;
		this.fName = fName;
		this.lastName = lastName;
		this.phone = phone;
		this.adress = adress;
	}
        
        //sınıflarda oluşturduğumuz değişkenlerin özelliklerini tanımlamak için get-set metodlarını kullandım
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	


}