package br.com.telzir.service.entity;

public class PrecoChamada {
    private final double valorMinuto;
    private final DirecaoChamada direcaoChamada;

    public PrecoChamada(Localidade origem, Localidade destino, double valorMinuto) {
        this(new DirecaoChamada(origem, destino), valorMinuto);
    }

    public PrecoChamada(DirecaoChamada direcaoChamada, double valorMinuto) {
        this.direcaoChamada = direcaoChamada;
        this.valorMinuto = valorMinuto;
    }

    public Localidade getOrigem() {
        return direcaoChamada.getOrigem();
    }

    public Localidade getDestino() {
        return direcaoChamada.getDestino();
    }

    public double getValorMinuto() {
        return valorMinuto;
    }

    public boolean isDirecaoIgual(DirecaoChamada direcaoChamada) {
        return this.direcaoChamada.equals(direcaoChamada);
    }
}
