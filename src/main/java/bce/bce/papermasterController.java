package bce.bce;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class papermasterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboNewspaper;

    @FXML
    private TextField txtPrice;

    Connection con;
    PreparedStatement pst;



    @FXML
    void btnAvail(ActionEvent event) {
        String Pname=comboNewspaper.getSelectionModel().getSelectedItem();
        float Pprice=Float.parseFloat(txtPrice.getText());
        try
        {
            pst=con.prepareStatement("insert into papers values(?,?)");
            pst.setString(1, Pname);
            pst.setFloat(2, Pprice);
            int count=pst.executeUpdate();
            //System.out.println(Pname);
            if(count!=0)
            {
                makeAlert("Newspaper "+Pname+" of  price Rs."+Pprice+" is Recorded!");
            }


        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @FXML
    void btnEdit(ActionEvent event) {

        String Pname=comboNewspaper.getSelectionModel().getSelectedItem();
        float Pprice=Float.parseFloat(txtPrice.getText());
        try
        {
            pst=con.prepareStatement("update papers set pprice=? where pname=?");
            pst.setString(2, Pname);
            pst.setFloat(1, Pprice);
            int count=pst.executeUpdate();
            //System.out.println(Pname);
            if(count!=0)
            {
                makeAlert("Newspaper "+Pname+"'s  price is Updated to "+Pprice+" !");
            }

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @FXML
    void doPaperSearch(MouseEvent event) {
        try {
            String PNAME=comboNewspaper.getSelectionModel().getSelectedItem();
            pst=con.prepareStatement("select * from papers where pname=?");
            pst.setString(1,PNAME);
            ResultSet table= pst.executeQuery();//table is 2d array//array of objects
            while(table.next())
            {
                Float Pr=table.getFloat("pprice");
                txtPrice.setText(String.valueOf(Pr));

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @FXML
    void doWithdraw(ActionEvent event) {

        try
        {
            String Pname=comboNewspaper.getSelectionModel().getSelectedItem();
            pst=con.prepareStatement("delete from papers where pname=?");
            pst.setString(1,Pname);
            int count=pst.executeUpdate();
            if(count!=0)
            {
                makeAlert("Newapaper "+Pname+" is Deleted !");

            }

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e);
        }


    }


    void makeAlert(String msg)
    {
        Alert A=new Alert(AlertType.CONFIRMATION);
        //A.setTitle("Selected Newspaper And Its Price");
        //A.setHeaderText("Look At Information");
        A.setContentText(msg);
        A.showAndWait();

    }

    void doFillPapers()
    {
        try {
            pst=con.prepareStatement("select distinct pname from papers");
            ResultSet table= pst.executeQuery();//table is 2d array//array of objects
            while(table.next())
            {
                String pap=table.getString("pname");
                comboNewspaper.getItems().add(pap);
                //System.out.println(pap);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        doFillPapers();


    }

}
