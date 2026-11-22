public class WeeklyHabit extends Habit {
    private static final long serialVersionUID = 1L;

    public WeeklyHabit(String name, String description) {
        super(name, description);
    }

    @Override
    public String getFrequencyLabel() {
        return "Weekly";
    }
}
