package dao.cruddao;

import beans.Buyer;
import beans.User;

public interface BuyerDAO extends CRUDDao<Buyer, String>{

	User findByUsername(String username);

}
