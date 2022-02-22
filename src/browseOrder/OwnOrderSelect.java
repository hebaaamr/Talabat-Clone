package browseOrder;

import users.*;
import meal.Order;
import java.util.ArrayList;
import java.util.List;

public class OwnOrderSelect extends OrderSelect {

    public OwnOrderSelect(String username) {
        super(username);
        flag = true;
        fillComboBox();
    }

    @Override
    protected void fillComboBox() {
        List<Order> orderList = new ArrayList<Order>();
        Owner owner = new Owner();
        orderList = owner.browseOrder(super.getUsername());
        for (Order order : orderList) {
            comboBox.addItem(order.getName());
        }
    }
}
