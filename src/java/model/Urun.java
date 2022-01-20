/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author evren
 */
public class Urun {
    // oluşturacak olduğumu urunun özelliklerini yazıyoruz ve bu değişkenleri oluşturuyouz//
    public int id;
    public String kategori;
    public String renk;
    public String marka;
    public int stokAdedi;
    
    
    // iki adet constructor oluşturuyoruz.Bi tanesi yeni urun girişi için diğeri ise update işlemi için //
     public Urun(int id, String kategori, String renk, String marka, int stokAdedi) {
		super();
		this.id = id;
		this.kategori = kategori;
		this.renk = renk;
		this.marka = marka;
		this.stokAdedi = stokAdedi;
	}
      public Urun(String kategori, String renk, String marka, int stokAdedi) {
		super();
		this.kategori = kategori;
		this.renk = renk;
		this.marka = marka;
		this.stokAdedi = stokAdedi;
	}
      
      // neden get-set metodlarını kullandım? Çünkü sınıflarda oluşturduğumuz değişkenlerin özelliklerini tanımlamak için
    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }

    
    public String getKategori() {
        return kategori;
    }

    
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    
    public String getRenk() {
        return renk;
    }

    
    public void setRenk(String renk) {
        this.renk = renk;
    }

    
    public String getMarka() {
        return marka;
    }

    
    public void setMarka(String marka) {
        this.marka = marka;
    }

  
    public int getStokAdedi() {
        return stokAdedi;
    }

    
    public void setStokAdedi(int stokAdedi) {
        this.stokAdedi = stokAdedi;
    }
    
}
