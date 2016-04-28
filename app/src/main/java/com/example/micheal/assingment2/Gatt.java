package com.example.micheal.assingment2;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by Micheal on 26/04/16.
 */
@Table
public class Gatt extends SugarRecord {

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    public Gatt(){}

    public Gatt(String name, String type, double alcPerc, double volume, double price, boolean ml) {
        this.name = name;
        this.type = type;
        this.alcPerc = alcPerc;
        this.volume = volume;
        this.price = price;
        this.ml = ml;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAlcPerc() {
        return alcPerc;
    }

    public void setAlcPerc(double alcPerc) {
        this.alcPerc = alcPerc;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isMl() {
        return ml;
    }

    public void setMl(boolean ml) {
        this.ml = ml;
    }

    private String name;
    private String type;
    private double alcPerc;
    private double volume;
    private double price;
    private boolean ml;

    @Override
    public String toString() {
        return "Gatt Object {" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", alcPerc=" + alcPerc +
                ", volume=" + volume +
                ", price=" + price +
                ", ml=" + ml +
                '}';
    }
}
