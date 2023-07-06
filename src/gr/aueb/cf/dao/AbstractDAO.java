package gr.aueb.cf.dao;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDAO<T> implements IGenericDAO<T> {
    private final Map<Long, T> storage = new HashMap<>();

    private Class<T> persistentClass;

    public AbstractDAO() {}

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    public T insert(Long id, T t) {
        storage.put(id, t);
        return t;
    }

    @Override
    public T update(Long id, T t) {
        return storage.replace(id, t);
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

    @Override
    public T get(Long id) {
        return storage.get(id);
    }

    @Override
    public Map<Long, T> getALl() {
        return new HashMap<>(storage);
    }
}
