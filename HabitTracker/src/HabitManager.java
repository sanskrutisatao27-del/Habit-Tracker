import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HabitManager {
    private final List<Habit> habits;

    public HabitManager() {
        habits = new ArrayList<>();
    }

    public void addHabit(Habit habit) {
        if (habit == null) {
            throw new IllegalArgumentException("Habit cannot be null.");
        }
        habits.add(habit);
    }

    public void deleteHabit(int index) {
        if (index < 0 || index >= habits.size()) {
            throw new IndexOutOfBoundsException("Please select a valid habit.");
        }
        habits.remove(index);
    }

    public List<Habit> getHabits() {
        return Collections.unmodifiableList(habits);
    }

    public int getTotalHabits() {
        return habits.size();
    }

    public int getCompletedTodayCount() {
        int count = 0;
        for (Habit habit : habits) {
            if (habit.isCompletedOn(LocalDate.now())) {
                count++;
            }
        }
        return count;
    }

    public int getPendingTodayCount() {
        return getTotalHabits() - getCompletedTodayCount();
    }

    public double getTodayCompletionPercentage() {
        if (habits.isEmpty()) {
            return 0.0;
        }
        return (getCompletedTodayCount() * 100.0) / habits.size();
    }
}
