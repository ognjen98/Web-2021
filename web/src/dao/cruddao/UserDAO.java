package dao.cruddao;

import beans.User;

public interface UserDAO extends CRUDDao<User, String> {

	User findByUsername(String username);

}
