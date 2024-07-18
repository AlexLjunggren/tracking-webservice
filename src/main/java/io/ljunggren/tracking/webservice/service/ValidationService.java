package io.ljunggren.tracking.webservice.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import io.ljunggren.validator.Validator;

@Service
public class ValidationService {

    public Set<String> validate(Object object) {
        Validator validator = new Validator(object);
        validator.validate();
        return validator.getErrorMessages();
    }
    
}
