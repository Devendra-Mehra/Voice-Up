package com.devendra.voiceup.utils.custom_exception;

import com.devendra.voiceup.utils.FieldType;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */
public class FieldException extends Exception {

    private FieldType fieldType;

    public FieldException(String emptyFieldException, FieldType fieldType) {
        super(emptyFieldException);
        this.fieldType = fieldType;
    }

    public FieldType getFieldType() {
        return fieldType;
    }
}
