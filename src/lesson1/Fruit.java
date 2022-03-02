package lesson1;

// 3.a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
// 3.d. вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах;
abstract class Fruit {
    abstract String getNameSingle();
    abstract String getNameMultiple(boolean genitive);
    abstract float getWeight();
}