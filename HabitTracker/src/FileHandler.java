import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_NAME = "habits.dat";

    public static void saveHabits(List<Habit> habits) {
        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME))) {
            output.writeObject(new ArrayList<>(habits));
        } catch (IOException e) {
            throw new RuntimeException("Could not save habits: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Habit> loadHabits() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream(file))) {
            return (List<Habit>) input.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Could not load saved habits: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
