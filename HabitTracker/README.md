# Habit Tracker — Java OOP Mini Project

## Requirements
- JDK 17 or later (JDK 8+ should also work with this source)
- Visual Studio Code
- VS Code Extension Pack for Java

## Run in VS Code
1. Extract the ZIP file.
2. Open the `HabitTracker` folder in VS Code.
3. Install the **Extension Pack for Java** if VS Code prompts you.
4. Open `src/Main.java`.
5. Click **Run** above the `main` method, or use the Run button.
6. The app stores saved habits in `habits.dat` in the working directory.

## Run from the VS Code terminal
Open Terminal > New Terminal, then run:

```bash
cd src
javac *.java
java Main
```

## OOP concepts
- Abstraction: `Habit` is an abstract class.
- Inheritance: `DailyHabit` and `WeeklyHabit` extend `Habit`.
- Encapsulation: habit fields are private and accessed through methods.
- Polymorphism: `getFrequencyLabel()` is overridden and called through `Habit` references.
- Overloading: `markCompleted()` and `markCompleted(LocalDate)` are overloaded.
- Exception handling: invalid names and invalid table selections are handled.
- File handling: `FileHandler` saves/loads the habit list using Java object streams.

## Note
The current streak counts consecutive calendar days ending today, or yesterday if today is not completed yet. The frequency field is descriptive; weekly habits are still manually marked complete by the user.


## Updated attractive interface
The dashboard now has a modern gradient welcome banner, colorful summary cards,
a cleaner habit table, clearer action buttons, and a daily progress bar.
The project still uses Java Swing and the same saved-habit data file.
