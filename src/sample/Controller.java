package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private double offsetX, offsetY;
    private double currentX, currentY;
    private double newTranslateX, newTranslateY;

    private int chosenPointIndex;
    private int currentPointId = 0;

    @FXML
    private ImageView imageView00, imageView10, imageView01, imageView11;

    @FXML
    private AnchorPane anchorGrid00, anchorGrid10, anchorGrid01, anchorGrid11;

    @FXML
    private ListView<VBox> pointsList;

    private TextField textField, textField2;

    private Button test;

    private ArrayList<ArrayList<Node>> test2;

    private ArrayList<Double> cordX, cordY;

    private ArrayList<Node> points, points2, testPoints;

    private ArrayList<TextField> xCordsTextFields, yCordsTextFields;

    private int pointIds = 0;

    private int clickedPointId;

    private ArrayList<ArrayList> clickedPointList2;
    private ArrayList<Node> clickedPointList;

    private ArrayList<Double> startX, startY;

    double tempCurrentX, tempCurrentY;

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

        cordX = new ArrayList<>();
        cordY = new ArrayList<>();
        points = new ArrayList<>();
        test2 = new ArrayList<>();
        xCordsTextFields = new ArrayList<>();
        yCordsTextFields = new ArrayList<>();
        testPoints = new ArrayList<>();
        clickedPointList = new ArrayList<>();
        clickedPointList2 = new ArrayList<>();
        startX = new ArrayList<>();
        startY = new ArrayList<>();
    }

    public void makePoint(double mouseX, double mouseY){
        Color generatedColor = generateColor();
        System.out.println("COLOR: " + generatedColor);

        Node point = new Circle(mouseX + 125, mouseY + 60, 10, generatedColor);
        Node point2 = new Circle(mouseX + 125, mouseY + 60, 10, generatedColor);
        Node point3 = new Circle(mouseX + 125, mouseY + 60, 10, generatedColor);
        Node point4 = new Circle(mouseX + 125, mouseY + 60, 10, generatedColor);

        point.setId(String.valueOf(pointIds));
        point2.setId(String.valueOf(pointIds));
        point3.setId(String.valueOf(pointIds));
        point4.setId(String.valueOf(pointIds));

        points2 = new ArrayList<>();

        points.add(point);
        points.add(point2);
        points.add(point3);
        points.add(point4);

        points2.add(point);
        points2.add(point2);
        points2.add(point3);
        points2.add(point4);

        for(Node tmp : points2){
            System.out.println(tmp);
        }
        test2.add(points2);

        cordX.add(mouseX);
        cordY.add(mouseY);

        anchorGrid00.getChildren().add(point);
        anchorGrid10.getChildren().add(point2);
        anchorGrid01.getChildren().add(point3);
        anchorGrid11.getChildren().add(point4);

        currentX = mouseX;
        currentY = mouseY;

        startX.add(currentX);
        startY.add(currentY);

        Label title = new Label("Point");
        Label label = new Label("x =");
        textField = new TextField(String.valueOf(mouseX).split("\\.")[0]);
        textField.setPrefWidth(60);
        textField.setId(String.valueOf(pointIds));
        xCordsTextFields.add(textField);
        Label label2 = new Label("y =");
        textField2 = new TextField(String.valueOf(mouseY).split("\\.")[0]);
        textField2.setPrefWidth(60);
        textField2.setId(String.valueOf(pointIds));
        yCordsTextFields.add(textField2);
        test = new Button("Test");
        VBox vb = new VBox();
        HBox hb = new HBox();
        hb.getChildren().addAll(label, textField, label2, textField2);
        hb.setSpacing(10);
        vb.getChildren().addAll(title, hb, test);
        vb.setSpacing(6);
        pointsList.getItems().add(vb);

        pointIds++;
        currentPointId += 1;
        System.out.println(pointsList.getSelectionModel().getSelectedIndex());

        textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("XTEXT" + textField.getText());
                System.out.println(((TextField)(event.getSource())).getId());
            }
        });

        test.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println("chosen: " + chosenPointIndex);

                ArrayList<Node> currentPoint = test2.get(chosenPointIndex);

                double tmpX = cordX.get(chosenPointIndex);
                double tmpY = cordY.get(chosenPointIndex);

                System.out.println("DUPA: " + xCordsTextFields.get(chosenPointIndex).getText());
                System.out.println("DUPA: " + yCordsTextFields.get(chosenPointIndex).getText());

                for(Node current : currentPoint){
                    current.setTranslateX(Double.parseDouble(xCordsTextFields.get(chosenPointIndex).getText()) - tmpX);
                    current.setTranslateY(Double.parseDouble(yCordsTextFields.get(chosenPointIndex).getText()) - tmpY);
                }

            }
        });

        for(Node tmp : points){
            tmp.setOnMousePressed(event -> circleOnMousePressedEventHandler(event));
            tmp.setOnMouseDragged(event -> drag(event));
            tmp.setOnMouseReleased(event -> dragReleased(event));
        }
    }

    public void drag(MouseEvent event) {
        offsetX = event.getSceneX() - orgSceneX;
        offsetY = event.getSceneY() - orgSceneY;

        newTranslateX = orgTranslateX + offsetX;
        newTranslateY = orgTranslateY + offsetY;

//        ((Circle)(event.getSource())).setTranslateX(newTranslateX);
//        ((Circle)(event.getSource())).setTranslateY(newTranslateY);

        for(Node tmp : clickedPointList){
            tmp.setTranslateX(newTranslateX);
            tmp.setTranslateY(newTranslateY);
        }
    }

    public void dragReleased(MouseEvent event){
        offsetX = event.getSceneX() - orgSceneX;
        offsetY = event.getSceneY() - orgSceneY;

        TextField tempX = xCordsTextFields.get(currentPointId);
        TextField tempY = yCordsTextFields.get(currentPointId);

        double obecneX = startX.get(currentPointId);
        double obecneY = startY.get(currentPointId);

        tempX.setText(String.valueOf((obecneX + newTranslateX)).split("\\.")[0]);
        tempY.setText(String.valueOf((obecneY + newTranslateY)).split("\\.")[0]);

        clickedPointList.clear();
    }

    public void circleOnMousePressedEventHandler(MouseEvent event){
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();

        currentPointId = Integer.parseInt(((Circle)(event.getSource())).getId());

        TextField tempX = xCordsTextFields.get(currentPointId);
        TextField tempY = yCordsTextFields.get(currentPointId);

        tempCurrentX = Double.parseDouble(tempX.getText());
        tempCurrentY = Double.parseDouble(tempY.getText());

        clickedPointList.addAll(test2.get(currentPointId));

        orgTranslateX = clickedPointList.get(0).getTranslateX();
        orgTranslateY = clickedPointList.get(0).getTranslateY();

        System.out.println("ID PUNKTU: " + ((Circle)(event.getSource())).getId());

    }


    public void handleMouseClick(MouseEvent event) {
        chosenPointIndex = pointsList.getSelectionModel().getSelectedIndex();
        System.out.println(pointsList.getSelectionModel().getSelectedIndex());
        for(Double temp : cordX){
            System.out.println(temp);
        }
    }

    public Color generateColor(){
        Random rand = new Random();

        double r = rand.nextDouble();
        double g = rand.nextDouble();
        double b = rand.nextDouble();

        Color generatedColor = new Color(r, g, b, 1);

        generatedColor.brighter();

        return generatedColor;
    }
}
