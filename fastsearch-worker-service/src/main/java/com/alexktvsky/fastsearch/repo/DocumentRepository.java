package com.alexktvsky.fastsearch.repo;

import com.alexktvsky.fastsearch.model.DocumentEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface DocumentRepository extends ReactiveMongoRepository<DocumentEntity, ObjectId> {

    @Query("{ $text: { $search: ?0 } }")
    Flux<DocumentEntity> findByPhrase(String phrase);
}
