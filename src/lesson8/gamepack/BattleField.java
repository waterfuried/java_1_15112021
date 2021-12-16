package lesson8.gamepack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleField extends JPanel {
    final GameWindow gameWindow;
    private int size;
    private int winningLength;

    private boolean isInit;
    private boolean useDrawing = true;

    private int cellWidth;
    private int cellHeight;

    public BattleField (GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    void clearAll() {
        removeAll();
        revalidate();
    }

    void startNewGame(int size, int winningLength, boolean useDrawing) {
        this.size = size;
        this.winningLength = winningLength;
        this.useDrawing = useDrawing;

        isInit = true;
        clearAll();
        if (useDrawing) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    int cellX = e.getX() / cellWidth;
                    int cellY = e.getY() / cellHeight;
                    if (Game.makeHumanTurn(cellX, cellY)) {
                        int gameState = Game.checkGameOver();
                        if (gameState == 0) {
                            Game.changePlayer();
                            if (!Game.isPlayerTurn) Game.makeComputerTurn();
                        } else {
                            gameWindow.showMessage(
                                    gameState == 1
                                            ? "Вы победили!"
                                            : "Игра закончена вничью.");
                        }
                    }
                }
            });
        } else {
            setLayout(new GridLayout(size, size));
            for (int i = 0; i < size * size; i++)
                add(Game.getButton(i));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!isInit) {
            return;
        }

        if (useDrawing) {
            cellWidth = getWidth() / size;
            cellHeight = getHeight() / size;

            g.setColor(Color.DARK_GRAY);
            ((Graphics2D)g).setStroke(new BasicStroke(2f));

            for (int i = 1; i < size; i++) {
                int y = i * cellHeight;
                g.drawLine(0, y, getWidth(), y);
            }

            for (int i = 1; i < size; i++) {
                int x = i * cellWidth;
                g.drawLine(x, 0, x, getHeight());
            }

            for (int i = 0; i < Game.getSize(); i++) {
                for (int j = 0; j < Game.getSize(); j++) {
                    switch (Game.map[j][i]) {
                        case Game.DOT_X: drawX(g, i, j); break;
                        case Game.DOT_0: draw0(g, i, j);
                    }
                }
            }
            drawWinningLine(g);
        }
        repaint();
    }

    private void drawX (Graphics g, int cellX, int cellY) {
        g.setColor(Color.BLUE);
        ((Graphics2D)g).setStroke(new BasicStroke(2f));

        int oppX = (cellX + 1) * cellWidth - 1, oppY = (cellY + 1) * cellHeight - 1;
        g.drawLine(cellX * cellWidth, cellY * cellHeight, oppX, oppY);
        g.drawLine(oppX, cellY * cellHeight, cellX * cellWidth, oppY);
    }

    private void draw0 (Graphics g, int cellX, int cellY) {
        g.setColor(Color.RED);
        ((Graphics2D)g).setStroke(new BasicStroke(2f));

        g.drawOval(cellX * cellWidth, cellY * cellHeight, cellWidth - 1, cellHeight - 1);
    }

    private void drawWinningLine (Graphics g) {
        int winner = -1;
        WinningLine wl = Game.processAllLines(true);
        if (wl != null) {
            winner = Game.getMapAt(wl.x, wl.y) == Game.DOT_X ? 0 : 1;
        }

        if (winner >= 0) {
            int x = wl.x * cellWidth + (cellWidth / 2);
            int y = wl.y * cellHeight + (cellHeight / 2);
            int endX = x;
            int endY = y + (wl.type == 0 ? 0 : cellHeight * (winningLength - 1));
            int delta = 0;
            if (wl.type != 1) delta = wl.type == 3 ? -1 : 1;
            endX += delta * cellWidth * (winningLength - 1);

            g.setColor(winner == 0 ? Color.BLUE : Color.RED);
            ((Graphics2D)g).setStroke(new BasicStroke(8f));
            g.drawLine(x, y, endX, endY);
        }
    }
}