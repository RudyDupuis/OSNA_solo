package fr.eni.OSNA.dal;

import java.util.List;

public interface DAO<T> {
	public T selectById(int id) throws Exception;
	public List<T> selectAll() throws Exception;
	public void update(T t) throws Exception;
	public void insert(T t) throws Exception;
	public void delete(int id) throws Exception;
}
