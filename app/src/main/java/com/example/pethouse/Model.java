package com.example.pethouse;

public class Model {
     String nomPet,age,sexe,image ;

     Model(){

     }

    public Model(String nomPet, String age, String sexe, String image) {
        this.nomPet = nomPet;
        this.age = age;
        this.sexe = sexe;
        this.image = image;
    }

    public String getNomPet() {
        return nomPet;
    }

    public void setNomPet(String nomPet) {
        this.nomPet = nomPet;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
