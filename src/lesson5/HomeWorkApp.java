/*
	1. Создать класс "Сотрудник" с полями: ФИО, должность, email, телефон, зарплата, возраст.
	2. Конструктор класса должен заполнять эти поля при создании объекта.
	3. Внутри класса «Сотрудник» написать метод, который выводит информацию об объекте в консоль.
	4. Создать массив из 5 сотрудников.
	Пример:
	Person[] persArray = new Person[5]; // Вначале объявляем массив объектов
	persArray[0] = new Person("Ivanov Ivan", "Engineer", "ivivan@mailbox.com", "892312312", 30000, 30);
	// потом для каждой ячейки массива задаем объект
	persArray[1] = new Person(...);
	...
	persArray[4] = new Person(...);

	5. С помощью цикла вывести информацию только о сотрудниках старше 40 лет.
*/
package lesson5;

public class HomeWorkApp {
    public static void main (String[] args) {
        Person[] persons = new Person[] {
            new Person("Ivanov Ivan", "Engineer", "ivivan@mailbox.com ", "892312312", 30000, 30),
            new Person("Petrov Petr", "Technic", "ppetro@mailbox.com ", "899927755", 20000, 20),
            new Person("Karasyova Olga", "HR Manager", "karasyova@company.ru ", "880043210", 40000, 33),
            new Person("Budnicky Igor", "Team Lead", "budnicky@company.ru ", "880056789", 70000, 45),
            new Person("Novikova Elena", "Methodist", "ENovikova@company.ru ", "880011111", 60000, 41)
        };

        System.out.println("Сотрудники старше 40 лет:");
        for (Person p : persons) {
            if (p.getAge() > 40) p.printInfo();
        }
    }
}