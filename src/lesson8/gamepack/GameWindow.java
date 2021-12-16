package lesson8.gamepack;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    static final int WINDOW_WIDTH = 500;
    static final int WINDOW_HEIGHT = 555;

    private final SettingsWindow settingsWindow;
    private final BattleField battleField;

    public GameWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - WINDOW_WIDTH) / 2,
                (screenSize.height - WINDOW_HEIGHT) / 2,
                    WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Win Tic Tac Toe");

        settingsWindow = new SettingsWindow(this);
        battleField = new BattleField(this);
        add(battleField, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(1, 2));
        JButton btnNewGame = new JButton("Начать новую игру");
        JButton btnExit = new JButton("Выход");

        btnExit.setBackground(Color.PINK);
        btnNewGame.setBackground(Color.CYAN);

        btnExit.addActionListener(e -> System.exit(0));
        btnNewGame.addActionListener(actionEvent -> {
            settingsWindow.setPositionRelativeTo(this);
            settingsWindow.setVisible(true);
        });

        panel.add(btnNewGame);
        panel.add(btnExit);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);
    }

    void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Текущая игра",
                JOptionPane.INFORMATION_MESSAGE);
    }

    void startNewGame(int size, int winningLength, boolean useDrawing) {
        battleField.startNewGame(size, winningLength, useDrawing);
    }
}
