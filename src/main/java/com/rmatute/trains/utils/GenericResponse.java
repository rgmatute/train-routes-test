package com.rmatute.trains.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> {

    private T  data;
    private boolean success = true;
    private Integer size = null;
    private String message;
    private Object errors = null;
    private Integer errorCode = null;

    public GenericResponse<T> data( T data){
        this.data = data;
        return this;
    }

    public GenericResponse<T> success(boolean success){
        this.success = success;
        return this;
    }

    public GenericResponse<T> size(int size){
        this.size = size;
        return this;
    }

    public GenericResponse<T> message(String message){
        this.message = message;
        return this;
    }

    public GenericResponse<T> errors(Object errors){
        this.errors = errors;
        return this;
    }

    public GenericResponse<T> errorCode(Integer errorCode){
        this.errorCode = errorCode;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
