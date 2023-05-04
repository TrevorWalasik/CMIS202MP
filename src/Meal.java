import java.io.Serializable;
import java.util.ArrayList;

public class Meal implements Serializable {
    protected String mealName;
    private Macronutrient macros;
    private ArrayList<Food> foods;

    public Meal(String mealName, Macronutrient macros, ArrayList<Food> foods) {
        this.mealName = mealName;
        this.macros = macros;
        this.foods = foods;
    }

    public String getMealName() {
        return mealName;
    }

    public Macronutrient getMacros() {
        return macros;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setMacros(Macronutrient macros) {
        this.macros = macros;
    }

    public void setFood(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public String showFoods(Food[] foods) {
        StringBuilder sb = new StringBuilder();
        for (Food food : foods) {
            sb.append(food.getFoodName()).append(", ");
        }
        return sb.toString();
    }

    public void sortFoodsByCalories() {
        mergeSort(0, foods.size() - 1);
    }

    private void mergeSort(int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int middleIndex = (leftIndex + rightIndex) / 2;
            mergeSort(leftIndex, middleIndex);
            mergeSort(middleIndex + 1, rightIndex);
            merge(leftIndex, middleIndex, rightIndex);
        }
    }

    private void merge(int leftIndex, int middleIndex, int rightIndex) {
        ArrayList<Food> leftArray = new ArrayList<>(foods.subList(leftIndex, middleIndex + 1));
        ArrayList<Food> rightArray = new ArrayList<>(foods.subList(middleIndex + 1, rightIndex + 1));

        int leftArrayIndex = 0;
        int rightArrayIndex = 0;
        int mergedArrayIndex = leftIndex;

        while (leftArrayIndex < leftArray.size() && rightArrayIndex < rightArray.size()) {
            if (leftArray.get(leftArrayIndex).getCalories() >= rightArray.get(rightArrayIndex).getCalories()) {
                foods.set(mergedArrayIndex, leftArray.get(leftArrayIndex));
                leftArrayIndex++;
            } else {
                foods.set(mergedArrayIndex, rightArray.get(rightArrayIndex));
                rightArrayIndex++;
            }
            mergedArrayIndex++;
        }

        while (leftArrayIndex < leftArray.size()) {
            foods.set(mergedArrayIndex, leftArray.get(leftArrayIndex));
            leftArrayIndex++;
            mergedArrayIndex++;
        }

        while (rightArrayIndex < rightArray.size()) {
            foods.set(mergedArrayIndex, rightArray.get(rightArrayIndex));
            rightArrayIndex++;
            mergedArrayIndex++;
        }
    }
}