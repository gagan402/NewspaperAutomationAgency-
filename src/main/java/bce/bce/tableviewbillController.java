package bce.bce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class tableviewbillController {
    @javafx.fxml.FXML
    private RadioButton radPending;
    @javafx.fxml.FXML
    private ToggleGroup rad;
    @javafx.fxml.FXML
    private RadioButton radPaid;
    @javafx.fxml.FXML
    private ComboBox<String> comboMobile;
    @javafx.fxml.FXML
    private TableView<billbean> table;
    ObservableList<billbean> list;
    Connection con;
    PreparedStatement pst;

    public void doExport(ActionEvent actionEvent) {
        try
        {
            writeExcel();
            makeAlert("Exported");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void writeExcel() throws IOException {
        Writer writer=null;
        try
        {
            File file=new File("something.csv");
            writer =new BufferedWriter(new FileWriter(file));
            String text="Mobile,Date(From),Date(T0),Bill,Bill Status\n";
            writer.write(text);
            for(billbean i:list)
            {
                text=i.getMobile()+","+i.getDFrom()+","+i.getDTo()+","+i.getBill()+","+i.getStatus()+"\n";
                writer.write(text);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            writer.flush();
            writer.close();
        }
    }

    public void doSearching(ActionEvent actionEvent) {
        if(radPending.isSelected())
        {
            TableColumn<billbean, String> mobile=new TableColumn<billbean, String>("Mobile");
            mobile.setCellValueFactory(new PropertyValueFactory<>("mobile")); //name of column
            mobile.setMinWidth(141);

            TableColumn<billbean, String> DFrom=new TableColumn<billbean, String>("Date(From)");
            DFrom.setCellValueFactory(new PropertyValueFactory<>("DFrom"));
            DFrom.setMinWidth(141);

            TableColumn<billbean, String> DTo=new TableColumn<billbean, String>("Date(T0)");
            DTo.setCellValueFactory(new PropertyValueFactory<>("DTo"));
            DTo.setMinWidth(141);

            TableColumn<billbean, String> Bill=new TableColumn<billbean, String>("Bill");
            Bill.setCellValueFactory(new PropertyValueFactory<>("Bill"));
            Bill.setMinWidth(141);

            TableColumn<billbean, String> status=new TableColumn<billbean, String>("Bill Status");
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            status.setMinWidth(141);

            table.getColumns().clear();
            table.getColumns().addAll(new ArrayList<>(Arrays.asList(mobile,DFrom,DTo,Bill,status)));
            table.setItems(FetchPending());


        }

        if(radPaid.isSelected())
        {
            TableColumn<billbean, String> mobile=new TableColumn<billbean, String>("Mobile");
            mobile.setCellValueFactory(new PropertyValueFactory<>("mobile")); //name of column
            mobile.setMinWidth(141);

            TableColumn<billbean, String> DFrom=new TableColumn<billbean, String>("Date(From)");
            DFrom.setCellValueFactory(new PropertyValueFactory<>("DFrom"));
            DFrom.setMinWidth(141);

            TableColumn<billbean, String> DTo=new TableColumn<billbean, String>("Date(T0)");
            DTo.setCellValueFactory(new PropertyValueFactory<>("DTo"));
            DTo.setMinWidth(141);

            TableColumn<billbean, String> Bill=new TableColumn<billbean, String>("Bill");
            Bill.setCellValueFactory(new PropertyValueFactory<>("Bill"));
            Bill.setMinWidth(141);

            TableColumn<billbean, String> status=new TableColumn<billbean, String>("Bill Status");
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            status.setMinWidth(141);

            table.getColumns().clear();
            table.getColumns().addAll(new ArrayList<>(Arrays.asList(mobile,DFrom,DTo,Bill,status)));
            table.setItems(FetchPaid());


        }
    }

    private ObservableList<billbean> FetchPaid() {
        list  = FXCollections.observableArrayList();
        ObservableList<billbean>ary = FXCollections.observableArrayList();
        try {

            pst = con.prepareStatement("select * from billgen");
            ResultSet table = pst.executeQuery();

            while (table.next()) {
                String mob = table.getString("mobile");
                String from = String.valueOf(table.getDate("dateFrom").toLocalDate());
                String to = String.valueOf(table.getDate("dateTo").toLocalDate());
                String bill = String.valueOf(table.getString("bill"));
                String status = String.valueOf(table.getString("billStatus"));
                String ans = "1";
                if (status.equals(ans))
                {
                    billbean ref = new billbean(mob, from, to, bill, status);
                    ary.add(ref);
                    list.add(ref);

                }
            }
        }
        catch(Exception ex) { ex.printStackTrace(); }
        return ary;
    }

    private ObservableList<billbean> FetchPending() {
        list  = FXCollections.observableArrayList();
        ObservableList<billbean>ary = FXCollections.observableArrayList();
        try {

            pst = con.prepareStatement("select * from billgen");
            ResultSet table = pst.executeQuery();

            while (table.next()) {
                String mob = table.getString("mobile");
                String from = String.valueOf(table.getDate("dateFrom").toLocalDate());
                String to = String.valueOf(table.getDate("dateTo").toLocalDate());
                String bill = String.valueOf(table.getString("bill"));
                String status = String.valueOf(table.getString("billStatus"));
                String ans = "0";
                if (status.equals(ans))
                {
                    billbean ref = new billbean(mob, from, to, bill, status);
                    ary.add(ref);
                    list.add(ref);

                }
            }
        }
             catch(Exception ex) { ex.printStackTrace(); }
            return ary;
    }

    public void doBillHistory(ActionEvent actionEvent) {
        TableColumn<billbean, String> mobile=new TableColumn<billbean, String>("Mobile");
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile")); //name of column
        mobile.setMinWidth(141);

        TableColumn<billbean, String> DFrom=new TableColumn<billbean, String>("Date(From)");
        DFrom.setCellValueFactory(new PropertyValueFactory<>("DFrom"));
        DFrom.setMinWidth(141);

        TableColumn<billbean, String> DTo=new TableColumn<billbean, String>("Date(T0)");
        DTo.setCellValueFactory(new PropertyValueFactory<>("DTo"));
        DTo.setMinWidth(141);

        TableColumn<billbean, String> Bill=new TableColumn<billbean, String>("Bill");
        Bill.setCellValueFactory(new PropertyValueFactory<>("Bill"));
        Bill.setMinWidth(141);

        TableColumn<billbean, String> status=new TableColumn<billbean, String>("Bill Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setMinWidth(141);

        table.getColumns().clear();
        table.getColumns().addAll(new ArrayList<>(Arrays.asList(mobile,DFrom,DTo,Bill,status)));
        table.setItems(FetchHistory());



    }

    private ObservableList<billbean> FetchHistory() {
        list  = FXCollections.observableArrayList();
        ObservableList<billbean>ary = FXCollections.observableArrayList();
        try {

            pst = con.prepareStatement("select * from billgen");
            ResultSet table=pst.executeQuery();

            while(table.next()) {
                String mob=table.getString("mobile");
                String from= String.valueOf(table.getDate("dateFrom").toLocalDate());
                String to= String.valueOf(table.getDate("dateTo").toLocalDate());
                String bill=String.valueOf(table.getString("bill"));
                String status=String.valueOf(table.getString("billStatus"));


                billbean ref=new billbean(mob,from,to,bill,status);
                ary.add(ref);
                list.add(ref);
            }

        }
        catch(Exception ex) { ex.printStackTrace(); }
        return ary;
    }

    void doFillMobile()
    {
        try {
            pst=con.prepareStatement("select distinct mobile from billgen");
            ResultSet table= pst.executeQuery();
            while(table.next())
            {
                String mob=table.getString("mobile");
                comboMobile.getItems().add(String.valueOf(mob));

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
        doFillMobile();
    }

}
