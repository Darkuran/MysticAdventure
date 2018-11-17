package mysticgame;

public class Combination {
    private Item firstItem;
    private Item secondItem;
    private Item result;
    private String message;

    public Combination(Item firstItem, Item secondItem, Item result, String message) {
        this.firstItem = firstItem;
        this.secondItem = secondItem;
        this.result = result;
        this.message = message;
    }

    public Item getFirstItem() {
        return firstItem;
    }

    public Item getSecondItem() {
        return secondItem;
    }

    public Item getResult() {
        return result;
    }

    public void printMessage() {
        if (message != null) {
            System.out.println(message);
        }
    }
}
