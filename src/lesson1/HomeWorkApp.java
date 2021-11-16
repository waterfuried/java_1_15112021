/*
	1. Создать пустой проект в IntelliJ IDEA, создать класс HomeWorkApp,
	и прописать в нем метод main().
	2. Создайте метод printThreeWords(), который при вызове
	должен отпечатать в столбец три слова: Orange, Banana, Apple.
	Orange
	Banana
	Apple
	3. Создайте метод checkSumSign(), в теле которого объявите две int переменные a и b,
	и инициализируйте их любыми значениями,	которыми захотите.
	Далее метод должен просуммировать эти переменные, и если их сумма больше или равна 0,
	то вывести в консоль сообщение “Сумма положительная”,
	в противном случае - “Сумма отрицательная”;
	4. Создайте метод printColor() в теле которого задайте int переменную value и
	инициализируйте ее любым значением.
	Если value меньше 0 (0 включительно),
	то в консоль метод должен вывести сообщение “Красный”,
	если лежит в пределах от 0 (0 исключительно) до 100 (100 включительно), то “Желтый”,
	если больше 100 (100 исключительно) - “Зеленый”;
	5. Создайте метод compareNumbers(), в теле которого объявите две int переменные a и b,
	и инициализируйте их любыми значениями, которыми захотите.
	Если a больше или равно b, то необходимо вывести в консоль сообщение “a >= b”,
	в противном случае “a < b”;

	Методы из пунктов 2-5 вызовите из метода main() и посмотрите корректно ли они работают.
*/
package lesson1;

public class HomeWorkApp
{
    static void printThreeWords()
    {
       for (String s : new String[] { "Orange", "Banana", "Apple" })
       {
           System.out.println(s);
       }
    }

    static void checkSumSign(int a, int b)
    {
        System.out.println("Сумма " + (a + b < 0 ? "отрицательная" : "положительная"));
    }

    static void printColor()
    {
        int value = (int)Math.round(Math.random() * 200);
        if (Math.random() * 100 < 50) value *= -1;
        System.out.print(value + " - ");
        if (value <= 0)
          System.out.println("Красный");
        else if (value <= 100)
          System.out.println("Желтый");
        else
          System.out.println("Зеленый");
    }

    static void compareNumbers(int a, int b)
    {
        System.out.println(a < b ? "a < b" : "a >= b");
    }

    public static void main(String[] args) {
	    printThreeWords();
	    checkSumSign(-5, 3);
	    printColor();
	    compareNumbers(-7, -2);
    }
}
