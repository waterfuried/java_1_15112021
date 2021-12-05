package lesson6;

public abstract class Animal {
    /*
       в дочерних классах поле с таким же именем допустимо:
       его изменение поменяет его значение в своем классе,
       изменение же поля родителя - через указание его класса
     */
    static int totalNumber = 0;

    int maxRunDistance, maxSwimDistance;
    boolean male = true;
    String name;

    public Animal () { totalNumber++; }

     /*
       Поскольку значения параметров по смыслу не могут быть отрицательными,
       их наличие рассматривается как опечатка и заменяется на абсолютную величину.
       Поскольку в дочерних классах отрицательные значения рассматриваются иначе,
       эти конструкторы фактически не используются.
      */
    public Animal (int maxRunDistance, int maxSwimDistance) {
        this.maxRunDistance = Math.abs(maxRunDistance);
        this.maxSwimDistance = Math.abs(maxSwimDistance);
        totalNumber++;
    }

    public Animal (int maxRunDistance, int maxSwimDistance, String name) {
        this.maxRunDistance = Math.abs(maxRunDistance);
        this.maxSwimDistance = Math.abs(maxSwimDistance);
        this.name = name;
        totalNumber++;
    }

    public Animal (String name) {
        this.name = name;
        totalNumber++;
    }

    public Animal (boolean male) {
        this.male = male;
        totalNumber++;
    }

    // в этих двух методах отрицательные значения рассматриваются как опечатка,
    // в дочерних классах переопределяются
    public void setMaxRunDistance (int maxRunDistance) {
        this.maxRunDistance = Math.abs(maxRunDistance);
    }

    public void setMaxSwimDistance (int maxSwimDistance) {
        this.maxSwimDistance = Math.abs(maxSwimDistance);
    }

    public void setName (String name) {
        this.name = name;
    }

    public void run (int obstLength) {
        printNameAndAction("пробежал", maxRunDistance >= obstLength, obstLength);
    }

    public void swim (int obstLength) {
        printNameAndAction("проплыл", maxSwimDistance >= obstLength, obstLength);
    }

    void printName() {
        if (name != null && name.length() != 0) {
            System.out.print(name + " ");
        }
    }

    void printNameAndAction (String action, boolean done, int obstLength) {
        printName();
        System.out.println((done ? "" : "не ") + action + (male ? "" : "а") + " " + obstLength + "м");
    }

    public void printInfo () {
        printName();
        printGender();
        System.out.println(
           (maxRunDistance == 0 ? "не может бегать" : "может пробежать " + maxRunDistance + "м") + ", " +
           (maxSwimDistance == 0 ? "не может плавать" : "может проплыть " + maxSwimDistance + "м"));
    }

    // в дочерних классах геттеры с тем же именем не переопределяют, а скрывают этот геттер
    public static int getCount() { return totalNumber; }

    // с объявлением этого метода абстрактным (и использованием его здесь)
    // потребовалось объявить абстрактным и класс,
    // но определять одноименные методы (выполняющие схожие операции)
    // в дочерних классах можно и без этого
    public abstract void printGender();
}
