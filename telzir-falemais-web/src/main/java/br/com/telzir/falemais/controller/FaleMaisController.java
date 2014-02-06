package br.com.telzir.falemais.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.telzir.falemais.controller.anotacao.Servico;
import br.com.telzir.falemais.controller.json.PrecoLigacaoJson;
import br.com.telzir.falemais.controller.json.SerializacaoJson;
import br.com.telzir.service.PlanoPromocionalService;
import br.com.telzir.service.calculopreco.constante.TipoPlano;
import br.com.telzir.service.entity.DirecaoChamada;
import br.com.telzir.service.entity.Localidade;
import br.com.telzir.service.exception.ServiceException;

@Resource
public class FaleMaisController extends AbstractController {
    @Servico
    private PlanoPromocionalService planoPromocionalService;

    public FaleMaisController(Result result) {
        super(result);
    }

    @Get("/")
    public void faleMaisHome() {
        addAtributo("listaLocalidade", planoPromocionalService.pesquisarLocalidade());
        addAtributo("listaTipoPlano", TipoPlano.values());
    }

    @Get("precoligacao")
    public void calcularPrecoLigacao(TipoPlano tipoPlano, Localidade origem, Localidade destino, int minutos) {
        final DirecaoChamada direcaoChamada = new DirecaoChamada(origem, destino);
        try {
            double precoComFalemais = this.planoPromocionalService.calcularPrecoLigacao(tipoPlano, direcaoChamada,
                    minutos);
            double precoSemFalemais = this.planoPromocionalService.calcularPrecoLigacao(direcaoChamada, minutos);

            serializarJson(new SerializacaoJson("precoLigacao", new PrecoLigacaoJson(tipoPlano, direcaoChamada,
                    minutos, precoSemFalemais, precoComFalemais)));
        } catch (ServiceException e) {
            serializarJson(new SerializacaoJson("precoLigacao", new PrecoLigacaoJson(tipoPlano, direcaoChamada,
                    minutos, null, null)));
        }
    }
}
