import java.io.PrintStream;
import java.util.Scanner;

import Storages.Storage;
import Storages.Item;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static PrintStream out = System.out;

    public static void main(String[] args) {
        // объект для управления складом
        Storage storage = new Storage();
        // проверка корректности отображения пустого склада
        out.println(storage);

        // добавление объектов на склад
        storage.addItem("Яблоки", 5);
        storage.addItem("Бананы", 10);
        storage.addItem("Ананасы", 20);
        storage.addItem("Арбузы", 100);

        // отображение содержимого склада
        out.println(storage);

        // количество позиций и общее количество предметов на складе
        out.println("количество позиций на складе: " + storage.countItems() + "\nобщее количество предметов на складе: " + storage.countAllItems());
        // список предметов в заданном диапазоне
        Item[] itm = storage.getItemsInCount(10, 100);
        // вывод этого списка на экран двумя способами
        out.println("\nСписок предметов на складе в диапазоне количества от 10 до 100: \n" + new Storage(itm));
        for (Item i : itm) out.println(i);

        // первые 2 позиции списка по количеству (без изменения их положения на складе)
        itm = storage.largestN(2);
        // вывод этого списка
        out.println("\nПервые две по количеству позиции на складе: \n" + new Storage(itm));
        for (Item i : itm) out.println(i);


        // добавить на склад предметы из данного списка
        storage.addItem(itm);
        // вывод склада с уже добавленными предметами
        out.println("Добавили на склад первые две позиции по количеству, которые уже были на складе (удвоили количество этих предметов на складе)\n" + storage);

        // удаление всех Арбузов
        storage.deleteItem("Арбузы");
        // со склада взяли 9 бананов
        storage.takeItem("Бананы", 5);
        // вывод товаров склада уже без арбузов и без 5 бананов
        out.println("Склад без 5 бананов и без арбузов\n" + storage);
        // со склада взяли 10 ананасов
        storage.takeItem("Ананасы", 10);

        // количество яблок на складе и общее количество предметов на складе
        out.println("количество яблок на складе / общее количество предметов на складе:\n" + storage.getCountOfItem("Яблоки") + " / " + storage.countAllItems());
        // процентное соотношение яблок к общему количеству предметов
        out.println("Процентное соотношение количества яблок к общему количеству предметов на складе: " + storage.percent("Яблоки"));
        // вывод списка всех позиций склада, отсортированных по убыванию и при этом сортирует по убыванию изначальный склад
        out.println("Вывод позиций склада, отсортированных по убыванию: \n" + new Storage(storage.largest()) + "\n" + storage);
    }
}