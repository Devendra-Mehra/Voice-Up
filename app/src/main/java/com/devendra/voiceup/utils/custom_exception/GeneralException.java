package com.devendra.voiceup.utils.custom_exception;

import com.devendra.voiceup.utils.FieldType;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */
public class GeneralException extends Exception {

    private FieldType fieldType;

    public GeneralException(String generalException, FieldType fieldType) {
        super(generalException);
        this.fieldType = fieldType;
    }

    public FieldType getFieldType() {
        return fieldType;
    }
}
