import java.io.Serializable;

public class Macronutrient implements Serializable {
    private int calories;
    private int fats;
    private int carbs;
    private int protein;

    public Macronutrient(int calories, int fats, int carbs, int protein) {
        this.calories = calories;
        this.fats = fats;
        this.carbs = carbs;
        this.protein = protein;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }
    public void addProtein(int pro) {
        this.protein += pro;
    }

    public void addCarb(int carb) {
        this.carbs += carb;
    }

    public void addFats(int fat) {
        this.fats += fat;
    }

    public void addCals(int cal) {
        this.calories += cal;
    }

    public String showFoods(Food[] foods) {
        String result = "";
        for (Food food : foods) {
            result += food.getFoodName() + " (" + food.getCalories() + ")\n";
        }
        return result;
    }

    @Override
    public String toString() {
        return
                "Calories: " + calories + "\n" +
                        "Fats: " + fats + "\n" +
                        "Carbs: " + carbs + "\n" +
                        "Protein: " + protein + "\n";
    }
}