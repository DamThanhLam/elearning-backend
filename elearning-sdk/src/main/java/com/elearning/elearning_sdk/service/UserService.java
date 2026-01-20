package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.converter.ModelToEntity;
import com.elearning.elearning_sdk.entity.User;
import com.elearning.elearning_sdk.exception.NotFoundException;
import com.elearning.elearning_sdk.model.SaveUserInformationModel;
import com.elearning.elearning_sdk.model.SaveUserModel;
import com.elearning.elearning_sdk.model.UserInformationModel;
import com.elearning.elearning_sdk.model.UserModel;
import com.elearning.elearning_sdk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelToEntity modelToEntity;
    private final EntityToModel entityToModel;

    public Mono<ObjectId> addUser(SaveUserModel model) {
        User entity = modelToEntity.toEntity(model);
        return userRepository.save(entity)
            .map(User::getId);
    }

    public Mono<Void> updateUser(ObjectId id, SaveUserInformationModel model) {
        return getUserByIdOrThrow(id)
            .map(entity -> {
                modelToEntity.mergeToEntity(model, entity);
                return entity;
            })
            .flatMap(userRepository::save)
            .then();
    }

    public Mono<UserModel> getUserById(ObjectId id) {
        return userRepository.findById(id)
            .map(entityToModel::toModel);
    }

    public Mono<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(entityToModel::toModel);
    }

    private Mono<User> getUserByIdOrThrow(ObjectId id) {
        return userRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("User")));
    }

    public Mono<Void> updateVerifyEmailByEmail(
        String email,
        boolean verifyEmail
    ) {
        return getUserByEmailOrThrow(email)
            .flatMap(entity -> {
                entity.setEmailVerified(verifyEmail);
                return userRepository.save(entity);
            })
            .then();
    }

    public Mono<UserModel> findByPhone(String phone) {
        return userRepository.findUserByPhone(phone)
            .map(entityToModel::toModel);
    }

    private Mono<User> getUserByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
            .switchIfEmpty(Mono.error(new NotFoundException("User")));
    }

    public Mono<ObjectId> getIdByEmail(String username) {
        return getUserByEmailOrThrow(username)
            .map(User::getId);
    }

    public Mono<Void> updatePassword(ObjectId id, String newPassword) {
        return getUserByIdOrThrow(id)
            .flatMap(entity -> {
                entity.setPassword(newPassword);
                return userRepository.save(entity);
            })
            .then();
    }

    public Mono<UserInformationModel> getUserInformationById(ObjectId userId) {
        return userRepository.findById(userId)
            .map(entityToModel::toUserInformationModel);
    }

    public Mono<UserInformationModel> getUserInformationByEmail(String email) {
        return userRepository.findByEmail(email)
            .flatMap(entity ->
                Mono.justOrEmpty(entityToModel.toUserInformationModel(entity))
            );
    }

    public Mono<Map<ObjectId, UserInformationModel>> getUserInformationMapByIds(
        List<ObjectId> userIds
    ) {
        return userRepository.findAllById(userIds)
            .map(entityToModel::toUserInformationModel)
            .collectMap(
                UserInformationModel::getId,
                Function.identity()
            );
    }

    public Mono<String> getEmailByUserId(
        ObjectId userId
    ) {
        return userRepository.findEmailById(userId);
    }
}
