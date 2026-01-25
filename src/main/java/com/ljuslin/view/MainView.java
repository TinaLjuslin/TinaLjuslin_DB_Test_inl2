package com.ljuslin.view;

//import com.ljuslin.controller.ItemController;
import com.ljuslin.controller.ItemController;
import com.ljuslin.controller.MemberController;
/*
import com.ljuslin.controller.RentalController;
import com.ljuslin.controller.RevenueController;
*/
import com.ljuslin.controller.RentalController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


/**
 * Holds the main scene and calls other views to get tabs.
 *
 * @author Tina Ljuslin
 */
public class MainView extends View{
    private final String TOP_HEADER = "Wigells uthyrning av accessoarer för gentlemän";
    private MemberController memberController;
    private ItemController itemController;
 private RentalController rentalController;
//     private RevenueController revenueController;

    private Stage stage;
    private HBox topHBox;
    private double xOffset = 0;
    private double yOffset = 0;
    private Label topLabel;
    private Region topRegion;
    private Button minButton;
    private Button closeButton;
    private VBox root;
    private HBox bottomHBox;
    private Label bottomLabel;
    private Region bottomRegion;
    private Scene scene;
    private TabPane tabPane;
    private Tab memberTab;
    private Tab itemTab;
    private Tab rentalTab;
    //private Tab revenueTab;

    private Timeline timeline;
    private long totalSeconds = 0;

    public MainView() {
    }
    public MainView(MemberController memberController, ItemController itemController,
                    RentalController rentalController){
        this.memberController = memberController;
        this.itemController = itemController;
        this.rentalController = rentalController;
    }
    /*public MainView(MemberController memberController, ItemController itemController,
                    RentalController rentalController, RevenueController revenueController) {

        this.memberController = memberController;
        this.itemController = itemController;
        this.rentalController = rentalController;
        this.revenueController = revenueController;
    }
*/
    public void start(Stage stage) {
        // Om du inte har controllers för dessa än, skapa tomma tabbar så länge:
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        topLabel = new Label(TOP_HEADER);
        topRegion = new Region();
        HBox.setHgrow(topRegion, Priority.ALWAYS);
        minButton = new Button("_");
        closeButton = new Button("X");
        minButton.getStyleClass().setAll("min-button");
        closeButton.getStyleClass().setAll("close-button");
        minButton.setOnAction(ae -> {
            stage.setIconified(true);
        });
        closeButton.setOnAction(ae -> {
            System.exit(0);
        });
        topHBox = new HBox(topLabel, topRegion, minButton, closeButton);
        topHBox.getStyleClass().add("window-header");
        topHBox.setOnMousePressed(me -> {
            xOffset = me.getSceneX();
            yOffset = me.getSceneY();
        });
        topHBox.setOnMouseDragged(me -> {
            stage.setX((me.getScreenX() - xOffset));
            stage.setY((me.getScreenY() - yOffset));
        });
        root = new VBox();
        root.getStyleClass().add("rootVBox");
        bottomLabel = new Label();
        startTimer();
        bottomHBox = new HBox();

        bottomRegion = new Region();
        bottomHBox.getChildren().addAll(bottomRegion, bottomLabel);
        HBox.setHgrow(bottomRegion, Priority.ALWAYS);
        bottomHBox.getStyleClass().add("window-bottom");

        tabPane = new TabPane();
        root.getChildren().addAll(topHBox, tabPane, bottomHBox);

        scene = new Scene(root, 1000, 600);
        memberTab = memberController.getTab();
        memberTab.setClosable(false);
        itemTab = itemController.getTab();
        itemTab.setClosable(false);
        rentalTab = rentalController.getTab();
        rentalTab.setClosable(false);
  //      revenueTab = revenueController.getTab();
  //      revenueTab.setClosable(false);

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if (newTab != null) {
                    String tabTitle = newTab.getText();
                    tabClick(tabTitle);
                }
            }
        });
        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene.getStylesheets().add(css);
        //varför körs en select igen när nästa rad körs?
        tabPane.getTabs().addAll(memberTab, itemTab, rentalTab);//, revenueTab);
        stage.setScene(scene);

        memberController.setStage(stage);
        itemController.setStage(stage);
        rentalController.setStage(stage);
    //    revenueController.setStage(stage);
        memberController.setScene(scene);
        itemController.setScene(scene);
        rentalController.setScene(scene);
    //    revenueController.setScene(scene);
        stage.show();
    }

    private void tabClick(String tabName) {
        switch (tabName) {
            case "Medlemmar":
                memberController.populateTable();
                break;
            case "Varor":
                itemController.populateTable();
                break;
            case "Uthyrningar":
                rentalController.populateTable();
                break;
      //      case "Ekonomi":
      //          revenueController.populateTable();
        }
    }

    private void startTimer() {
        if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
            return;
        }
        bottomLabel.setText("00:00:00");
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), av -> {
            totalSeconds++;
            long h = totalSeconds / 3600;
            long min = (totalSeconds % 3600) / 60;
            long sec = totalSeconds % 60;

            String s = String.format("%02d:%02d:%02d", h, min, sec);
            bottomLabel.setText(s);
        });
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
