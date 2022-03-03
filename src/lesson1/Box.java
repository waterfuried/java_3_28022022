package lesson1;

import java.util.*;

/*
    ! 1) в комментариях перед методами оставлен код первого варианта -
    !    без использования обобщенного типа, если код двух вариантов различается
    ! 2) с учетом типобезопасности выброс особого исключения в методах
    !    add и sendContents не нужен - он был реализован для (в) первой версии

    3.b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта,
    поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
 */
class Box<T extends Fruit> {
    public static final float DELTA = 0.001f;

    // 3.c. Для хранения фруктов внутри коробки можете использовать ArrayList;
    ArrayList<Fruit> container;

    Box () {
        container = new ArrayList<>();
    }

    /*
       3.d. Сделать метод getWeight() который высчитывает вес коробки,
       зная количество фруктов и вес одного фрукта
    */
    public float getWeight() {
        float sum = 0f;
        for (Fruit f : container) sum += f.getWeight();
        return sum;
    }

    /*
       3.e. Внутри класса коробка сделать метод compare, который позволяет сравнить
       текущую коробку с той, которую подадут в compare в качестве параметра,
       true - если их веса равны, false в противном случае
       (коробки с яблоками мы можем сравнивать с коробками с апельсинами)
    */
    public boolean compare(Box<?> box) { // Box box - в не параметризованном виде
        return Math.abs(getWeight() - box.getWeight()) < DELTA;
    }

    /*
       3.f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
       (помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами),
       соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты,
       которые были в этой коробке;
     */
    /*public boolean sendContents(Box box) throws ImproperBoxUsageException {
        if (box != null) {
            if (container.get(0).getClass() == box.container.get(0).getClass()) {
                box.container.addAll(container);
                container.clear();
                return true;
            } else
                throw new ImproperBoxUsageException(container.get(0).getNameMultiple(false),
                            box.container.get(0).getNameMultiple(true), false);
        }
        return false;
    }*/
    public boolean sendContents(Box<?> box) {
        if (box != null) {
            box.container.addAll(container);
            container.clear();
        }
        return box != null;
    }

    // 3.g. Не забываем про метод добавления фрукта в коробку.
    /*public void add(Fruit fruit) throws ImproperBoxUsageException {
        if (container == null) {
            container = new ArrayList<>();
        } else {
             if (container.get(0).getClass() != fruit.getClass())
                throw new ImproperBoxUsageException(fruit.getNameSingle(),
                        container.get(0).getNameMultiple(true), true);
            else
                container.add(fruit);
        }
    }*/
    public void add(T fruit) {
        container.add(fruit);
    }

    // тип фруктов в коробке
    public String getContents(boolean genitive) {
        return container.size() > 0
                ? container.get(0).getNameMultiple(genitive)
                : "коробка пуста";
    }
}