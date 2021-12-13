package lesson7;
// класс миски
public class Dish
{
    // объем миски (в у.е., минимальный - 1)
    int volume;
    // заполнение миски едой (в у.е.)
    int filledTo;

    public Dish (int volume, int filledTo) {
        this.volume = Math.max(1, volume);
        this.filledTo = filledTo > this.volume ? this.volume : Math.max(0, filledTo);
    }

    public int getFilledTo () { return filledTo; }
    public int getVolume () { return volume; }

    public void setFilledTo (int filledTo)  {
        this.filledTo = filledTo > volume ? volume : Math.max(0, filledTo);
    }
}
