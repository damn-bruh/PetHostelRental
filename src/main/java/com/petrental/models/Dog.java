package com.petrental.models;

public class Dog extends Animals {
    private int age;
    private int weight;
    private String gender;
    private String breed;

    public Dog(int ID, String name, String breed,int age, int weight ,String gender) {
        super(ID, name);
        this.age = age;
        this.gender = gender;
        this.breed = breed;
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public String getBreed() {
        return breed;
    }

    @Override
    public String toString() {
        return "Pet ID: " + ID + "\nName: " + name + "\nAge: " + age +
                "\nWeight: " + weight + "\nGender: " + gender + "\nBreed: " + breed;
    }
}
