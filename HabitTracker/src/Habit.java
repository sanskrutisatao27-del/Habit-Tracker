import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/*
 * Abstract parent class. Common habit information and behaviour
 * are kept here so that DailyHabit and WeeklyHabit can reuse it.
 */
public abstract class Habit implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private final LocalDate createdDate;
    private final Set<LocalDate> completedDates;

    public Habit(String name, String description) {
        setName(name);
        this.description = description == null ? "" : description.trim();
        this.createdDate = LocalDate.now();
        this.completedDates = new HashSet<>();
    }

    // Method overloading: second constructor-style method is represented
    // by the overloaded markCompleted methods below.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Habit name cannot be empty.");
        }
        this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description.trim();
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Set<LocalDate> getCompletedDates() {
        return new HashSet<>(completedDates);
    }

    public boolean isCompletedOn(LocalDate date) {
        return completedDates.contains(date);
    }

    public void markCompleted() {
        markCompleted(LocalDate.now());
    }

    public void markCompleted(LocalDate date) {
        completedDates.add(date);
    }

    public void undoCompletion(LocalDate date) {
        completedDates.remove(date);
    }

    public int getTotalCompletions() {
        return completedDates.size();
    }

    public int getCurrentStreak() {
        LocalDate day = LocalDate.now();
        if (!completedDates.contains(day)) {
            day = day.minusDays(1);
        }

        int streak = 0;
        while (completedDates.contains(day)) {
            streak++;
            day = day.minusDays(1);
        }
        return streak;
    }

    public abstract String getFrequencyLabel();
}
