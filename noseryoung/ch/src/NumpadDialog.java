import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Optional;

public class NumpadDialog {

    public static Optional<String> show(String title) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JLabel display = new JLabel("ID: ", SwingConstants.CENTER);
        display.setFont(new Font("Monospaced", Font.BOLD, 22));
        display.setOpaque(true);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        mainPanel.add(display, BorderLayout.NORTH);

        StringBuilder input = new StringBuilder();
        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 5, 5));

        ActionListener numListener = e -> {
            input.append(e.getActionCommand());
            display.setText("ID: " + input.toString());
        };

        for (int i = 1; i <= 9; i++) {
            JButton b = new JButton(String.valueOf(i));
            b.addActionListener(numListener);
            buttonPanel.add(b);
        }

        JButton clearBtn = new JButton("X");
        clearBtn.setForeground(Color.RED);
        clearBtn.addActionListener(e -> {
            input.setLength(0);
            display.setText("ID: ");
        });
        buttonPanel.add(clearBtn);

        JButton zeroBtn = new JButton("0");
        zeroBtn.addActionListener(numListener);
        buttonPanel.add(zeroBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                null,
                mainPanel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION && input.length() > 0) {
            return Optional.of(input.toString());
        }

        return Optional.empty();
    }
}