package core.model;

import java.util.Objects;

public class Count {
    
    private int id;
    private int idCount;

    public Count(int id, int count) {

        this.id = id;
        this.idCount = count;

    }

    public int gId() {

        return this.id;

    }

    public int gCount() {

        return this.idCount;

    }

    @Override
    public String toString() {

        return String.format("Count: %s", this.idCount);

    }

    @Override
    public int hashCode() {

        return 13 * Objects.hash(this.id, this.idCount);

    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj.getClass() == this.getClass()) {

            return this.id == ((Count) obj).gId();

        }

        return false;

    }

}
