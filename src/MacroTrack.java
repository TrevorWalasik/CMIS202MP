import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.*;


public class MacroTrack extends Application {
    private Stage stage;
    private BorderPane borderPane;
    ArrayList<Food> foodList = new ArrayList<Food>();
    Queue<Meal> mealQueue = new LinkedList<>();
    Macronutrient currentMac = new Macronutrient(0, 0, 0, 0);
    String currentMeal = "";
    public HashSet<Meal> mealSet = new HashSet<Meal>();
    private static MealsIO save = new MealsIO("save.dat");
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        Text headerText = new Text("Macro Track");
        headerText.setFont(Font.font("Verdana", 30));
        headerText.setFill(Color.rgb(128, 128, 128));

        Button addButton = new Button("Add Meal");
        addButton.setPrefWidth(150);
        addButton.setMinWidth(150);
        addButton.setOnAction(e -> addMealGUI());

        Button recallButton = new Button("Recall Meal");
        recallButton.setPrefWidth(150);
        recallButton.setMinWidth(150);
        recallButton.setOnAction(e -> changeScreen(recallButton.getText()));

        Button selectButton = new Button("Selected Meals");
        selectButton.setPrefWidth(150);
        selectButton.setMinWidth(150);
        selectButton.setOnAction(e -> changeScreen(selectButton.getText()));

        Button sortProteinButton = new Button("Show Daily Meals");
        sortProteinButton.setPrefWidth(150);
        sortProteinButton.setMinWidth(150);
        sortProteinButton.setOnAction(e -> changeScreen(sortProteinButton.getText()));

        HBox buttonHBox = new HBox(10, addButton, recallButton, selectButton, sortProteinButton);
        buttonHBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(10, headerText, buttonHBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        borderPane.setStyle("-fx-background-color: #ADD8E6;");

        Scene scene = new Scene(borderPane, 800, 600);

        stage.setTitle("Macro Track");
        stage.setScene(scene);
        stage.show();
    }

    public void changeScreen(String title) {
        Text headerText = new Text(title);
        headerText.setFont(Font.font("Verdana", 30));
        headerText.setFill(Color.rgb(128, 128, 128));

        VBox vBox = new VBox(10, headerText);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        borderPane.setCenter(vBox);
    }

    public void addMealGUI() {
        currentMac.setFats(0);
        currentMac.setCalories(0);
        currentMac.setCarbs(0);
        currentMac.setProtein(0);

        Label mealLabel = new Label("Current Meal:");
        Label foodLabel = new Label("Food Name:");
        Label calsLabel = new Label("Calories:");
        Label carbsLabel = new Label("Carbs (g):");
        Label fatsLabel = new Label("Fats (g):");
        Label proteinLabel = new Label("Protein (g):");

        TextField foodTextField = new TextField();
        TextField calsTextField = new TextField();
        TextField carbsTextField = new TextField();
        TextField fatsTextField = new TextField();
        TextField proteinTextField = new TextField();

        Button addFoodButton = new Button("Add Food");
        Button saveMealButton = new Button("Save Meal");
        Button backToHomeButton = new Button("Back to Home");

        HBox foodHBox = new HBox(10, foodLabel, foodTextField);
        foodHBox.setAlignment(Pos.CENTER);

        HBox calsHBox = new HBox(10, calsLabel, calsTextField);
        calsHBox.setAlignment(Pos.CENTER);

        HBox carbsHBox = new HBox(10, carbsLabel, carbsTextField);
        carbsHBox.setAlignment(Pos.CENTER);

        HBox fatsHBox = new HBox(10, fatsLabel, fatsTextField);
        fatsHBox.setAlignment(Pos.CENTER);

        HBox proteinHBox = new HBox(10, proteinLabel, proteinTextField);
        proteinHBox.setAlignment(Pos.CENTER);

        HBox buttonHBox = new HBox(10, addFoodButton, saveMealButton, backToHomeButton);
        buttonHBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(20, mealLabel, foodHBox, calsHBox, carbsHBox, fatsHBox, proteinHBox, buttonHBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);

        Scene scene = new Scene(borderPane, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Food");
        stage.show();

        addFoodButton.setOnAction(e -> {
            String food = foodTextField.getText();

            String cals = calsTextField.getText();
            int calories = Integer.parseInt(cals);


            String carbs = carbsTextField.getText();
            int carbohydrates = Integer.parseInt(carbs);

            String fats = fatsTextField.getText();
            int fatsAmount = Integer.parseInt(fats);

            String protein = proteinTextField.getText();
            int proteinAmount = Integer.parseInt(protein);

            String foodInfo = food + " - " + cals + " cals, " + carbs + "g carbs, " + fats + "g fats, " + protein + "g protein";

            mealLabel.setText(mealLabel.getText() + "\n" + foodInfo);

            foodTextField.clear();
            calsTextField.clear();
            carbsTextField.clear();
            fatsTextField.clear();
            proteinTextField.clear();

            foodList.add(new Food(food, calories, carbohydrates, fatsAmount, proteinAmount));
            currentMac.addFats(fatsAmount);
            currentMac.addCarb(carbohydrates);
            currentMac.addProtein(proteinAmount);
            currentMac.addCals(calories);
            currentMeal = currentMeal + food + ", ";
        });
        saveMealButton.setOnAction(e -> {
            Meal meal = new Meal(currentMeal, currentMac, foodList);
            mealQueue.add(meal);
            save.addMeal(meal);
            save.saveMeals();

            Label mealSavedLabel = new Label("Meal Saved");
            mealSavedLabel.setFont(new Font(30));
            borderPane.setTop(mealSavedLabel);

            PauseTransition delay = new PauseTransition(Duration.seconds(3));

            delay.setOnFinished(event -> stage.close());

            delay.play();
        });

        backToHomeButton.setOnAction(e -> {
            stage.close();
        });
    }



    public static void main(String[] args) {
        save.loadMeals();
        save.displayMeals();
        HashSet<Meal> meals = save.getMeals();

        launch(args);
    }
}