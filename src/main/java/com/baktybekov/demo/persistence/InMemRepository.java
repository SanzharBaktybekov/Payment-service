package com.baktybekov.demo.persistence;

import com.baktybekov.demo.model.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

@org.springframework.stereotype.Repository
public class InMemRepository implements CrudRepository<Payment, UUID> {
    private final Map<UUID, Payment> store = new HashMap<>();

    @Override
    public <S extends Payment> S save(S entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Payment> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(e -> store.put(e.getId(), e));
        return entities;
    }

    @Override
    public Optional<Payment> findById(UUID uuid) {
        return Optional.ofNullable(store.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return store.containsKey(uuid);
    }

    @Override
    public Iterable<Payment> findAll() {
        return store.values();
    }

    @Override
    public Iterable<Payment> findAllById(Iterable<UUID> uuids) {
        List<Payment> result = new ArrayList<>();
        uuids.forEach(id -> {
            if(store.containsKey(id)) {
                result.add(store.get(id));
            }
        });
        return result;
    }

    @Override
    public long count() {
        return store.size();
    }

    @Override
    public void deleteById(UUID uuid) {
        store.remove(uuid);
    }

    @Override
    public void delete(Payment entity) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        uuids.forEach(store::remove);
    }

    @Override
    public void deleteAll(Iterable<? extends Payment> entities) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
