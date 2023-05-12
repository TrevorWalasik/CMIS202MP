import java.io.*;
import java.util.*;

public class MealsIO {
    private HashSet<Meal> meals;
    private String fileName;

    public MealsIO(String fileName) {
        this.fileName = fileName;
        meals = new HashSet<>();
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
    }

    public HashSet<Meal> getMeals() {
        return meals;
    }

    public void saveMeals() {
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(meals);
            oos.flush();
            oos.close();
            byte[] bytes = baos.toByteArray();
            raf.setLength(bytes.length);
            raf.seek(0);
            raf.write(bytes);
            raf.close();
        } catch (IOException e) {
            System.err.println("Error saving meals to file: " + e.getMessage());
        }
    }

    public void loadMeals() {
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "r");
            byte[] bytes = new byte[(int) raf.length()];
            raf.readFully(bytes);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            meals = (HashSet<Meal>) ois.readObject();
            ois.close();
            raf.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading meals from file: " + e.getMessage());
        }
    }

    public void displayMeals() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj;
            while ((obj = ois.readObject()) != null) {
                System.out.println(obj.toString());
            }

        } catch (EOFException e) {
            // reached end of file
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}