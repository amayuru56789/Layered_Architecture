package controller;

import bo.custom.BoFactory;
import bo.custom.CustomerBO;
import bo.custom.impl.CustomerBOImpl;
import com.jfoenix.controls.JFXTextField;
import dao.CrudDAO;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFormController {
    //private final CustomerBO customerBO = (CustomerBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    public JFXTextField txtId;
    public JFXTextField txtTitle;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtCode;

    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    //private final CrudDAO<CustomerDTO,String> customerDAO = new CustomerDAOImpl();
    private CustomerBO customerBO = new CustomerBOImpl();

    public void initialize(){
        try {
            loadAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        //CustomerDAO customerDAO = new CustomerDAOImpl();
        //ArrayList<CustomerDTO> allCustomers = customerDAO.getAllCustomers();
        //ArrayList<CustomerDTO> allCustomers = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomer();
        /*for (CustomerDTO customer:allCustomers
             ) {

        }*/
    }

    public void SaveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        //CustomerDAO customerDAO = new CustomerDAOImpl();
        CustomerDTO customerDTO = new CustomerDTO(
                txtId.getText(),txtTitle.getText(),txtName.getText(),txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtCode.getText()
        );
        //customerDAO.addCustomer(customerDTO);

        if (customerBO.addCustomer(customerDTO)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
        }

    }

    public void selectCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
/*

        String custId = txtId.getText();
        CustomerDTO c1 = new CustomerController().getCustomer(custId);
        if (c1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
        }else {
            setData(c1);
        }
*/

    }

    void setData(CustomerDTO c1) {

        txtId.setText(c1.getId());
        txtTitle.setText(c1.getTitle());
        txtName.setText(c1.getName());
        txtAddress.setText(c1.getAddress());
        txtCity.setText(c1.getCity());
        txtProvince.setText(c1.getProvince());
        txtCode.setText(c1.getPostalCode());

    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        //CustomerDAO customerDAO = new CustomerDAOImpl();
        //return customerDAO.ifCustomerExists(id);
        return false;
    }

    private String generateNewId() throws SQLException, ClassNotFoundException {
        //CustomerDAO customerDAO = new CustomerDAOImpl();
        //return customerDAO.generateNewID();
        return null;
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //CustomerDAO customerDAO = new CustomerDAOImpl();
        CustomerDTO customerDTO = new CustomerDTO(
                txtId.getText(),txtTitle.getText(),
                txtName.getText(),txtAddress.getText(),txtCity.getText(),
                txtProvince.getText(),txtCode.getText()
        );
        //customerDAO.updateCustomer(customerDTO);
       if (customerBO.updateCustomer(customerDTO))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated...").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();

    }
}
