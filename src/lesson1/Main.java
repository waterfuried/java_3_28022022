package lesson1;

import java.util.*;

public class Main {
    // сгенерировать случайное число в указанном диапазоне
    static int randomNumber(int min, int max) {
        return min + Math.round((float) Math.random() * (max - min));
    }

    public static void main(String[] args) {
        /*
          задания 1 и 2
        */
        Generic<Integer> intArr = new Generic<>(new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
        Generic<String> strArr = new Generic<>(new String[] { "A", "B", "C", "Z", "Y", "X" });

        System.out.println(intArr.get(1) + " " + intArr.get(7));
        System.out.println(strArr.get(2) + " " + strArr.get(5));
        intArr.swap(1, 7);
        strArr.swap(2, 5);

        System.out.println("перестановка: \n" + intArr.get(1) + " " + intArr.get(7));
        System.out.println(strArr.get(2) + " " + strArr.get(5));
        ArrayList<Integer> intList1 = intArr.convert();
        ArrayList<String> strList1 = strArr.convert();
        ArrayList<Integer> intList2 = new Generic<Integer>().convert(new Integer[] { 9, 8, 7, 6, 5 });
        ArrayList<String> strList2 = new Generic<String>().convert(new String[] { "x", "y", "z" });
        System.out.println(
                "после преобразования в динамический список:\n" +
                intList1.get(3) + " " + intList2.get(4) + "\n" + strList1.get(3) + " " + strList2.get(1));

        /*
          задание 3
        */
        System.out.println("\nКоробки с фруктами:");
        Apple apple = new Apple();
        Orange orange = new Orange();

        // пока тип Box не был параметризованным, массив можно было задать так
        //Box[] box = new Box[] { new Box(), new Box(), new Box() };

        Box<Apple> box1 = new Box<>();
        Box<Orange> box2 = new Box<>();
        Box<Fruit> emptyBox = new Box<>();

        int i, numApp = randomNumber(1, 100), numOrn = randomNumber(1, 50);

        //обработка особого исключения нужна была пока Box не был обобщенным
        /*try {
            // первый цикл: box[0].add(apple)
            box[0].add(orange); // после первого цикла, было возможно пока Box не был параметризованным
            // второй цикл: box[1].add(orange)
            box[1].add(apple); // после второго цикла, было возможно пока Box не был параметризованным
            // ...вывод после пересыпания фруктов
            for (i = 0; i < box.length; i++)
                System.out.println("\t" + (i + 1) + "-я коробка: " + box[i].getContents(false));
        } catch (ImproperBoxUsageException ex) {
            ex.printStackTrace();
        }*/
        for (i = 0; i < numApp; i++) box1.add(apple);
        for (i = 0; i < numOrn; i++) box2.add(orange);

        System.out.println(
                "Вес коробки " + box1.getContents(true) +
                (box1.compare(box2)
                    ? " совпадает с весом коробки " + box2.getContents(true) +
                      " и равен " + box1.getWeight()
                    : ": " + box1.getWeight() +
                      "\nВес коробки " + box2.getContents(true) + ": " + box2.getWeight())
                + "\n3-я коробка: " + emptyBox.getContents(false) + "\n");

        if (box1.sendContents(emptyBox)) {
            System.out.println(
                emptyBox.getContents(false) + " из первой коробки пересыпаны в третью коробку:" +
                    "\n\t1-я коробка: " + box1.getContents(false) +
                    "\n\t2-я коробка: " + box2.getContents(false) +
                    "\n\t3-я коробка: " + emptyBox.getContents(false));
        }
    }
}