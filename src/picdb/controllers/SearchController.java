package picdb.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SearchController extends AbstractController {
    @FXML
    public Button searchButton;
    public TextField searchText;

    private PictureScrollerController PSC;


    public void search(Event e) {
        searchButton.setStyle("-fx-background-color: #11111111; -fx-border-radius: 10 10 10 10; -fx-border-color:#1111FF55; -fx-border-width: 1;");
        PSC.loadPictures(searchText.getText());
    }

    public void hoverEnter(MouseEvent mouseEvent) {
        searchButton.setStyle("-fx-background-color: #11111111; -fx-border-radius: 10 10 10 10; -fx-border-width: 1; -fx-border-color:transparent;");
    }

    public void hoverExit(MouseEvent mouseEvent) {
        searchButton.setStyle("-fx-background-color: transparent; -fx-border-radius: 10 10 10 10; -fx-border-width: 1; -fx-border-color:transparent;");
    }

    public void searched(MouseEvent mouseEvent) {
        searchButton.setStyle("-fx-background-color: #11111111; -fx-border-radius: 10 10 10 10; -fx-border-color:transparent; -fx-border-width: 1;");
    }

    public void setPictureScrollerController(PictureScrollerController psc){
        this.PSC = psc;
    }

}
