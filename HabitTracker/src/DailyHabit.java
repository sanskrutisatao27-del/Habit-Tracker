public class DailyHabit extends Habit {
    private static final long serialVersionUID = 1L;

    public DailyHabit(String name, String description) {
        super(name, description);
    }

    @Override
    public String getFrequencyLabel() {
        return "Daily";
    }
}
