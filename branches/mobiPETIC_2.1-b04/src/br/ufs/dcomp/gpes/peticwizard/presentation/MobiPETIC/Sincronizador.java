package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

/**
 * <code>Service</code> que sincroniza os dados editados na aplicação com o
 * servidor.
 * 
 * @see IntentService
 */
public class Sincronizador extends IntentService {

	private SharedPreferences preferencia;

	public Sincronizador() {
		super("Sincronizador");
	}

	/**
	 * Chamado automaticamente para realizar as operações do
	 * <code>Service</code>.
	 * <p>
	 * Envia os dados alterados ao servidor e recebe a confirmação caso as
	 * alterações tenham sido efetuadas.
	 * </p>
	 * 
	 * @param intent
	 *            Objeto <code>Intent</code> usado para iniciar o
	 *            <code>Service</code>
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		// Enviará item a item ID do processo e últimaAtualização do
		// questionário de maturidade dos processos que
		// sofreram atualização e receberá a confirmação da sincronização ou a
		// lista de identificadores das questões divergentes.
		try {
			// Log.i("++++++++++++", "Iniciando Serviço!");
			Cursor cursor = getContentResolver().query(
					DBDefinicoes.Processos.CONTENT_URI,
					new String[] { DBDefinicoes.Processos.COLUMN_NAME_ID },
					DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR + " = ?",
					new String[] { "1" },
					DBDefinicoes.Processos.DEFAULT_SORT_ORDER);
			while (cursor.moveToNext()) {
				URL url = new URL(
						"http://10.0.1.106:8081/Teste/atualizarprocessoquestoes"
								+ escreverProcessoQuestoes(cursor.getString(0)));
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				// urlConnection.setDoOutput(true);
				// usar a linha abaixo, se tamanho do corpo da informação que
				// será enviada é conhecido
				// urlConnection.setFixedLengthStreamingMode(100 /*tamanho*/);
				// ou se o tamanho é desconhecido
				// urlConnection.setChunkedStreamingMode(0 /*padrão '0' ou
				// definir tamanho de uso*/);

				// OutputStream out = new
				// BufferedOutputStream(urlConnection.getOutputStream());
				// metodo de escrita
				// escreverProcessoQuestoes(/*out*/);

				InputStream in = new BufferedInputStream(
						urlConnection.getInputStream());
				// o retorno do pedido pode ser boolean (sucesso no envio ou
				// não)
				// neste caso haverá um tratamento para isso (talvez repetir a
				// operação)
				if (in.read() == Byte.valueOf(Byte.parseByte("1"))) {
					// Log.e("++++++++++++", "Entrou e modificou processo " +
					// cursor.getString(0) + "!!!!");
					ContentValues values = new ContentValues();
					values.put(DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR,
							0);
					getContentResolver().update(
							DBDefinicoes.Processos.CONTENT_URI, values,
							DBDefinicoes.Processos.COLUMN_NAME_ID + " = ?",
							new String[] { cursor.getString(0) });
				}
			}
			cursor.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prepara os dados para o envio como parametros de uma requisição HTTP.
	 * 
	 * @return <code>String</code> preparada para ser anexada à URL.
	 * @throws IOException
	 */
	private String escreverProcessoQuestoes(String idProcesso)
			throws IOException {
		preferencia = getSharedPreferences("PrefMobiPETIC", MODE_PRIVATE);
		int id_empresa = preferencia.getInt("Empresa", 0);

		// ProcessoQuestoesMaturidadeProtos.ProcessoQuestoes.Builder
		// procQuestoesP =
		// ProcessoQuestoesMaturidadeProtos.ProcessoQuestoes.newBuilder();
		Cursor cursor = getContentResolver().query(
				DBDefinicoes.ProcessoQuestoes.CONTENT_URI,
				new String[] {
						DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO,
						DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO,
						DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO },
				DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO + " = ?",
				new String[] { idProcesso },
				DBDefinicoes.ProcessoQuestoes.DEFAULT_SORT_ORDER);

		StringBuilder sb = new StringBuilder("?empresa_id=" + id_empresa
				+ "&proc_quest=");

		while (cursor.moveToNext()) {
			// Log.i("++++++++++++", "Atualização encontrada!");
			// ProcessoQuestoesMaturidadeProtos.ProcessoQuestao.Builder unidade
			// = ProcessoQuestoesMaturidadeProtos.ProcessoQuestao.newBuilder();
			sb.append(cursor.getString(0) + "_" + cursor.getInt(1) + "_"
					+ cursor.getString(2));
			/*
			 * unidade.setIdProcesso(cursor.getString(0))
			 * .setIdQuestao(cursor.getInt(1)) .setMarcado(cursor.getString(2));
			 * procQuestoesP.addProcessoQuestao(unidade.build());
			 */
			sb.append("&proc_quest=");
		}
		cursor.close();
		sb.delete(sb.length() - 12, sb.length());
		// procQuestoesP.build().writeTo(out);
		return sb.toString();
		// return (procQuestoesP.getProcessoQuestaoCount() > 0);
	}
}