package br.com.telzir.service.calculopreco.exception;

import br.com.telzir.service.exception.ServiceException;

public class CalculoPrecoLigacaoException extends ServiceException {

    private static final long serialVersionUID = 3341748586415097659L;

    public CalculoPrecoLigacaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
