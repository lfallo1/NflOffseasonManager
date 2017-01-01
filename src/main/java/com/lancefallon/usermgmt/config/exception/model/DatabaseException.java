package com.lancefallon.usermgmt.config.exception.model;

public class DatabaseException extends AbstractException {
    private static final long serialVersionUID = 1L;
    
    private CustomErrorMessage error;
    
    public DatabaseException(CustomErrorMessage error){
        this.error = error; 
    }
    
    public CustomErrorMessage getError(){
        return this.error;
    }
}
