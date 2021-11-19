/*
    1. Написать метод, принимающий на вход два числа, и проверяющий,
       что их сумма лежит в пределах от 10 до 20 (включительно),
       если да – вернуть true, в противном случае – false;
    2. Написать метод, которому в качестве параметра передается целое число,
       метод должен напечатать в консоль положительное число передали или отрицательное
       (Замечание: ноль считаем положительным числом.);
    3. Написать метод, которому в качестве параметра передается целое число,
       метод должен вернуть true, если число отрицательное, и вернуть false если положительное.
    4. Написать метод, которому в качестве аргументов передается строка и число,
       метод должен отпечатать в консоль указанную строку, указанное количество раз;
    5. *Написать метод, который определяет, является ли год високосным, и возвращает boolean
       (високосный - true, не високосный - false). 
       Каждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.
 */
package lesson2;

import java.util.*;

public class HomeWorkApp
{
    // 1. проверить, что сумма лежит в пределах от 10 до 20 (включительно)
    static boolean isSumFrom10to20 (int a, int b)
    {
        int sum = a + b;
        return sum >= 10 && sum <= 20;
    }

    // 2. напечатать в консоль положительное число передано или отрицательное
    //    ноль считаем положительным числом
    static void testSign (int num)
    {
        System.out.println("число " + num + " - " + (isNegative(num) ? "отрицательное" : "положительное"));
    }

    // 3. проверка на отрицательность
    //    ноль считаем положительным числом
    static boolean isNegative (int num)
    {
        return num < 0;
    }

    // 4. Написать метод, которому в качестве аргументов передается строка и число,
    //    метод должен отпечатать в консоль указанную строку, указанное количество раз
    static void repeatString (String s, int count)
    {
        for (int i = 0; i++ < count; )
        {
            System.out.println(s);
        }
    }

    // 5. определить, является ли год високосным
    static boolean isLeapYear (int year)
    {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static void main (String[] args)
    {
        System.out.println("Сумма чисел " + (isSumFrom10to20(15, -5) ? "" : "не") +
                "лежит в пределах от 10 до 20");
        testSign(-140);
        repeatString("Эта строка выведена в консоль", 3);

        Scanner scanner = new Scanner(System.in);
        int year = 0;
        System.out.println();
        do {
            System.out.print("Пожалуйста, укажите любой год: ");
            try { year = scanner.nextInt(); }
            catch (Exception ex) { scanner.next(); }
        } while (year <= 0);
        System.out.println(year + " год - " + (isLeapYear(year) ? "" : "не ") + "високосный");
        scanner.close();
    }
}
