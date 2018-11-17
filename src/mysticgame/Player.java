package mysticgame;

public class Player {
    private Location location;
    private Inventory inventory;

    public Player(Location location) {
        this.location = location;
        inventory = new Inventory();
    }

    // Выводит описание текущей локации
    public void lookAround() {
        System.out.println(location);
    }

    // Выводит список предметов в инвентаре
    public void showInventory() {
        System.out.println("Ваш инвентарь: " + inventory);
    }

    // Сменяет текущую локацию игрока
    public void move(String directionName) {
        Direction direction;
        Location nextLoc;

        switch (directionName) {
            case "наверх":
            case "вверх":
                direction = Direction.UP;
                break;
            case "вниз":
                direction = Direction.DOWN;
                break;
            case "север":
                direction = Direction.NORTH;
                break;
            case "юг":
                direction = Direction.SOUTH;
                break;
            case "запад":
            case "налево":
                direction = Direction.WEST;
                break;
            case "восток":
            case "направо":
                direction = Direction.EAST;
                break;
            default:
                direction = null;
        }
        if (direction != null) {
            nextLoc = location.nextLocation(direction);
            if (nextLoc != null) {
                location = nextLoc;
                System.out.println(location);
            } else {
                System.out.println(" ! Вы не можете туда пойти");
            }
        } else {
            System.out.println(" ! Некорректный синтаксис команды 'идти'\n" +
                    " ! Для помощи введите: команда 'идти'");
        }
    }

    // Добавляет в инвентарь игрока предмет
    public void pickUp(String itemName) {
        Item item = location.getItems().getItem(itemName);

        if (item != null) {
            if (item.isMovable()) {
                inventory.addItem(item);
                location.getItems().removeItem(item);
                System.out.println("Вы подобрали предмет: " + item);
            } else {
                System.out.println("Невозможно подобрать данный предмет");
            }
        } else {
            System.out.println(" ! Предмет не найден или неккоректно введено его название\n" +
                    " ! Для помощи введите: команда 'взять'");
        }
    }

    // Производит необходимые действия при использовании предметов
    public void use(String firstItemName, String secondItemName, Combination[] combinations) {
        boolean usable = true;
        Item firstItem = inventory.getItem(firstItemName);
        Item secondItem = inventory.getItem(secondItemName);
        Item secondLocItem = location.getItems().getItem(secondItemName);

        // Проверка на наличие указанных предметов
        if (firstItem == null ) {
            System.out.println(" ! У вас нет указанного предмета в инвентаре или неккоректно введено его название\n" +
                    " ! Для помощи введите: команда использовать");
            usable = false;
        }
        else if (secondItem == null) {
            if (secondLocItem != null) {
                secondItem = secondLocItem;
            }
            else {
                System.out.println(" ! Предмет(ы) или объект(ы) отсутствует или вы допустили ошибку в его названии\n" +
                        " ! Для помощи введите: команда использовать");
                usable = false;
            }
        }

        // Поиск комбинаций и выполнение определенных действий при найденных совпадениях
        if (usable) {
            for (Combination combination : combinations) {
                Item combFirstItem = combination.getFirstItem();
                Item combSecondItem = combination.getSecondItem();

                if ((firstItem == combFirstItem && secondItem == combSecondItem) ||
                        (firstItem == combSecondItem && secondItem == combFirstItem)) {
                    if (combination.getResult() != null) {
                        if (firstItem.isMovable()) {
                            inventory.removeItem(firstItem);
                            location.getItems().removeItem(firstItem);
                        }
                        if (secondItem.isMovable()) {
                            inventory.removeItem(secondItem);
                            location.getItems().removeItem(secondItem);
                        }
                        inventory.addItem(combination.getResult());
                        System.out.println("Получено: " + combination.getResult());
                    }
                    combination.printMessage();
                    return;
                }
            }
            System.out.println("Ничего не произошло");
        }
    }

    // Возвращает инвентарь игрока
    public Inventory getInventory() {
        return inventory;
    }
}
