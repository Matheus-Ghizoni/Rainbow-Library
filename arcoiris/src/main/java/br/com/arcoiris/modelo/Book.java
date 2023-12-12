package br.com.arcoiris.modelo;

import java.io.Serializable;

public class Book implements Serializable{

	private static final long serialVersionUID = 1L;

    private int code;
    private String title;
    private String author;
    private String location;
    private int fkcategory;
    private String namecategory;
    private int status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor(){
        return author;
    }
    
    public void setAuthor(String author){
        this.author = author;
    }
    
    public String getLocation(){
        return location;
    }
    
    public void setLocation(String location){
        this.location = location;
    }

    public int getfkCategory(){
        return fkcategory;
    }

    public void setfkCategory(int fkcategory){
        this.fkcategory = fkcategory;
    }
    
    public String getNameCategory(){
        return namecategory;
    }

    public void setNameCategory(String namecategory){
        this.namecategory = namecategory;
    }
    
    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }
	
}
