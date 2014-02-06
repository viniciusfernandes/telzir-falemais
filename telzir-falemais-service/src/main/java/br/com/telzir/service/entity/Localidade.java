package br.com.telzir.service.entity;

public class Localidade {
    private String codigo;

    public Localidade() {
    }

    public Localidade(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Localidade && this.codigo != null && this.codigo.equals(((Localidade) o).getCodigo());
    }

    @Override
    public int hashCode() {
        return this.codigo == null ? -1 : this.codigo.hashCode();
    }

    @Override
    public String toString() {
        return this.getCodigo();
    }
}
