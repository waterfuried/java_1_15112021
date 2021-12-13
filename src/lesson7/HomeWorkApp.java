package lesson7;
/*
  1. Расширить задачу про котов и тарелки с едой.
  2. Сделать так, чтобы в тарелке с едой не могло получиться отрицательного количества еды
     (например, в миске 10 еды, а кот пытается покушать 15-20).
  3. Каждому коту нужно добавить поле сытость (когда создаем котов, они голодны).
     Если коту удалось покушать (хватило еды), сытость = true.
  4. Считаем, что если коту мало еды в тарелке, то он её просто не трогает,
     то есть не может быть наполовину сыт (это сделано для упрощения логики программы).
  5. Создать массив котов и тарелку с едой, попросить всех котов покушать из этой тарелки
     и потом вывести информацию о сытости котов в консоль.
  6. Добавить в тарелку метод, с помощью которого можно было бы добавлять еду в тарелку.
*/
public class HomeWorkApp {
    // критерий необходимости дополнять миску едой, выраженный в ее единицах -
    // предельное значение сытости любого кота
    static final int maxAppetite = 40;
    // предельное число котов
    static final int maxHorde = 13;

    // техника автоматического добавления еды
    static boolean autofilling = true;

    // сгенерировать случайное число в указанном диапазоне
    static int randomNumber (int min, int max)
    {
        return min + Math.round((float) Math.random() * (max - min));
    }

    public static void main (String[] args) {
        // одна миска на всех
        Dish dish = new Dish (100, 100);
        // коты
        Cat[] horde = new Cat[randomNumber(1, maxHorde)];

        System.out.println("К миске со " + dish.getFilledTo() + " ед. еды " +
                (horde.length == 1 ? "подошел кот" : "подходили коты, их было " + horde.length) + ".");

        if (horde.length > 1)
            System.out.println("Аппетиты котов:");
        else
            System.out.print("Аппетит кота");
        int i;
        for (i = 0; i < horde.length; i++) {
            horde[i] = new Cat(randomNumber(0, maxAppetite));
            System.out.println((horde.length > 1 ? (i + 1) + "-го" : "") + " - " + horde[i].getAppetite());
        }

        System.out.println();
        // отобразить результат кормления;
        // для отключения автозаполнения миски едой
        // изменить значение autofilling выше
        for (i = 0; i < horde.length; i++) {
            horde[i].eatFrom(dish);
            System.out.print((horde.length > 1 ? (i + 1) + "-й " : "") + "кот " +
                    (horde[i].getSatisfied() ? "поел и насытился" : "не ел и остался голодный"));
            int foodLeft = dish.getFilledTo();
            if (foodLeft == 0)
                System.out.print(". Внимание! Еда в миске закончилась");
            boolean needDot = true;
            while (foodLeft < maxAppetite && foodLeft < dish.getVolume() && autofilling) {
                // не переполняя миску докладывать случайные порции еды,
                // если ее осталось мало или она закончилась
                int n = randomNumber(1, maxAppetite);
                dish.setFilledTo(foodLeft + n);
                if (foodLeft + n > dish.getVolume()) n = dish.getFilledTo() - foodLeft;
                foodLeft = dish.getFilledTo();
                if (n > 0) System.out.print((needDot ? "." : "") +
                        " В миску положена порция еды: " + n + " ед.");
                needDot = false;
            }
            System.out.println(" (в миске осталось " + dish.getFilledTo() + " ед. еды).");
        }
    }
}
