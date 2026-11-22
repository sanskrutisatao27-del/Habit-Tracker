import javax.swing.SwingUtilities;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HabitManager manager = new HabitManager();

            List<Habit> savedHabits = FileHandler.loadHabits();
            for (Habit habit : savedHabits) {
                manager.addHabit(habit);
            }

            Dashboard dashboard = new Dashboard(manager);
            dashboard.setVisible(true);
        });
    }
}
