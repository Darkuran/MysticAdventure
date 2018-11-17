package mysticgame;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items; //Test

    public Inventory() {
        items = new ArrayList<>();
    }

    // Добавляет предмет
    public void addItem(Item item) {
        items.add(item);
    }

    // Возвращает найденный предмет. Если он отсутствует, возвращает null
    public Item getItem(String itemName) {
        for(Item item: items) {
            if (itemName.equalsIgnoreCase(item.getName())) {
                return item;
            }
        }
        return null;
    }

    // Удаляет предмет
    public void removeItem(Item item) {
            items.remove(item);
    }

    @Override
    public String toString() {
        String result = "";
        if (items.size() == 0) {
            return " пусто";
        }
        else {
            for (Item item : items) {
                if (item.isMovable()) {
                    result += item.toString();
                    result += ", ";
                }
            }
        }
        if (result.length() == 0) {
            result = " пусто";
        }
        else {
            result = result.substring(0, result.length()-2);
        }

        return result;
    }
}
