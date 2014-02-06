package br.com.telzir.service.calculopreco;

import br.com.telzir.service.entity.PrecoChamada;

public abstract class PlanoPromocional {
    private final int minutosGratuitos;
    private final double dezPorCento = 1.1d;

    public PlanoPromocional(int minutosGratuitos) {
        this.minutosGratuitos = minutosGratuitos;
    }

    public int getMinutosGratuitos() {
        return minutosGratuitos;
    }

    public double calcularPrecoPlano(PrecoChamada preco, int minutosLigacao) {
        if (minutosLigacao <= this.minutosGratuitos) {
            return 0;
        }

        final int excendente = minutosLigacao - minutosGratuitos;
        return excendente * dezPorCento * preco.getValorMinuto();
    }
}
