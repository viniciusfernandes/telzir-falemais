package br.com.telzir.falemais.controller.util;

public class ServiceLocatorException extends Exception {

    private static final long serialVersionUID = 7777846087328908059L;

    public ServiceLocatorException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
