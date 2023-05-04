import java.io.Serializable;

public class Food extends Macronutrient implements Serializable {
    private String foodName;

    public Food(String foodName, int calories, int fats, int carbs, int protein) {
        super(calories, fats, carbs, protein);
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void saveFood() {
        // TODO: Implement saveFood method
    }

    public void recallFood() {
        // TODO: Implement recallFood method
    }
}
