import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Optional;

public class NumpadDialog {

    public static Optional<String> show(String title) {
        return show(title, "ID", false);
    }

    public static Optional<String> show(String title, String displayLabel) {
        return show(title, displayLabel, false);
    }

    public static Optional<String> showDecimal(String title, String displayLabel) {
        return show(title, displayLabel, true);
    }

    private static Optional<String> show(String title, String displayLabel, boolean allowDecimal) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JLabel display = new JLabel(displayLabel + ": ", SwingConstants.CENTER);
        display.setFont(new Font("Monospaced", Font.BOLD, 22));
        display.setOpaque(true);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        mainPanel.add(display, BorderLayout.NORTH);

        StringBuilder input = new StringBuilder();
        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 5, 5));

        ActionListener numListener = e -> {
            input.append(e.getActionCommand());
            display.setText(displayLabel + ": " + input);
        };

        for (int i = 1; i <= 9; i++) {
            JButton b = new JButton(String.valueOf(i));
            b.addActionListener(numListener);
            buttonPanel.add(b);
        }

        if (allowDecimal) {
            JButton decimalBtn = new JButton(".");
            decimalBtn.addActionListener(e -> {
                if (input.indexOf(".") == -1) {
                    input.append(".");
                    display.setText(displayLabel + ": " + input);
                }
            });
            buttonPanel.add(decimalBtn);
        }

        JButton clearBtn = new JButton("X");
        clearBtn.setForeground(Color.RED);
        clearBtn.addActionListener(e -> {
            input.setLength(0);
            display.setText(displayLabel + ": ");
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
