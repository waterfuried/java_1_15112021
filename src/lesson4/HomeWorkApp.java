/*
    1. Полностью разобраться с кодом, попробовать переписать с нуля,
       стараясь не подглядывать в методичку.
    2. Переделать проверку победы, чтобы она не была реализована
       просто набором условий, например, с использованием циклов.
    3. * Попробовать переписать логику проверки победы,
         чтобы она работала для поля 5х5 и количества фишек 4.
         Очень желательно не делать это просто набором условий
         для каждой из возможных ситуаций;
    4. *** Доработать искусственный интеллект,
           чтобы он мог блокировать ходы игрока.

     данная версия является модификацией кода, написанного для решения схожей задачи
     в подготовительном курсе -- https://gb.ru/study_groups/17879/homeworks/99496,
     только усиление логики было выполнено не за счет подсчета очков для каждой клетки,
     а расчетом весов по всем возможным линиям хода и поиска наилучшего веса.
     Впоследствии, при тестировании на играх размера больше 3х3, идея осталась та же,
     но алгоритм расчета был модифицирован.
     Сейчас произведена его очередная модификация - под отличие длины выигрышной линии
     от длины стороны игрового поля. Идея с расчетом весов линий хода оставлена в силе,
     но с учетом изменений в правилах игры, она может не являться оптимальной
 */
package lesson4;

import java.util.*;

public class HomeWorkApp {
    final static int GAMESIZE = 5; // размер стороны игрового поля
    final static int WINSIZE = GAMESIZE - 1; // число ячеек в победной линии
    final static char[] GAMESYM = {'-', 'X', '0'}; // используемые символы
    final static char EMPTY = GAMESYM[0];

    static Scanner scanner;
    // признаки хода игрока, ничьей
    static boolean isPlayerTurn, drawGame;
    static int playerSym; // символ, которым ходит игрок
    static char[][] map; // игровое поле
    static int[][] weight; // веса линий ходов для обоих игроков

    // сгенерировать случайное число в указанном диапазоне
    static int randomNumber(int min, int max) {
        return min + Math.round((float)Math.random() * (max - min));
    }

    // вернуть название игрового символа
    static String getCharName (char ch) {
        if (ch == 'X')
            return "крестик";
        else if (ch == '0')
            return "нолик";
        else
            return "";
    }

    // получить символ хода игрока
    static char getPlayerChar () {
        return GAMESYM[playerSym];
    }

    // получить символ хода компьютера
    static char getComputerChar () {
        return GAMESYM[playerSym == 1 ? 2 : 1];
    }

    // получить имя текущего исполнителя хода
    static String getActorName () {
        return isPlayerTurn ? "игрок" : "компьютер";
    }

    // кодировать номер ячейки (x, y) одним числом
    static int encCellNum (int row, int col) {
        return GAMESIZE * row + col;
    }

    // проверить недопустимость координат
    static boolean isInvalidCell (int x, int y) {
        boolean wrong = x < 0 || x >= GAMESIZE || y < 0 || y >= GAMESIZE;
        if (!wrong)
            wrong = map[y][x] != EMPTY;

        return wrong;
    }

    // заменяет 2 первоначально отдельных метода -
    // проверку наличия выигрыша и расчет весов линий -
    // поскольку обход линий поля в них происходил одинаково
    static boolean processAllLines (boolean checkWin) {
        boolean won = false;
        for (int y = 0; !won && y < GAMESIZE; y++)
            for (int x = 0; !won && x < GAMESIZE; x++)
                for (int t = 0; !won && t <= 3; t++) {
                    // нужно ли проверять линию дальше?
                    boolean checking = x + WINSIZE <= GAMESIZE, checkY = y + WINSIZE <= GAMESIZE;
                    if (t == 1) checking = checkY;
                    if (t == 2) checking &= checkY;
                    if (t == 3) checking = x + 1 - WINSIZE >= 0 && checkY;
                    if (checking)
                        if (checkWin)
                            won = passAlong(x, y, t, 1) == 1;
                        else
                            passAlong(x, y, t, 2);
                }
        return won;
    }

