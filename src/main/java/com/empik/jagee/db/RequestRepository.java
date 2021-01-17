package com.empik.jagee.db;

import com.empik.jagee.db.entity.RequestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<RequestEntity, String> {

    Optional<RequestEntity> findByLogin(String login);
}
