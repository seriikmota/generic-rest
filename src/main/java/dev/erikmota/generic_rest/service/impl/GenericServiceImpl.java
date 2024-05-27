package dev.erikmota.generic_rest.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.erikmota.generic_rest.entity.impl.GenericModel;
import dev.erikmota.generic_rest.exceptions.DataException;
import dev.erikmota.generic_rest.exceptions.ParameterRequiredException;
import dev.erikmota.generic_rest.exceptions.errorMessages.ErrorEnum;
import dev.erikmota.generic_rest.service.IGenericService;
import dev.erikmota.generic_rest.validations.IValidations;
import dev.erikmota.generic_rest.validations.ValidationAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

public abstract class GenericServiceImpl<DTO, DTOCreate, DTOUpdate, DTOList, MODEL extends GenericModel<TYPE_PK>, REPOSITORY extends JpaRepository<MODEL, TYPE_PK>, TYPE_PK> implements IGenericService<DTO, DTOCreate, DTOUpdate, DTOList, TYPE_PK> {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private REPOSITORY repository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired(required = false)
    private List<IValidations<MODEL>> validations = new ArrayList<>();

    private final Map<String, Class<?>> classMap = getGenericClass(getClass());

    @SuppressWarnings("unchecked")
    public List<DTOList> listAll() {
        List<DTOList> list = new ArrayList<>();
        for (MODEL model : repository.findAll()) {
            list.add(mapper.convertValue(model, (Class<DTOList>) classMap.get("DTOList")));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public DTO create(DTOCreate dtoCreate) {
        MODEL data = mapper.convertValue(dtoCreate, (Class<MODEL>) classMap.get("MODEL"));
        prepareToCreate(data);
        validateMandatoryFields(data);
        validateBusinessLogic(data);
        validateBusinessLogicForInsert(data);
        return mapper.convertValue(repository.save(data), (Class<DTO>) classMap.get("DTO"));
    }

    @SuppressWarnings("unchecked")
    public DTO update(TYPE_PK id, DTOUpdate dtoUpdate) throws JsonMappingException {
        var dataDB = validateIdModelExistsAndGet(id);
        mapper.updateValue(dataDB, dtoUpdate);
        prepareToUpdate(dataDB);
        validateMandatoryFields(dataDB);
        validateBusinessLogic(dataDB);
        validateBusinessLogicForUpdate(dataDB);
        return mapper.convertValue(repository.save(dataDB), (Class<DTO>) classMap.get("DTO"));
    }

    @SuppressWarnings("unchecked")
    public DTO getById(TYPE_PK id){
        return mapper.convertValue(this.validateIdModelExistsAndGet(id), (Class<DTO>) classMap.get("DTO"));
    }

    @SuppressWarnings("unchecked")
    public DTO deleteById(TYPE_PK id){
        MODEL dataToRemove = this.validateIdModelExistsAndGet(id);
        validateBusinessLogicForDelete(dataToRemove);
        this.repository.delete(dataToRemove);
        prepareToDelete(dataToRemove);
        return mapper.convertValue(dataToRemove, (Class<DTO>) classMap.get("DTO"));
    }

    private MODEL validateIdModelExistsAndGet(TYPE_PK id){
        if (!Objects.nonNull(id)) throw new ParameterRequiredException("id");

        Optional<MODEL> byId = repository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new DataException(ErrorEnum.NOT_FOUND);
        }
    }

    protected void validateBusinessLogicForInsert(MODEL data) {
        validations.forEach(v -> v.validate(data, ValidationAction.CREATE));
    }

    protected void validateBusinessLogicForUpdate(MODEL data) {
        validations.forEach(v -> v.validate(data, ValidationAction.UPDATE));
    }

    protected void validateBusinessLogicForDelete(MODEL data) {
        validations.forEach(v -> v.validate(data, ValidationAction.DELETE));
    }

    protected void validateBusinessLogic(MODEL data) {
        validations.forEach(v -> v.validate(data, ValidationAction.GENERAL));
    }

    protected void validateMandatoryFields(MODEL data) {
        validations.forEach(v -> v.validate(data, ValidationAction.GENERAL_MANDATORY));
    }

    public static Map<String, Class<?>> getGenericClass(Class<?> clazz) {
        Map<String, Class<?>> genericClassMap = new HashMap<>();

        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType parameterizedType) {
            Type[] genericTypes = parameterizedType.getActualTypeArguments();
            TypeVariable<?>[] typeParameters = ((Class<?>) parameterizedType.getRawType()).getTypeParameters();

            for (int i = 0; i < genericTypes.length; i++) {
                if (genericTypes[i] instanceof Class<?>) {
                    genericClassMap.put(typeParameters[i].getName(), (Class<?>) genericTypes[i]);
                } else if (genericTypes[i] instanceof ParameterizedType) {
                    genericClassMap.put(typeParameters[i].getName(), (Class<?>) ((ParameterizedType) genericTypes[i]).getRawType());
                }
            }
        }

        return genericClassMap;
    }

    protected abstract void prepareToCreate(MODEL data);
    protected abstract void prepareToUpdate(MODEL dataDB);
    protected abstract void prepareToDelete(MODEL dataDB);
}
