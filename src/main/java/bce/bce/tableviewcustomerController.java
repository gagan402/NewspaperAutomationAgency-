package bce.bce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class tableviewcustomerController {
    @javafx.fxml.FXML
    private ComboBox<String> comboAreas;
    @javafx.fxml.FXML
    private ComboBox<String> comboPapers;
    Connection con;
    PreparedStatement pst;
    @javafx.fxml.FXML
    private TableView<customerbean> tblCusto;
    ObservableList<customerbean> list;
    public void ExportToExcel(ActionEvent actionEvent) {
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
            File file=new File("something1.csv");
            writer =new BufferedWriter(new FileWriter(file));
            String text="Mobile,Customer Name,Selected Papers,Area,Hawker Allocated,Date of Joining\n";
            writer.write(text);
            for(customerbean i:list)
            {
                text=i.getMobile()+","+i.getName()+","+i.getPapers()+","+i.getAreas()+","+i.getHawker()+","+i.getDate()+"\n";
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
    public void doFetch(ActionEvent actionEvent)
    {
        TableColumn<customerbean, String> mobile=new TableColumn<customerbean, String>("Mobile No.");
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile")); //name of column
        mobile.setMinWidth(100);

        TableColumn<customerbean, String> name=new TableColumn<customerbean, String>("Customer Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(130);

        TableColumn<customerbean, String> papers=new TableColumn<customerbean, String>("Selected papers");
        papers.setCellValueFactory(new PropertyValueFactory<>("papers"));
        papers.setMinWidth(130);

        TableColumn<customerbean, String> areas=new TableColumn<customerbean, String>("Area");
        areas.setCellValueFactory(new PropertyValueFactory<>("areas"));
        areas.setMinWidth(100);

        TableColumn<customerbean, String> hawker=new TableColumn<customerbean, String>("Hawker Allocated");
        hawker.setCellValueFactory(new PropertyValueFactory<>("hawker"));
        hawker.setMinWidth(130);

        TableColumn<customerbean, String> date=new TableColumn<customerbean, String>("Date of Joining");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        date.setMinWidth(130);

        tblCusto.getColumns().clear();
        tblCusto.getColumns().addAll(new ArrayList<>(Arrays.asList(mobile,name,papers,areas,hawker,date)));
        tblCusto.setItems(FetchPapers());

    }


    int isSubstring(String s1, String s2)
    {
        int M = s1.length();
        int N = s2.length();

        /* A loop to slide pat[] one by one */
        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++)
                if (s2.charAt(i + j) != s1.charAt(j))
                    break;

            if (j == M)
                return 1;
        }

        return 0;
    }

    private ObservableList<customerbean> FetchPapers()
    {
        list  = FXCollections.observableArrayList();
        String PAPER=comboPapers.getSelectionModel().getSelectedItem();
        ObservableList<customerbean>ary = FXCollections.observableArrayList();
        try {

            pst = con.prepareStatement("select * from customer");
            ResultSet table = pst.executeQuery();

            while (table.next()) {
                String mobile = table.getString("mobile");
                String name = table.getString("cname");
                String papers = table.getString("spapers");
                String areas = table.getString("area");
                // System.out.println(areas);
                String hawker = table.getString("hawker");
                String date = String.valueOf(table.getDate("dos").toLocalDate());
                //System.out.println(date);
                int ans=isSubstring(PAPER,papers);
                if(ans==1) {
                    customerbean ref = new customerbean(mobile, name, papers, areas, hawker, date);
                    ary.add(ref);
                    list.add(ref);
                }


            }
        }
        catch(Exception ex) { ex.printStackTrace(); }
        return ary;

    }

    public void doFilter(ActionEvent actionEvent) {


        TableColumn<customerbean, String> mobile=new TableColumn<customerbean, String>("Mobile No.");
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile")); //name of column
        mobile.setMinWidth(100);

        TableColumn<customerbean, String> name=new TableColumn<customerbean, String>("Customer Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(130);

        TableColumn<customerbean, String> papers=new TableColumn<customerbean, String>("Selected papers");
        papers.setCellValueFactory(new PropertyValueFactory<>("papers"));
        papers.setMinWidth(130);

        TableColumn<customerbean, String> areas=new TableColumn<customerbean, String>("Area");
        areas.setCellValueFactory(new PropertyValueFactory<>("areas"));
        areas.setMinWidth(100);

        TableColumn<customerbean, String> hawker=new TableColumn<customerbean, String>("Hawker Allocated");
        hawker.setCellValueFactory(new PropertyValueFactory<>("hawker"));
        hawker.setMinWidth(130);

        TableColumn<customerbean, String> date=new TableColumn<customerbean, String>("Date of Joining");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        date.setMinWidth(130);

        tblCusto.getColumns().clear();
        tblCusto.getColumns().addAll(new ArrayList<>(Arrays.asList(mobile,name,papers,areas,hawker,date)));
        tblCusto.setItems(FetchAreas());

    }

    private ObservableList<customerbean> FetchAreas() {
        list  = FXCollections.observableArrayList();
        String AREA=comboAreas.getSelectionModel().getSelectedItem();
        ObservableList<customerbean>ary = FXCollections.observableArrayList();
        try {

            pst = con.prepareStatement("select * from customer");
            ResultSet table=pst.executeQuery();

            while(table.next()) {
                String mobile=table.getString("mobile");
                String name = table.getString("cname");
                String papers = table.getString("spapers");
                String areas = table.getString("area");
               // System.out.println(areas);
                String hawker = table.getString("hawker");
                String date = String.valueOf(table.getDate("dos").toLocalDate());
                //System.out.println(date);

                   if(AREA.equals(areas))
                   {
                       customerbean ref = new customerbean(mobile, name, papers, areas, hawker, date);
                       ary.add(ref);
                       list.add(ref);
                   }

            }

        }
        catch(Exception ex) { ex.printStackTrace(); }
        return ary;
    }


    void doFillAreas()
    {
        try {
            pst=con.prepareStatement("select distinct area from customer");
            ResultSet table= pst.executeQuery();
            while(table.next())
            {
                String area=table.getString("area");
                comboAreas.getItems().add(String.valueOf(area));

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    void doFillpapers()
    {
        try {
            pst=con.prepareStatement("select distinct pname from papers");
            ResultSet table= pst.executeQuery();
            while(table.next())
            {
                String pap=table.getString("pname");
                comboPapers.getItems().add(String.valueOf(pap));

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
        con=mysqlconnector.doConnect();
        if(con==null)
        {
            System.out.println("Connection Problem");
        }
        else
        {
            System.out.println("Connected");
        }
        doFillAreas();
        doFillpapers();

    }


}
