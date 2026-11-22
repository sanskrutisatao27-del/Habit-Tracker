import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Dashboard extends JFrame {
    private static final Color BG = new Color(245, 247, 252);
    private static final Color INK = new Color(35, 43, 66);
    private static final Color MUTED = new Color(117, 126, 148);
    private static final Color PURPLE = new Color(103, 80, 220);
    private static final Color TEAL = new Color(35, 174, 153);
    private static final Color ORANGE = new Color(239, 157, 72);

    private final HabitManager manager;
    private final DefaultTableModel tableModel;
    private final JTable habitTable;
    private final JLabel totalLabel;
    private final JLabel completedLabel;
    private final JLabel pendingLabel;
    private final JLabel progressLabel;
    private final JProgressBar progressBar;

    public Dashboard(HabitManager manager) {
        this.manager = manager;
        setTitle("Habitly | Personal Habit Tracker");
        setSize(1050, 720);
        setMinimumSize(new Dimension(850, 620));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(0, 18));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(22, 28, 22, 28));
        setContentPane(root);

        // Attractive gradient welcome banner
        GradientPanel header = new GradientPanel();
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(23, 28, 23, 28));
        JLabel title = new JLabel("Habitly  ✦");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        JLabel subtitle = new JLabel("Small steps every day lead to big changes.");
        subtitle.setForeground(new Color(235, 232, 255));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));
        headingText.add(title);
        headingText.add(Box.createVerticalStrut(5));
        headingText.add(subtitle);
        JLabel date = new JLabel("✧  " + LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")) + "  ");
        date.setForeground(Color.WHITE);
        date.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.add(headingText, BorderLayout.WEST);
        header.add(date, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);
        root.add(content, BorderLayout.CENTER);

        JPanel stats = new JPanel(new GridLayout(1, 3, 15, 0));
        stats.setOpaque(false);
        totalLabel = makeStatCard(stats, "✦  TOTAL HABITS", "0", new Color(103, 80, 220));
        completedLabel = makeStatCard(stats, "✓  COMPLETED TODAY", "0", TEAL);
        pendingLabel = makeStatCard(stats, "◷  STILL TO DO", "0", ORANGE);
        content.add(stats, BorderLayout.NORTH);

        JPanel tableCard = new JPanel(new BorderLayout(0, 12));
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 242)),
                new EmptyBorder(18, 20, 18, 20)));
        JLabel tableTitle = new JLabel("Your habits");
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 19));
        tableTitle.setForeground(INK);
        JLabel tableHint = new JLabel("  Choose a habit below to update your progress");
        tableHint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tableHint.setForeground(MUTED);
        JPanel tableHeading = new JPanel(new BorderLayout());
        tableHeading.setOpaque(false);
        tableHeading.add(tableTitle, BorderLayout.WEST);
        tableHeading.add(tableHint, BorderLayout.SOUTH);
        tableCard.add(tableHeading, BorderLayout.NORTH);

        String[] columns = {"HABIT", "FREQUENCY", "TODAY", "TOTAL DONE", "CURRENT STREAK"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        habitTable = new JTable(tableModel);
        habitTable.setRowHeight(42);
        habitTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        habitTable.setForeground(INK);
        habitTable.setBackground(Color.WHITE);
        habitTable.setGridColor(new Color(239, 241, 247));
        habitTable.setShowVerticalLines(false);
        habitTable.setSelectionBackground(new Color(239, 235, 255));
        habitTable.setSelectionForeground(INK);
        habitTable.setFillsViewportHeight(true);
        habitTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        habitTable.setIntercellSpacing(new Dimension(0, 1));
        habitTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));
        habitTable.getTableHeader().setForeground(MUTED);
        habitTable.getTableHeader().setBackground(new Color(248, 249, 253));
        habitTable.getTableHeader().setPreferredSize(new Dimension(0, 38));
        habitTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(232, 235, 243)));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < columns.length; i++) habitTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        habitTable.getColumnModel().getColumn(0).setPreferredWidth(230);
        JScrollPane scroll = new JScrollPane(habitTable);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);
        content.add(tableCard, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout(15, 12));
        footer.setOpaque(false);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 0));
        actions.setOpaque(false);
        JButton addButton = makeButton("+  Add Habit", PURPLE, Color.WHITE);
        JButton completeButton = makeButton("✓  Mark Complete", TEAL, Color.WHITE);
        JButton undoButton = makeButton("↶  Undo Today", new Color(235, 238, 246), INK);
        JButton deleteButton = makeButton("✕  Delete", new Color(255, 235, 237), new Color(190, 65, 82));
        JButton refreshButton = makeButton("⟳  Refresh", new Color(235, 238, 246), INK);
        actions.add(addButton);
        actions.add(completeButton);
        actions.add(undoButton);
        actions.add(deleteButton);
        actions.add(refreshButton);
        footer.add(actions, BorderLayout.NORTH);

        JPanel progressCard = new JPanel(new BorderLayout(12, 8));
        progressCard.setBackground(Color.WHITE);
        progressCard.setBorder(new EmptyBorder(13, 17, 13, 17));
        progressLabel = new JLabel("Today's progress  •  0%");
        progressLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        progressLabel.setForeground(INK);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new Dimension(100, 13));
        progressBar.setForeground(TEAL);
        progressBar.setBackground(new Color(232, 239, 240));
        progressBar.setBorderPainted(false);
        progressCard.add(progressLabel, BorderLayout.NORTH);
        progressCard.add(progressBar, BorderLayout.CENTER);
        footer.add(progressCard, BorderLayout.SOUTH);
        root.add(footer, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addHabitDialog());
        completeButton.addActionListener(e -> markSelectedComplete());
        undoButton.addActionListener(e -> undoSelectedCompletion());
        deleteButton.addActionListener(e -> deleteSelectedHabit());
        refreshButton.addActionListener(e -> refreshTable());

        refreshTable();
    }

    private JLabel makeStatCard(JPanel parent, String heading, String initial, Color accent) {
        JPanel card = new JPanel(new BorderLayout(5, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 233, 242)),
                    new EmptyBorder(14, 16, 14, 16))));
        JLabel name = new JLabel(heading);
        name.setFont(new Font("SansSerif", Font.BOLD, 11));
        name.setForeground(MUTED);
        JLabel value = new JLabel(initial);
        value.setFont(new Font("SansSerif", Font.BOLD, 29));
        value.setForeground(INK);
        card.add(name, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);
        parent.add(card);
        return value;
    }

    private JButton makeButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(11, 15, 11, 15));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(background.darker());
                if (background.equals(new Color(235, 238, 246))) button.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(background);
                button.setForeground(foreground);
            }
        });
        return button;
    }

    private void addHabitDialog() {
        JTextField nameField = new JTextField(18);
        JTextField descriptionField = new JTextField(18);
        JComboBox<String> frequencyBox = new JComboBox<>(new String[]{"Daily", "Weekly"});
        styleInput(nameField);
        styleInput(descriptionField);
        frequencyBox.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel form = new JPanel(new GridLayout(0, 1, 7, 7));
        form.setBorder(new EmptyBorder(10, 12, 8, 12));
        form.setBackground(Color.WHITE);
        form.add(formLabel("Habit name"));
        form.add(nameField);
        form.add(formLabel("Description (optional)"));
        form.add(descriptionField);
        form.add(formLabel("How often?"));
        form.add(frequencyBox);

        int result = JOptionPane.showConfirmDialog(this, form, "Create a new habit",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String description = descriptionField.getText();
                Habit habit;
                if ("Weekly".equals(frequencyBox.getSelectedItem())) {
                    habit = new WeeklyHabit(name, description);
                } else {
                    habit = new DailyHabit(name, description);
                }
                manager.addHabit(habit);
                saveAndRefresh();
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        }
    }

    private JLabel formLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(INK);
        return label;
    }

    private void styleInput(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 222, 234)),
                new EmptyBorder(8, 9, 8, 9)));
    }

    private void markSelectedComplete() {
        int row = habitTable.getSelectedRow();
        if (!isValidSelection(row)) return;
        Habit habit = manager.getHabits().get(row);
        if (habit.isCompletedOn(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "This habit is already completed today.", "All done!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        habit.markCompleted();
        saveAndRefresh();
    }

    private void undoSelectedCompletion() {
        int row = habitTable.getSelectedRow();
        if (!isValidSelection(row)) return;
        Habit habit = manager.getHabits().get(row);
        habit.undoCompletion(LocalDate.now());
        saveAndRefresh();
    }

    private void deleteSelectedHabit() {
        int row = habitTable.getSelectedRow();
        if (!isValidSelection(row)) return;
        int answer = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to remove this habit?",
                "Delete habit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            manager.deleteHabit(row);
            saveAndRefresh();
        }
    }

    private boolean isValidSelection(int row) {
        if (row < 0 || row >= manager.getTotalHabits()) {
            showError("Please select a habit from the table first.");
            return false;
        }
        return true;
    }

    private void saveAndRefresh() {
        try {
            FileHandler.saveHabits(manager.getHabits());
            refreshTable();
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Habit> habits = manager.getHabits();
        for (Habit habit : habits) {
            tableModel.addRow(new Object[]{
                    habit.getName(),
                    habit.getFrequencyLabel(),
                    habit.isCompletedOn(LocalDate.now()) ? "✓ Done" : "○ Pending",
                    habit.getTotalCompletions(),
                    habit.getCurrentStreak() + " days"
            });
        }
        totalLabel.setText(String.valueOf(manager.getTotalHabits()));
        completedLabel.setText(String.valueOf(manager.getCompletedTodayCount()));
        pendingLabel.setText(String.valueOf(manager.getPendingTodayCount()));
        int percentage = (int) Math.round(manager.getTodayCompletionPercentage());
        progressLabel.setText("Today's progress  •  " + percentage + "%");
        progressBar.setValue(percentage);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Habitly", JOptionPane.ERROR_MESSAGE);
    }

    private static class GradientPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradient = new GradientPaint(0, 0, new Color(91, 72, 205),
                    getWidth(), getHeight(), new Color(43, 155, 169));
            g2.setPaint(gradient);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
            g2.dispose();
        }
        public Dimension getPreferredSize() { return new Dimension(100, 118); }
    }
}
