/*
1. Создать классы Собака и Кот с наследованием от класса Животное.
2. Все животные могут бежать и плыть. В качестве параметра каждому методу передается длина препятствия.
   Результатом выполнения действия будет печать в консоль.
   (Например, dogBobik.run(150); -> 'Бобик пробежал 150 м.');
3. У каждого животного есть ограничения на действия
   (бег: кот 200 м., собака 500 м.; плавание: кот не умеет плавать, собака 10 м.).
4. * Добавить подсчет созданных котов, собак и животных.
*/
package lesson6;

public class HomeWorkApp {
    public static void main (String[] args) {
        Cat cat1 = new Cat();
        Cat cat2 = new Cat(300,3);
        cat2.setName("Мурзик");
        Animal cat3 = new Cat ("Васька");
        cat3.printInfo();
        cat3.setMaxRunDistance(500);
        cat3.printInfo();
        Cat cat4 = new Cat(false);
        cat4.setName("Анфиса");

        Dog dog1 = new Dog(false);
        dog1.setName("Найда");
        Dog dog2 = new Dog(1,-5);
        dog2.printInfo();
        dog2.setMaxRunDistance(-1);
        dog2.printInfo();
        Dog dog3 = new Dog("Шарик");
        dog3.setMaxSwimDistance(20);

        cat1.printGender();
        cat1.run(250);
        cat2.swim(1);
        cat3.run(300);
        cat2.swim(4);
        cat4.swim(5);
        cat4.run(200);

        dog1.run(500);
        dog2.printGender();
        dog2.run(450);
        dog2.printGender();
        dog2.swim(5);
        dog3.swim(3);
        dog3.run(550);

        System.out.println(
                "\nЧисло котов: " + Cat.getCount() +
                "\nЧисло собак: " + Dog.getCount() +
                "\nЧисло животных: " + Animal.getCount());
    }
}