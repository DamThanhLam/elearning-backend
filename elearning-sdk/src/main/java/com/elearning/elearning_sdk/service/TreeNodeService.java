package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.converter.ModelToEntity;
import com.elearning.elearning_sdk.entity.TreeNode;
import com.elearning.elearning_sdk.exception.NotFoundException;
import com.elearning.elearning_sdk.model.SaveTreeNodeModel;
import com.elearning.elearning_sdk.model.TreeNodeModel;
import com.elearning.elearning_sdk.repository.TreeNodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class TreeNodeService {

    private final ModelToEntity modelToEntity;
    private final TreeNodeRepository treeNodeRepository;
    private final EntityToModel entityToModel;

    public Mono<String> addNode(
        String parentId,
        SaveTreeNodeModel model
    ) {
        TreeNode entity = modelToEntity.toEntity(model);
        return getNodeByIdOrThrow(parentId)
            .flatMap(parent ->
                treeNodeRepository.save(entity)
                    .flatMap(child -> {
                        List<String> childrenIds = new ArrayList<>();
                        if (parent.getChildren() != null) {
                            childrenIds = parent.getChildren();
                        }
                        childrenIds.add(child.getId());
                        entity.setChildren(childrenIds);
                        return treeNodeRepository.save(entity)
                            .thenReturn(child.getId());
                    })
            );
    }

    public Mono<Void> updateNode(
        String id,
        SaveTreeNodeModel model
    ) {
        return getNodeByIdOrThrow(id)
            .flatMap(entity -> {
                modelToEntity.mergeToEntity(model, entity);
                return treeNodeRepository.save(entity);
            })
            .then();
    }

    public Mono<Void> moveNode(
        String nodeId,
        String currentParentId,
        String newParentId
    ) {
        Mono<Void> removeFromOldParent =
            getNodeByIdOrThrow(currentParentId)
                .flatMap(currentNode -> {
                    currentNode.getChildren().remove(nodeId);
                    return treeNodeRepository.save(currentNode);
                })
                .then();

        Mono<Void> addToNewParent =
            getNodeByIdOrThrow(newParentId)
                .flatMap(newParentNode -> {
                    newParentNode.getChildren().add(nodeId);
                    return treeNodeRepository.save(newParentNode);
                })
                .then();

        return Mono.when(removeFromOldParent, addToNewParent);
    }

    public Mono<Void> deleteNode(
        String id
    ) {
        return treeNodeRepository.findByChildrenContains(Collections.singletonList(id))
            .flatMap(parent -> {
                parent.getChildren().remove(id);
                return treeNodeRepository.save(parent);
            })
            .then(
                treeNodeRepository.deleteById(id)
            );
    }

    public Flux<TreeNodeModel> getTreeNodeByIds(
        List<String> ids
    ) {
        return treeNodeRepository.findByIdIn(ids)
            .map(entityToModel::toModel);
    }

    public Mono<TreeNodeModel> getTreeNodeById(String id) {
        return treeNodeRepository.findById(id)
            .map(entityToModel::toModel);
    }

    private Mono<TreeNode> getNodeByIdOrThrow(
        String id
    ) {
        return treeNodeRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("treeNode")));
    }
}