    /** выполнить проход по линии
     * метод заменяет 3 первоначальных,
     * имевших разные типы возвращаемого значения
     * @param x     x-координата начала линии
     * @param y     y-координата начала линии
     * @param type  тип линии (0=строка, 1=столбец, 2=диагональ СЗ-ЮВ, 3=обратная диагональ СВ-ЮЗ)
     * @param task  назначение прохода:
     *                  0 = поиск свободной ячейки
     *                  1 = проверка наличия выигрыша
     *                  2 = расчет ее веса
     * @return зависит от назначения прохода:
     *                  0 : индекс первой свободной ячейки
     *                  1 : 1=выигрыш есть, 0=нет
     *                  2 : 0, корректируется массив весов
     */
    static int passAlong(int x, int y, int type, int task) {
        if (task == 0 && map[y][x] == EMPTY) return encCellNum(y, x);
        int dx = 0, dy = 0, d = GAMESIZE - WINSIZE + 1, lineIdx = 0;
        switch (type) {
            case 0: dx = 1; break;
            case 1: dy = 1; break;
            case 2: dx = 1; dy = 1; break;
            case 3: dx = -1; dy = 1;
        }
        if (task == 2) {
            switch (type) {
                case 1: lineIdx = GAMESIZE * d; break;
                case 2: lineIdx = 2 * GAMESIZE * d; break;
                // поскольку в вызывающем методе движение индекса происходит сначала внутри строки (по столбцам),
                // соответственно и обратные диагонали нужно искать, отталкиваясь от индекса столбца -
                // покрытие игрового поля линиями возможных ходов при этом не нарушается
                case 3: lineIdx = d * (2 * GAMESIZE + d);
            }
            if (type == 1)
                lineIdx += x * d + (d > 1 ? y % d : 0);
            else
                lineIdx += y * d + (d > 1 ? (type < 3 ? x : (GAMESIZE - x)) % d : 0);
        }
        int i0 = task == 2 ? 0 : 1, curX = x, curY = y;
        boolean cond = task != 1 || map[y][x] != EMPTY;
        for (int i = i0; cond && i < WINSIZE; i++) {
            curY = y + i * dy;
            curX = x + i * dx;
            switch (task) {
                case 0: cond = curY >= 0 && curX >= 0 && map[curY][curX] != EMPTY; break;
                case 1: cond = curY >= 0 && curX >= 0 && map[curY][curX] == map[y][x]; break;
                case 2: if (curY >= 0 && curX >= 0 && map[curY][curX] != EMPTY) {
                            weight[map[curY][curX] == getPlayerChar() ? 0 : 1][lineIdx]++;
                        }
            }
        }
        switch (task) {
            case 0: return cond ? -1 : encCellNum(curY, curX);
            case 1: return cond ? 1 : 0;
            default: return 0;
        }
    }

    /*
       создать веса линий для обоих игроков
       модификация для длины выигрышной линии
       не равной длине стороны игрового поля
    */
    static void makeWeights () {
        for (int y = 0; y < weight.length; y++)
            for (int x = 0; x < weight[0].length; x++) {
                weight[y][x] = 0;
            }
        processAllLines(false);
    }

    /*
       поиск наибольшего веса линии для игрока/компьютера - модификация под новый алгоритм
       при наличии на ней ходов противника, величиной не превышающем разности
       между размерами стороны поля и длиной выигрышной линии
     */
    static int findMaxWeight (int playerIdx) {
        int n = -1, w = 0;
        for (int i = 0; i < weight[0].length; i++) {
            if (weight[playerIdx][i] > w
                    && weight[playerIdx ^ 1][i] <= GAMESIZE - WINSIZE
                    && weight[playerIdx][i] + weight[playerIdx ^ 1][i] < WINSIZE) {
                n = i;
                w = weight[playerIdx][i];
            }
        }
        return n;
    }

    /*
        поиск свободной ячейки по диагонали (втч обратной), в строке или в столбце
        модификация под отличие длины выигрышной линии от размера игры:
        как следствие - наличие подстрок, подстолбцов и поддиагоналей
        TODO:
            для усиления ИИ отдавать приоритет той ячейке, ход в которую
            увеличивает вес более чем по одной линии (создает "вилку") -
            это было не сложно реализовать, пока длина выигрышной линии
            совпадала с длиной стороны игрового поля
     */
    static int findFreeCell (int weightIdx) {
        int d = GAMESIZE - WINSIZE + 1, type = 0, lineIdx, x, y;
        if (weightIdx >= d * (2 * GAMESIZE + d)) {
            lineIdx = weightIdx - d * (2 * GAMESIZE + d);
            type = 3;
        }
        else if (weightIdx >= 2 * GAMESIZE * d) {
            lineIdx = weightIdx - 2 * GAMESIZE * d;
            type = 2;
        }
        else if (weightIdx >= GAMESIZE * d) {
            lineIdx = weightIdx - GAMESIZE * d;
            type = 1;
        }
        else lineIdx = weightIdx;

        y = lineIdx / d;
        x = lineIdx % d;
        if (type == 1) {
            x = lineIdx / d;
            y = lineIdx % d;
        }
        if (type == 3) x += (WINSIZE - 1);

        return passAlong(x, y, type, 0);
    }

    // поиск лучшего решения для компьютера
    // модифицикация под новый алгоритм (см. следующий метод)
    static int findBestCell () {
        int cell = -1, lp = findMaxWeight(0), lc = findMaxWeight(1);
        if (lp >= 0 && lc >= 0) {
            cell = weight[1][lc] >= weight[0][lp] ? lc : lp;
        } else {
            if (lc >= 0) cell = lc;
            if (lp >= 0) cell = lp;
        }
        if (cell >= 0) cell = findFreeCell(cell);
        return cell;
    }

