import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SkinSelector extends JPanel {
    private Model character;
    private JPanel characterPanel;
    private JLabel skinLabel;

    public SkinSelector(Model character, JPanel characterPanel) {
        this.character = character;
        this.characterPanel = characterPanel;
        this.setOpaque(false);
        this.setLayout(null);

        JLabel leftSkinBtn = createArrow("Buttons/leftBtn.png", 0, 0);
        this.add(leftSkinBtn);

        JPanel skinBar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                // اللون الداخلي
                g2.setColor(new Color(255, 255, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

                // الحواف الخضراء الغامقة
                g2.setColor(new Color(56, 109, 38));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);

                g2.dispose();
            }
        };

        skinBar.setBounds(38, 0, 110, 30);
        skinBar.setOpaque(false);
        skinBar.setLayout(new BorderLayout());

        skinLabel = new JLabel(
                character.SKIN_NAMES[character.currentSkin],
                SwingConstants.CENTER
        );
        skinLabel.setFont(new Font("Arial", Font.BOLD, 14));
        skinLabel.setForeground(Color.BLACK);

        skinBar.add(skinLabel, BorderLayout.CENTER);
        this.add(skinBar);

        JLabel rightSkinBtn = createArrow("Buttons/rightBtn.png", 155, 0);
        this.add(rightSkinBtn);

        rightSkinBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (character.currentSkin < character.SKINS.length - 1) {
                    character.currentSkin++;
                } else {
                    character.currentSkin = 0;
                }
                skinLabel.setText(character.SKIN_NAMES[character.currentSkin]);
                characterPanel.repaint();
            }
        });

        leftSkinBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (character.currentSkin > 0) {
                    character.currentSkin--;
                } else {
                    character.currentSkin = character.SKINS.length - 1;
                }
                skinLabel.setText(character.SKIN_NAMES[character.currentSkin]);
                characterPanel.repaint();
            }
        });
    }

    @Override
    public void repaint() {
        super.repaint();
        if (skinLabel != null && character != null) {
            skinLabel.setText(character.SKIN_NAMES[character.currentSkin]);
        }
    }

    private JLabel createArrow(String imagePath, int x, int y) {
        java.net.URL imgURL = getClass().getResource("/" + imagePath);
        ImageIcon rawIcon = (imgURL != null) ? new ImageIcon(imgURL) : new ImageIcon(imagePath);

        Image scaledImg = rawIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel arrow = new JLabel(new ImageIcon(scaledImg));
        arrow.setBounds(x, y, 30, 31);
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return arrow;
    }
}
