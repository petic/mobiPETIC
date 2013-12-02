package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.rest;

import java.io.Serializable;
import java.net.HttpURLConnection;

public class Resposta implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int OK = HttpURLConnection.HTTP_OK;
	public static final int CREATED = HttpURLConnection.HTTP_CREATED;
	public static final int NO_CONTENT = HttpURLConnection.HTTP_NO_CONTENT;
	public static final int NOT_FOUND = HttpURLConnection.HTTP_NOT_FOUND;
	public static final int INTERNAL_SERVER_ERROR = HttpURLConnection.HTTP_INTERNAL_ERROR;

	private int codigo = OK;
	private byte[] corpo = null;

	public Resposta() {
		this(OK, null);
	}

	public Resposta(int codigo, byte[] corpo) {
		this.codigo = codigo;
		this.corpo = corpo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public byte[] getCorpo() {
		return corpo;
	}

	public void setCorpo(byte[] corpo) {
		this.corpo = corpo;
	}

}
