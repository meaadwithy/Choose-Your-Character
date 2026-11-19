import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class GUI {
    private static Model character = new Model();
    public static void main(String[] args) {
        JFrame frame = new JFrame("أختار شخصيتك المصغرة");
        frame.setSize(650, 600); // زيادة بسيطة في ارتفاع النافذة لتستوعب التباعد
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(23, 23, 55));
        frame.setLayout(null);

        // 1. قسم اسم المستخدم (مكانه في الأعلى)
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(new Color(82, 181, 243));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(new Color(10, 56, 232));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);

                g2.dispose();
            }
        };

        field.setBounds(180, 55, 200, 35);
        field.setFont(new Font("Arial", Font.BOLD, 16));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setForeground(Color.BLACK);
        field.setOpaque(false);

        JLabel label = new JLabel("اسم المستخدم :");
        label.setBounds(385, 55, 120, 32);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);

        // 2. شاشة عرض الشخصية (تم تنزيلها إلى Y = 140 لترك مسافة واضحة)
        JPanel characterPanel = new JPanel() {
            { setOpaque(false); }            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                // الشاشة البيضاء بحواف دائرية
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);

                // نخلي الشخصية داخل الحواف الدائرية
                g2.setClip(new java.awt.geom.RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 35, 35
                ));

                File skinFile = new File(character.SKINS[character.currentSkin]);
                if (skinFile.exists()) {
                    ImageIcon skinIcon = new ImageIcon(character.SKINS[character.currentSkin]);
                    g2.drawImage(skinIcon.getImage(), 0, 0, 280, 280, this);
                }

                File eyeFile = new File(character.EYES_COLORS[character.currentEyes]);
                if (eyeFile.exists()) {
                    ImageIcon eyeIcon = new ImageIcon(character.EYES_COLORS[character.currentEyes]);
                    g2.drawImage(eyeIcon.getImage(), 0, 0, 280, 280, this);
                }

                String hairImagePath = "";
                if (character.currentHair == 0) {
                    hairImagePath = character.HAIR1_COLORS[character.currentHairColor];
                } else if (character.currentHair == 1) {
                    hairImagePath = character.HAIR2_COLORS[character.currentHairColor];
                }

                if (!hairImagePath.isEmpty()) {
                    File hairFile = new File(hairImagePath);
                    if (hairFile.exists()) {
                        ImageIcon hairIcon = new ImageIcon(hairImagePath);
                        g2.drawImage(hairIcon.getImage(), 0, 0, 280, 280, this);
                    }
                }

                String outfitImagePath = "";
                if (character.currentOutfit == 0) {
                    outfitImagePath = character.OUTFIT_1[character.currentOutfitColor];
                } else if (character.currentOutfit == 1) {
                    outfitImagePath = character.OUTFIT_2[character.currentOutfitColor];
                } else if (character.currentOutfit == 2) {
                    outfitImagePath = character.OUTFIT_3[character.currentOutfitColor];
                } else if (character.currentOutfit == 3) {
                    outfitImagePath = character.OUTFIT_4[character.currentOutfitColor];
                }

                if (!outfitImagePath.isEmpty()) {
                    File outfitFile = new File(outfitImagePath);
                    if (outfitFile.exists()) {
                        ImageIcon outfitIcon = new ImageIcon(outfitImagePath);
                        g2.drawImage(outfitIcon.getImage(), 0, 0, 280, 280, this);
                    }
                }

                // الحواف الوردية
                g2.setClip(null);

                g2.setColor(new Color(210, 57, 160));
                g2.setStroke(new BasicStroke(
                        5,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND
                ));

                g2.drawRoundRect(
                        2, 2,
                        getWidth() - 5,
                        getHeight() - 5,
                        35, 35
                );

                g2.dispose();
            }
        };

        characterPanel.setBounds(330, 140, 280, 280);
        // 3. قسم البشرة (تم تنزيله إلى Y = 145)
        SkinSelector skinSelector = new SkinSelector(character, characterPanel);
        skinSelector.setBounds(30, 145, 190, 30);

        JLabel skinLabel = new JLabel("لون البشرة", SwingConstants.RIGHT);
        skinLabel.setBounds(225, 145, 90, 30);
        skinLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        skinLabel.setForeground(Color.WHITE);

        // 4. قسم العيون (تم تنزيله إلى Y = 195)
        EyesSelector eyesSelector = new EyesSelector(character, characterPanel);
        eyesSelector.setBounds(30, 195, 190, 30);

        JLabel eyesLabel = new JLabel("لون العيون", SwingConstants.RIGHT);
        eyesLabel.setBounds(225, 190, 90, 30);
        eyesLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        eyesLabel.setForeground(Color.WHITE);

        // 5. قسم الشعر (تم تنزيله إلى Y = 250)
        HairSelector hairSelector = new HairSelector(character, characterPanel);
        hairSelector.setBounds(30, 250, 190, 75);

        JLabel hairLabel = new JLabel("شكل الشعر", SwingConstants.RIGHT);
        hairLabel.setBounds(225, 250, 90, 30);
        hairLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        hairLabel.setForeground(Color.WHITE);
        JLabel hairColorLabel = new JLabel("لون الشعر", SwingConstants.RIGHT);
        hairColorLabel.setBounds(225, 285, 90, 30);
        hairColorLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        hairColorLabel.setForeground(Color.WHITE);
        frame.add(hairColorLabel);

        // 6. قسم اللبس (تم تنزيله إلى Y = 350)
        OutfitSelector outfitSelector = new OutfitSelector(character, characterPanel);
        outfitSelector.setBounds(30, 350, 190, 75);

        JLabel outfitLabel = new JLabel("شكل اللبس", SwingConstants.RIGHT);
        outfitLabel.setBounds(225, 350, 90, 30);
        outfitLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        outfitLabel.setForeground(Color.WHITE);
        JLabel outfitColorLabel = new JLabel("لون اللبس", SwingConstants.RIGHT);
        outfitColorLabel.setBounds(225, 385, 90, 30); // محاذاة مربعات ألوان اللبس
        outfitColorLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        outfitColorLabel.setForeground(Color.WHITE);
        frame.add(outfitColorLabel);

        // 7. زر العشوائي (تحت شاشة العرض مباشرة عند Y = 440)
        JButton randomBtn = new JButton("عشوائي 🎲") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                // اللون الداخلي الوردي
                g2.setColor(new Color(82, 181, 243));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                // الحواف البنفسجية
                g2.setColor(new Color(10, 56, 232));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);

                g2.dispose();
            }
        };

        randomBtn.setBounds(465, 440, 140, 36);
        randomBtn.setFont(new Font("Arial", Font.BOLD, 18));
        randomBtn.setForeground(Color.BLACK);
        randomBtn.setFocusPainted(false);
        randomBtn.setBorderPainted(true);
        randomBtn.setContentAreaFilled(false);
        randomBtn.setOpaque(false);
        randomBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        randomBtn.addActionListener(e -> {
            Random rand = new Random();

            character.currentSkin = rand.nextInt(character.SKINS.length);
            character.currentEyes = rand.nextInt(character.EYES_COLORS.length);

            character.currentHair = rand.nextInt(character.HAIR_NAMES.length);
            int totalHairColors = (character.currentHair == 0) ? character.HAIR1_COLORS.length : character.HAIR2_COLORS.length;
            character.currentHairColor = rand.nextInt(totalHairColors);

            character.currentOutfit = rand.nextInt(character.OUTFIT_NAMES.length);
            int totalOutfitColors = 0;
            if (character.currentOutfit == 0) totalOutfitColors = character.OUTFIT_1.length;
            else if (character.currentOutfit == 1) totalOutfitColors = character.OUTFIT_2.length;
            else if (character.currentOutfit == 2) totalOutfitColors = character.OUTFIT_3.length;
            else if (character.currentOutfit == 3) totalOutfitColors = character.OUTFIT_4.length;
            character.currentOutfitColor = rand.nextInt(totalOutfitColors);

            hairSelector.updateColorButtons();
            outfitSelector.updateColorButtons();

            characterPanel.repaint();
            skinSelector.repaint();
            hairSelector.repaint();
            outfitSelector.repaint();
        });

        // إضافة العناصر للنافذة
        frame.add(field);
        frame.add(label);
        frame.add(characterPanel);

        frame.add(skinLabel);
        frame.add(skinSelector);
        frame.add(eyesLabel);
        frame.add(eyesSelector);

        frame.add(hairLabel);
        frame.add(hairSelector);

        frame.add(outfitLabel);
        frame.add(outfitSelector);

        frame.add(randomBtn);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
