package browseOrder;

import users.*;
import meal.Order;
import java.util.ArrayList;
import java.util.List;

public class CusOrderSelect extends OrderSelect {

    public CusOrderSelect(String username) {
        super(username);
        flag = false;
        fillComboBox();
    }

    @Override
    protected void fillComboBox() {
        List<Order> orderList = new ArrayList<Order>();
        Customer cus = new Customer();
        orderList = cus.browseOrder(super.getUsername());
        for (Order order : orderList) {
            comboBox.addItem(order.getName());
        }

    }
}
