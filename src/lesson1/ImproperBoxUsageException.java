package lesson1;

// исключение требовалось, пока тип Box не был параметризованным
class ImproperBoxUsageException extends Exception {
    ImproperBoxUsageException(String newObj, String presentObj, boolean singleObj) {
        System.err.printf(
                "В коробку нельзя " + (singleObj ? "положить" : "пересыпать") +
                " %s - она уже используется для хранения %s%n",
                newObj, presentObj);
    }
}