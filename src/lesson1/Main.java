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
        Generic<Integer> intArr = new Generic<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        Generic<String> strArr = new Generic<>(new String[]{"A", "B", "C", "Z", "Y", "X"});

        System.out.println(intArr.get(1) + " " + intArr.get(7));
        System.out.println(strArr.get(2) + " " + strArr.get(5));
        intArr.swap(1, 7);
        strArr.swap(2, 5);

        System.out.println("перестановка: \n" + intArr.get(1) + " " + intArr.get(7));
        System.out.println(strArr.get(2) + " " + strArr.get(5));
        ArrayList<Integer> intList = intArr.convert();
        ArrayList<String> strList = strArr.convert();
        System.out.println("преобразование в динамический список:\n" + intList.get(3) + " " + strList.get(4));

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
        //try {
        for (i = 0; i < numApp; i++) box1.add(apple); //box[0].add(apple);
        //box[0].add(orange); //- было возможно пока Box не был параметризованным
        for (i = 0; i < numOrn; i++) box2.add(orange); //box[1].add(orange);
        //box[1].add(apple); //- было возможно пока Box не был параметризованным
        System.out.println(
                "Вес коробки " + box1.getContents(true) + // box[0]+
                        //(box[0].compare(box[1])
                        (box1.compare(box2)
                                ? " совпадает с весом коробки " + /*box[1]*/box2.getContents(true) +
                                " и равен " + /*box[0]*/box1.getWeight()
                                : ": " + /*box[0]*/box1.getWeight() +
                                "\nВес коробки " + /*box[1]*/box2.getContents(true) + ": " + box2.getWeight())
                        + "\n3-я коробка: " + /*box[2]*/emptyBox.getContents(false) + "\n");

        //if (box[0].sendContents(box[1]))
        if (box1.sendContents(emptyBox)) {
            System.out.println(
                    emptyBox.getContents(false) + " из первой коробки пересыпаны в третью коробку:" +
                            "\n\t1-я коробка: " + box1.getContents(false) +
                            "\n\t2-я коробка: " + box2.getContents(false) +
                            "\n\t3-я коробка: " + emptyBox.getContents(false));
            //for (i = 0; i < box.length; i++)
            //System.out.println("\t" + (i + 1) + "-я коробка: " + box[i].getContents(false));
        }
    /*} catch (ImproperBoxUsageException ex) {
      ex.printStackTrace();
    }*/
    }
}