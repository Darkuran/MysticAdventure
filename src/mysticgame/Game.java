package mysticgame;

import java.util.Scanner;

public class Game {
    private Player player;
    private Help help;
    private Combination[] combinations;

    public Game() {
        help = new Help();
        init();
    }

    // Инициализация игры
    private void init() {
        // Создание предметов
        Item bucket = new Item("ведро", "пустое", true);
        Item bucketPlusChain = new Item("ведро", "с цепью", true);
        Item bucketWithChain = new Item("ведро", "с приваренной цепью", true);
        Item bucketWithWater = new Item("ведро", "полное воды", true);
        Item whisky = new Item("виски", "односолодовый", true);
        Item chain = new Item("цепь", "из нержавеющей стали", true);
        Item frog = new Item("лягушка", "с крохотными лапами", true);
        Item crystal = new Item("кристалл", "",true);
        Item wizard = new Item("волшебник", "",false);
        Item burner = new Item("горелка", "",false);
        Item well = new Item("колодец", "",false);

        // Создание цепочек взаимодействия предметов и игровых объектов
        combinations = new Combination[]{
                new Combination(bucket, chain, bucketPlusChain, "Вы приложили к ведру цепь."),
                new Combination(bucket, well, null, "Вода слишком далеко. Не достать."),
                new Combination(bucketPlusChain, burner, bucketWithChain, "Вы крепко приварили цепь к ведру."),
                new Combination(bucketWithChain, well, bucketWithWater, "Вы набрали воды в ведро."),
                new Combination(bucketWithWater, wizard, crystal, "Вы вылили ведро воды на спящего волшебника.\n" +
                        "Волшебник вскакивает и начинает отряхиваться.\n" +
                        "Приведя себя в порядок, он благодарит вас за помощь и протягивает вам магический кристалл."),

                new Combination(bucket, burner, null, "Нужно найти что-то, что можно приварить к ведру"),
                new Combination(bucket, frog, null, "Вы хотите посадить лягушку в ведро? Плохая идея"),
                new Combination(bucket, whisky, null, "Вы собрались пить виски из ведра?"),
                new Combination(bucket, wizard, null, "Пожалуй не стоить бить пустым ведром по волшебнику, чтобы его разбудить"),
                new Combination(chain, burner, null, "Нужно найти что-то, что можно приварить к цепи"),
                new Combination(whisky, wizard, null, "Боюсь вылив бутылку виски на волшебника, он не проснется"),
                new Combination(frog, chain, null, "Хотите привязать крохотную лягушку к цепи? Выглядит абсурдно"),
                new Combination(frog, well, null, "Плохая идея, кидать лягушку в колодец"),
                new Combination(frog, burner, null, "Слишком жестоко, жарить лягушку на горелке"),
                new Combination(frog, whisky, null, "Хотите напоить лягушку? Плохая идея")};

        // Создание локаций
        Location livingRoom = new Location("Гостинная дома");
        Location attic = new Location("Чердак дома");
        Location garden = new Location("Прекрасный сад");

        // Конструирование локации "Гостиная"
        livingRoom.setDescription("Рядом на кровати громко храпит " + wizard + ".\n" +
                "На востоке от вас есть дверь, рядом лестница наверх.");
        livingRoom.addItems(bucket, whisky, wizard);
        livingRoom.addNextLoc(Direction.UP, attic);
        livingRoom.addNextLoc(Direction.EAST, garden);

        // Конструирование локации "Чердак"
        attic.setDescription("В углу видна гигантская " + burner + ".\n" +
                "Вниз ведет лестница.");
        attic.addItems(burner);
        attic.addNextLoc(Direction.DOWN, livingRoom);

        // Конструирование локации "Сад"
        garden.setDescription("Прямо по курсу находится " + well + ".\n" +
                "На западе дверь в дом.");
        garden.addItems(well);
        garden.addItems(chain, frog);
        garden.addNextLoc(Direction.WEST, livingRoom);

        // Создание игрока с указанием стартовой локации в конструкторе
        player = new Player(livingRoom);
    }

    // Запуск игры
    public void start() {
        Scanner scanner = new Scanner(System.in);
        String input[];
        boolean isPlay = true;

        System.out.println("Добро пожаловать в игру.");
        help.howToPlay();
        player.lookAround();

        while (isPlay) {
            System.out.print("> ");
            input = scanner.nextLine().toLowerCase().split(" ");

            switch (input[0]) {
                case "осмотреться":
                    player.lookAround();
                    break;
                case "инвентарь":
                    player.showInventory();
                    break;
                case "идти":
                    if (input.length == 2) {
                        player.move(input[1]);
                    } else {
                        badArgsNotify(input[0]);
                    }
                    break;
                case "взять":
                    if (input.length == 2) {
                        player.pickUp(input[1]);
                    } else {
                        badArgsNotify(input[0]);
                    }
                    break;
                case "использовать":
                    if (input.length == 3) {
                        player.use(input[1], input[2], combinations);
                    } else {
                        badArgsNotify(input[0]);
                    }
                    break;
                case "команда":
                    if (input.length == 2) {
                        help.aboutCommand(input[1]);
                    } else {
                        badArgsNotify(input[0]);
                    }
                    break;
                case "команды":
                    help.aboutAllCommands();
                    break;
                case "выход":
                    isPlay = false;
                    break;
                default:
                    System.out.println(" ! Несуществующая команда. Проверьте написание\n" +
                            " ! Для вывода списка комманд введите: команды");

            }
            if (player.getInventory().getItem("Кристалл") != null) {
                isPlay = false;
            }
        }
        System.out.println("Игра завершена.");

    }

    // Выводит сообщение о неверном количестве аргументов для команды
    private void badArgsNotify(String command) {
        System.out.println(" ! Неверное количество аргументов\n" +
                " ! Для помощи введите: команда '" + command + "'");
    }
}
