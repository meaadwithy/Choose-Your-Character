import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OutfitSelector extends JPanel {
    private JPanel colorContainer;
    private Model character;
    private JPanel characterPanel;
    private JLabel outfitNameLabel;

    public OutfitSelector(Model character, JPanel characterPanel) {
        this.character = character;
        this.characterPanel = characterPanel;

        this.setOpaque(false);
        this.setLayout(null);

        JLabel leftOutfitBtn = createArrow("Buttons/leftBtn.png", 0, 0);
        this.add(leftOutfitBtn);

        JPanel outfitBar = new JPanel() {
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

                // الحواف الوردية الغامقة
                g2.setColor(new Color(56, 109, 38));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);

                g2.dispose();
            }
        };

        outfitBar.setBounds(38, 0, 110, 30);
        outfitBar.setOpaque(false);
        outfitBar.setLayout(new BorderLayout());

        outfitNameLabel = new JLabel(
                character.OUTFIT_NAMES[character.currentOutfit],
                SwingConstants.CENTER
        );
        outfitNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        outfitNameLabel.setForeground(Color.BLACK);

        outfitBar.add(outfitNameLabel, BorderLayout.CENTER);
        this.add(outfitBar);

        outfitNameLabel = new JLabel(character.OUTFIT_NAMES[character.currentOutfit], SwingConstants.CENTER);
        outfitNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        outfitNameLabel.setForeground(Color.BLACK);
        outfitBar.add(outfitNameLabel, BorderLayout.CENTER);
        this.add(outfitBar);

        JLabel rightOutfitBtn = createArrow("Buttons/rightBtn.png", 155, 0);
        this.add(rightOutfitBtn);

        colorContainer = new JPanel();
        colorContainer.setBounds(0, 40, 190, 30);
        colorContainer.setOpaque(false);
        colorContainer.setLayout(null);
        this.add(colorContainer);

        updateColorButtons();

        rightOutfitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (character.currentOutfit < character.OUTFIT_NAMES.length - 1) {
                    character.currentOutfit++;
                } else {
                    character.currentOutfit = 0;
                }
                character.currentOutfitColor = 0;
                outfitNameLabel.setText(character.OUTFIT_NAMES[character.currentOutfit]);
                updateColorButtons();
                characterPanel.repaint();
            }
        });

        leftOutfitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (character.currentOutfit > 0) {
                    character.currentOutfit--;
                } else {
                    character.currentOutfit = character.OUTFIT_NAMES.length - 1;
                }
                character.currentOutfitColor = 0;
                outfitNameLabel.setText(character.OUTFIT_NAMES[character.currentOutfit]);
                updateColorButtons();
                characterPanel.repaint();
            }
        });
    }

    public void updateColorButtons() {
        colorContainer.removeAll();
        Color[] outfit1Palette = {
                new Color(255, 232, 144),
                new Color(206, 71, 161),
                new Color(111, 30, 163),
                new Color(144, 202, 255)
        };Color[] outfit2Palette = {
                new Color(234, 196, 49),
                new Color(206, 71, 161),
                new Color(0, 94, 182),
                new Color(142, 44, 44)
        };Color[] outfit3And4Palette = {
                new Color(206, 71, 161),
                new Color(111, 30, 163),
                new Color(55, 140, 219),
                new Color(189, 63, 63)
        };

        Color[] activePalette;
        if (character.currentOutfit == 0) {
            activePalette = outfit1Palette;
        } else if (character.currentOutfit == 1) {
            activePalette = outfit2Palette;
        } else {
            activePalette = outfit3And4Palette;
        }

        int totalColors = activePalette.length;
        int btnSize = 22;
        int gap = 5;
        int startX = (190 - (totalColors * (btnSize + gap) - gap)) / 2;

        for (int i = 0; i < totalColors; i++) {
            final int index = i;
            JButton colorBtn = new JButton() {
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
            colorBtn.setBackground(activePalette[i]);
            colorBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            colorBtn.addActionListener(e -> {
                character.currentOutfitColor = index;
                characterPanel.repaint();
            });
            colorContainer.add(colorBtn);
        }

        colorContainer.revalidate();
        colorContainer.repaint();
    }
    public void repaint() {
        super.repaint();
        if (outfitNameLabel != null && character != null) {
            outfitNameLabel.setText(character.OUTFIT_NAMES[character.currentOutfit]);
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
