package com.training.cardealership.cars;

import java.util.Objects;

public class CarDTO {
    private String brand;
    private String model;
    private int price;
    private int year;
    private int mileage;
    private String colour;


    public CarDTO(String brand, String model, int price, int year, int mileage, String colour) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.year = year;
        this.mileage = mileage;
        this.colour = colour;
    }

    public CarDTO() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", year=" + year +
                ", mileage=" + mileage +
                ", colour='" + colour + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO carDTO = (CarDTO) o;
        return price == carDTO.price && year == carDTO.year && mileage == carDTO.mileage && Objects.equals(brand, carDTO.brand) && Objects.equals(model, carDTO.model) && Objects.equals(colour, carDTO.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, price, year, mileage, colour);
    }
}
