/*
1. Задать целочисленный массив, состоящий из элементов 0 и 1.
   Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ].
   С помощью цикла и условия заменить 0 на 1, 1 на 0;
2. Задать пустой целочисленный массив длиной 100.
   С помощью цикла заполнить его значениями 1 2 3 4 5 6 7 8 … 100;
3. Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ],
   пройти по нему циклом, и числа меньшие 6 умножить на 2;
4. Создать квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое),
   и с помощью цикла(-ов) заполнить его диагональные элементы единицами
   (можно только одну из диагоналей, если обе сложно).
   Определить элементы одной из диагоналей можно по следующему принципу:
   индексы таких элементов равны, то есть [0][0], [1][1], [2][2], …, [n][n];
5. Написать метод, принимающий на вход два аргумента: len и initialValue,
   и возвращающий одномерный массив типа int длиной len, каждая ячейка которого равна initialValue;
6. * Задать одномерный массив и найти в нем минимальный и максимальный элементы ;
7. ** Написать метод, в который передается не пустой одномерный целочисленный массив,
   метод должен вернуть true, если в массиве есть место, в котором сумма левой и правой части массива равны.
   **Примеры:
   checkBalance([2, 2, 2, 1, 2, 2, ||| 10, 1]) → true, т.е. 2 + 2 + 2 + 1 + 2 + 2 = 10 + 1
   checkBalance([1, 1, 1, ||| 2, 1]) → true, т.е. 1 + 1 + 1 = 2 + 1

   граница показана символами |||, эти символы в массив не входят и не имеют никакого отношения к ИЛИ.

8. *** Написать метод, которому на вход подается одномерный массив и число n
   (может быть положительным, или отрицательным), при этом метод должен сместить все элементы массива на n позиций.
   Элементы смещаются циклично. Для усложнения задачи нельзя пользоваться вспомогательными массивами.
   Примеры:
     [ 1, 2, 3 ] при n = 1 (на один вправо) -> [ 3, 1, 2 ];
     [ 3, 5, 6, 1] при n = -2 (на два влево) -> [ 6, 1, 3, 5 ].
   При каком n в какую сторону сдвиг можете выбирать сами.
*/
package lesson3;

import java.util.*;

public class HomeWorkApp {
    // вывести непустое сообщение в консоль
    static void showMessage(String msg) {
        if (msg == null || msg.length() == 0) return;
        System.out.println(msg + ":");
    }

    // отобразить содержимое одномерного массива
    static void showArrayContents (int[] arr) {
        for (int elem : arr) {
            System.out.printf(" %2d", elem);
        }
        System.out.println();
    }

    // отобразить содержимое одномерного массива (с предварительным сообщением)
    static void printArray (int[] arr, String msg) {
        showMessage(msg);
        showArrayContents(arr);
    }

    // отобразить содержимое двумерного массива (с предварительным сообщением)
    static void printArray (int[][] arr, String msg) {
        showMessage(msg);
        for (int[] i : arr ) { showArrayContents(i); }
    }

    // сформировать верный падеж слова в зависимости от числа (ноль не передается в метод)
    // используется в задачах 7 и 8
    static String syntaxForNumber (int num, boolean genitive) {
        String s = "элемент";
        int modNum = Math.abs(num);
        if (genitive) {
            if (modNum < 10 || modNum > 20) {
                switch (modNum % 10) {
                    case 1:
                        break;
                    case 2:
                    case 3:
                    case 4:
                        s += "а";
                        break;
                    default:
                        s += "ов";
                }
            } else {
                s += "ов";
            }
            s += " " + (num > 0 ? "вправо" : "влево");
        } else {
            s += (modNum % 10 == 1 && modNum != 11) ? "а" : "ов";
        }

        return modNum + " " + s;
    }

    // метод для задания 5 - возвращает одномерный массив типа int длиной len,
    // каждая ячейка которого равна initialValue
    static int[] fillArrayWithValue (int len, int initialValue) {
        int[] n = new int[len];
        Arrays.fill(n, initialValue);
        return n;
    }

