package lesson8.gamepack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsWindow extends JDialog {
    static final int WINDOW_WIDTH = 400;
    static final int WINDOW_HEIGHT = 300;

    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;

    private static boolean useDrawing = true;
    private static int playerChar;

    public SettingsWindow(GameWindow gameWindow) {
        setPositionRelativeTo(gameWindow);
        setTitle("Параметры игры");

        setLayout(new GridLayout(7, 1));

        add(new Label("Размер игры:"));
        JSlider slFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        slFieldSize.setMajorTickSpacing(1);
        slFieldSize.setPaintLabels(true);
        slFieldSize.setPaintTicks(true);
        add(slFieldSize);

        add(new Label("Длина победной линии:"));
        JSlider slWinningLength = new JSlider(MIN_FIELD_SIZE, MIN_FIELD_SIZE, MIN_FIELD_SIZE);
        slWinningLength.setMajorTickSpacing(1);
        slWinningLength.setPaintLabels(true);
        slWinningLength.setPaintTicks(true);
        slWinningLength.setEnabled(false);
        slFieldSize.addChangeListener(changeEvent -> {
            slWinningLength.setMaximum(slFieldSize.getValue());
            slWinningLength.setEnabled(slWinningLength.getMaximum() > MIN_FIELD_SIZE);
        });
        add(slWinningLength);

        JPanel panelPlayerChar = new JPanel();
        panelPlayerChar.add(new JLabel("Символ игрока:"));
        JComboBox<String> jComboBox = new JComboBox<>(new String[] {"X", "0", "случайный"});
        jComboBox.addActionListener(actionEvent -> playerChar = jComboBox.getSelectedIndex());
        panelPlayerChar.add(jComboBox);
        add(panelPlayerChar);

        JPanel panelDisplay = new JPanel();
        panelDisplay.add(new JLabel("Метод отображения:"));
        JRadioButton rbtnDrawing = new JRadioButton("рисованный");
        JRadioButton rbtnButtoned = new JRadioButton("кнопочный");
        ActionListener al = actionEvent -> useDrawing = rbtnDrawing.isSelected();
        rbtnDrawing.setSelected(true);
        rbtnDrawing.addActionListener(al);
        rbtnButtoned.addActionListener(al);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbtnDrawing);
        bg.add(rbtnButtoned);
        panelDisplay.add(rbtnDrawing);
        panelDisplay.add(rbtnButtoned);
        add(panelDisplay);

        JButton btnNewGame = new JButton("Начать игру");

        btnNewGame.addActionListener(actionEvent -> {
            int size = slFieldSize.getValue();
            int winningLength = slWinningLength.getValue();

            Game.setGameParams(size, winningLength);
            Game.initGame(playerChar, useDrawing, gameWindow);

            gameWindow.startNewGame(size, winningLength, useDrawing);
            setVisible(false);
        });

        add(btnNewGame);

        setVisible(false);
    }

    final void setPositionRelativeTo (GameWindow owner) {
        Rectangle r = owner.getBounds();
        setBounds(r.x + (r.width - this.getWidth()) / 2,
                r.y + (r.height - this.getHeight()) / 2,
                WINDOW_WIDTH, WINDOW_HEIGHT);
    }
}
