package bce.bce;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class customermanagerController {

    @javafx.fxml.FXML
    private TextField txtName;
    @javafx.fxml.FXML
    private ListView<String> listApaperName;
    @javafx.fxml.FXML
    private ListView<String> listApaperPrice;
    @javafx.fxml.FXML
    private ListView<String> listSpaperName;
    @javafx.fxml.FXML
    private ListView<String> listSpaperPrice;
    @javafx.fxml.FXML
    private ComboBox<String> comboMobile;


    Connection con;
    PreparedStatement pst;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAdd;
    @FXML
    private ComboBox<String> comboArea;
    @FXML
    private TextField txtHawker;
    @FXML
    private DatePicker dob;
    @FXML
    private ImageView SearchMobile;

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


    @FXML
    public void doSelectOnClick(MouseEvent event) {
        if(event.getClickCount()==1)
        {
            listApaperPrice.getSelectionModel().select(listApaperName.getSelectionModel().getSelectedIndex());
        }
        if(event.getClickCount()==2)
        {
            String SP=listApaperName.getSelectionModel().getSelectedItem();
            String PP=listApaperPrice.getSelectionModel().getSelectedItem();
            listSpaperName.getItems().add(SP);
            listSpaperPrice.getItems().add(PP);

        }

    }
    @FXML
    public void doUnSelectOnClick(MouseEvent event)
    {
        if(event.getClickCount()==1)
        {
            listSpaperPrice.getSelectionModel().select(listSpaperName.getSelectionModel().getSelectedIndex());
        }
        if(event.getClickCount()==2)
        {
            String SP=listSpaperName.getSelectionModel().getSelectedItem();
            String PP=listSpaperPrice.getSelectionModel().getSelectedItem();
            listSpaperName.getItems().remove(SP);
            listSpaperPrice.getItems().remove(PP);
        }


    }


    @FXML
    public void ClickOnArea(ActionEvent actionEvent) {
        String AG=comboArea.getSelectionModel().getSelectedItem();
        try
        {
            pst=con.prepareStatement("select * from hawker where area like ?");
            pst.setString(1,"%"+AG+"%");
            ResultSet table=pst.executeQuery();
            while(table.next())
            {
                txtHawker.setText(table.getString("hname"));
            }
        }
        catch(Exception  e)
        {
            e.printStackTrace();
        }
    }


    public void doSubscribe(ActionEvent actionEvent) {
        String Mob=comboMobile.getSelectionModel().getSelectedItem();
        String Cname=txtName.getText();
        String Spapers="";
        String Sprices="";
        ObservableList<String>  SP=listSpaperName.getItems();
        for(String i:SP)
        {
            Spapers=Spapers+","+i;
            if(Spapers.startsWith(","))
            {
                Spapers=Spapers.substring(1);

            }
        }
        ObservableList<String>  PP=listSpaperPrice.getItems();
        for(String j:PP)
        {
            Sprices=Sprices+","+j;
            if(Sprices.startsWith(","))
            {
                Sprices=Sprices.substring(1);

            }
        }
        String Area=comboArea.getSelectionModel().getSelectedItem();
        String Haw=txtHawker.getText();
        String Email=txtEmail.getText();
        String Add=txtAdd.getText();

        LocalDate D=dob.getValue();
        java.sql.Date A=java.sql.Date.valueOf(D);

        try
        {
            pst=con.prepareStatement("insert into customer values(?,?,?,?,?,?,?,?,?)");
            pst.setString(1,Mob);
            pst.setString(2,Cname);
            pst.setString(3,Spapers);
            pst.setString(4,Sprices);
            pst.setString(5,Area);
            pst.setString(6,Haw);
            pst.setString(7,Email);
            pst.setString(8,Add);
            pst.setDate(9,A);
            int count=pst.executeUpdate();
            if(count!=0)
            {
                makeAlert("Subscribed!");
            }


        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            //throw new RuntimeException("uncaught", e);
        }

    }

    public void doUpdate(ActionEvent actionEvent) {
        String Mob=comboMobile.getSelectionModel().getSelectedItem();
        String Cname=txtName.getText();
        String Spapers="";
        String Sprices="";
        ObservableList<String>  SP=listSpaperName.getItems();
        for(String i:SP)
        {
            Spapers=Spapers+","+i;
            if(Spapers.startsWith(","))
            {
                Spapers=Spapers.substring(2);

            }
        }
        ObservableList<String>  PP=listSpaperPrice.getItems();
        for(String i:PP)
        {
            Sprices=Sprices+","+i;
            if(Sprices.startsWith(","))
            {
                Sprices=Sprices.substring(2);

            }
        }
        String Area=comboArea.getSelectionModel().getSelectedItem();
        String Haw=txtHawker.getText();
        String Email=txtEmail.getText();
        String Add=txtAdd.getText();

        LocalDate D=dob.getValue();
        java.sql.Date A=java.sql.Date.valueOf(D);

        try
        {                                                    //in parameter
            pst=con.prepareStatement("update customer set cname=?,spapers=?,sprices=?,area=?,hawker=?,email=?,address=?,dos=? where mobile=?");
            pst.setString(9,Mob);
            pst.setString(1,Cname);
            pst.setString(2,Spapers);
            pst.setString(3,Sprices);
            pst.setString(4,Area);
            pst.setString(5,Haw);
            pst.setString(6,Email);
            pst.setString(7,Add);
            pst.setDate(8,A);
            int count=pst.executeUpdate();
            if(count!=0)
            {
                makeAlert("Updated!");
            }

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            //throw new RuntimeException("uncaught", e);
        }



    }

    public void doUnsubscribe(ActionEvent actionEvent) {

        try
        {
            String mob=comboMobile.getSelectionModel().getSelectedItem();
            pst=con.prepareStatement("delete from customer where mobile=?");
            pst.setString(1, mob);
            int count=pst.executeUpdate();
            if(count!=0)
            {
                 makeAlert("Unsubscribed!");
            }

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.toString());
        }

    }

    @FXML
    public void doSearchMobile(Event event) {
        //System.out.println("Hello");
        try {
            String Mob=comboMobile.getSelectionModel().getSelectedItem();
            pst=con.prepareStatement("select * from customer where mobile=?");
            pst.setString(1, Mob);
            ResultSet table= pst.executeQuery();//table is 2d array//array of objects
            while(table.next())
            {
                String Cname=table.getString("cname");
                String Sp=table.getString("spapers");
                String Pp=table.getString("sprices");
                String Ar=table.getString("area");
                String haw=table.getString("hawker");
                String em=table.getString("email");
                String Add=table.getString("address");
                Date da=table.getDate("dos");

                txtName.setText(Cname);

                String[] res = Sp.split(",", 0);
                for(String myStr: res) {
                    listSpaperName.getItems().add(myStr);
                }

                String[] ress = Pp.split(",", 0);
                for(String myStr: ress) {
                    listSpaperPrice.getItems().add(myStr);
                }
                comboArea.getSelectionModel().clearSelection();
                comboArea.setValue(Ar);
                txtHawker.setText(haw);;
                txtEmail.setText(em);
                txtAdd.setText(Add);
                dob.setValue(da.toLocalDate());


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
        con = mysqlconnector.doConnect();
        if (con == null) {
            System.out.println("Connection Problem");
        } else {
            System.out.println("Connected");
        }
        doFillMobile();
        ArrayList<String> papers=new ArrayList<String>(Arrays.asList("AA","BB","CC","DD","EE","FF","GG","HH","II","JJ"));
        listApaperName.getItems().addAll(papers);
        listApaperName.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ArrayList<String> prices=new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9","10"));
        listApaperPrice.getItems().addAll(prices);
        listApaperPrice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ArrayList<String> areas=new ArrayList<String>(Arrays.asList("A","B","C","D","E","F","G","H","I","J"));
        comboArea.getItems().addAll(areas);
    }



}
