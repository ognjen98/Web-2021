package dao.cruddao;

import beans.User;

public interface UserDAO extends CRUDDao<User, Integer> {

	User findByUsername(String username);

}
