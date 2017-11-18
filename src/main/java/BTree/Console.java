package BTree;

import java.util.Scanner;

class Console {
    private int ansver=1;
    private Scanner in = new Scanner(System.in);
    int checkInt() {
        while (true) {
            try {
                return Integer.parseInt(in.nextLine());
            }
            catch (NumberFormatException ex) {
                System.out.println("Ошибка ввода данных, ведите корректное число для дальнейшей работы");
            }
        }
    }
    void readConsole() throws Exception {
        Tree tree = new Tree();
        while (ansver != 0) {
            System.out.println("Считать значания из файла для построения бинарного дерева поиска?\nда(1)/нет, выход(0)");
            ansver=checkInt();
            if  (ansver == 1) {
                tree.fileRead(tree);
                if (tree.getRoot()==null)
                    System.out.println("Бинарное дерево не задано, введите корректные данные в файл");
                else {
                    System.out.println("Бинарное дерево поиска");
                    tree.print();
                    ansver = 1;
                    while (ansver != 0) {
                        System.out.println("Выберите действие: добавить(1)/изменить(2)/удалить(3)/выход(0):");
                        ansver=checkInt();
                        switch (ansver) {
                            case 1:
                                System.out.println("Вы выбрали добавление элемента, введите его значение:");
                                tree.insert(checkInt());
                                tree.print();
                                break;
                            case 2:
                                System.out.println("Вы выбрали изменение элемента, введите: значение элемента, который нужно изменить и значение элемента, на которое нужно изменить.");
                                tree.change(checkInt(), checkInt());
                                tree.print();
                                break;
                            case 3:
                                System.out.println("Вы выбрали удаление элемента, введите его значение:");
                                tree.remove(checkInt());
                                tree.print();
                                break;
                            case 0:
                                break;
                        }
                    }
                }
            }
        }
    }
}
