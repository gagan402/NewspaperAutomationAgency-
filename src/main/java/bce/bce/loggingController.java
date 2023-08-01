package bce.bce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class loggingController {
    String passcode="bce";
    @FXML
    private PasswordField pass;
    @FXML
    private ImageView log;


    public void doLogin(MouseEvent mouseEvent) {
        String pa=pass.getText();
        if(pa.equals(passcode))
        {
            logging();
        }
        else {
            makeAlert("WRONG PASSCODE!");
        }

    }

    private void logging() {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admindesk.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1150, 620);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();

            //to hide login page
            Scene s1=(Scene)log.getScene();
            s1.getWindow().hide();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    void makeAlert(String msg)
    {
        Alert A=new Alert(Alert.AlertType.INFORMATION);
        A.setContentText(msg);
        A.showAndWait();
    }



}
