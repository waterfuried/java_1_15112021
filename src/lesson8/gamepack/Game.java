package lesson8.gamepack;

public class Game {
    private static GameWindow gameWindow;
    private static int GAMESIZE;
    private static int WINSIZE;

    // используемые символы
    static final char DOT_X = 'X';
    static final char DOT_0 = '0';
    static final char EMPTY = ' ';
    final static char[] GAMESYM = { EMPTY, DOT_X, DOT_0 };

    static int playerSym;
    static boolean isPlayerTurn;    // признак хода игрока
    static boolean useButtons;      // признак "кнопочного" интерфейса

    static char[][] map;
    static int[][] weight;          // веса линий ходов для обоих игроков
    static Button[] cell;           // массив кнопок

    static void initGame(int playerChar, boolean useDrawing, GameWindow gameWin) {
        gameWindow = gameWin;
        map = new char[GAMESIZE][GAMESIZE];
        int wsz = GAMESIZE - WINSIZE + 1;
        weight = new int[2][2 * wsz * (GAMESIZE + wsz)];
        for (int y = 0; y < GAMESIZE; y++)
            for (int x = 0; x < GAMESIZE; x++)
            {
                map[y][x] = EMPTY;
            }
        useButtons = !useDrawing;
        if (useButtons) {
            // создать массив кнопок
            cell = new Button[GAMESIZE * GAMESIZE];
            for (int i = 0; i < cell.length; i++) {
                cell[i] = new Button(gameWindow, i);
            }
        }
        isPlayerTurn = true;
        if (playerChar < 2)
            playerSym = playerChar + 1;
        else {
            //определить, кто будет ходить первым, и вывести об этом сообщение
            isPlayerTurn = randomNumber(1, 10) > 5;
            playerSym = randomNumber(1, 2);
            gameWindow.showMessage("Первый ход за " + getActorName() + "ом.\n" +
                    (isPlayerTurn ? "Вы ходите" : "Он ходит")  + " " +
                    getCharName(getCurrentPlayerChar()) + "ами.");
        }
        if (!isPlayerTurn) makeComputerTurn();
    }

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

    // получить символ хода текущего игрока
    static char getCurrentPlayerChar () {
        return GAMESYM[isPlayerTurn ? playerSym : ((playerSym - 1) ^ 1) + 1];
    }

    static String getPlayerSymbol () { return GAMESYM[playerSym] + ""; }

    static char getMapAt (int x, int y) { return map[y][x]; }
    static void setMapAt (int x, int y, char c) { map[y][x] = c; }

    static int getSize() { return GAMESIZE; }

    static Button getButton (int idx) { return cell[idx]; }

    static void setGameParams (int size, int winningLength) {
        GAMESIZE = size;
        WINSIZE = winningLength;
    }

    // получить имя текущего исполнителя хода
    static String getActorName () {
        return isPlayerTurn ? "игрок" : "компьютер";
    }

    // проверить недопустимость координат
    static boolean isInvalidCell (int x, int y) {
        boolean wrong = x < 0 || x >= GAMESIZE || y < 0 || y >= GAMESIZE;
        if (!wrong) wrong = map[y][x] != EMPTY;

        return wrong;
    }

    // передать ход
    static void changePlayer() { isPlayerTurn = !isPlayerTurn; }

    // кодировать номер ячейки (x, y) одним числом
    static int encCellNum (int row, int col) {
        return GAMESIZE * row + col;
    }

    // проверка наличия выигрыша
    static boolean isGameWon () {
        return processAllLines(true) != null;
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

    // проверить завершение игры
    static int checkGameOver () {
        if (isGameWon())
            return 1;
        else
            return isGameDraw() ? -1 : 0;
    }

    // обход линий поля,
    // используется при проверке наличия выигрыша и расчете весов линий,
    // поскольку в них он происходит одинаково
    static WinningLine processAllLines (boolean checkWin) {
        boolean won = false;
        WinningLine wl = null;
        for (int y = 0; !won && y < GAMESIZE; y++)
            for (int x = 0; !won && x < GAMESIZE; x++)
                for (int t = 0; !won && t <= 3; t++)
                {
                    // нужно ли проверять линию дальше?
                    boolean checking = x + WINSIZE <= GAMESIZE, checkY = y + WINSIZE <= GAMESIZE;
                    if (t == 1) checking = checkY;
                    if (t == 2) checking &= checkY;
                    if (t == 3) checking = x + 1 - WINSIZE >= 0 && checkY;
                    if (checking)
                        if (checkWin) {
                            won = passAlong(x, y, t, 1) == 1;
                            if (won) wl = new WinningLine(x, y, t);
                        } else
                            passAlong(x, y, t, 2);
                }
        return wl;
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

    /** выполнить проход по линии
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
            else // исправлена ошибка определения индекса обратной диагонали
                lineIdx += y * d + (d > 1 ? (type < 3 ? x : (GAMESIZE - 1 - x)) % d : 0);
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
            совпадала с длиной стороны игрового поля.
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
        // исправлено определение x-координаты обратной диагонали
        if (type == 3) x = GAMESIZE - 1 - x;

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
        char c = getCurrentPlayerChar();
        setMapAt(x, y, c);
        if (useButtons) getButton(encCellNum(y,x)).setText(c + "");
        int gameState = checkGameOver();
        if (gameState == 0)
            changePlayer();
        else
            gameWindow.showMessage(gameState == 1 ? "Победил компьютер." : "Игра закончена вничью.");
    }

    static boolean makeHumanTurn (int x, int y) {
        // не обрабатывать щелчки после завершения игры
        if (checkGameOver() == 0) {
            if (getMapAt(x, y) == EMPTY) {
                setMapAt(x, y, getCurrentPlayerChar());
                return true;
            }
        }
        return false;
    }

}
