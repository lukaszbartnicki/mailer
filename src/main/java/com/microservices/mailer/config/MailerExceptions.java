package com.microservices.mailer.config;

public class MailerExceptions extends Throwable {
    public static class InvalidEmailException extends Exception {
        public InvalidEmailException(String message){
            super(message);
        }
    }
    public static class DuplicateMailException extends Exception {
        public DuplicateMailException(String message){
            super(message);
        }
    }
    public static class EmptyListException extends Exception {
        public EmptyListException(String message){
            super(message);
        }
    }
    public static class NothingToDeleteException extends Exception {
        public NothingToDeleteException(String message){
            super(message);
        }
    }
    public static class NothingToChangeException extends Exception {
        public NothingToChangeException(String message){
            super(message);
        }
    }
    public static class IncorrectChangeException extends Exception {
        public IncorrectChangeException(String message){
            super(message);
        }
    }
    public static class NothingToSendException extends Exception {
        public NothingToSendException(String message){
            super(message);
        }
    }
}
