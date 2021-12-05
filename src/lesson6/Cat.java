package lesson6;

public class Cat extends Animal
{
    static int totalNumber = 0;

    void setLimits() {
        maxRunDistance = 200;
        maxSwimDistance = 0;
    }

    public Cat () { super(); setLimits(); totalNumber++; }

    public Cat (String name) { super(name); setLimits(); totalNumber++; }

    public Cat (boolean male) { super(male); setLimits(); totalNumber++; }

    /*
      Отрицательные значения парметров означают значения по умолчанию.

      До замены в родительском конструкторе переданных значений на их модуль
      он инициализировал соответствующее поле нулем.
      При такой инициализации объект не получит значений по умолчанию для класса,
      но данный конструктор этого и не предполагал - только переданными значениями
      с их возможной коррекцией по здравому смыслу.
      Другой вариант решения:
         в классе объявить константы
             final static int maxDogRun = 500;
             final static int maxDogSwim = 10;
         в методе setLimits:
             maxRunDistance = maxDogRun;
             maxSwimDistance = maxDogSwim;
         в конструкторе:
             // вызову super не может предшествовать ничего
             super(
                 maxRunDistance >= 0 ? maxRunDistance : maxDogRun,
                 maxSwimDistance >= 0 ? maxSwimDistance : maxDogSwim);
     */
    public Cat (int maxRunDistance, int maxSwimDistance) {
        super();
        setLimits();
        if (maxRunDistance >= 0) this.maxRunDistance = maxRunDistance;
        if (maxSwimDistance >= 0) this.maxSwimDistance = maxSwimDistance;
        totalNumber++;
    }

    /*
      переопределение родительских методов:
      здесь отрицательные значения рассматриваются
      не как опечатка, а как значения по умолчанию
    */
    public void setMaxRunDistance (int maxRunDistance) {
        this.maxRunDistance = maxRunDistance < 0 ? 200 : maxRunDistance;
    }

    public void setMaxSwimDistance (int maxSwimDistance) {
        this.maxSwimDistance = maxSwimDistance < 0 ? 0 : maxSwimDistance;
    }

    public static int getCount() { return totalNumber; }

    public void printGender() {
        if (name == null || name.length() == 0) {
            System.out.print("Безымянн" + (male ? "ый кот" : "ая кошка") + " ");
        }
    }
}
