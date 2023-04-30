package core.model;

import java.util.Objects;

public class Toy {
    
    private int id;
    private String name;
    private float weight;

    public Toy(int id, String name, float weight) {

        this.id = id;
        this.name = name;
        this.weight = weight;

    }

    public int gId() {

        return this.id;

    }

    public String gName() {

        return this.name;

    }

    public float gWeight() {

        return this.weight;

    }

    @Override
    public String toString() {
        
        return String.format("ID: %s, Name: %s, Weight: %s", this.id, this.name, this.weight);

    }

    @Override
    public int hashCode() {
        
        return 13 * Objects.hash(this.name, this.weight);

    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj != null && obj.getClass() == this.getClass()) {

            return this.id == ((Toy) obj).gId();

        }

        return false;

    }

}
