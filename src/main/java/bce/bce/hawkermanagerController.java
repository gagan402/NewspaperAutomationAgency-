package bce.bce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class hawkermanagerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView Pic;
    @FXML
    private ComboBox<String> combohName;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAllocatedAreas;
    @FXML
    private TextField txtMobile;

    @FXML
    private ComboBox<String> txtTotalAreas;

    @FXML
    private Label txtxPath;

    Connection con;
    PreparedStatement pst;



    @FXML
    void doEnroll(ActionEvent event) {
        String Hname=combohName.getSelectionModel().getSelectedItem();
        String Mobile=txtMobile.getText();
        String Add=txtAddress.getText();
        String Area=txtAllocatedAreas.getText();
        String PicPath=txtxPath.getText();

        try
        {
            pst=con.prepareStatement("insert into hawker values(?,?,current_date(),?,?,?)");
            pst.setString(1,Hname);
            pst.setString(2,Mobile);
            pst.setString(3,Add);
            pst.setString(4,Area);
            pst.setString(5,PicPath);
            int count=pst.executeUpdate();
            if(count!=0)
            {
                makeAlert("Record Enrolled");
            }

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();


        }

    }

    @FXML
    void doFire(ActionEvent event) {
        try
        {
            String Hname=combohName.getSelectionModel().getSelectedItem();
            pst=con.prepareStatement("delete from hawker where hname=?");
            pst.setString(1, Hname);
            int count=pst.executeUpdate();
            if(count!=0)
            {
               makeAlert(Hname+" is fired.");
            }

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.toString());
        }


    }

    @FXML
    void doNew(ActionEvent event) {

        combohName.getSelectionModel().clearSelection();
        combohName.setValue(null);
        txtMobile.setText("");
        txtAddress.setText("");
        txtTotalAreas.getSelectionModel().clearSelection();
        txtTotalAreas.setValue(null);
        ans="";
        txtAllocatedAreas.setText("");
        txtxPath.setText("");

        Pic.setImage(null);
    }

    @FXML
    void doSearchNames(MouseEvent event) {
        try {
            String Hname=combohName.getSelectionModel().getSelectedItem();
            pst=con.prepareStatement("select * from hawker where hname=?");
            pst.setString(1, Hname);
            ResultSet table= pst.executeQuery();//table is 2d array//array of objects
            while(table.next())
            {
                String Mobile=table.getString("mobile");
                String Add=table.getString("address");
                String Area=table.getString("area");
                String PicPath=table.getString("picpath");


                txtMobile.setText(Mobile);
                txtAddress.setText(Add);
                txtAllocatedAreas.setText(Area);
                txtxPath.setText(PicPath);


                Image img=new Image(PicPath);
                Pic.setImage(img);
                //Pic.setImage(new Image(new FileInputStream(PicPath)));


            }
        } catch (SQLException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void doSelectPic(MouseEvent event) {
        FileChooser FC=new FileChooser();
        FC.setTitle("Open Resource file");
        FC.getExtensionFilters().addAll(new ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        File selectedFile=FC.showOpenDialog(null);

        if(selectedFile!=null)
        {
            txtxPath.setText(selectedFile.getPath());
            Image img=new Image(selectedFile.toURI().toString());
            System.out.println(selectedFile.toURI().toString());
            Pic.setImage(img);
            txtxPath.setText(selectedFile.toURI().toString());

        }
        else
        {
            txtxPath.setText("No picture");
        }

    }

    @FXML
    void doUpdate(ActionEvent event) {

        String Hname=combohName.getSelectionModel().getSelectedItem();
        String Mobile=txtMobile.getText();
        String Add=txtAddress.getText();
        String Area=txtAllocatedAreas.getText();
        String PicPath=txtxPath.getText();

        try
        {
            pst=con.prepareStatement("update hawker set mobile=?,dob=current_date(),address=?,area=?,picpath=? where hname=?");
            pst.setString(5,Hname);
            pst.setString(1,Mobile);
            pst.setString(2,Add);
            pst.setString(3,Area);
            pst.setString(4,PicPath);
            int count=pst.executeUpdate();
            if(count!=0)
            {
                makeAlert("Record Updated");
            }

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }
    void doFillHnames()
    {
        try {
            pst=con.prepareStatement("select distinct hname from hawker");
            ResultSet table= pst.executeQuery();//table is 2d array//array of objects
            while(table.next())
            {
                String Hname=table.getString("hname");
                combohName.getItems().add(String.valueOf(Hname));
               // System.out.println(Hname);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    String ans="";
    @FXML
    void doOnTotalComboClick(ActionEvent event) {

        String item = txtTotalAreas.getSelectionModel().getSelectedItem();

        ans=ans+item+",";
        String S=ans.substring(0,ans.length()-1);
        txtAllocatedAreas.setText(S);
        //System.out.println(item+" ");
    }



    void makeAlert(String msg)
    {
        Alert A=new Alert(AlertType.CONFIRMATION);
        //A.setTitle("Selected Newspaper And Its Price");
        //A.setHeaderText("Look At Information");
        A.setContentText(msg);
        A.showAndWait();

    }

    @FXML
    void initialize() {
        con=mysqlconnector.doConnect();
        if(con==null)
        {
            System.out.println("Connection Problem");
        }
        else
        {
            System.out.println("Connected");
        }
        dob.setValue(LocalDate.now());
        ArrayList<String> items=new ArrayList<String>(Arrays.asList("A","B","C","D"));
        txtTotalAreas.getItems().addAll(items);

        doFillHnames();
        assert combohName != null : "fx:id=\"combohName\" was not injected: check your FXML file 'hawkerView.fxml'.";
        assert dob != null : "fx:id=\"dob\" was not injected: check your FXML file 'hawkerView.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'hawkerView.fxml'.";
        assert txtAllocatedAreas != null : "fx:id=\"txtAllocatedAreas\" was not injected: check your FXML file 'hawkerView.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'hawkerView.fxml'.";
        assert txtTotalAreas != null : "fx:id=\"txtTotalAreas\" was not injected: check your FXML file 'hawkerView.fxml'.";
        assert txtxPath != null : "fx:id=\"txtxPath\" was not injected: check your FXML file 'hawkerView.fxml'.";

    }


}
