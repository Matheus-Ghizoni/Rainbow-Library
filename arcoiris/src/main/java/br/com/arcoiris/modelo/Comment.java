package br.com.arcoiris.modelo;

import java.io.Serializable;

public class Comment implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private int id;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

}