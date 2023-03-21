package REPOSITORY;

import java.util.List;

public interface DAOInterface<T, K> {
    boolean create(T t);
    T read(K id);
    List<T> readAll();
    boolean update(T t);
    boolean delete(K id);
}
