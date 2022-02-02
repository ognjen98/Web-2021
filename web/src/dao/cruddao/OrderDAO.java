package dao.cruddao;

import beans.Order;

public interface OrderDAO extends CRUDDao<Order, String> {

	public Order findByUniqueId(String id);
}
