package dao.cruddao;

import beans.Buyer;
import beans.User;

public interface BuyerDAO extends CRUDDao<Buyer, Integer>{

	User findByUsername(String username);

}
