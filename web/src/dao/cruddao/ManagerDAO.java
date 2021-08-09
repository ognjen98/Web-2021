package dao.cruddao;

import beans.Manager;
import beans.User;

public interface ManagerDAO extends CRUDDao<Manager, String>{

	Manager findByUsername(String username);
}
