package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.TreeNode;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface TreeNodeRepository extends ReactiveMongoRepository<TreeNode, String> {
    Mono<TreeNode> findByChildrenContains(List<String> children);
}