    // методы для задания 6 - поиск минимального и максимального элементов одномерного массива
    static int findMin (int[] arr) {
        int n = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < n) n = arr[i];
        }
        return n;
    }
    static int findMax (int[] arr) {
        int n = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > n) n = arr[i];
        }
        return n;
    }

    // метод для задания 7, проверяющий, что сумма левой и правой части массива равны
    static boolean checkBalance (int[] arr) {
        int i, sumL = 0, sumAll = 0;

        for (i = 0; i < arr.length; i++) {
            sumAll += arr[i];
        }

        i = 0;
        do {
            sumL += arr[i++];
        } while (sumL != (sumAll - sumL) && i <= arr.length-1);

        printArray(arr, "\nВ массиве");
        if (sumL == sumAll - sumL) {
            System.out.println("сумма " + syntaxForNumber(i, false) + " слева равна сумме " +
                    syntaxForNumber(arr.length-i, false) + " справа");
        } else {
            System.out.println("суммы элементов справа и слева не равны.");
        }

        return sumL == sumAll - sumL;
    }

    // циклический сдвиг массива на 1 позицию вправо/влево
    static void shiftArrayOnce (int[] arr, boolean shiftRight) {
        int i, k;
        if (shiftRight) {
            k = arr[arr.length - 1];
            for (i = arr.length - 1; i > 0; i--) {
                arr[i] = arr[i-1];
            }
            arr[0] = k;
        } else {
            k = arr[0];
            for (i = 0; i < arr.length - 1; i++) {
                arr[i] = arr[i+1];
            }
            arr[arr.length - 1] = k;
        }
    }

    /**
     * перевернуть часть массива
     * @param arr       массив
     * @param firstIdx  индекс элемента, с которого выполнять переворот
     * @param count     количество переворачиваемых элементов
     * для перестановки элементов можно было бы использовать метод swap из java.util.Collections
     * или даже просто использовать метод rotate из того же пакета, который и выполняет циклический
     * сдвиг массива/коллекции на нужное число позиций, но задача все-таки учебная :)
     */
    static void reverse(int[] arr, int firstIdx, int count) {
        for (int i = 0; i < count/2; i++) {
            int temp = arr[firstIdx + i];
            arr[firstIdx + i] = arr[firstIdx + count - 1 - i];
            arr[firstIdx + count - 1 - i] = temp;
        }
    }

    // вернуть знак числа или ноль
    static int getSign(int num) {
        return num == 0 ? 0 : num < 0 ? -1 : 1;
    }

    // метод для задания 8, выполняющий сдвиг массива вправо/влево
    static void shiftArray (int[] arr, int numShift) {
        // из-за цикличности сдвига стоит сделать пересчет его величины (для уменьшения числа итераций):
        // а) если величина больше размера массива,
        // б) сдвиг в противоположную сторону может быть меньше, чем в заданную
        System.out.println();
        int corrShift = Math.abs(numShift) >= arr.length ? numShift % arr.length : numShift;
        if (corrShift != 0) {
            if (Math.abs(corrShift) > arr.length / 2) {
                corrShift = -getSign(corrShift) * (arr.length - Math.abs(corrShift));
            }
            if (corrShift != numShift)
                System.out.print("Сдвиг на " + syntaxForNumber(numShift, true) +
                        " заменен сдвигом на " + syntaxForNumber(corrShift, true));
            printArray(arr, "\nНовый массив до сдвига");

            // реализация сдвига через последовательные сдвиги на 1 элемент
            for (int i = 0; i < Math.abs(corrShift); i++) {
                shiftArrayOnce(arr, corrShift > 0);
            }
            printArray(arr, "после 1 сдвига (на " + syntaxForNumber(corrShift, true) + ")");

            /* реализация сдвига через перевороты частей массива:
                1. сдвиг вправо ?
                    да:
                        перевернуть первые (N-k) элементов (N - размер массива, k - величина сдвига)
                    нет:
                        перевернуть первые k элементов
                2. перевернуть элементы в оставшейся части
                3. перевернуть весь массив
            */
            if (corrShift < 0) {
                reverse(arr, 0, Math.abs(corrShift));
                reverse(arr, Math.abs(corrShift), arr.length-Math.abs(corrShift));
            } else {
                reverse(arr, 0, arr.length-Math.abs(corrShift));
                reverse(arr, arr.length-Math.abs(corrShift), Math.abs(corrShift));
            }
            reverse(arr, 0, arr.length);
            printArray(arr, "...и после 2 сдвига (на " + syntaxForNumber(corrShift, true) + ")");
        } else {
            System.out.println("Метод shiftArray: значение сдвига равно 0.");
        }
    }

    // сгенерировать случайное (в том числе, отрицательное) число в указанном диапазоне
    static int randomNumber(int min, int max, boolean mightBeNegative)
    {
        int n = min + Math.round((float) Math.random() * (max - min));
        if (mightBeNegative && randomNumber(1, 10, false) > 5)
        {
            n *= -1;
        }
        return n;
    }

    public static void main (String[] args) {
        int i;
        // задача 1
        int[] iArr = new int[] { 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 };
        for (i = 0; i < iArr.length; i++) {
            iArr[i] ^= 1;
        }
        printArray(iArr, null);

        // задача 2
        int[] iArr100 = new int[100];
        for (i = 0; i < iArr100.length; i++) {
            iArr100[i] = i+1;
        }
        printArray(iArr100, null);

        // задача 3
        int[] iArr2 = new int[] { 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 };
        for (i = 0; i < iArr2.length; i++)
            if (iArr2[i] < 6) iArr2[i] *= 2;
        printArray(iArr2, null);

        // задача 4
        // пусть двумерный массив будет не менее чем из 2 и не более чем из 15 элементов
        final int minSize = 2, maxSize = 15;
        int size = randomNumber(minSize, maxSize, false), tmp;
        int[][] arr2D = new int[size][size];
        printArray(arr2D, "\nДвумерный массив до преобразования" );
        for (i = 0; i < size; i++ ) {
            arr2D[i][i] = 1;
            arr2D[i][size - i - 1] = 1;
        }
        printArray(arr2D, "...и после");

        // задача 5
        int[] newArr = fillArrayWithValue(size, tmp = randomNumber(0, 1000, true));
        printArray(newArr, "\nСоздан массив размера " + size + ", каждый элемент которого равен " + tmp);

        // задача 6
        printArray(iArr2, "\nВ нижеследующем массиве минимальный элемент -- " + findMin(iArr2) +
                ", максимальный -- " + findMax(iArr2));

        /* задача 7
           примеры массивов с равными суммами:
           1, 2, 2, 1
           2, 1, 1, 2, 2
           1, 2, 3, 4, 10
           5, 1, 1, 2, 3, 6
           0, 0, 0, 0, 0, 0
           1, 1, 1, 0, 0, 0, 3
         */
        checkBalance(new int[] { 1, 1, 1, 0, 1, 1, 3 });

        // задача 8 : циклический сдвиг элементов массива
        shiftArray(new int[] { 1, 2, 1, 7, 0, 0, 9, 11, 17 }, randomNumber(0, 15, true));
    }
}
