//package com.elearning.elearning_sdk.service;
//
//import fit.iuh.edu.com.converter.EntityToModel;
//import fit.iuh.edu.com.converter.ModelToEntity;
//import fit.iuh.edu.com.entity.Assignment;
//import fit.iuh.edu.com.entity.DataType;
//import fit.iuh.edu.com.entity.EClass;
//import fit.iuh.edu.com.model.DataMappingModel;
//import fit.iuh.edu.com.model.EClassModel;
//import fit.iuh.edu.com.model.SaveEClassModel;
//import fit.iuh.edu.com.repository.AssignmentRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//import static fit.iuh.edu.com.constant.Constant.USER_ECLASS_PIN;
//
//@Service
//@AllArgsConstructor
//public class EClassService {
//
//    private final AssignmentService assignmentService;
//    private final EClassRepository eclassRepository;
//    private final ModelToEntity modelToEntity;
//    private final EntityToModel entityToModel;
//    private final AssignmentRepository assignmentRepository;
//    private final DataMappingService dataMappingService;
//
//    public long addEClass(
//        long teacherId,
//        SaveEClassModel model
//    ) {
//        EClass entity = modelToEntity.toEntity(model);
//        entity.setTeacherId(teacherId);
//        eclassRepository.save(entity);
//        return entity.getId();
//    }
//
//    public void updateEClass(
//        long eclassId,
//        SaveEClassModel model
//    ) {
//        EClass entity = getEClassByIdOrThrow(eclassId);
//        modelToEntity.mergeToEntity(model, entity);
//        eclassRepository.save(entity);
//    }
//
//    public EClassModel getEClassById(long eclassId) {
//        Optional<EClass> entity = eclassRepository.findById(eclassId);
//        return entity.map(entityToModel::toModel).orElse(null);
//    }
//
//    public EClassModel joinCodeExists(String joinCode) {
//        return entityToModel.toModel(
//            eclassRepository.findByJoinCode(joinCode)
//        );
//    }
//
//    public void updateEClassPinByUserIdAndEClassId(
//        long userId,
//        long eclassId,
//        boolean pin
//    ) {
//        dataMappingService.saveDataMappingUnique(
//            userId,
//            eclassId,
//            USER_ECLASS_PIN,
//            String.valueOf(pin),
//            DataType.BOOLEAN
//        );
//    }
//
//    public List<EClassModel> getEClassPinByUserId(
//        long userId
//    ) {
//        List<DataMappingModel> dataMappings = dataMappingService
//            .getDataMappingsByFromIdAndMetaKey(
//                userId,
//                USER_ECLASS_PIN
//            );
//        List<Long> eclasssIds = dataMappings.stream().map(
//            it -> {
//                boolean pin = (boolean) it.getDataType().validate(
//                    it.getMetaValue()
//                );
//                if (!pin) {
//                    return null;
//                }
//                return it.getToId();
//            }
//        ).filter(Objects::nonNull).toList();
//
//        return getEClassByIds(eclasssIds);
//    }
//
//    public List<EClassModel> getEClassByIds(List<Long> eclasssIds) {
//        List<EClass> entities = eclassRepository.findAllById(eclasssIds);
//        return entities.stream().map(entityToModel::toModel).toList();
//    }
//
//    public List<EClassModel> getEClassesByTeacherId(
//        long teacherId
//    ) {
//        List<EClass> entities = eclassRepository.findByTeacherId(teacherId);
//        return entityToModel.toModel(entities);
//    }
//
//    public double getClassAveragesByEClassId(long classId) {
//        EClass entity = getEClassByIdOrThrow(classId);
//        long teacherId = entity.getTeacherId();
//        List<Assignment> assignments = assignmentRepository.findByClassId(classId);
//        return assignments.stream()
//            .map(assignment ->
//                assignmentService.getAssignmentAverage(
//                    teacherId,
//                    assignment.getId()
//                )
//            )
//            .filter(Objects::nonNull)
//            .mapToDouble(BigDecimal::doubleValue)
//            .average()
//            .orElse(0.0);
//    }
//
//    private EClass getEClassByIdOrThrow(long id) {
//        Optional<EClass> entity = eclassRepository.findById(id);
//        if (entity.isEmpty()) {
//            throw new ResourceNotFoundException("eclass");
//        }
//        return entity.get();
//    }
//}
