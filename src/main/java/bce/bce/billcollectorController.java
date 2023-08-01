package bce.bce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class billcollectorController {
    @javafx.fxml.FXML
    private TextField txtAmount;
    @javafx.fxml.FXML
    private ComboBox<String> comboMobile;
    @javafx.fxml.FXML
    private DatePicker dateFrom;
    @javafx.fxml.FXML
    private DatePicker dateTo;
    Connection con;
    PreparedStatement pst;

    public void doPaymentDone(ActionEvent actionEvent) {
        String mobile=comboMobile.getSelectionModel().getSelectedItem();

        try {
            pst=con.prepareStatement("select * from billgen where mobile=?");
            pst.setString(1, mobile);
            ResultSet table= pst.executeQuery();
            while(table.next())
            {
                pst=con.prepareStatement("update billgen set billStatus=? where mobile=?");
                pst.setInt(1, 1);
                pst.setString(2,mobile);
                int count=pst.executeUpdate();
                if(count!=0)
                {
                    makeAlert("Payment Done!");
                }
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void doBillingDetails(ActionEvent actionEvent) {
        String mob=comboMobile.getSelectionModel().getSelectedItem();

        try {
            pst=con.prepareStatement("select * from billgen where mobile=?");
            pst.setString(1, mob);
            ResultSet table= pst.executeQuery();
            while(table.next())
            {
               int ans=table.getInt("billStatus");
                LocalDate F= table.getDate("dateFrom").toLocalDate();
                LocalDate T= table.getDate("dateTo").toLocalDate();
                int amount =table.getInt("bill");

                if(ans==0)
                {
                    txtAmount.setText(String.valueOf(amount));
                    dateFrom.setValue(F);
                    dateTo.setValue(T);
                }
                if(ans==1)
                {
                    makeAlert("Payment is already done!");
                }


            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    void doFillMobile()
    {
        try {
            pst=con.prepareStatement("select distinct mobile from billgen");
            ResultSet table= pst.executeQuery();//table is 2d array//array of objects
            while(table.next())
            {
                String mob=table.getString("mobile");
                comboMobile.getItems().add(mob);
                //System.out.println(roll);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void makeAlert(String msg)
    {
        Alert A=new Alert(Alert.AlertType.CONFIRMATION);
        A.setContentText(msg);
        A.showAndWait();
    }
    @FXML
    void initialize()
    {
        con = mysqlconnector.doConnect();
        if (con == null) {
            System.out.println("Connection Problem");
        } else {
            System.out.println("Connected");
        }
        doFillMobile();

    }

}

