package controllers;

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

    private int imageWidth = 250;
    private int imageHeight = 220;
    private double anchorTopImageMargin = 60;
    private double anchorLeftImageMargin = 125;
    private int pointRadius = 7;

    private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    private double clickedPointStartX, clickedPointStartY;

    private int clickedPointId, focusedTextFieldId;
    private int pointIds = 0;

    private ArrayList<ArrayList<Node>> allPoints;
    private ArrayList<Node> clickedPointList;
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
        Image img = new Image("images/rtgimage.jpg");

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

            image.setOnMouseClicked(event -> makePoint(event.getX(), event.getY()));
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

        ArrayList<Node> currentPoints = new ArrayList<>();

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

        Label xLabel = new Label("x =");

        TextField xCoordTextField = new TextField(String.valueOf(mouseX).split("\\.")[0]);
        xCoordTextField.setPrefWidth(60);
        xCoordTextField.setId(String.valueOf(pointIds));
        xCordsTextFields.add(xCoordTextField);

        Label yLabel = new Label("y =");

        TextField yCoordTextField = new TextField(String.valueOf(mouseY).split("\\.")[0]);
        yCoordTextField.setPrefWidth(60);
        yCoordTextField.setId(String.valueOf(pointIds));
        yCordsTextFields.add(yCoordTextField);

        Button deletePointButton = new Button("Delete");
        deletePointButton.setId(String.valueOf(pointIds));

        VBox vbPoints = new VBox();
        HBox hbInputs = new HBox();
        HBox hbButtons = new HBox();

        hbInputs.getChildren().addAll(xLabel, xCoordTextField, yLabel, yCoordTextField);
        hbInputs.setSpacing(5);
        hbInputs.setAlignment(Pos.CENTER);

        hbButtons.getChildren().addAll(deletePointButton);
        hbButtons.setSpacing(10);
        hbButtons.setAlignment(Pos.BOTTOM_RIGHT);
        hbButtons.setPadding(new Insets(0, 10, 5, 0 ));

        vbPoints.getChildren().addAll(title, hbInputs, hbButtons);
        vbPoints.setSpacing(6);
        pointsListView.getItems().add(vbPoints);

        pointIds++;

        xCoordTextField.setOnMouseClicked(event -> {
            focusedTextFieldId = Integer.parseInt(((TextField)(event.getSource())).getId());

            TextField focusedTextField = xCordsTextFields.get(focusedTextFieldId);

            focusedTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                ArrayList<Node> currentPoint = allPoints.get(focusedTextFieldId);
                double tmpX = startXCoords.get(focusedTextFieldId);

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
                            current.setTranslateX(Double.parseDouble(xCordsTextFields.get(focusedTextFieldId).getText()) - tmpX);
                        }
                    } catch (NumberFormatException ignored){}
                }
            });
            validateInputs(focusedTextField, imageWidth);
        });


        yCoordTextField.setOnMouseClicked(event -> {
            focusedTextFieldId = Integer.parseInt(((TextField)(event.getSource())).getId());
            TextField focusedTextField = yCordsTextFields.get(focusedTextFieldId);

            focusedTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                ArrayList<Node> currentPoint = allPoints.get(focusedTextFieldId);
                double tmpY = startYCoords.get(focusedTextFieldId);

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
                            current.setTranslateY(Double.parseDouble(yCordsTextFields.get(focusedTextFieldId).getText()) - tmpY);
                        }
                    } catch (NumberFormatException ignored){}
                }
            });
            validateInputs(focusedTextField, imageHeight);
        });

        deletePointButton.setOnAction(event -> {
            int clickedId = Integer.parseInt(((Button)(event.getSource())).getId());

            for(int i = 0; i < mainPanes.size(); i++){
                mainPanes.get(i).getChildren().remove(allPoints.get(clickedId).get(i));
            }
            pointsListView.getItems().remove(vbPoints);
        });

        for(Node tmp : currentPoints){
            tmp.setOnMousePressed(this::pointOnMousePressed);
            tmp.setOnMouseDragged(this::dragPoint);
            tmp.setOnMouseReleased(this::dragPointReleased);
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

    private void pointOnMousePressed(MouseEvent event){
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();

        clickedPointId = Integer.parseInt(((Circle)(event.getSource())).getId());

        clickedPointList.addAll(allPoints.get(clickedPointId));

        clickedPointStartX = startXCoords.get(clickedPointId);
        clickedPointStartY = startYCoords.get(clickedPointId);

        orgTranslateX = clickedPointList.get(0).getTranslateX();
        orgTranslateY = clickedPointList.get(0).getTranslateY();
    }

    private void dragPoint(MouseEvent event) {
        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;

        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        if(clickedPointStartX + newTranslateX > imageWidth)
            newTranslateX = imageWidth - clickedPointStartX;
        if(clickedPointStartX + newTranslateX < 0)
            newTranslateX = -clickedPointStartX;

        if(clickedPointStartY + newTranslateY > imageHeight)
            newTranslateY = imageHeight - clickedPointStartY;
        if(clickedPointStartY + newTranslateY < 0)
            newTranslateY = -clickedPointStartY;

        for(Node tmp : clickedPointList){
            tmp.setTranslateX(newTranslateX);
            tmp.setTranslateY(newTranslateY);

            TextField tempXCoords = xCordsTextFields.get(clickedPointId);
            TextField tempYCoords = yCordsTextFields.get(clickedPointId);

            double startX = startXCoords.get(clickedPointId);
            double startY = startYCoords.get(clickedPointId);

            double xNewPosition = startX + newTranslateX;
            double yNewPosition = startY + newTranslateY;

            tempXCoords.setText(String.valueOf(xNewPosition).split("\\.")[0]);
            tempYCoords.setText(String.valueOf(yNewPosition).split("\\.")[0]);
        }
    }

    private void dragPointReleased(MouseEvent event){
        clickedPointList.clear();
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

    private Color generateColor(){
        Random rand = new Random();

        double r = rand.nextDouble();
        double g = rand.nextDouble();
        double b = rand.nextDouble();

        Color generatedColor = new Color(r, g, b, 1);

        return generatedColor;
    }
}