<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Telzir - Planos Promocionais</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/tabela.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/formulario.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/input.css"/>" />

<script type="text/javascript"
	src="<c:url value="/js/jquery-1.9.1.js"/>"></script>
	
<script type="text/javascript"
	src="<c:url value="/js/jquery.mask.min.js"/>"></script>

<script type="text/javascript">
	$(this).ready(function() {
		$('#minutos').mask('99999');
		
		$('#minutos').keyup(calcularPreco);
		$('#origem').change(calcularPreco);
		$('#destino').change(calcularPreco);
		$('#tipoPlano').change(calcularPreco);
	});

	function calcularPreco() {

		var request = $.ajax({
			type : "get",
			url : '<c:url value="/precoligacao"/>',
			data : $('#camposPesquisa').serialize()
		});

		request.done(function(response) {

			var precoLigacao = response.precoLigacao;
			var colunas = $('#tabelaPrecoLigacao #linhaPreco')[0].cells;
			colunas[0].innerHTML = precoLigacao.origem;
			colunas[1].innerHTML = precoLigacao.destino;
			colunas[2].innerHTML = precoLigacao.descricaoPlano;
			colunas[3].innerHTML = precoLigacao.minutos;
			colunas[4].innerHTML = precoLigacao.precoSemFalemais;
			colunas[5].innerHTML = precoLigacao.precoComFalemais;
		});

		request.fail(function(request, status, excecao) {
			alert(request);
		});
	}
</script>
</head>
<body>

	<fieldset id="camposPesquisa">
		<legend>::: Pesquisa dos Preços das Ligações :::</legend>
		<div class="label" style="width: 20%">Origem:</div>
		<div class="input" style="width: 5%">
			<select id="origem" name="origem.codigo" style="width: 100%">
				<c:forEach var="localidade" items="${listaLocalidade}">
					<option value="${localidade.codigo}">${localidade.codigo}</option>
				</c:forEach>
			</select>
		</div>
		<div class="label" style="width: 8%">Destino:</div>
		<div class="input" style="width: 5%">
			<select id="destino" name="destino.codigo" style="width: 100%">
				<c:forEach var="localidade" items="${listaLocalidade}">
					<option value="${localidade.codigo}">${localidade.codigo}</option>
				</c:forEach>
			</select>
		</div>
		<div class="label" style="width: 8%">Plano:</div>
		<div class="input" style="width: 10%">
			<select id="tipoPlano" name="tipoPlano" style="width: 100%">
				<c:forEach var="tipoPlano" items="${listaTipoPlano}">
					<option value="${tipoPlano}">${tipoPlano.descricao}</option>
				</c:forEach>
			</select>
		</div>
		<div class="label" style="width: 8%">Minutos:</div>
		<div class="input" style="width: 5%">
			<input type="text" id="minutos" name="minutos">
		</div>
	</fieldset>
	<table id="tabelaPrecoLigacao" class="listrada">
		<caption>Planos Promocionais - Fale Mais</caption>
		<thead>
			<tr>
				<th>Origem</th>
				<th>Destino</th>
				<th>Plano</th>
				<th>Minutos</th>
				<th>Sem Plano (R$)</th>
				<th>Com Plano (R$)</th>
			</tr>
		</thead>
		<tbody>
			<tr id="linhaPreco">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>


</body>
</html>