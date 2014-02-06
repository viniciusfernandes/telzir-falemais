package br.com.telzir.falemais.controller.json;

import br.com.telzir.service.calculopreco.constante.TipoPlano;
import br.com.telzir.service.entity.DirecaoChamada;
import br.com.telzir.util.StringUtils;

public class PrecoLigacaoJson {
    private final String descricaoPlano;
    private final String origem;
    private final String destino;
    private final int minutos;
    private final String precoComFalemais;
    private final String precoSemFalemais;

    public PrecoLigacaoJson(TipoPlano tipoPlano, DirecaoChamada direcaoChamada, int minutos, Double precoSemFalemais,
            Double precoComFalemais) {
        this.descricaoPlano = tipoPlano == null ? "" : tipoPlano.getDescricao();
        this.origem = direcaoChamada == null ? "" : direcaoChamada.getOrigem().getCodigo();
        this.destino = direcaoChamada == null ? "" : direcaoChamada.getDestino().getCodigo();
        this.minutos = minutos;
        this.precoSemFalemais = precoSemFalemais == null ? "---" : StringUtils.formatarValorMonetario(precoSemFalemais);
        this.precoComFalemais = precoComFalemais == null ? "---" : StringUtils.formatarValorMonetario(precoComFalemais);
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public String getDescricaoPlano() {
        return descricaoPlano;
    }

    public String getPrecoComFalemais() {
        return precoComFalemais;
    }

    public String getPrecoSemFalemais() {
        return precoSemFalemais;
    }

    public int getMinutos() {
        return minutos;
    }

}
