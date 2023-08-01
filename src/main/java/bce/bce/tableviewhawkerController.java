package bce.bce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class tableviewhawkerController {

    @javafx.fxml.FXML
    private TableView<hawkerbean> tblHawkers;
    Connection con;
    PreparedStatement pst;




    public void doFetchHawkers(ActionEvent actionEvent) {
        TableColumn<hawkerbean, String> name=new TableColumn<hawkerbean, String>("Hawker Name");
        name.setCellValueFactory(new PropertyValueFactory<>("hname")); //name of column
        name.setMinWidth(150);

        TableColumn<hawkerbean, String> mobile=new TableColumn<hawkerbean, String>("Hawker Mobile No");
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobile.setMinWidth(150);

        TableColumn<hawkerbean, String> alloarea=new TableColumn<hawkerbean, String>("Allocated Areas");
        alloarea.setCellValueFactory(new PropertyValueFactory<>("allo_areas"));
        alloarea.setMinWidth(150);

        TableColumn<hawkerbean, String> doj=new TableColumn<hawkerbean, String>("Date of joining");
        doj.setCellValueFactory(new PropertyValueFactory<>("doj"));
        doj.setMinWidth(150);
        tblHawkers.getColumns().clear();
        tblHawkers.getColumns().addAll(new ArrayList<>(Arrays.asList(name, mobile, alloarea, doj)));
        tblHawkers.setItems(FetchHawkers());

    }

    private ObservableList<hawkerbean> FetchHawkers() {
        ObservableList<hawkerbean>ary = FXCollections.observableArrayList();
        try {

            pst = con.prepareStatement("select * from hawker");
            ResultSet table=pst.executeQuery();

            while(table.next()) {
                String mno=table.getString("mobile");
                String name = table.getString("hname");
                String DOJ = String.valueOf(table.getDate("dob").toLocalDate());
                String alloarea=table.getString("area");

                hawkerbean ref=new hawkerbean(name, mno, alloarea, DOJ);
                ary.add(ref);
            }

        }
        catch(Exception ex) { ex.printStackTrace(); }
        return ary;
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

    }


}
