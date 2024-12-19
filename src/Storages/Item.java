package Storages;

// Класс Item для хранения значения товара (названия и количества)
public class Item {
    // У каждого Item есть название и количество
    public String name = "";
    public int count = 0;

    // Для более удобного отображения объектов типа Item
    @Override
    public String toString() {
        return name + ": " + count;
    }
}