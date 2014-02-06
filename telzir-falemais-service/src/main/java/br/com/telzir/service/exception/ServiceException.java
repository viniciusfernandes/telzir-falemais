package br.com.telzir.service.exception;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true, inherited = true)
public class ServiceException extends Exception {

    private static final long serialVersionUID = 4002460058473505938L;

    private List<String> listaMensagem;

    public ServiceException() {
        listaMensagem = new ArrayList<String>();
    }

    public ServiceException(String mensagem) {
        super(mensagem);
        this.listaMensagem = new ArrayList<String>();
        this.listaMensagem.add(mensagem);
    }

    public ServiceException(String mensagem, Throwable causa) {
        super(mensagem, causa);
        this.listaMensagem = new ArrayList<String>();
        this.listaMensagem.add(mensagem);
    }

    public ServiceException(final List<String> listaMensagem) {
        this.listaMensagem = listaMensagem;
    }

    public List<String> getListaMensagem() {
        return this.listaMensagem;
    }

    public void addMensagem(List<String> listaMensagem) {
        this.listaMensagem.addAll(listaMensagem);
    }

    public void addMensagem(String mensagem) {
        this.listaMensagem.add(mensagem);
    }

    public String getMensagemConcatenada() {
        final StringBuilder concatenada = new StringBuilder();
        for (String mesagem : this.listaMensagem) {
            concatenada.append(mesagem).append(". ");
        }
        return concatenada.toString();
    }

    public boolean contemExceptionPropagada() {
        return getCause() != null;
    }

    public boolean contemMensagem() {
        return !this.listaMensagem.isEmpty();
    }
}
