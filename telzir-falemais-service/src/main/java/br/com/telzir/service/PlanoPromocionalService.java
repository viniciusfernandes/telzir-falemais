package br.com.telzir.service;

import java.util.List;

import javax.ejb.Local;

import br.com.telzir.service.calculopreco.constante.TipoPlano;
import br.com.telzir.service.entity.DirecaoChamada;
import br.com.telzir.service.entity.Localidade;
import br.com.telzir.service.entity.PrecoChamada;
import br.com.telzir.service.exception.ServiceException;

@Local
public interface PlanoPromocionalService {
    List<Localidade> pesquisarLocalidade();

    List<PrecoChamada> pesquisarPrecoChamada();

    Localidade pesquisarLocalidadeByCodigo(String codigo);

    double calcularPrecoLigacao(TipoPlano tipoPlano, PrecoChamada preco, int minutos) throws ServiceException;

    PrecoChamada pesquisarPrecoChamada(DirecaoChamada direcaoChamada) throws ServiceException;

    double calcularPrecoLigacao(TipoPlano tipoPlano, DirecaoChamada direcaoChamada, int minutos)
            throws ServiceException;

    double calcularPrecoLigacao(DirecaoChamada direcaoChamada, int minutos) throws ServiceException;
}
