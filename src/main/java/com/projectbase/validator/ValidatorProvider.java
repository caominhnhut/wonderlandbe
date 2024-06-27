package com.projectbase.validator;

import java.util.EnumMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projectbase.factory.ValidationType;

@Component
public class ValidatorProvider{

    @Autowired
    private List<Validator<?>> validators;

    private EnumMap<ValidationType, List<Validator<?>>> mapValidators = new EnumMap<>(ValidationType.class);

    @PostConstruct
    public void initialize(){
        for(Validator<?> validator: validators){
            ValidationType type = validator.getType();
            if(mapValidators.containsKey(type)){
                mapValidators.get(type).add(validator);
            }else {
                mapValidators.put(type, List.of(validator));
            }
        }
    }

    public <T> void execute(ValidationType type, T data){
        for(Validator validator : mapValidators.get(type)){
            validator.validate(data);
        }
    }
}
