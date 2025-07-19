package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)       //Using this in the exception class will cause SpringBoot to respond
                                                    // with the specified HTTP status code whenever this exception is thrown from your controller
public class ResourceNotFoundException extends RuntimeException {

    /*
    This is a customException class
    1.We want the resource name in the exception that is why we create the `resourceName` instance variable
    we need to provide the `fieldName`, `fieldValue` as parameters in the exception message that is why we have added
    these instance variables.
     */
    private String resourceName;
    private String fieldName;
    private Long fieldValue;

    //3. Create getter methods as we are already setting using the constructor so explicit setters are not needed
    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Long getFieldValue() {
        return fieldValue;
    }

    // 2. Created a constructor with allArgs
    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
        // we need to pass the message to the parent class that's why we use super()
        // Calls the constructor of the parent class (RuntimeException) and passes the formatted message.
        super(String.format("Resource %s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        // ex: Resource Post not found with id : 1

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }


}
