package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    private double anchorTopImageMargin = 60;
    private double anchorLeftImageMargin = 125;
    private int pointRadius = 7;
    private int imageWidth = 250;
    private int imageHeight = 220;

    private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY, offsetX, offsetY, newTranslateX, newTranslateY;
    private double currentStartX, currentStartY;

    private int currentPointId, xTextFieldId, yTextFieldId;
    private int pointIds = 0;

    private TextField textField, textField2;
    private Button deletePointButton;

    private ArrayList<ArrayList<Node>> allPoints;
    private ArrayList<Node> currentPoints, clickedPointList;
    private ArrayList<TextField> xCordsTextFields, yCordsTextFields;
    private ArrayList<ImageView> images;
    private ArrayList<AnchorPane> mainPanes;
    private ArrayList<Double> startXCoords, startYCoords;

    @FXML
    private ImageView imageView00, imageView10, imageView01, imageView11;
    @FXML
    private AnchorPane anchorGrid00, anchorGrid10, anchorGrid01, anchorGrid11;
    @FXML
    private ListView<VBox> pointsListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("images/testrtg.jpg");

        images = new ArrayList<>();
        images.add(imageView00);
        images.add(imageView10);
        images.add(imageView01);
        images.add(imageView11);

        mainPanes = new ArrayList<>();
        mainPanes.add(anchorGrid00);
        mainPanes.add(anchorGrid10);
        mainPanes.add(anchorGrid01);
        mainPanes.add(anchorGrid11);

        for(ImageView image : images){
            image.setImage(img);

            image.setFitWidth(imageWidth);
            image.setFitHeight(imageHeight);

            AnchorPane.setTopAnchor(image, anchorTopImageMargin);
            AnchorPane.setLeftAnchor(image, anchorLeftImageMargin);

            image.setOnMouseClicked(event -> {
                System.out.println("X: " + event.getX() + "Y: " + event.getY());
                makePoint(event.getX(), event.getY());
            });
        }

        allPoints = new ArrayList<>();
        xCordsTextFields = new ArrayList<>();
        yCordsTextFields = new ArrayList<>();
        clickedPointList = new ArrayList<>();
        startXCoords = new ArrayList<>();
        startYCoords = new ArrayList<>();
    }

    private void makePoint(double mouseX, double mouseY){
        Color generatedColor = generateColor();

        currentPoints = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            Node point = new Circle(mouseX + anchorLeftImageMargin, mouseY + anchorTopImageMargin, pointRadius, generatedColor);

            point.setId(String.valueOf(pointIds));

            currentPoints.add(point);
        }
        allPoints.add(currentPoints);

        for(int i = 0; i < mainPanes.size(); i++){
            mainPanes.get(i).getChildren().add(currentPoints.get(i));
        }

        startXCoords.add(mouseX);
        startYCoords.add(mouseY);

        Label title = new Label("Point");
        title.setTextFill(generatedColor);
        title.setFont(Font.font("Verdana", 17));
        title.setPadding(new Insets(5,0,0, 5));

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

        deletePointButton = new Button("Delete");
        deletePointButton.setId(String.valueOf(pointIds));

        VBox vb = new VBox();
        HBox hb = new HBox();
        HBox hbButtons = new HBox();
        hb.getChildren().addAll(label, textField, label2, textField2);
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hbButtons.getChildren().addAll(deletePointButton);
        hbButtons.setSpacing(10);
        hbButtons.setAlignment(Pos.BOTTOM_RIGHT);
        hbButtons.setPadding(new Insets(0, 15, 5, 0 ));
        vb.getChildren().addAll(title, hb, hbButtons);
        vb.setSpacing(6);
        pointsListView.getItems().add(vb);

        pointIds++;

        textField.setOnMouseClicked(event -> {
            xTextFieldId = Integer.parseInt(((TextField)(event.getSource())).getId());

            TextField focusedTextField = xCordsTextFields.get(xTextFieldId);

            focusedTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                ArrayList<Node> currentPoint = allPoints.get(xTextFieldId);
                double tmpX = startXCoords.get(xTextFieldId);

                if(!newValue.matches("\\d*")){
                    focusedTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }

                for(Node current : currentPoint){
                    try{
                        if(focusedTextField.getText().isEmpty())
                            current.setTranslateX(0 - tmpX);
                        else if(Integer.parseInt(focusedTextField.getText()) > imageWidth){
                            current.setTranslateX(imageWidth - tmpX);
                        }
                        else{
                            current.setTranslateX(Double.parseDouble(xCordsTextFields.get(xTextFieldId).getText()) - tmpX);
                        }
                    } catch (NumberFormatException ex){}
                }
            });
            validateInputs(focusedTextField, imageWidth);
        });

        textField2.setOnMouseClicked(event -> {
            yTextFieldId = Integer.parseInt(((TextField)(event.getSource())).getId());
            TextField focusedTextField = yCordsTextFields.get(yTextFieldId);

            focusedTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                ArrayList<Node> currentPoint = allPoints.get(yTextFieldId);
                double tmpY = startYCoords.get(yTextFieldId);

                if(!newValue.matches("\\d*")){
                    focusedTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }

                for(Node current : currentPoint){
                    try{
                        if(focusedTextField.getText().isEmpty())
                            current.setTranslateY(0 - tmpY);
                        else if(Integer.parseInt(focusedTextField.getText()) > imageHeight){
                            current.setTranslateY(imageHeight - tmpY);
                        }
                        else{
                            current.setTranslateY(Double.parseDouble(yCordsTextFields.get(yTextFieldId).getText()) - tmpY);
                        }
                    } catch (NumberFormatException ex){}
                }
            });
            validateInputs(focusedTextField, imageHeight);
        });

        deletePointButton.setOnAction(event -> {
            int clickedId = Integer.parseInt(((Button)(event.getSource())).getId());

            for(int i = 0; i < mainPanes.size(); i++){
                mainPanes.get(i).getChildren().remove(allPoints.get(clickedId).get(i));
            }
            pointsListView.getItems().remove(vb);
        });

        for(Node tmp : currentPoints){
            tmp.setOnMousePressed(event -> circleOnMousePressedEventHandler(event));
            tmp.setOnMouseDragged(event -> drag(event));
            tmp.setOnMouseReleased(event -> dragReleased(event));
        }
    }

    private void validateInputs(TextField focusedTextField, int imageParam) {
        focusedTextField.focusedProperty().addListener((arg0, oldValue, newValue) ->{
            if(focusedTextField.getText().isEmpty())
                focusedTextField.setText(String.valueOf(0));

            try{
                if (!newValue) {
                    if(Integer.parseInt(focusedTextField.getText()) > imageParam){
                        focusedTextField.setText(String.valueOf(imageParam));
                    }
                }
            } catch (NumberFormatException ex){
                focusedTextField.setText(String.valueOf(imageParam));
            }
        });
    }

    private void drag(MouseEvent event) {
        offsetX = event.getSceneX() - orgSceneX;
        offsetY = event.getSceneY() - orgSceneY;

        newTranslateX = orgTranslateX + offsetX;
        newTranslateY = orgTranslateY + offsetY;

        if(currentStartX + newTranslateX > imageWidth)
            newTranslateX = imageWidth - currentStartX;
        if(currentStartX + newTranslateX < 0)
            newTranslateX = -currentStartX;

        if(currentStartY + newTranslateY > imageHeight)
            newTranslateY = imageHeight - currentStartY;
        if(currentStartY + newTranslateY < 0)
            newTranslateY = -currentStartY;

        for(Node tmp : clickedPointList){
            tmp.setTranslateX(newTranslateX);
            tmp.setTranslateY(newTranslateY);
        }
    }

    private void dragReleased(MouseEvent event){
        offsetX = event.getSceneX() - orgSceneX;
        offsetY = event.getSceneY() - orgSceneY;

        TextField tempX = xCordsTextFields.get(currentPointId);
        TextField tempY = yCordsTextFields.get(currentPointId);

        double startX = startXCoords.get(currentPointId);
        double startY = startYCoords.get(currentPointId);

        double xNewPosition = startX + newTranslateX;
        double yNewPosition = startY + newTranslateY;

        tempX.setText(String.valueOf(xNewPosition).split("\\.")[0]);
        tempY.setText(String.valueOf(yNewPosition).split("\\.")[0]);

        clickedPointList.clear();
    }

    private void circleOnMousePressedEventHandler(MouseEvent event){
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();

        currentPointId = Integer.parseInt(((Circle)(event.getSource())).getId());

        clickedPointList.addAll(allPoints.get(currentPointId));

        currentStartX = startXCoords.get(currentPointId);
        currentStartY = startYCoords.get(currentPointId);

        orgTranslateX = clickedPointList.get(0).getTranslateX();
        orgTranslateY = clickedPointList.get(0).getTranslateY();
    }

    private Color generateColor(){
        Random rand = new Random();

        double r = rand.nextDouble();
        double g = rand.nextDouble();
        double b = rand.nextDouble();

        Color generatedColor = new Color(r, g, b, 1);

        generatedColor.brighter();

        return generatedColor;
    }

    @FXML
    private void deleteAllPoints(){

        for(ArrayList<Node> tmp : allPoints){
            for(int i = 0; i < mainPanes.size(); i++){
                mainPanes.get(i).getChildren().remove(tmp.get(i));
            }
        }

        allPoints.clear();
        xCordsTextFields.clear();
        yCordsTextFields.clear();
        startXCoords.clear();
        startYCoords.clear();
        clickedPointList.clear();
        pointsListView.getItems().clear();
        pointIds = 0;
    }
}
