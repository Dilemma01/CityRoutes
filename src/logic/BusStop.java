package logic;

public class BusStop {
    private String name;

    public BusStop(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BusStop{" +
                "nombre='" + name + '\'' +
                '}';
    }
}
