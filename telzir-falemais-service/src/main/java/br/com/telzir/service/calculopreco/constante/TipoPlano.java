package br.com.telzir.service.calculopreco.constante;

public enum TipoPlano {
    FALEMAIS30(30, "Fale mais 30"),
    FALEMAIS60(60, "Fale mais 60"),
    FALEMAIS120(120, "Fale mais 120");

    private final int minutos;
    private final String descricao;

    private TipoPlano(int minutos, String descricao) {
        this.minutos = minutos;
        this.descricao = descricao;
    }

    public int getMinutos() {
        return minutos;
    }

    public String getDescricao() {
        return descricao;
    }
}
