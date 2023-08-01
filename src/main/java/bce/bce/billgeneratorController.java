package bce.bce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static java.time.temporal.ChronoUnit.DAYS;

public class billgeneratorController {
    @javafx.fxml.FXML
    private ComboBox<String> comboMobile;
    @javafx.fxml.FXML
    private TextField txtNewspaper;
    @javafx.fxml.FXML
    private TextField txtPrices;
    @javafx.fxml.FXML
    private TextField txtTotalPrices;
    @javafx.fxml.FXML
    private TextField txtMissing;
    @javafx.fxml.FXML
    private DatePicker lastDate;
    @javafx.fxml.FXML
    private DatePicker currentDate;
    @javafx.fxml.FXML
    private TextField txtBill;
    Connection con;
    PreparedStatement pst;
    void doFillMobile()
    {
        try {
            pst=con.prepareStatement("select distinct mobile from customer");
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

    public void doGenerateBill(ActionEvent actionEvent) {
        String mob=comboMobile.getSelectionModel().getSelectedItem();
        LocalDate dateBefore=lastDate.getValue();
        LocalDate dateAfter=currentDate.getValue();
        long days=DAYS.between(dateBefore,dateAfter);

        //System.out.println(days);
        int DAY=(int)days - Integer.parseInt(txtMissing.getText());
        //System.out.println(DAY);
        int bill=DAY*Integer.parseInt(txtTotalPrices.getText());
        txtBill.setText(String.valueOf(bill));
        try
        {
            pst=con.prepareStatement("insert into billgen values(?,?,?,?,?)");
            pst.setString(1,mob);
            pst.setDate(2, Date.valueOf(dateBefore));
            pst.setDate(3, Date.valueOf(dateAfter));
            pst.setInt(4,bill);
            pst.setInt(5,0);
            int count=pst.executeUpdate();
            if(count!=0)
            {
                makeAlert("Bill generated and saved!");
            }


        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            //throw new RuntimeException("uncaught", e);
        }




    }

    public void OnClickSearch(MouseEvent mouseEvent) throws SQLException {
        String mob=comboMobile.getSelectionModel().getSelectedItem();
        String pap="";
        String pri="";
        try {

            pst = con.prepareStatement("select * from customer where mobile=?");
            pst.setString(1, mob);
            ResultSet table = pst.executeQuery();
            while (table.next()) {
                pap = table.getString("spapers");
                pri = table.getString("sprices");
                txtNewspaper.setText(pap);
                txtPrices.setText(pri);
            }
            int tot = 0;
            String[] res = pri.split(",", 0);
            for (String myStr : res) {
                int ans = Integer.parseInt(myStr);
                tot = tot + ans;
            }
            txtTotalPrices.setText(String.valueOf(tot));


            pst = con.prepareStatement("select * from billgen where mobile=?");
            pst.setString(1, mob);
            ResultSet table2 = pst.executeQuery();
            int flag = 0;
            while (table2.next()) {

               java.sql.Date D = table2.getDate("dateTo");
                lastDate.setValue(D.toLocalDate());
                flag = 1;
            }

                if (flag == 0)
                {
                    pst = con.prepareStatement("select * from customer where mobile=?");
                    pst.setString(1, mob);
                    ResultSet table3 = pst.executeQuery();
                    while (table3.next()) {
                       java.sql.Date D = table3.getDate("dos");
                        lastDate.setValue(D.toLocalDate());

                    }
                }


        }
        catch(Exception  e)
        {
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
    void initialize() {
        con = mysqlconnector.doConnect();
        if (con == null) {
            System.out.println("Connection Problem");
        } else {
            System.out.println("Connected");
        }
        doFillMobile();

    }

}
