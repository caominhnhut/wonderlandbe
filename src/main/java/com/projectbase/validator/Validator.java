package com.projectbase.validator;

import com.projectbase.factory.ValidationType;

public interface Validator<T>{

    ValidationType getType();

    void validate(T data);
}
