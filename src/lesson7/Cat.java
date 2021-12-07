package lesson7;

public class Cat {
    // количество (объем) еды, съедаемое котом за раз
    int appetite;
    // сытый/голодный
    boolean satisfied;
    // кличка
    String name;

    public Cat (String name) {
        this.name = name;
    }

    // аппетит либо есть, либо нет, но никак не отрицательный
    public Cat (int appetite) {
        this.appetite = Math.max(0, appetite);
    }

    public int getAppetite () { return appetite; }
    public boolean getSatisfied () { return satisfied; }

    public void setAppetite (int appetite) { this.appetite = Math.max(0, appetite); }
    public void setSatisfied (boolean satisfied) { this.satisfied = satisfied; }

    // поесть из миски: кот ест, если нет сытости и есть аппетит
    public void eatFrom (Dish dish) {
        if (!getSatisfied() && appetite > 0) {
            setSatisfied(dish.getFilledTo() >= appetite);
            if (dish.getFilledTo() >= appetite)
                dish.setFilledTo(dish.getFilledTo() - appetite);
        }
    }
}
