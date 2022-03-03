package lesson1;

import java.util.*;

class Generic<T> {
    private final T[] genArr;

    Generic () { genArr = null; }

    Generic (T[] arr) { genArr = arr; }

    private boolean isValidIndex(int idx) { return genArr != null && idx >= 0 && idx < genArr.length; }

    public T get(int idx) throws ArrayIndexOutOfBoundsException {
        if (isValidIndex(idx))
            return genArr[idx];
        else
            throw new ArrayIndexOutOfBoundsException();
    }

    /*
      1. Написать метод, который меняет два элемента массива местами.
         (массив может быть любого ссылочного типа);
     */
    public void swap(int idx1, int idx2) throws ArrayIndexOutOfBoundsException {
        if (isValidIndex(idx1) && isValidIndex(idx2)) {
            if (idx1 != idx2) {
                T k = genArr[idx1];
                genArr[idx1] = genArr[idx2];
                genArr[idx2] = k;
            }
        } else
            throw new ArrayIndexOutOfBoundsException();
    }

    /*
      2. Написать метод, который преобразует массив в ArrayList;
     */
    public ArrayList<T> convert() {
        return genArr == null ? null : new ArrayList<>(Arrays.asList(genArr));
    }

    public ArrayList<T> convert(T[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }
}