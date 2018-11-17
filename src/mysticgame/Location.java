package mysticgame;

import java.util.HashMap;

public class Location {
    private String name;
    private String description;
    private Inventory items;
    private HashMap<Direction, Location> nextLocations;

    public Location(String name) {
        this.name = name;
        description = "Описание локации отсутствует.";
        items = new Inventory();
        nextLocations = new HashMap<>();
    }

    // Устанавливает описание локации
    public void setDescription(String description) {
        this.description = description;
    }

    // Добавляет предметы на локацию
    public void addItems(Item... itemArr) {
        for (Item item : itemArr) {
            items.addItem(item);
        }
    }

    // Добавляет следующую локацию по указанному направлению
    public void addNextLoc(Direction direction, Location location) {
        nextLocations.put(direction, location);
    }

    // Возвращает следующую локацию по указанному направлению. Если локация отсутсвует, возвращает null
    public Location nextLocation(Direction direction) {
        return nextLocations.get(direction);
    }

    // Возвращает список предметов в виде объекта Inventory
    public Inventory getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Локация: " + name + ".\n" + description + "\n" +
                "Предметы разбросанные по локации: " + items.toString();
    }
}
