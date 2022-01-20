/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author evren
 */
public class Satis { // oluşturacak olduğumuz satış özelliklerini yazıyoruz ve bu değişkenleri oluşturuyouz//
        public int id;
        public Urun urun;
	public Musteri musteri;
        public int stokAdedi;
        
        
         //constructor oluşturuyoruz. yeni satış girişi için,update işlemi için.yüzyüze satış için//
        public Satis(int id, Urun urun, Musteri musteri, int stokAdedi) {
		super();
		this.id = id;
		this.urun = urun;
		this.musteri = musteri;
                this.stokAdedi = stokAdedi;
	}
        public Satis(int id, Urun urun, Musteri musteri) {
		super();
		this.id = id;
		this.urun = urun;
		this.musteri = musteri;
	}
	public Satis(Urun urun, Musteri musteri) {
		super();
		this.urun = urun;
		this.musteri = musteri;

	}
        public Satis(Urun urun, Musteri musteri, int stokAdedi) {
		super();
		this.urun = urun;
		this.musteri = musteri;
                this.stokAdedi=stokAdedi;

	}
        
             // neden get-set metodlarını kullandım? Çünkü sınıflarda oluşturduğumuz değişkenlerin özelliklerini tanımlamak için
         public int getStokAdedi() {
        return stokAdedi;
    }

  
    public void setStokAdedi(int stokAdedi) {
        this.stokAdedi = stokAdedi;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the urun
     */
    public Urun getUrun() {
        return urun;
    }

    /**
     * @param urun the urun to set
     */
    public void setUrun(Urun urun) {
        this.urun = urun;
    }

    /**
     * @return the musteri
     */
    public Musteri getMusteri() {
        return musteri;
    }

    /**
     * @param musteri the musteri to set
     */
    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }
    
    
}
