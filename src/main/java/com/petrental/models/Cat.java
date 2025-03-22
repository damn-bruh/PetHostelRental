package com.petrental.models;

public class Cat extends Animals {
    private String breed;
    private int age;
    private int weight;
    private String gender;

    public Cat(int ID, String name, String breed, int age, int weight, String gender) {
        super(ID, name);
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
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
