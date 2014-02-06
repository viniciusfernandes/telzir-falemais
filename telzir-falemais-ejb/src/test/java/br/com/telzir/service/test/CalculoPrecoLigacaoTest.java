package br.com.telzir.service.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.telzir.service.PlanoPromocionalService;
import br.com.telzir.service.calculopreco.constante.TipoPlano;
import br.com.telzir.service.entity.DirecaoChamada;
import br.com.telzir.service.entity.Localidade;
import br.com.telzir.service.exception.ServiceException;
import br.com.telzir.service.impl.PlanoPromocionalServiceImpl;

public class CalculoPrecoLigacaoTest {
    private PlanoPromocionalService planoPromocionalService;

    @Before
    public void setUp() throws Exception {
        this.planoPromocionalService = new PlanoPromocionalServiceImpl();
    }

    // A implementacao desse metodo cobre o mesmo bloco de codigo para todos os
    // tipos de planos
    @Test
    public void validarCalculoPrecoLigacao() {

        final DirecaoChamada direcaoChamada = new DirecaoChamada(new Localidade("011"), new Localidade("016"));
        try {
            // Validando o valor dos minutos utilizados
            planoPromocionalService.calcularPrecoLigacao(TipoPlano.FALEMAIS30, direcaoChamada, -1);
            Assert.fail("Os minutos utilizados devem ser positivos");
        } catch (ServiceException e) {
            // Espera-se que a excecao seja tratada por uma camada superior
        }

        try {
            // Validando a obrigatoriedade da chamada
            planoPromocionalService.calcularPrecoLigacao(TipoPlano.FALEMAIS30, (DirecaoChamada) null, 3);
            Assert.fail("A direcao da chamada eh obrigatorio para o calculo");
        } catch (ServiceException e) {
            // Espera-se que a excecao seja tratada por uma camada superior
        }

        try {
            // Validando a obrigatoriedade do tipo de plano
            planoPromocionalService.calcularPrecoLigacao(null, direcaoChamada, 3);
        } catch (ServiceException e) {
            Assert.fail("O tipo de plano nao eh obrigatorio para o calculo, "
                    + "pois sem plano escolhido utilizaremos uma ligacao comum. Causa: " + e.getMensagemConcatenada());
        }
    }

    @Test
    public void calcularPrecoLigacaoComum() {
        final DirecaoChamada direcaoChamada = this.gerarDirecaoChamada();

        try {
            final double valorMinuto = this.planoPromocionalService.pesquisarPrecoChamada(direcaoChamada)
                    .getValorMinuto();
            final int minutosUtilizados = 10;
            final double precoLigacaoPrevisto = minutosUtilizados * valorMinuto;

            // Testando o valor limite inferior para ligacao gratuita
            Assert.assertEquals("O preco para a ligacao de " + minutosUtilizados
                    + " minutos nao esta correto pois esses minutos deveriam ser gratuitos", precoLigacaoPrevisto,
                    planoPromocionalService.calcularPrecoLigacao(null, direcaoChamada, minutosUtilizados), 0d);
        } catch (ServiceException e) {
            Assert.fail("Nao foi possivel calcular o preco da ligacao. Causa: " + e.getMensagemConcatenada());
        }
    }

    @Test
    public void calcularPrecoLigacaoFaleMais30() {
        this.calculoPrecoTest(TipoPlano.FALEMAIS30);
    }

    @Test
    public void calcularPrecoLigacaoFaleMais60() {
        this.calculoPrecoTest(TipoPlano.FALEMAIS60);
    }

    @Test
    public void calcularPrecoLigacaoFaleMais120() {
        this.calculoPrecoTest(TipoPlano.FALEMAIS120);
    }

    // Metodo utilizado para reaproveitamento da rotina de teste
    private void calculoPrecoTest(TipoPlano tipoPlano) {
        final DirecaoChamada direcaoChamada = this.gerarDirecaoChamada();
        double precoLigacaoPrevisto = 0d;
        final int delta = 10;
        int minutosUtilizados = 0;

        try {
            minutosUtilizados = tipoPlano.getMinutos() - delta;
            // Testando o valor limite inferior para ligacao gratuita
            Assert.assertEquals("O preco para a ligacao de " + minutosUtilizados
                    + " minutos nao esta correto pois esses minutos deveriam ser gratuitos", precoLigacaoPrevisto,
                    planoPromocionalService.calcularPrecoLigacao(tipoPlano, direcaoChamada, minutosUtilizados), 0d);
        } catch (ServiceException e) {
            Assert.fail("Nao foi possivel calcular o preco da ligacao");
        }

        try {
            minutosUtilizados = tipoPlano.getMinutos();
            // Testando o valor limite para ligacao gratuita
            Assert.assertEquals("O preco para a ligacao de " + minutosUtilizados
                    + " minutos nao esta correto pois esses minutos deveriam ser gratuitos", precoLigacaoPrevisto,
                    planoPromocionalService.calcularPrecoLigacao(tipoPlano, direcaoChamada, minutosUtilizados), 0d);
        } catch (ServiceException e) {
            Assert.fail("Nao foi possivel calcular o preco da ligacao");
        }

        try {
            precoLigacaoPrevisto = 20.9d;
            // Testando a ligacao que estourou o valor limite para ligacao
            // gratuita
            minutosUtilizados = tipoPlano.getMinutos() + delta;
            Assert.assertEquals("O preco para a ligacao de " + minutosUtilizados + " minutos nao esta correto",
                    precoLigacaoPrevisto,
                    planoPromocionalService.calcularPrecoLigacao(tipoPlano, direcaoChamada, minutosUtilizados), 0d);
        } catch (ServiceException e) {
            Assert.fail("Nao foi possivel calcular o preco da ligacao. Causa: " + e.getMensagemConcatenada());
        }
    }

    private DirecaoChamada gerarDirecaoChamada() {
        return new DirecaoChamada(new Localidade("011"), new Localidade("016"));
    }
}
