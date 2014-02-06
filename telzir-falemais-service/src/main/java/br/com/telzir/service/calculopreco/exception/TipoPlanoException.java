package br.com.telzir.service.calculopreco.exception;

public class TipoPlanoException extends Exception {

    private static final long serialVersionUID = -3951289139425133128L;

    public TipoPlanoException(String message) {
        super(message);
    }

    public TipoPlanoException(String message, Throwable cause) {
        super(message, cause);
    }

}
