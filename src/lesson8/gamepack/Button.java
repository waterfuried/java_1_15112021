package lesson8.gamepack;

import javax.swing.*;

public class Button extends JButton {
    static final int size = 100;    // размер кнопки

    public Button (GameWindow gameWindow, int index) {
        setSize(size - 5, size - 5);
        addActionListener(actEvent -> {
            int x = index % Game.getSize(), y = index / Game.getSize();
            if (Game.makeHumanTurn(x, y)) {
                this.setText(Game.getPlayerSymbol());
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
        });
    }

}
