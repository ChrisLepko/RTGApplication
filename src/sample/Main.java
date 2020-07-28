package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("RTGApplication");

        Scene scene = new Scene(root);
        scene.getStylesheets().add("sample/test.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    private void moveCircleOnMousePress(Scene scene, final Circle circle, final TranslateTransition transition) {
//        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override public void handle(MouseEvent event) {
//                if (!event.isControlDown()) {
//                    circle.setCenterX(event.getSceneX());
//                    circle.setCenterY(event.getSceneY());
//                } else {
//                    transition.setToX(event.getSceneX() - circle.getCenterX());
//                    transition.setToY(event.getSceneY() - circle.getCenterY());
//                    transition.playFromStart();
//                }
//            }
//        });
//    }


    public static void main(String[] args) {
        launch(args);
    }
}
