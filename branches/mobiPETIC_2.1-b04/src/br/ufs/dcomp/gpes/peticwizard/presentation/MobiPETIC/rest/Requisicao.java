package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.rest;

public class Requisicao {
	public static final String SERVIDOR = "http://peticwizard-gpes.rhcloud.com";
	public static final String SERVICO = SERVIDOR + "/rest";

	public static final int TIMEOUT = 5000;

	public static final String HEADER_ACCEPT = "Accept";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";

	public static final String METHOD_DELETE = "DELETE";
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";

	private String url, metodo;
	private boolean esperarResposta = false;
	private byte[] parametro = null;

	public Requisicao(String url, String metodo, boolean esperarResposta,
			byte[] parametro) {
		this.url = url;
		this.metodo = metodo;
		this.esperarResposta = esperarResposta;
		this.parametro = parametro;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public byte[] getParametro() {
		return parametro;
	}

	public void setParametro(byte[] parametro) {
		this.parametro = parametro;
	}

	public boolean esperarResposta() {
		return esperarResposta;
	}

	public void setEsperarResposta(boolean esperarResposta) {
		this.esperarResposta = esperarResposta;
	}
}