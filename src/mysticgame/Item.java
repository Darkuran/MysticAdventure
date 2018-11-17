package mysticgame;

public class Item {
    private String name;
    private String description;
    private boolean movable;

    public Item(String name, String description, boolean movable) {
        this.name = name;
        this.description = description;
        this.movable = movable;
    }

    public String getName() {
        return name;
    }

    public boolean isMovable() {
        return movable;
    }

    @Override
    public String toString() {
        String split = isMovable() ? "[]" : "''";
        return split.charAt(0) + name + split.charAt(1) + description + "";
    }
}
