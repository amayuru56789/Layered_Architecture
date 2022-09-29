package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.orderDetailsDAOImpl;
import dao.custom.orderDetailsDAO;
import db.DbConnection;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.orderDetailsDTO;
import entity.Order;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import dto.ItemDTO;
import views.tm.CartTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CashierFormController {

    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final ItemDAO itemDAO = new ItemDAOImpl();

    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final orderDetailsDAO orderDetailsDAO = new orderDetailsDAOImpl();
    //private final CrudDAO<orderDetailsDTO, String> orderDetailsDAO = new orderDetailsDAOImpl();

    public Label lblDate;
    public Label lblTime;
    public JFXComboBox cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtQtyOnHand;
    public TableView<CartTm> tblCart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQTY;
    public TableColumn colDiscount;
    public JFXTextField txtQty;
    public JFXComboBox cmbICustId;
    public JFXTextField txtDiscount;
    public Label lblOid;
    public Label lblTot;
    public JFXTextField txtPrice;

    int RawForRemove = -1;

    public void initialize() {

        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        setOrderId();
        loadDateAndTime();
        loadAllCustomerId();
        loadItemCodes();

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            RawForRemove = (int) newValue;
        });

    }

    private void loadAllCustomerId() {
        try {
            ArrayList<CustomerDTO> all = customerDAO.getAll();
            for (CustomerDTO customerDTO : all
            ) {
                cmbICustId.getItems().add(customerDTO.getId());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {

        ItemDTO i1 = itemDAO.search(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            txtDescription.setText(i1.getDescription());
            txtPackSize.setText(i1.getPackSize());
            txtPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
        }

    }

    private void loadItemCodes() {

        try {
            ArrayList<ItemDTO> all = itemDAO.getAll();
            for (ItemDTO dto : all
            ) {
                cmbItemCode.getItems().add(dto.getItemCode());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadDateAndTime() {

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );

        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void addToCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String description = txtDescription.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        double total = (qty * price) - discount;

        if (qtyOnHand < qty) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        CartTm tm = new CartTm(
                String.valueOf(cmbItemCode.getValue()),
                description,
                price,
                qty,
                discount,
                total
        );

        int raw = isExists(tm);
        if (raw == -1) {
            obList.add(tm);
        } else {
            CartTm temp = obList.get(raw);
            CartTm newOtm = new CartTm(
                    temp.getItemCode(),
                    temp.getDescription(),
                    temp.getUnitPrice(),
                    temp.getQty() + qty,
                    temp.getDiscount(),
                    total
            );
            obList.remove(raw);
            obList.add(newOtm);
        }

        tblCart.setItems(obList);
        calculateCost();
        tblCart.refresh();
    }

    private void calculateCost() {
        double tot = 0;
        for (CartTm temp : obList
        ) {
            tot += temp.getTot();
        }
        lblTot.setText(tot + " /=");
    }

    private int isExists(CartTm tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemCode().equals(obList.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    public void openCustomerForm(ActionEvent actionEvent) throws IOException {

        Parent load = FXMLLoader.load(getClass().getResource("../views/CustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Customer Form");
        stage.show();

    }

    private void setOrderId() {
        try {
            lblOid.setText(orderDAO.generateNewOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        OrderDTO order = new OrderDTO(
                orderDAO.generateNewOrderId(),
                lblDate.getText(),
                (String) cmbICustId.getValue()
        );
        if (orderDAO.placeOrder(order)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            //setOrderId();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try again..").show();
        }
    }

    public void clearCartOnAction(ActionEvent actionEvent) {
        if (RawForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a Raw").show();
        } else {
            obList.remove(RawForRemove);
            calculateCost();
            tblCart.refresh();
        }
    }

    public ItemDTO findItem(String code) {
        try {
            return itemDAO.search(code);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}
