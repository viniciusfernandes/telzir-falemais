package br.com.telzir.falemais.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.view.Results;
import br.com.telzir.falemais.controller.anotacao.Servico;
import br.com.telzir.falemais.controller.exception.ControllerException;
import br.com.telzir.falemais.controller.json.SerializacaoJson;
import br.com.telzir.falemais.controller.util.ServiceLocator;
import br.com.telzir.falemais.controller.util.ServiceLocatorException;

abstract class AbstractController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private String homePath;
    private Result result;

    public AbstractController(Result result) {
        this.result = result;
        try {
            this.init();

        } catch (ServiceLocatorException e) {
            this.logger.log(Level.SEVERE, "Falha no lookup de algum servico", e);
            this.result.include("erro",
                    "Falha no localizacao de algum servico. Verifique o log do servidor para maiores detalhes. CAUSA: "
                            + e.getMessage());
            irTelaErro();
        }

        try {
            this.inicializarPathHome();
        } catch (ControllerException e) {
            this.logger.log(Level.SEVERE, "O metodo home do controller " + this.getClass().getName()
                    + " nao foi bem definido", e);
            this.result.include("erro", "Falha inicializacao do PATH do metodo home do controller "
                    + this.getClass().getName() + ". Verifique o log do servidor para maiores detalhes.");
            irTelaErro();
        }
    }

    void addAtributo(String nomeAtributo, Object valorAtributo) {
        this.result.include(nomeAtributo, valorAtributo);
    }


    void irPaginaHome() {
        if (this.homePath == null) {
            throw new IllegalStateException("O controller " + this.getClass().getName() + " nao possui um metodo HOME");
        }
        this.result.redirectTo(this.homePath);
    }

    void serializarJson(SerializacaoJson serializacaoJson) {
        if (serializacaoJson == null) {
            return;
        }

        Serializer serializer = null;
        if (serializacaoJson.contemNome()) {
            serializer = this.result.use(Results.json()).from(serializacaoJson.getObjeto(), serializacaoJson.getNome());
        } else {
            serializer = this.result.use(Results.json()).from(serializacaoJson.getObjeto());
        }

        if (serializacaoJson.isRecursivo()) {
            serializer = serializer.recursive();
        }

        if (serializacaoJson.contemInclusaoAtributo()) {
            serializer.include(serializacaoJson.getAtributoInclusao());
        }

        if (serializacaoJson.contemExclusaoAtributo()) {
            serializer.exclude(serializacaoJson.getAtributoExclusao());
        }
        serializer.serialize();
    }

    void init() throws ServiceLocatorException {

        Field[] listaCampos = this.getClass().getDeclaredFields();
        for (Field campo : listaCampos) {
            if (campo.isAnnotationPresent(Servico.class)) {
                try {
                    campo.setAccessible(true);
                    campo.set(this, ServiceLocator.locate(campo.getType()));
                } catch (Exception e) {
                    throw new ServiceLocatorException("Falha na inicilizacao dos servicos do controller", e);
                } finally {
                    campo.setAccessible(false);
                }
            }
        }
    }

    

    /*
     * Metodo utilizado para definir qual eh o metodo HOME definido em cada
     * controller, isto eh, o PATH que aponta para a tela inicial
     */
    private void inicializarPathHome() throws ControllerException {
        String nome = this.getClass().getSimpleName().replace("Controller", "");
        nome += "Home";
        Method[] metodos = this.getClass().getDeclaredMethods();
        for (Method metodo : metodos) {
            if (metodo.getName().equalsIgnoreCase(nome)) {
                Get get = metodo.getAnnotation(Get.class);
                if (get == null) {
                    throw new ControllerException("É obrigatório que o método home " + metodo.getName()
                            + " seja anotado com @Get");
                }
                this.homePath = "/" + get.value()[0];
                break;
            }
        }
    }

    private void irTelaErro() {
        this.result.forwardTo(ErroController.class).erroHome();
        this.result.include("ancora", "topo");
    }

}
