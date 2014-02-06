package br.com.telzir.service.calculopreco;

import java.util.HashMap;
import java.util.Map;

import br.com.telzir.service.calculopreco.constante.TipoPlano;
import br.com.telzir.service.calculopreco.exception.TipoPlanoException;

public final class PlanoPromocionalFactory {
    private static Map<TipoPlano, Class<? extends PlanoPromocional>> mapa;
    static {
        mapa = new HashMap<TipoPlano, Class<? extends PlanoPromocional>>();
        mapa.put(TipoPlano.FALEMAIS30, FaleMais30.class);
        mapa.put(TipoPlano.FALEMAIS60, FaleMais60.class);
        mapa.put(TipoPlano.FALEMAIS120, FaleMais120.class);
    }

    private PlanoPromocionalFactory() {
    }

    public static PlanoPromocional getPlano(TipoPlano tipoPlano) throws TipoPlanoException {
        Class<? extends PlanoPromocional> plano = mapa.get(tipoPlano);
        if (plano == null) {
            throw new TipoPlanoException("O plano fale mais do tipo: " + tipoPlano.getDescricao() + " nao existe.");
        }

        try {
            return plano.newInstance();
        } catch (Exception e) {
            throw new TipoPlanoException("Falha na criacao do plano fale mais do tipo: " + tipoPlano.getDescricao(), e);
        }
    }
}
