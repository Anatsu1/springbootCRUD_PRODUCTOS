package com.boostmyfool.beastore.repositories;

import com.boostmyfool.beastore.models.Productos;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ProductosRepository extends MongoRepository<Productos, ObjectId> {
}
