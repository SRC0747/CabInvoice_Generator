package com.cabinvoicegenerator;

public class InvoiceGeneratorException extends Exception{
    public enum ExceptionType {
        USER_ALREADY_EXISTS
    }
    public ExceptionType type;
    public InvoiceGeneratorException(ExceptionType type, String message)
    {
        super(message);
        this.type = type;
    }
}
