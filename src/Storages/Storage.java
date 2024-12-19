package Storages;

public class Storage {
    // Массив предметов, хранимых в хранилище
    private Storages.Item[] items;

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
