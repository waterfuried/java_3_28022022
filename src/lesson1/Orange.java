package lesson1;

class Orange extends Fruit {
    private static final float FRUIT_WEIGHT = 1.5f;
    private static final String NAME_SINGLE = "апельсин";

    @Override float getWeight() { return FRUIT_WEIGHT; }
    @Override String getNameSingle() { return NAME_SINGLE; }
    @Override String getNameMultiple(boolean genitive) {
        return genitive ? NAME_SINGLE + "ов" : NAME_SINGLE + "ы";
    }
}