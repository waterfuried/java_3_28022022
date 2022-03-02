package lesson1;

class Apple extends Fruit {
    private static final float FRUIT_WEIGHT = 1.0f;
    private static final String NAME_SINGLE = "яблоко";

    @Override float getWeight() { return FRUIT_WEIGHT; }
    @Override String getNameSingle() { return NAME_SINGLE; }
    @Override String getNameMultiple(boolean genitive) {
        return genitive
                ? NAME_SINGLE.substring(0, NAME_SINGLE.length() - 1)
                : NAME_SINGLE.substring(0, NAME_SINGLE.length() - 1) + "и";
    }
}