import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HairSelector extends JPanel {
    private JPanel colorContainer;
    private Model character;
    private JPanel characterPanel;
    private JLabel hairNameLabel;

    public HairSelector(Model character, JPanel characterPanel) {
        this.character = character;
        this.characterPanel = characterPanel;

        this.setOpaque(false);
        this.setLayout(null);

        JLabel leftHairBtn = createArrow("Buttons/leftBtn.png", 0, 0);
        this.add(leftHairBtn);

        JPanel hairBar = new JPanel() {
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

                // الحواف البنفسجية الغامقة
                g2.setColor(new Color(56, 109, 38));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);

                g2.dispose();
            }
        };

        hairBar.setBounds(38, 0, 110, 30);
        hairBar.setOpaque(false);
        hairBar.setLayout(new BorderLayout());

        hairNameLabel = new JLabel(character.HAIR_NAMES[character.currentHair], SwingConstants.CENTER);
        hairNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        hairNameLabel.setForeground(Color.BLACK);
        hairBar.add(hairNameLabel, BorderLayout.CENTER);
        this.add(hairBar);

        JLabel rightHairBtn = createArrow("Buttons/rightBtn.png", 155, 0);
        this.add(rightHairBtn);

        colorContainer = new JPanel();
        colorContainer.setBounds(0, 40, 190, 30);
        colorContainer.setOpaque(false);
        colorContainer.setLayout(null);
        this.add(colorContainer);

        updateColorButtons();

        rightHairBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (character.currentHair < character.HAIR_NAMES.length - 1) {
                    character.currentHair++;
                } else {
                    character.currentHair = 0;
                }
                character.currentHairColor = 0;
                hairNameLabel.setText(character.HAIR_NAMES[character.currentHair]);
                updateColorButtons();
                characterPanel.repaint();
            }
        });

        leftHairBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (character.currentHair > 0) {
                    character.currentHair--;
                } else {
                    character.currentHair = character.HAIR_NAMES.length - 1;
                }
                character.currentHairColor = 0;
                hairNameLabel.setText(character.HAIR_NAMES[character.currentHair]);
                updateColorButtons();
                characterPanel.repaint();
            }
        });
    }

    public void updateColorButtons() {
        colorContainer.removeAll();

        Color[] hairPalette = {
                new Color(255, 232, 144),
                new Color(220, 108, 44),
                new Color(189, 63, 63),
                new Color(244, 117, 201),
                new Color(171, 85, 221),
                new Color(144, 202, 255)
        };

        int totalColors = hairPalette.length;
        int btnSize = 22;
        int gap = 5;
        int startX = (190 - (totalColors * (btnSize + gap) - gap)) / 2;

        for (int i = 0; i < totalColors; i++) {
            final int index = i;

            JButton colorBtn = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                    g2.dispose();
                }
            };

            colorBtn.setBounds(startX + i * (btnSize + gap), 3, btnSize, btnSize);
            colorBtn.setBackground(hairPalette[i]);
            colorBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            colorBtn.addActionListener(e -> {
                character.currentHairColor = index;
                characterPanel.repaint();
            });
            colorContainer.add(colorBtn);
        }

        colorContainer.revalidate();
        colorContainer.repaint();
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
