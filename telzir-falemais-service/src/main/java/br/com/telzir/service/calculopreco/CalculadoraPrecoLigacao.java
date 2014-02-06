package br.com.telzir.service.calculopreco;

import br.com.telzir.service.calculopreco.constante.TipoPlano;
import br.com.telzir.service.calculopreco.exception.CalculoPrecoLigacaoException;
import br.com.telzir.service.calculopreco.exception.TipoPlanoException;
import br.com.telzir.service.entity.PrecoChamada;

public final class CalculadoraPrecoLigacao {
    private static final CalculadoraPrecoLigacao CALCULADORA;
    static {
        CALCULADORA = new CalculadoraPrecoLigacao();
    }

    private CalculadoraPrecoLigacao() {
    }

    public static CalculadoraPrecoLigacao getInstance() {
        return CALCULADORA;
    }

    public double calcular(TipoPlano tipoPlano, PrecoChamada preco, int minutos) throws CalculoPrecoLigacaoException {
        // Caso nao tenhamos plano promocional escolhido calcularemos uma
        // ligacao comum
        if (tipoPlano == null) {
            return minutos * preco.getValorMinuto();
        }

        try {
            return PlanoPromocionalFactory.getPlano(tipoPlano).calcularPrecoPlano(preco, minutos);
        } catch (TipoPlanoException e) {
            throw new CalculoPrecoLigacaoException("Falha no calculo do preco da ligacao para o plano "
                    + tipoPlano.getDescricao(), e);
        }
    }
}
