import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import Storages.Storage;
import Storages.Item;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static PrintStream out = System.out;
/*
    // Класс Item, созданный для задания объектов, хранимых на складе
    public static class Item {
        // У каждого Item есть название и количество
        public String name = "";
        public int count = 0;

        // Для более удобного отображения объектов типа Item
        @Override
        public String toString() {
            return name + ": " + count;
        }
    }

    public static class Storage {
        // Массив предметов, хранимых в хранилище
        private Item[] items;

        // Конструктор (инициализируется массив длины 0, так как склад изначально пустой)
        public Storage() {
            items = new Item[0];
        }

        // Конструктор хранилища с данными объектами
        public Storage(Item[] items) {
            this.items = new Item[0];
            addItem(items);
        }

        // Метод для нахождения позиции предмета в хранилище по названию, если предмета нет, возвращает -1
        public int posItem(String s) {
            int pos = -1;
            for (int i = 0; i < items.length; i++)
                if (items[i].name.equals(s)) pos = i;
            return pos;
        }

        // Добавить объект типа Item в хранилище
        public void addItem(Item a) {
            // Если количество больше нуля, так как иначе ничего добавлять не нужно
            if (a.count > 0) {
                // Поиск позиции в хранилище
                int pos = posItem(a.name);
                // Если в хранилище таких предметов нет, то добавляем
                if (pos == -1) {
                    // Создаётся массив, длина которого на 1 больше, чем длина изначального массива
                    Item[] tempArr = new Item[items.length + 1];
                    // Копирование элементов во временный массив
                    for (int i = 0; i < items.length; i++)
                        tempArr[i] = items[i];
                    // добавление в конец временного массива нового элемента
                    tempArr[items.length] = a;
                    // изменение массива хранимых объектов
                    items = tempArr.clone();
                } else items[pos].count += a.count;
                // если такой предмет уже есть просто прибавляет к количеству данное количество
            }
        }

        // Добавление массива предметов в массив хранимых объектов
        public void addItem(Item[] array) {
            for (Item a : array) {
                addItem(a);
            }
        }

        // добавление предмета на склад через создание нового объекта Item и добавление его через ранее написанный addItem
        public void addItem(String s, int cnt) {
            if (cnt > 0) {
                Item a = new Item();
                a.name = s;
                a.count = cnt;
                addItem(a);
//                int pos = posItem(a.name);
//                if (pos == -1) {
//                    Item[] tempArr = new Item[items.length + 1];
//                    for (int i = 0; i < items.length; i++)
//                        tempArr[i] = items[i];
//                    tempArr[items.length] = a;
//                    items = tempArr.clone();
//                } else items[pos].count += a.count;
            }
        }

        // функция для перевода в строку (вывод содержимого склада)
        @Override
        public String toString() {
            String s = "Склад пуст";
            if (items.length != 0) {
                s = "";
                // Добавление в строку s объектов, хранимых на складе
                for (int i = 0; i < items.length; i++) {
                    // для красивого отображения первых предметов со склада
                    if ('a' + i < 'z')
                        s += "(" + (char) ('a' + i) + ") ";
                    else s += "(" + (i + 1) + ") ";
                    s += items[i] + "\n";
                }
            }
            return s;
        }

        // полное удаление объекта со склада
        public void deleteItem(String s) {
            // Если объект есть на складе
            if (posItem(s) != -1) {
                // Если при этом на складе только один объект - сделать пустым список хранимых объектов
                if (items.length <= 1) items = new Item[0];
                else {
                    // создание временного списка, который на 1 меньше, чем изначальный
                    Item[] tempArr = new Item[items.length - 1];
                    // переменная, используемая для сдвига при проходе по массиву
                    int flag = 0;
                    // Проход по индексам изначального списка
                    for (int i = 0; i < items.length; i++) {
                        // Если встретили объект, который нужно удалить - просто не записываем его во временный массив
                        // и прибавляем 1 к flag
                        if (items[i].name.equals(s)) flag += 1;
                        // иначе добавляем объект во временный список
                        else tempArr[i - flag] = items[i];
                    }
                    // Перезаписываем массив хранимых объектов
                    items = tempArr.clone();
                }
            }
        }

        // взять cnt предметов с названием s из массива хранимых объектов
        public void takeItem(String s, int cnt) {
            int pos = posItem(s);
            // Если предмет есть на складе
            if (pos != -1) {
                // Уменьшить кол-во этого предмета в хранилище на cnt
                items[pos].count -= cnt;
                // Если забрали всё, то удалить этот предмет из списка хранимых предметов
                if (items[pos].count <= 0) deleteItem(s);
            }
        }

        // узнать кол-во предметов с таким названием, хранимых в хранилище
        public int getCountOfItem(String s) {
            int pos = posItem(s);
            // Если таких предметов нет - вернуть 0
            if (pos == -1) return 0;
            // иначе вернуть количество
            else return items[pos].count;
        }

        // Список предметов, количество которых находится в заданном диапазоне
        public Item[] getItemsInCount(int begin, int end) {
            // для удобства хранения данных используется класс Storage для того, чтобы было легче записывать предметы из диапазона
            Storage tempStorage = new Storage();
            // проход по всем предметам на складе, если их кл-во находится в заданном диапазоне - добавить их на склад
            for (Item i : items) {
                if (i.count >= begin && i.count <= end) tempStorage.addItem(i);
            }
            // вернуть список предметов, хранимых на складе tempStorage
            return tempStorage.items;
        }

        // та же самая функция, но возвращает список подходящих предметов в виде строки
        public String getAsStringInCount(int begin, int end) {
            String s = "";
            for (Item i : items) {
                if (i.count >= begin && i.count <= end) s += i + "\n";
            }
            return s;
        }

        // функция, которая возвращает список позиций на складе
        public int countItems() {
            return items.length;
        }

        // функция, которая возвращает общее количество предметов на складе
        public int countAllItems() {
            int cnt = 0;
            for (Item i : items) cnt += i.count;
            return cnt;
        }

        // функция, которая возвращает процентное соотношение количества предметов с данным названием к общему количеству предметов на складе
        public double percent(String s) {
            int pos = posItem(s);
            // если предмета нет на складе вернуть 0
            if (pos == -1) return 0;
            // иначе вернуть процентное соотношение
            else return (double) items[pos].count / countAllItems() * 100;
        }

        // функция для сортировки склада
        public void sort() {
            // сортировка вставкой
            for (int i = 1; i < items.length; i++) {
                int t = i;
                Item tempItem = items[i];
                while (t > 0 && items[t - 1].count < tempItem.count) {
                    items[t] = items[t - 1];
                    t--;
                }
                if (t != i) items[t] = tempItem;
            }
        }

        // функция, которая возвращает отсортированный массив склада
        public Item[] sorted() {
            // сортировка вставками
            Item[] tempArr = items.clone();
            for (int i = 1; i < tempArr.length; i++) {
                int t = i;
                Item tempItem = tempArr[i];
                while (t > 0 && tempArr[t - 1].count < tempItem.count) {
                    tempArr[t] = tempArr[t - 1];
                    t--;
                }
                if (t != i) tempArr[t] = tempItem;
            }
            return tempArr;
        }

        // первые n наибольших предметов по их количеству
        public Item[] largest(int n) {
            // если n больше, чем предметов на складе, то будет выполняться также, как для n = количеству предметов
            if (n > items.length) n = items.length;
            // создаётся временный массив из n элементов
            Item[] sorted = new Item[n];
            // сортировка массива предметов
            sort();
            // добавление первых n элементов отсортированного массива во временный массив
            for (int i = 0; i < n; i++) {
                sorted[i] = items[i];
            }
            return sorted;
        }

        // сортирует массив предметов на складе и возвращает его
        public Item[] largest() {
            sort();
            return items;
        }

        // та же функция, но не меняет массив предметов, хранимых на складе
        public Item[] largestN(int n) {
            // если n больше, чем предметов на складе, то будет выполняться также, как для n = количеству предметов
            if (n > items.length) n = items.length;
            // создаётся временный массив из n элементов
            Item[] sorted = new Item[n];
            // временный массив, являющийся отсортированным массивом предметов, хранящихся на складе
            Item[] tempItems = sorted();
            // добавление первых n элементов отсортированного массива во временный массив
            for (int i = 0; i < n; i++) {
                sorted[i] = tempItems[i];
            }
            return sorted;
        }

        // возвращает отсортированный по количеству массив предметов, хранящихся на складе
        public Item[] largestN() {
            return sorted();
        }
    }
*/
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
        storage.addItem("AAAAA", 0);
        storage.addItem("AAAAA", -10);

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
        for (int i = 0; i < 250; i++) {
            storage.addItem("Мандарины" + i, (i + 1) * 50);
        }
        out.println(storage);
        out.println(new Storage(storage.largestN()));
    }
}