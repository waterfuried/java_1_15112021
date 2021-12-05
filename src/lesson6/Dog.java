package lesson6;

public class Dog extends Animal
{
    static int totalNumber = 0;

    void setLimits() {
        maxRunDistance = 500;
        maxSwimDistance = 10;
    }

    public Dog () { super(); setLimits(); totalNumber++; }

    public Dog (boolean male) { super(male); setLimits(); totalNumber++; }

    public Dog (String name) { super(name); setLimits(); totalNumber++; }

    // Отрицательные значения парметров означают значения по умолчанию.
    // о другом варианте решения для этого конструктора см. класс Cat
    public Dog (int maxRunDistance, int maxSwimDistance) {
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
        this.maxRunDistance = maxRunDistance < 0 ? 500 : maxRunDistance;
    }

    public void setMaxSwimDistance (int maxSwimDistance) {
        this.maxSwimDistance = maxSwimDistance < 0 ? 10 : maxSwimDistance;
    }

    public static int getCount() { return totalNumber; }

    public void printGender() {
        if (name == null || name.length() == 0) {
            System.out.print("Безымянн" + (male ? "ый пес" : "ая собака") + " ");
        }
    }
}
