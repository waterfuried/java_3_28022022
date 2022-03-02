package lesson1;

import java.util.*;

/*
    3.b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта,
    поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
 */
class Box<T extends Fruit> {
    // 3.c. Для хранения фруктов внутри коробки можете использовать ArrayList;
    ArrayList<Fruit> container;

    /*
       3.d. Сделать метод getWeight() который высчитывает вес коробки,
       зная количество фруктов и вес одного фрукта
    */
    public float getWeight() throws NullPointerException {
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
    public boolean compare(Box<?> box) throws NullPointerException { // Box box - в не параметризованном виде
        return getWeight() == box.getWeight();
    }

    // с учетом типобезопасности выброс особого исключения для этого и следующего методов не нужен,
    // но был реализован для (в) первой версии - пока тип Box не был параметризованным
    /*
       3.f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
       (помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами),
       соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты,
       которые были в этой коробке;
     */
    //public boolean sendContents(Box box)
    public boolean sendContents(Box<?> box) /*throws ImproperBoxUsageException*/ {
        if (container != null && box != null) {
            if (box.container == null) {
                box.container = new ArrayList<>(container);
            } /*else {
                if (container.get(0).getClass() != box.container.get(0).getClass())
                    throw new ImproperBoxUsageException(container.get(0).getNameMultiple(false),
                            box.container.get(0).getNameMultiple(true), false);
            }*/
            box.container.addAll(container);
            container.clear();
            return true;
        }
        return false;
    }

    // 3.g. Не забываем про метод добавления фрукта в коробку.
    // public void add(Fruit fruit)
    public void add(T fruit) /*throws ImproperBoxUsageException*/ {
        if (container == null) {
            container = new ArrayList<>(new ArrayList<>(Collections.singletonList(fruit)));
        } else {
             /*if (container.get(0).getClass() != fruit.getClass())
                throw new ImproperBoxUsageException(fruit.getNameSingle(),
                        container.get(0).getNameMultiple(true), true);
            else*/
                container.add(fruit);
        }
    }

    // тип фруктов в коробке
    public String getContents(boolean genitive) {
        return container != null && container.size() > 0
                ? container.get(0).getNameMultiple(genitive)
                : "коробка пуста";
    }
}