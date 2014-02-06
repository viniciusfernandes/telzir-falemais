package br.com.telzir.falemais.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class ErroController extends AbstractController {

    public ErroController(Result result) {
        super(result);
    }

    @Get("erro")
    public void erroHome() {
    }
}
