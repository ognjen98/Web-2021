package dao.cruddao;

import java.util.Collection;

public interface CRUDDao<T, ID> {

	int count();

	boolean add(T entity);

	boolean update(T entity);

	boolean delete(T entity);

	void deleteAll();

	boolean deleteById(ID id);

	boolean existsById(ID id);

	boolean isDeleted(String id);

	Collection<T> findAll();

	T findById(ID id);

	boolean save();

	boolean saveAll(Collection<T> entities);
}
