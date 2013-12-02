package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.rest;

import java.net.HttpURLConnection;
import java.net.URL;

public class Cliente {

	public static Resposta realizarRequisicao(Requisicao requisicao) {
		try {
			URL url = new URL(requisicao.getUrl());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(requisicao.getParametro() != null);
			conn.setRequestMethod(requisicao.getMetodo());
			conn.setRequestProperty(Requisicao.HEADER_ACCEPT,
					"application/x-protobuf");
			if (requisicao.getParametro() != null) {
				conn.setRequestProperty(Requisicao.HEADER_CONTENT_TYPE,
						"application/x-protobuf");
				conn.getOutputStream().write(requisicao.getParametro());
			}
			int responseCode = conn.getResponseCode();
			Resposta resposta = new Resposta(responseCode, null);
			if ((requisicao.esperarResposta())
					&& (conn.getInputStream().available() > 0)) {
				byte[] responseBody = new byte[conn.getInputStream()
						.available()];
				conn.getInputStream().read(responseBody);
				resposta.setCorpo(responseBody);
			}
			return resposta;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}