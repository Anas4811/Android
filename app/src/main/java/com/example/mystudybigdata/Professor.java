package com.example.mystudybigdata;

public class Professor {
    private String name;
    private String email;
    private String matiere;

    public Professor(String name, String email,String matiere) {
        this.name = name;
        this.email = email;
        this.matiere=matiere;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getMatiere(){
        return matiere;
    }
}