package br.com.telzir.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.telzir.service.PlanoPromocionalService;
import br.com.telzir.service.calculopreco.CalculadoraPrecoLigacao;
import br.com.telzir.service.calculopreco.constante.TipoPlano;
import br.com.telzir.service.entity.DirecaoChamada;
import br.com.telzir.service.entity.Localidade;
import br.com.telzir.service.entity.PrecoChamada;
import br.com.telzir.service.exception.ServiceException;
import br.com.telzir.util.StringUtils;

@Stateless
public class PlanoPromocionalServiceImpl implements PlanoPromocionalService {
    @Override
    public Localidade pesquisarLocalidadeByCodigo(String codigo) {
        if (StringUtils.isEmpty(codigo)) {
            return null;
        }

        // Pesquisando apenas os codigos existentes
        final List<Localidade> listaLocalidade = pesquisarLocalidade();
        for (Localidade localidade : listaLocalidade) {
            if (codigo.equals(localidade.getCodigo())) {
                return localidade;
            }
        }
        return null;
    }

    @Override
    public List<Localidade> pesquisarLocalidade() {
        final List<Localidade> listaLocalidade = new ArrayList<Localidade>();
        listaLocalidade.add(new Localidade("011"));
        listaLocalidade.add(new Localidade("016"));
        listaLocalidade.add(new Localidade("017"));
        listaLocalidade.add(new Localidade("018"));
        return listaLocalidade;
    }

    @Override
    public List<PrecoChamada> pesquisarPrecoChamada() {
        List<PrecoChamada> listaPreco = new ArrayList<PrecoChamada>();
        // Aqui chamamos o servico de pesquisa para recuperar as localidades
        // pois assim garantimos a existencias dos codigos e assim associamos os
        // precos de cada chamada
        listaPreco.add(new PrecoChamada(pesquisarLocalidadeByCodigo("011"), pesquisarLocalidadeByCodigo("016"), 1.9d));
        listaPreco.add(new PrecoChamada(pesquisarLocalidadeByCodigo("011"), pesquisarLocalidadeByCodigo("017"), 1.7d));
        listaPreco.add(new PrecoChamada(pesquisarLocalidadeByCodigo("011"), pesquisarLocalidadeByCodigo("018"), 0.9d));
        listaPreco.add(new PrecoChamada(pesquisarLocalidadeByCodigo("016"), pesquisarLocalidadeByCodigo("011"), 2.9d));
        listaPreco.add(new PrecoChamada(pesquisarLocalidadeByCodigo("017"), pesquisarLocalidadeByCodigo("011"), 2.7d));
        listaPreco.add(new PrecoChamada(pesquisarLocalidadeByCodigo("018"), pesquisarLocalidadeByCodigo("011"), 1.9d));
        return listaPreco;
    }

    @Override
    public double calcularPrecoLigacao(TipoPlano tipoPlano, PrecoChamada preco, int minutos) throws ServiceException {

        if (preco == null) {
            throw new ServiceException("Preco da chamada eh obrigatorio para o calculo");
        }

        if (minutos < 0) {
            throw new ServiceException("Os minutos devem ser valores positivos para o calculo");
        }

        return CalculadoraPrecoLigacao.getInstance().calcular(tipoPlano, preco, minutos);
    }

    @Override
    public double calcularPrecoLigacao(TipoPlano tipoPlano, DirecaoChamada direcaoChamada, int minutos)
            throws ServiceException {
        final PrecoChamada preco = this.pesquisarPrecoChamada(direcaoChamada);
        return this.calcularPrecoLigacao(tipoPlano, preco, minutos);
    }

    @Override
    public double calcularPrecoLigacao(DirecaoChamada direcaoChamada, int minutos) throws ServiceException {
        final PrecoChamada preco = this.pesquisarPrecoChamada(direcaoChamada);
        return this.calcularPrecoLigacao(null, preco, minutos);
    }

    @Override
    public PrecoChamada pesquisarPrecoChamada(DirecaoChamada direcaoChamada) throws ServiceException {
        if (direcaoChamada == null) {
            throw new ServiceException("A direcao da chamada eh obrigatoria para a pesquisa dos precos das ligacoes");
        }

        List<PrecoChamada> listaPreco = this.pesquisarPrecoChamada();
        for (PrecoChamada precoChamada : listaPreco) {
            if (precoChamada.isDirecaoIgual(direcaoChamada)) {
                return precoChamada;
            }
        }

        throw new ServiceException("Nao existe preco para ligacoes de " + direcaoChamada.getOrigem() + " para "
                + direcaoChamada.getDestino());
    }

}
