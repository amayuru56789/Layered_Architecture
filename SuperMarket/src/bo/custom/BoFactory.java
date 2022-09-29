package bo.custom;

import bo.SuperBO;
import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;

public class BoFactory {

    private static BoFactory boFactory;

    private BoFactory(){
    }

    public static BoFactory getBoFactory(){
        if (boFactory == null){
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes{
        CUSTOMER, ITEM
    }

}