    /*
       выполнить ход компьютера (алгоритм модифицирован для игр размера более 3х3):
       1. центральная ячейка занята ?
          да:
            сформировать веса линий для игрока и компьютера
            ЛК = линия с максимальным весом для компьютера*
            ЛИ = линия с максимальным весом для игрока*
            ЛК и ЛИ найдены ?
              да:
                вес ЛК больше веса ЛИ ?
                  да: выбрать линию ЛК
                  нет: выбрать линию ЛИ
                  перейти к п.2
              нет:
                ЛК найдена ?
                  да: выбрать линию ЛК
                  нет:
                    ЛИ найдена ?
                      да: выбрать линию ЛИ
                      нет: сформировать случайные координаты
          нет:
            выбрать координаты центральной ячейки
       2. сделать ход по выбранной ранее линии/сформированным координатам
       * примечание:
            при определении максимального веса линии для компьютера/игрока
            рассматривать только те линии, где вес противника не больше
            разности между размером игры и длиной выигрышной линии
    */
    static void makeComputerTurn () {
        int x, y, center = GAMESIZE / 2;
        if (isInvalidCell(center, center)) {
            makeWeights();
            int cellNum = findBestCell();
            if (cellNum >= 0) {
                // новые координаты
                y = cellNum / GAMESIZE;
                x = cellNum % GAMESIZE;
            } else {
                // случайные координаты
                do {
                    x = randomNumber(0, GAMESIZE-1);
                    y = randomNumber(0, GAMESIZE-1);
                } while (isInvalidCell(x, y));
            }
        } else {
            x = y = center; // ход в центр, если возможно
        }
        System.out.println("Компьютер выбрал ячейку " + (y + 1) + " " + (x + 1));
        map[y][x] = getComputerChar();
    }

    // выполнить следующий ход игрока
    static void makePlayerTurn () {
        int x, y;
        do
        {
            System.out.println("Введите координаты ячейки (строка столбец)");
            try {
                y = scanner.nextInt() - 1; // Считывание номера строки
                x = scanner.nextInt() - 1; // Считывание номера столбца
            } catch (Exception ex) { scanner.next(); x = y = -1; }
        } while (isInvalidCell(x, y));
        map[y][x] = getPlayerChar();
    }

    // выполнить следующий ход игры
    static void makeNextTurn () {
        if (isPlayerTurn)
            makePlayerTurn();
        else
            makeComputerTurn();
        printMap();
    }

    // проверка наличия выигрыша
    static boolean isGameWon () {
        return processAllLines(true);
    }

    // проверить игру на ничью - нет пустых клеток
    static boolean isGameDraw() {
        boolean draw = true;
        for (int y = 0; draw && y < GAMESIZE; y++)
            for (int x = 0; draw && x < GAMESIZE; x++) {
                draw = map[y][x] != EMPTY;
            }
        return draw;
    }

    // инициализировать игровое поле
    static void initMap () {
        map = new char[GAMESIZE][GAMESIZE];
        int wsz = GAMESIZE - WINSIZE + 1;
        weight = new int[2][2 * wsz * (GAMESIZE + wsz)];
        for (int y = 0; y < GAMESIZE; y++)
            for (int x = 0; x < GAMESIZE; x++) {
                map[y][x] = EMPTY;
            }
    }

    // отобразить игровое поле
    static void printMap () {
        int i;
        System.out.print(" ");
        for (i = 1; i <= GAMESIZE; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (i = 0; i < GAMESIZE; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < GAMESIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // начать игру
    static void initGame () {
        isPlayerTurn = randomNumber(1, 10) > 5;
        playerSym = randomNumber(1, 2);
        System.out.println("Первый ход за " + getActorName() + "ом. " +
                (isPlayerTurn ? "Вы ходите" : "Он ходит") + " " +
                getCharName(isPlayerTurn ? getPlayerChar() : getComputerChar()) + "ами.");
        if (isPlayerTurn) {
            printMap();
        }
        scanner = new Scanner(System.in);
    }

    // вывести сообщение о завершении игры
    static void printGameFinished () {
        System.out.println("Игра закончена " +
                (drawGame ? "вничью" : "выигрышем " + getActorName() + "а") + ".");
    }

    public static void main (String[] args) {
        initMap();
        initGame();

        boolean gameOver;
        do {
            makeNextTurn();
            gameOver = isGameWon();
            if (!gameOver) {
                isPlayerTurn = !isPlayerTurn;
                gameOver = drawGame = isGameDraw();
            }
        } while (!gameOver);

        printGameFinished();
    }
}
