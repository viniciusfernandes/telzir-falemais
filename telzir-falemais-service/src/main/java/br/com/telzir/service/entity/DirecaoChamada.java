package br.com.telzir.service.entity;

public class DirecaoChamada {
    private final Localidade origem;
    private final Localidade destino;

    public DirecaoChamada(Localidade origem, Localidade destino) {
        if (origem == null || destino == null) {
            throw new IllegalArgumentException("Origem e destino sao obrigatorios");
        }
        this.origem = origem;
        this.destino = destino;
    }

    public Localidade getOrigem() {
        return origem;
    }

    public Localidade getDestino() {
        return destino;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DirecaoChamada && this.origem.equals(((DirecaoChamada) o).getOrigem())
                && this.destino.equals(((DirecaoChamada) o).getDestino());
    }

    @Override
    public int hashCode() {
        return (this.origem.getCodigo() + this.destino.getCodigo()).hashCode();
    }

    @Override
    public String toString() {
        return this.origem + " => " + this.destino;
    }
}
