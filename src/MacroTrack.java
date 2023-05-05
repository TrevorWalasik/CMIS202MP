import javafx.application.Application;

import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import javax.swing.*;
import java.util.*;


public class MacroTrack extends Application {
    private Stage stage;
    private BorderPane borderPane;
    ArrayList<Food> foodList = new ArrayList<Food>();
    Queue<Meal> mealQueue = new LinkedList<>();
    Macronutrient currentMac = new Macronutrient(0, 0, 0, 0);
    String currentMeal = "";
    public HashSet<Meal> mealSet = new HashSet<Meal>();
    public FoodBST dailyFoodBST = new FoodBST();
    private static MealsIO save = new MealsIO("save.dat");

    private Hashtable<String, Integer> ht = new Hashtable<>();

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
        recallButton.setOnAction(e -> recallMeal());

        Button selectButton = new Button("Selected Meals");
        selectButton.setPrefWidth(150);
        selectButton.setMinWidth(150);
        selectButton.setOnAction(e -> selectedMealsGUI());

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
        borderPane.setStyle("-fx-background-color: #ADD8E6;");
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

    private TableView<Meal> table;

    private void recallMeal() {
        TableColumn<Meal, String> mealColumn = new TableColumn<>("Meal");
        mealColumn.setMinWidth(200);
        mealColumn.setCellValueFactory(new PropertyValueFactory<>("mealName"));

        TableColumn<Meal, Integer> caloriesColumn = new TableColumn<>("Calories");
        caloriesColumn.setMinWidth(200);
        caloriesColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMacros().getCalories()).asObject());

        TableColumn<Meal, Integer> proteinColumn = new TableColumn<>("Protein");
        proteinColumn.setMinWidth(200);
        proteinColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMacros().getProtein()).asObject());

        TableColumn<Meal, Integer> carbsColumn = new TableColumn<>("Carbs");
        carbsColumn.setMinWidth(200);
        carbsColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMacros().getCarbs()).asObject());

        TableColumn<Meal, Integer> fatColumn = new TableColumn<>("Fat");
        fatColumn.setMinWidth(200);
        fatColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMacros().getFats()).asObject());

        table = new TableView<>();
        table.getColumns().addAll(mealColumn, caloriesColumn, proteinColumn, carbsColumn, fatColumn);

        loadSaves();

        VBox vBox = new VBox();
        Label removeLabel = new Label("Click \"d\" to remove meal from database");
        removeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        removeLabel.setTextFill(Color.rgb(128, 128, 128));
        removeLabel.setAlignment(Pos.CENTER);

        Label addLabel = new Label("Click \"s\" to add meal to your daily meals");
        addLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        addLabel.setTextFill(Color.rgb(128, 128, 128));
        addLabel.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #ADD8E6;");

        vBox.getChildren().addAll(removeLabel, addLabel, table);

        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 1000, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Recall Meal");
        stage.show();

        table.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D) {
                Meal selectedMeal = table.getSelectionModel().getSelectedItem();
                if (selectedMeal != null) {
                    table.getItems().remove(selectedMeal);
                    save.removeMeal(selectedMeal);
                }
            }

            if (event.getCode() == KeyCode.S) {
                Meal selectedMeal = table.getSelectionModel().getSelectedItem();
                mealQueue.add(selectedMeal);
                for(int i = 0; i<selectedMeal.getFoods().size(); i++) {
                    dailyFoodBST.addFood(selectedMeal.getFoods().get(i));
                }
                //dailyFoodBST.printInOrder();
            }
        });

    }

    private void loadSaves() {
        for (Meal meal : save.getMeals()) {
            addMealRow(meal);
        }
    }

    private void addMealRow(Meal meal) {
        ObservableList<Meal> data = table.getItems();
        data.add(meal);
        table.setItems(data);
    }

    private TableView<Meal> table1;
    private TableView<Food> table2;

    private void loadQ() {
        Queue<Meal> queueCopy = new LinkedList<>(mealQueue);
        while (!queueCopy.isEmpty()) {
            addMealRow2(queueCopy.poll());
        }
    }

    private void addMealRow2(Meal meal) {
        ObservableList<Meal> data = table1.getItems();
        data.add(meal);
        table1.setItems(data);
    }

    public void selectedMealsGUI() {
        // Create the table columns
        TableColumn<Meal, String> mealColumn = new TableColumn<>("Selected Meals");
        mealColumn.setMinWidth(200);
        mealColumn.setCellValueFactory(new PropertyValueFactory<>("mealName"));

        TableColumn<Meal, Integer> caloriesColumn = new TableColumn<>("Most Calories");
        caloriesColumn.setMinWidth(200);
        caloriesColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMacros().getCalories()).asObject());

        TableColumn<Meal, Integer> proteinColumn = new TableColumn<>("Most Protein");
        proteinColumn.setMinWidth(200);
        proteinColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMacros().getProtein()).asObject());

        TableColumn<Meal, Integer> carbsColumn = new TableColumn<>("Most Carbs");
        carbsColumn.setMinWidth(200);
        carbsColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMacros().getCarbs()).asObject());

        TableColumn<Meal, Integer> fatColumn = new TableColumn<>("Most Fat");
        fatColumn.setMinWidth(200);
        fatColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMacros().getFats()).asObject());

        // Create the first table and add the columns
        table1 = new TableView<>();
        table1.getColumns().addAll(mealColumn, caloriesColumn, proteinColumn, carbsColumn, fatColumn);
        loadQ();
        // Create the table columns for the second table
        TableColumn<Map.Entry<String, Integer>, String> foodNameColumn = new TableColumn<>("Food Name");
        foodNameColumn.setMinWidth(500);
        foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));

        TableColumn<Map.Entry<String, Integer>, Integer> foodCaloriesColumn = new TableColumn<>("Calories");
        foodCaloriesColumn.setMinWidth(500);
        foodCaloriesColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());

        TableView<Map.Entry<String, Integer>> table2 = new TableView<>();
        table2.getColumns().addAll(foodNameColumn, foodCaloriesColumn);

        Hashtable<String, Integer> foodTable = dailyFoodBST.getFoodTable();

        Queue<Map.Entry<String, Integer>> queue = new LinkedList<>(foodTable.entrySet());
        ObservableList<Map.Entry<String, Integer>> foodItems = FXCollections.observableArrayList();
        while (!queue.isEmpty()) {
            foodItems.add(queue.remove());
        }
        table2.setItems(foodItems);

        VBox vBox = new VBox();
        Label removeLabel = new Label("Click \"d\" to remove meal from day");
        removeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        removeLabel.setTextFill(Color.rgb(128, 128, 128));
        removeLabel.setAlignment(Pos.CENTER);

        vBox.setStyle("-fx-background-color: #ADD8E6;");

        vBox.getChildren().addAll(removeLabel, table1, table2);

        vBox.setAlignment(Pos.CENTER);


        Scene scene = new Scene(vBox, 1000, 600);
        scene.setFill(Color.rgb(173, 216, 230));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Selected Meals");
        stage.show();
    }

    private void populateBST() {
        Queue<Meal> refQ = mealQueue;
        while (!refQ.isEmpty()) {
            Meal meal = refQ.poll();
            for (Food food : meal.getFoods()) {
                dailyFoodBST.addFood(food);
            }
        }
    }

    private void testMethod() {
        populateBST();

        Hashtable<String, Integer> foodCaloriesTable = dailyFoodBST.getFoodTable();

        // iterate through the Hashtable and check every key
        for (String foodName : foodCaloriesTable.keySet()) {
            int calories = foodCaloriesTable.get(foodName);
            System.out.println("Food: " + foodName + ", Calories: " + calories);
        }
    }
    public static void main(String[] args) {
        save.loadMeals();
        HashSet<Meal> meals = save.getMeals();
        launch(args);
        save.saveMeals();
    }
}