package dao.cruddao;

import beans.Supplier;
import beans.User;

public interface SupplierDAO extends CRUDDao<Supplier, String>{

	Supplier findByUsername(String username);
}
