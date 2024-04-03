package com.rmatute.trains.utils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

public class GenericException extends AbstractThrowableProblem {
	
	private static final long serialVersionUID = 1L;

    private String message;

    private Status status;

    private boolean success;

    private Object data;

    private String originalType;

    private Map<String, Object> mapFields;

    public GenericException(Exception ex) {
        super(null, ex.getMessage(), (StatusType)Status.BAD_REQUEST);
        this.message = ex.getMessage();
        this.status = Status.BAD_REQUEST;
        this.data = null;
        this.originalType = null;
    }

    public GenericException(String message, Status status) {
        super(null, message, (StatusType)status);
        this.message = message;
        this.status = status;
        this.data = null;
        this.originalType = null;
    }

    public GenericException(String message, Status status, boolean success) {
        super(null, message, (StatusType)status);
        this.message = message;
        this.status = status;
        this.success = success;
        this.data = null;
        this.originalType = null;
    }

    public GenericException(URI type, String title, StatusType status) {
        super(type, title, status);
    }

    public GenericException(String message, Status status, boolean success, Object data) {
        super(null, message, (StatusType)status);
        this.message = message;
        this.status = status;
        this.success = success;
        this.data = data;
        this.originalType = null;
    }

    public GenericException(Status status, boolean success, Object data) {
        super(null, null, (StatusType)status);
        this.message = null;
        this.status = status;
        this.success = success;
        this.data = data;
        this.originalType = null;
    }

    public GenericException(String message, Status status, String originalType) {
        super(null, message, (StatusType)status);
        this.message = message;
        this.status = status;
        this.success = false;
        this.data = null;
        this.originalType = originalType;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOriginalType() {
        return this.originalType;
    }

    public void setOriginalType(String originalType) {
        this.originalType = originalType;
    }

    public GenericException(String message, Status status, Map<String, Object> responseNewField) {
        super(null, message, (StatusType)status);
        this.message = message;
        this.status = status;
        // this.success = success;
        // this.data = data;
        this.originalType = null;

        if(Objects.nonNull(responseNewField)) {
            responseNewField.forEach(this::addField);
            this.data = null;
        }

        if(Utils.statusCodeIs2xx(status.getStatusCode())){
            this.success = true;
        }else if(Utils.statusCodeIs5xx(status.getStatusCode()) || Utils.statusCodeIs4xx(status.getStatusCode())){
            this.success = false;
        }
    }

    public Map<String, Object> getMapFields() {
        return mapFields;
    }

    public void addField(String key, Object value) {
        if(Objects.isNull(this.mapFields)){
            this.mapFields = new HashMap<>();
        }
        this.mapFields.put(key, value);
    }


}
