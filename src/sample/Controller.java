package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    @FXML
    private ImageView imageView00, imageView10, imageView01, imageView11;

    @FXML
    private AnchorPane anchorGrid00, anchorGrid10, anchorGrid01, anchorGrid11;

    @FXML
    private ListView<VBox> pointsList;

    private TextField textField, textField2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("images/testrtg.jpg");
        imageView00.setImage(img);
        imageView10.setImage(img);
        imageView01.setImage(img);
        imageView11.setImage(img);

        imageView00.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                System.out.println("X: " + event.getX() + "Y: " + event.getY());
                makePoint(event.getX(), event.getY());

            }
        });

        imageView10.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("X: " + event.getX() + "Y: " + event.getY());
                makePoint(event.getX(), event.getY());
            }
        });

        imageView01.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("X: " + event.getX() + "Y: " + event.getY());
                makePoint(event.getX(), event.getY());
            }
        });

        imageView11.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("X: " + event.getX() + "Y: " + event.getY());
                makePoint(event.getX(), event.getY());
            }
        });
    }

    public void makePoint(double mouseX, double mouseY){
        Node point = new Circle(mouseX + 125, mouseY + 60, 10, Color.RED);
        Node point2 = new Circle(mouseX + 125, mouseY + 60, 10, Color.RED);
        Node point3 = new Circle(mouseX + 125, mouseY + 60, 10, Color.RED);
        Node point4 = new Circle(mouseX + 125, mouseY + 60, 10, Color.RED);

        ArrayList<Node> points = new ArrayList<>();
        points.add(point);
        points.add(point2);
        points.add(point3);
        points.add(point4);

        anchorGrid00.getChildren().add(point);
        anchorGrid10.getChildren().add(point2);
        anchorGrid01.getChildren().add(point3);
        anchorGrid11.getChildren().add(point4);

        Label title = new Label("Point");
        Label label = new Label("x =");
        textField = new TextField(String.valueOf(mouseX));
        textField.setPrefWidth(60);
        textField.setEditable(false);
        Label label2 = new Label("y =");
        textField2 = new TextField(String.valueOf(mouseY));
        textField2.setPrefWidth(60);
        textField2.setEditable(false);
        VBox vb = new VBox();
        HBox hb = new HBox();
        hb.getChildren().addAll(label, textField, label2, textField2);
        hb.setSpacing(10);
        vb.getChildren().addAll(title, hb);
        vb.setSpacing(6);
        pointsList.getItems().add(vb);
        System.out.println(pointsList.getSelectionModel().getSelectedIndex());

        for(Node tmp : points){
            tmp.setOnMousePressed(event -> circleOnMousePressedEventHandler(event));
            tmp.setOnMouseDragged(event -> drag(event));
        }

//        point.setOnMousePressed(event -> circleOnMousePressedEventHandler(event));
//        point.setOnMouseDragged(event -> drag(event));
//        point2.setOnMousePressed(event -> circleOnMousePressedEventHandler(event));
//        point2.setOnMouseDragged(event -> drag(event));
//        point3.setOnMousePressed(event -> circleOnMousePressedEventHandler(event));
//        point3.setOnMouseDragged(event -> drag(event));
//        point4.setOnMousePressed(event -> circleOnMousePressedEventHandler(event));
//        point4.setOnMouseDragged(event -> drag(event));


    }
    public void drag(MouseEvent t) {
//        Node n = (Node)event.getSource();
//        System.out.println("X after: " + (event.getX() - 125) + "Y after: " + (event.getY() - 60));
//        n.setTranslateX(event.getX() - 125);
//        n.setTranslateY(event.getY() - 60);
////        n.set
////        n.setTranslateY(n.getTranslateY()+ event.getY() - 60);
//        System.out.println(event.getX() - 125);
        double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        ((Circle)(t.getSource())).setTranslateX(newTranslateX);
        ((Circle)(t.getSource())).setTranslateY(newTranslateY);
    }

    public void circleOnMousePressedEventHandler(MouseEvent t){
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
        orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
    }


    public void handleMouseClick(MouseEvent event) {
        System.out.println("clicked on " + pointsList.getSelectionModel().getSelectedItem());
        pointsList.getSelectionModel().getSelectedIndex();

    }
}
