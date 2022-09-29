package controller;

import bo.custom.BoFactory;
import bo.custom.ItemBO;
import bo.custom.impl.ItemBOImpl;
import com.jfoenix.controls.JFXTextField;
//import dao.custom.impl.ItemDAOImpl;
import dao.CrudDAO;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.ItemDTO;
import views.tm.ItemTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemFormController {
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtqtyOnHand;
    public TableView<ItemTM> tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colSize;
    public TableColumn colQTY;
    //private final ItemBO itemBO = (ItemBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ITEM);
    //private final ItemDAO itemDAO = new ItemDAOImpl();
    //private final CrudDAO<ItemDTO,String> itemDAO = new ItemDAOImpl();

    private final ItemBO itemBO = new ItemBOImpl();

    public void initialize(){

        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));

        try {
            loadAllItems();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadAllItems() throws SQLException, ClassNotFoundException {

        /*Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Item");
        ResultSet rst = stm.executeQuery();
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        while (rst.next()){
            obList.add(new ItemTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5)
            ));
        }*/
        tblItem.getItems().clear();
        //ItemDAOImpl itemDAO = new ItemDAOImpl();
        //ItemDAO itemDAO = new ItemDAOImpl();
        //ArrayList<ItemDTO> allItems = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = itemBO.getAllItems();
        for (ItemDTO item:allItems
             ) {
            tblItem.getItems().add(new ItemTM(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand()));
        }

    }

    public void saveItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

    /*    ItemDTO i1 = new ItemDTO(txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtqtyOnHand.getText())
        );

        if (saveItem(i1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }*/
        //ItemDAOImpl itemDAO = new ItemDAOImpl();
        ItemDTO dto = new ItemDTO(txtItemCode.getText(),txtDescription.getText(),
                txtPackSize.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtqtyOnHand.getText()));
        if (itemBO.addItem(dto)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }

    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code = txtItemCode.getText();
       // ItemDAOImpl itemDAO = new ItemDAOImpl();
        if (itemBO.deleteItem(code)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }

        tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
        tblItem.getSelectionModel().clearSelection();
    }

    public void updateOnAction(ActionEvent actionEvent) {
       //ItemDAOImpl itemDAO = new ItemDAOImpl();
        ItemDTO dto = new ItemDTO(txtItemCode.getText(),txtDescription.getText(),
                txtPackSize.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtqtyOnHand.getText()));
        try {
            if(itemBO.updateItem(dto)){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated...").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        //return itemDAO.ifItemExist(code);
        return itemBO.ifItemExist(code);
    }

    private String generateNewId() {
        try {
            //return itemDAO.generateNewID();
            return itemBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I001";
    }
}
