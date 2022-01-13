// import java.sql.Connection;
// import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application {
    TableView<Mahasiswa> tableView = new TableView<Mahasiswa>();
    
    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Nilai OOP");

        TableColumn<Mahasiswa, Integer> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Mahasiswa, String> columnNAMA = new TableColumn<>("NAMA");
        columnNAMA.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<Mahasiswa, String> columnNIM = new TableColumn<>("NIM");
        columnNIM.setCellValueFactory(new PropertyValueFactory<>("nim"));

        TableColumn<Mahasiswa, String> columnJURUSAN = new TableColumn<>("JURUSAN");
        columnJURUSAN.setCellValueFactory(new PropertyValueFactory<>("jurusan"));

        TableColumn<Mahasiswa, String> columnFAKULTAS = new TableColumn<>("FAKULTAS");
        columnFAKULTAS.setCellValueFactory(new PropertyValueFactory<>("fakultas"));

        

        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnNAMA);
        tableView.getColumns().add(columnNIM);
        tableView.getColumns().add(columnJURUSAN);
        tableView.getColumns().add(columnFAKULTAS);
       
        // Button
        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("Add");
        toolBar.getItems().add(button1);
        button1.setOnAction(e -> Add());

        Button button2 = new Button("Edit");
        toolBar.getItems().add(button2);
        button2.setOnAction(e -> Edit());

        Button button3 = new Button("Delete");
        toolBar.getItems().add(button3);
        button3.setOnAction(e -> Delete());

        load();
        VBox vbox = new VBox(tableView, toolBar);
        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();

        Statement stmt;

        // String url = "jdbc:mysql://localhost:3306/db_mahasiswa";
        // String user = "root";
        // String pass = "";
        
        try {
            Data db = new Data();
            stmt = db.conn.createStatement();
            ResultSet record = stmt.executeQuery("select * from tb_mahasiswa");
            tableView.getItems().clear();

            while (record.next()){
                tableView.getItems()
                .add(new Mahasiswa(record.getInt("id"), record.getString("nama"), record.getString("nim"), record.getString("jurusan"), record.getString("fakultas")));
            }
        } 
        catch (SQLException e) {
            System.out.println("koneksi gagal");
        }

    }
    public void load() {
        Statement stmt;
        tableView.getItems().clear();
        try {
            Data db = new Data();
            // Connection conn = DriverManager.getConnection(url, user, pass);
            stmt = db.conn.createStatement();
            ResultSet record = stmt.executeQuery("select * from tb_mahasiswa");

            while (record.next()) {
                tableView.getItems().add(new Mahasiswa(record.getInt("id"), record.getString("nama"),
                        record.getString("nim"), record.getString("jurusan"), record.getString("fakultas")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            System.out.println("");
        }
    }

    // Bagian add
    public void Add() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Add Data");

        TextField namaField = new TextField();
        TextField nimField = new TextField();
        TextField jurusanField = new TextField();
        TextField fakultasField = new TextField();
        Label labelnama = new Label("Nama");
        Label labelnim = new Label("NIM");
        Label labeljurusan = new Label("Jurusan");
        Label labelfakultas = new Label("Fakultas");

        VBox hbox1 = new VBox(5, labelnama, namaField);
        VBox hbox2 = new VBox(5, labelnim, nimField);
        VBox hbox3 = new VBox(5, labeljurusan, jurusanField);
        VBox hbox4 = new VBox(5, labelfakultas, fakultasField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, hbox4, save);

        Scene scene = new Scene(vbox, 500, 500);

        save.setOnAction(e -> {
            Data db = new Data();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into tb_mahasiswa set nama='%s', nim='%s', jurusan='%s', fakultas='%s'";
                sql = String.format(sql, namaField.getText(), nimField.getText(), jurusanField.getText(), fakultasField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    // Bagian Edit
    public void Edit() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Edit Data");

        TextField idField = new TextField();
        TextField namaField = new TextField();
        Label labelid = new Label("Id");
        Label labelnama = new Label("Nama");
        
        VBox hbox1 = new VBox(5, labelid, idField);
        VBox hbox2 = new VBox(5, labelnama, namaField);
        VBox vbox = new VBox(20, hbox1, hbox2,  save);

        Scene scene = new Scene(vbox, 500, 500);

        save.setOnAction(e -> {
            Data db = new Data();
            try {
                Statement state = db.conn.createStatement();
                String sql = "update tb_mahasiswa set nama='%s' where id='%s'";
                sql = String.format(sql, namaField.getText(), idField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }


    // Bagian Delete
    public void Delete() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Delete Data");

        TextField nimField = new TextField();
        Label labelnim = new Label("NIM");

        VBox hbox2 = new VBox(5, labelnim, nimField);
        VBox vbox = new VBox(20, hbox2, save);

        Scene scene = new Scene(vbox, 500, 500);

        save.setOnAction(e -> {
            Data db = new Data();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from tb_mahasiswa where nim='%s'";
                sql = String.format(sql, nimField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }
}