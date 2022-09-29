package controller;

import bo.custom.ItemBO;
import bo.custom.impl.ItemBOImpl;
import dao.custom.impl.orderDetailsDAOImpl;
import dao.custom.orderDetailsDAO;
import dto.ItemDTO;
import dto.orderDetailsDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import views.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SystemReportFormController {

    public TableView tblReport;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colQTY;
    public TableColumn colDiscount;

    private final orderDetailsDAO orderDetailsDAO = new orderDetailsDAOImpl();

    public void initialize(){

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("orderQTY"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        try {
            loadAllItems();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadAllItems() throws SQLException, ClassNotFoundException {
        tblReport.getItems().clear();
        ArrayList<orderDetailsDTO> allItems = orderDetailsDAO.getAll();
        for (orderDetailsDTO orderDetail:allItems
        ) {
            tblReport.getItems().add(new orderDetailsDTO(orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getOrderQTY(),orderDetail.getDiscount()));
        }
    }

    public void openItemForm(ActionEvent actionEvent) throws IOException {

        Parent load = FXMLLoader.load(getClass().getResource("../views/ItemForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Items");
        stage.show();

    }
}
