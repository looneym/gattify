package gattify.com.gattify;

import com.orm.SugarRecord;
import com.orm.dsl.Table;import java.lang.Long;import java.lang.Override;import java.lang.String;

/**
 * DB Entity Gatt represents a purchasable alcoholic item
 */
@Table
public class Gatt extends SugarRecord {

    // Class-level attributes

    private Long id;
    private String name;
    private String type;
    private double alcPerc;
    private double volume;
    private double price;
    private double score;
    private double units;

    // Constructors

    public Gatt(){}

    public Gatt(String name, String type, double alcPerc, double volume, double price, double units) {
        this.name = name;
        this.type = type;
        this.alcPerc = alcPerc;
        this.volume = volume;
        this.price = price;
        this.units= units;
        createScore();
    }

    // Helper methods

    private void createScore(){
        score = (alcPerc * volume * units) / price;
    }

    // Setters and getters

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    // toString

    @Override
    public String toString() {
        return "Gatt Object {" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", alcPerc=" + alcPerc +
                ", volume=" + volume +
                ", price=" + price +
                '}';
    }
}
