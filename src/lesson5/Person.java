package lesson5;

// Создать класс "Сотрудник" с полями: ФИО, должность, email, телефон, зарплата, возраст
public class Person {
    String name, position, email, phone;
    int salary, age;

    // Конструктор класса должен заполнять эти поля при создании объекта
    public Person (String name, String position, String email, String phone, int salary, int age) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }

    // Внутри класса «Сотрудник» написать метод, который выводит информацию об объекте в консоль
    public void printInfo () {
        System.out.println("\nФИО: " + name + "\nДолжность: " + position + "\nВозраст: " + age +
                "\ne-mail: " + email + "\nТелефон: " + phone + "\nЗарплата: " + salary);
    }

    public int getAge() { return age; }
}
