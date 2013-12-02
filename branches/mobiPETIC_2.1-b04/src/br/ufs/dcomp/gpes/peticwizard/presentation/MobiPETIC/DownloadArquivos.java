package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;

/**
 * <code>Service</code> que atualiza, em <i>background</i>, os dados para
 * atualizar o banco de dados.
 * 
 * @see IntentService
 */
public class DownloadArquivos extends IntentService {

	private SharedPreferences preferencia;

	public DownloadArquivos() {
		super("DowloadArquivos");
	}

	/**
	 * Chamado automaticamente para realizar as operações do
	 * <code>Service</code>.
	 * <p>
	 * Download e armazenamento em banco de dados <i>SQLite</i> dos dados do
	 * artefato PETIC da empresa.
	 * </p>
	 * 
	 * @param intent
	 *            Objeto <code>Intent</code> usado para iniciar o
	 *            <code>Service</code>
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		// iniciando variáveis e objetos
		boolean alterou = false;
		boolean completou = false;
		preferencia = getSharedPreferences("PrefMobiPETIC", MODE_PRIVATE);
		int id_empresa = preferencia.getInt("Empresa", 0);
		Long ultimaAlteracao = preferencia.getLong("Alterado_em", 0);
		// editor para efetuar modificações nas variáveis compartilhadas
		Editor editor = preferencia.edit();
		// lista de operações para efetuar as inserções no banco de dados.
		ArrayList<ContentProviderOperation> op = new ArrayList<ContentProviderOperation>();
		// Objetos de manipulação dos arquivos binários Protocol Buffers
		AreasProtos.Areas areasP = null;
		SubareasProtos.Subareas subareasP = null;
		ProcessosProtos.Processos processosP = null;
		QuestoesMaturidadeProtos.Questoes questoesP = null;
		ProcessoQuestoesMaturidadeProtos.ProcessoQuestoes p_questoesP = null;
		AcoesProtos.Acoes acoesP = null;

		while (!completou) {
			try {
				// Download e armazenamento das áreas PETIC pertencentes à
				// empresa.
				// Log.i("++DownloadArquivos++","Buscando novos registros para a lista de áreas no servidor...");

				// recebendo registros do servidor.
				areasP = AreasProtos.Areas.parseFrom(getServRemotoAreas(
						id_empresa, ultimaAlteracao));

				// adicionando as instruções de inserções de cada registro na
				// lista de operações, caso haja.
				if (areasP != null && areasP.getAreaCount() > 0) {
					alterou = true;
					// Log.i("++DownloadArquivos++","Inserindo novas Áreas no banco");
					for (int i = 0; i < areasP.getAreaCount(); i++) {
						op.add(ContentProviderOperation
								.newInsert(DBDefinicoes.Areas.CONTENT_URI)
								.withValue(DBDefinicoes.Areas.COLUMN_NAME_ID,
										areasP.getArea(i).getId())
								.withValue(DBDefinicoes.Areas.COLUMN_NAME_NOME,
										areasP.getArea(i).getNome()).build());
					}
				}
				if (alterou) {
					getContentResolver().delete(DBDefinicoes.Areas.CONTENT_URI,
							null, null); // removendo os dados desatualizados
					getContentResolver().applyBatch(DBDefinicoes.AUTHORITY, op); // inserindo
																					// os
																					// dados
																					// atualizados
					alterou = false;
				}
				op.clear(); // limpando a lista de operações para receber novas
							// operações.

				// Download e armazenamento das sub-áreas PETIC pertencentes à
				// empresa.
				// Log.i("++DownloadArquivos++","Buscando novos registros para a lista de subareas no servidor...");

				// recebendo registros do servidor.
				subareasP = SubareasProtos.Subareas
						.parseFrom(getServRemotoSubareas(id_empresa,
								ultimaAlteracao));

				// adicionando as instruções de inserções de cada registro na
				// lista de operações, caso haja.
				if (subareasP != null && subareasP.getSubareaCount() > 0) {
					alterou = true;
					// Log.i("++DownloadArquivos++","Inserindo novas Sub-áreas no banco");
					for (int i = 0; i < subareasP.getSubareaCount(); i++) {
						op.add(ContentProviderOperation
								.newInsert(DBDefinicoes.Subareas.CONTENT_URI)
								.withValue(
										DBDefinicoes.Subareas.COLUMN_NAME_ID,
										subareasP.getSubarea(i).getId())
								.withValue(
										DBDefinicoes.Subareas.COLUMN_NAME_NOME,
										subareasP.getSubarea(i).getNome())
								.withValue(
										DBDefinicoes.Subareas.COLUMN_NAME_ID_AREA,
										subareasP.getSubarea(i).getAreaId())
								.build());
					}
				}
				if (alterou) {
					getContentResolver().delete(
							DBDefinicoes.Subareas.CONTENT_URI, null, null); // removendo
																			// os
																			// dados
																			// desatualizados
					getContentResolver().applyBatch(DBDefinicoes.AUTHORITY, op); // inserindo
																					// os
																					// dados
																					// atualizados
					alterou = false;
				}
				op.clear(); // limpando a lista de operações para receber novas
							// operações.

				// Download e armazenamento dos processos PETIC pertencentes à
				// empresa.
				// Log.i("++DownloadArquivos++","Buscando novos registros para a lista de processos no servidor...");

				// recebendo registros do servidor.
				processosP = ProcessosProtos.Processos
						.parseFrom(getServRemotoProcessos(id_empresa,
								ultimaAlteracao));

				// adicionando as instruções de inserções de cada registro na
				// lista de operações, caso haja.
				if (processosP != null && processosP.getProcessoCount() > 0) {
					alterou = true;
					// Log.i("++DownloadArquivos++","Inserindo novas Processos no banco");
					for (int i = 0; i < processosP.getProcessoCount(); i++) {
						if (Long.valueOf(processosP.getProcesso(i)
								.getModificacao()) > ultimaAlteracao)
							ultimaAlteracao = Long.valueOf(processosP
									.getProcesso(i).getModificacao());
						Builder insert = ContentProviderOperation
								.newInsert(DBDefinicoes.Processos.CONTENT_URI)
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_ID,
										processosP.getProcesso(i).getId())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_NOME,
										processosP.getProcesso(i).getNome())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_PRIORITARIO,
										processosP.getProcesso(i)
												.getEPrioritario())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_PRIORIDADE_DEF,
										processosP.getProcesso(i)
												.getPrioridadeDef())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_DESCRICAO,
										processosP.getProcesso(i)
												.getDescricao())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_ENTRADAS,
										processosP.getProcesso(i).getEntradas())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_SAIDAS,
										processosP.getProcesso(i).getSaidas())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_RESPONSAVEL,
										processosP.getProcesso(i)
												.getResponsavel())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_STAKEHOLDERS,
										processosP.getProcesso(i)
												.getStakeholders())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_ID_SUBAREA,
										processosP.getProcesso(i)
												.getSubareaId())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_DATA_MODIFICACAO,
										processosP.getProcesso(i)
												.getModificacao())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE,
										processosP.getProcesso(i)
												.getQuestionario()
												.getMaturidade().ordinal())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT,
										processosP.getProcesso(i)
												.getQuestionario()
												.getMaturidadeAnt().ordinal())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_MODIFICACAO_QUESTIONARIO,
										processosP.getProcesso(i)
												.getQuestionario()
												.getModificacao())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO,
										processosP.getProcesso(i)
												.getQuestionario().getEditor())
								.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR,
										0);
						Cursor cursor = getContentResolver()
								.query(DBDefinicoes.Processos.CONTENT_URI,
										new String[] {
												DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE,
												DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT,
												DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO,
												DBDefinicoes.Processos.COLUMN_NAME_MODIFICACAO_QUESTIONARIO,
												DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR },
										DBDefinicoes.Processos.COLUMN_NAME_ID
												+ " = ?",
										new String[] { processosP
												.getProcesso(i).getId() }, null);
						if (cursor.moveToNext())
							if (cursor.getInt(4) == 1)
								insert.withValue(
										DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE,
										cursor.getInt(0))
										.withValue(
												DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT,
												cursor.getInt(1))
										.withValue(
												DBDefinicoes.Processos.COLUMN_NAME_MODIFICACAO_QUESTIONARIO,
												cursor.getLong(3))
										.withValue(
												DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO,
												cursor.getString(2))
										.withValue(
												DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR,
												1);
						cursor.close();
						op.add(insert.build());
					}
				}
				if (alterou) {
					getContentResolver().delete(
							DBDefinicoes.Processos.CONTENT_URI, null, null); // removendo
																				// os
																				// dados
																				// desatualizados
					getContentResolver().applyBatch(DBDefinicoes.AUTHORITY, op); // inserindo
																					// os
																					// dados
																					// atualizados
					editor.putLong("Alterado_em", ultimaAlteracao);
					editor.commit();
					alterou = false;
				}
				op.clear(); // limpando a lista de operações para receber novas
							// operações.

				// Download e armazenamento das questões do questionário de
				// mauridade da PETIC pertencentes com possíveis edições da
				// empresa.
				// recebendo registros do servidor.
				questoesP = QuestoesMaturidadeProtos.Questoes
						.parseFrom(getServRemotoQuestoes(id_empresa,
								ultimaAlteracao));

				// adicionando as instruções de inserções de cada registro na
				// lista de operações, caso haja.
				if (questoesP != null && questoesP.getQuestaoCount() > 0) {
					alterou = true;
					// Log.i("++DownloadArquivos++","Inserindo novas Questoes no banco");
					for (int i = 0; i < questoesP.getQuestaoCount(); i++) {
						op.add(ContentProviderOperation
								.newInsert(DBDefinicoes.Questoes.CONTENT_URI)
								.withValue(
										DBDefinicoes.Questoes.COLUMN_NAME_ID,
										questoesP.getQuestao(i).getId())
								.withValue(
										DBDefinicoes.Questoes.COLUMN_NAME_NIVEL,
										questoesP.getQuestao(i).getNivel())
								.withValue(
										DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO,
										questoesP.getQuestao(i).getDescricao())
								.build());
					}
				}
				if (alterou) {
					getContentResolver().delete(
							DBDefinicoes.Questoes.CONTENT_URI, null, null); // removendo
																			// os
																			// dados
																			// desatualizados
					getContentResolver().applyBatch(DBDefinicoes.AUTHORITY, op); // inserindo
																					// os
																					// dados
																					// atualizados
					alterou = false;
				}
				op.clear(); // limpando a lista de operações para receber novas
							// operações.

				// Download e armazenamento dos registros de marcações das
				// questões do questionário de maturidde da PETIC para cada
				// processo pertencentes à empresa.
				// recebendo registros do servidor.
				p_questoesP = ProcessoQuestoesMaturidadeProtos.ProcessoQuestoes
						.parseFrom(getServRemotoProcessoQuestoes(id_empresa,
								ultimaAlteracao));

				// adicionando as instruções de inserções de cada registro na
				// lista de operações, caso haja.
				if (p_questoesP != null
						&& p_questoesP.getProcessoQuestaoCount() > 0) {
					alterou = true;
					// Log.i("++DownloadArquivos++","Inserindo as Soluções das Questões para cada processo no banco");
					for (int i = 0; i < p_questoesP.getProcessoQuestaoCount(); i++) {
						op.add(ContentProviderOperation
								.newInsert(
										DBDefinicoes.ProcessoQuestoes.CONTENT_URI)
								.withValue(
										DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO,
										p_questoesP.getProcessoQuestao(i)
												.getIdQuestao())
								.withValue(
										DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO,
										p_questoesP.getProcessoQuestao(i)
												.getIdProcesso())
								.withValue(
										DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO,
										p_questoesP.getProcessoQuestao(i)
												.getMarcado()).build());
					}
				}
				if (alterou) {
					getContentResolver().delete(
							DBDefinicoes.ProcessoQuestoes.CONTENT_URI, null,
							null); // removendo os dados desatualizados
					getContentResolver().applyBatch(DBDefinicoes.AUTHORITY, op); // inserindo
																					// os
																					// dados
																					// atualizados
					alterou = false;
				}

				// Download e armazenamento dos registros de ações para cada
				// processo pertencentes à empresa.
				// recebendo registros do servidor.
				acoesP = AcoesProtos.Acoes.parseFrom(getServRemotoAcoes(
						id_empresa, ultimaAlteracao));

				// adicionando as instruções de inserções de cada registro na
				// lista de operações, caso haja.
				if (acoesP != null && acoesP.getAcaoCount() > 0) {
					alterou = true;
					// Log.i("++DownloadArquivos++","Inserindo as Ações para cada processo no banco");
					for (int i = 0; i < acoesP.getAcaoCount(); i++) {
						op.add(ContentProviderOperation
								.newInsert(DBDefinicoes.Acoes.CONTENT_URI)
								.withValue(DBDefinicoes.Acoes.COLUMN_NAME_ID,
										acoesP.getAcao(i).getId())
								.withValue(DBDefinicoes.Acoes.COLUMN_NAME_NOME,
										acoesP.getAcao(i).getNome())
								.withValue(
										DBDefinicoes.Acoes.COLUMN_NAME_RESPONSAVEL,
										acoesP.getAcao(i).getResponsavel())
								.withValue(
										DBDefinicoes.Acoes.COLUMN_NAME_DATA_INICIO,
										acoesP.getAcao(i).getDataInicio())
								.withValue(
										DBDefinicoes.Acoes.COLUMN_NAME_DATA_FIM,
										acoesP.getAcao(i).getDataFim())
								.withValue(
										DBDefinicoes.Acoes.COLUMN_NAME_CUSTO,
										acoesP.getAcao(i).getCusto())
								.withValue(
										DBDefinicoes.Acoes.COLUMN_NAME_ESFORCO,
										acoesP.getAcao(i).getEsforco())
								.withValue(
										DBDefinicoes.Acoes.COLUMN_NAME_ID_PROCESSO,
										acoesP.getAcao(i).getProcessoId())
								.withValue(
										DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA,
										(acoesP.getAcao(i).getAdotada()) ? 1
												: 0).build());
					}
				}
				if (alterou) {
					getContentResolver().delete(DBDefinicoes.Acoes.CONTENT_URI,
							null, null); // removendo os dados desatualizados
					getContentResolver().applyBatch(DBDefinicoes.AUTHORITY, op); // inserindo
																					// os
																					// dados
																					// atualizados
					alterou = false;
				}

				completou = true; // a atualização ocorreu sem erros ou não
									// houve alteração após a data informada.
									// Finalize o serviço.

				// Log.i("++DownloadArquivos++","Informando conclusões de atualizações");
			} catch (Exception e) { // ocorreu um erro durante alguma operação,
									// as informações poderão estar incompletas.
				long endTime = System.currentTimeMillis() + 60000;
				while (System.currentTimeMillis() < endTime) { // espere 1
																// min...
					synchronized (this) {
						try {
							wait(endTime - System.currentTimeMillis());
						} catch (Exception e2) {
						}
					}
				}
				completou = preferencia.getBoolean("Fim", false); // ... e
																	// repita o
																	// download
																	// dos dados
				e.printStackTrace();
			}
		}
	}

	/**
	 * Realiza consulta remota para obter as áreas PETIC da empresa se
	 * alteradas/inseridas após a última consulta.
	 * 
	 * @param empresa
	 *            Identificador da empresa da consulta.
	 * @param dt
	 *            Momento em que ocorreu a última consulta.
	 * @return Arquivo com a lista das áreas no formato <i>Protocol Buffers</i>.
	 * @throws Exception
	 */
	// Método que realiza a consulta remota - Simulado **A ser implementado**
	// Apenas serão retornados desse método os registros das areas modificados
	// após a data passada por parâmetro
	private InputStream getServRemotoAreas(int empresa, long dt)
			throws Exception {
		/*
		 * HttpClient client = new DefaultHttpClient(); // Simulação. O web
		 * service remoto receberá também os parâmetros que informam a empresa e
		 * última data de busca HttpGet request = new HttpGet(new
		 * URI("http://177.49.214.167:8082/Teste/areasserv?empresa_id="+ empresa
		 * +"&data="+ dt)); // HttpGet request = new HttpGet(new URI(
		 * "http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b01/res/raw/areas_"
		 * + rand)); HttpResponse response = client.execute(request);
		 * 
		 * return response.getEntity().getContent();
		 */

		URL url = new URL(
				"http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b03/arquivos_de_simulacao/areas");
		// URL url = new
		// URL("http://10.60.152.84:8081/Teste/areasserv?empresa_id="+ empresa
		// +"&data="+ dt);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		// return new BufferedInputStream(urlConnection.getInputStream());
		// Requisicao requisicao = new Requisicao(Requisicao.SERVICO + "/areas",
		// Requisicao.METHOD_GET, true, null);
		// Resposta resposta = Cliente.realizarRequisicao(requisicao);

		return new BufferedInputStream(urlConnection.getInputStream());// resposta.getCorpo();
	}

	/**
	 * Realiza consulta remota para obter as sub-áreas PETIC da empresa se
	 * alteradas/inseridas após a última consulta.
	 * 
	 * @param empresa
	 *            Identificador da empresa da consulta.
	 * @param dt
	 *            Momento em que ocorreu a última consulta.
	 * @return Arquivo com a lista das sub-áreas no formato <i>Protocol
	 *         Buffers</i>.
	 * @throws Exception
	 */
	// Método que realiza a consulta remota - Simulado **A ser implementado**
	// Apenas serão retornados desse método os registros das subareas
	// modificados após a data passada por parâmetro
	private InputStream getServRemotoSubareas(int empresa, long dt)
			throws Exception {
		// HttpClient client = new DefaultHttpClient();
		/*
		 * // Simulação. O web service remoto receberá também os parâmetros que
		 * informam a empresa e última data de busca HttpGet request = new
		 * HttpGet(new
		 * URI("http://177.49.214.167:8082/Teste/subareasserv?empresa_id="+
		 * empresa +"&data="+ dt)); //HttpGet request = new HttpGet(new URI(
		 * "http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b01/res/raw/subareas_"
		 * + rand)); HttpResponse response = client.execute(request);
		 * 
		 * return response.getEntity().getContent();
		 */

		URL url = new URL(
				"http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b03/arquivos_de_simulacao/subareas");
		// URL url = new
		// URL("http://10.60.152.84:8081/Teste/subareasserv?empresa_id="+
		// empresa +"&data="+ dt);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		return new BufferedInputStream(urlConnection.getInputStream());
	}

	/**
	 * Realiza consulta remota para obter os processos PETIC da empresa se
	 * alterados/inseridos após a última consulta.
	 * 
	 * @param empresa
	 *            Identificador da empresa da consulta.
	 * @param dt
	 *            Momento em que ocorreu a última consulta.
	 * @return Arquivo com a lista dos processos no formato <i>Protocol
	 *         Buffers</i>.
	 * @throws Exception
	 */
	// Método que realiza a consulta remota - Simulado **A ser implementado**
	// Apenas serão retornados desse método os registros das subareas
	// modificados após a data passada por parâmetro
	private InputStream getServRemotoProcessos(int empresa, long dt)
			throws Exception {
		/*
		 * HttpClient client = new DefaultHttpClient(); // Simulação. O web
		 * service remoto receberá também os parâmetros que informam a empresa e
		 * última data de busca HttpGet request = new HttpGet(new
		 * URI("http://177.49.214.167:8082/Teste/processosserv?empresa_id="+
		 * empresa +"&data="+ dt)); //HttpGet request = new HttpGet(new URI(
		 * "http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b01/res/raw/processos_"
		 * + rand)); HttpResponse response = client.execute(request);
		 * 
		 * return response.getEntity().getContent();
		 */

		URL url = new URL(
				"http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b03/arquivos_de_simulacao/processos");
		// URL url = new
		// URL("http://10.60.152.84:8081/Teste/processosserv?empresa_id="+
		// empresa +"&data="+ dt);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		return new BufferedInputStream(urlConnection.getInputStream());
	}

	/**
	 * Realiza consulta remota para obter as questões do questionário de
	 * maturidade se alterados/inseridos após a última consulta.
	 * 
	 * @param empresa
	 *            Identificador da empresa da consulta.
	 * @param dt
	 *            Momento em que ocorreu a última consulta.
	 * @return Arquivo com a lista das questões no formato <i>Protocol
	 *         Buffers</i>.
	 * @throws Exception
	 */
	// Método que realiza a consulta remota - Simulado **A ser implementado**
	// Apenas serão retornados desse método os registros das questões
	// modificadas após a data passada por parâmetro
	private InputStream getServRemotoQuestoes(int empresa, long dt)
			throws Exception {
		/*
		 * HttpClient client = new DefaultHttpClient(); // Simulação. O web
		 * service remoto receberá também os parâmetros que informam a empresa e
		 * última data de busca HttpGet request = new HttpGet(new
		 * URI("http://177.49.214.167:8082/Teste/processosserv?empresa_id="+
		 * empresa +"&data="+ dt)); //HttpGet request = new HttpGet(new URI(
		 * "http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b01/res/raw/processos_"
		 * + rand)); HttpResponse response = client.execute(request);
		 * 
		 * return response.getEntity().getContent();
		 */

		// /ESTA PARTE DEVERÁ SER REMOVIDA - TRATAMENTO PARA NÃO HAVER
		// SOBRESCRITA NAS ALTERAÇÕES NO BANCO LOCAL
		Cursor cursor = getContentResolver().query(
				DBDefinicoes.Questoes.CONTENT_URI,
				new String[] { DBDefinicoes.Questoes.COLUMN_NAME_ID }, null,
				null, null);

		if (cursor.moveToNext()) {
			cursor.close();
			return null;
		}
		cursor.close();
		// /ESTA PARTE DEVERÁ SER REMOVIDA - TRATAMENTO PARA NÃO HAVER
		// SOBRESCRITA NAS ALTERAÇÕES NO BANCO LOCAL

		cursor.close();
		URL url = new URL(
				"http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b03/arquivos_de_simulacao/questoes");
		// URL url = new
		// URL("http://10.60.152.84:8081/Teste/questoesserv?empresa_id="+
		// empresa +"&data="+ dt);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		return new BufferedInputStream(urlConnection.getInputStream());
	}

	/**
	 * Realiza consulta remota para obter as informações das questões do
	 * questionário de maturidade se alterados/inseridos após a última consulta.
	 * 
	 * @param empresa
	 *            Identificador da empresa da consulta.
	 * @param dt
	 *            Momento em que ocorreu a última consulta.
	 * @return Arquivo com a lista das informações (chekc/uncheck) das questões
	 *         no formato <i>Protocol Buffers</i>.
	 * @throws Exception
	 */
	// Método que realiza a consulta remota - Simulado **A ser implementado**
	// Apenas serão retornados desse método os registros das informações das
	// questões modificadas após a data passada por parâmetro
	private InputStream getServRemotoProcessoQuestoes(int empresa, long dt)
			throws Exception {
		/*
		 * HttpClient client = new DefaultHttpClient(); // Simulação. O web
		 * service remoto receberá também os parâmetros que informam a empresa e
		 * última data de busca HttpGet request = new HttpGet(new
		 * URI("http://177.49.214.167:8082/Teste/processosserv?empresa_id="+
		 * empresa +"&data="+ dt)); //HttpGet request = new HttpGet(new URI(
		 * "http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b01/res/raw/processos_"
		 * + rand)); HttpResponse response = client.execute(request);
		 * 
		 * return response.getEntity().getContent();
		 */

		// /ESTA PARTE DEVERÁ SER REMOVIDA - TRATAMENTO PARA NÃO HAVER
		// SOBRESCRITA NAS ALTERAÇÕES NO BANCO LOCAL
		Cursor cursor = getContentResolver()
				.query(DBDefinicoes.ProcessoQuestoes.CONTENT_URI,
						new String[] { DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO },
						null, null, null);

		if (cursor.moveToNext()) {
			cursor.close();
			return null;
		}
		cursor.close();
		// /ESTA PARTE DEVERÁ SER REMOVIDA - TRATAMENTO PARA NÃO HAVER
		// SOBRESCRITA NAS ALTERAÇÕES NO BANCO LOCAL

		URL url = new URL(
				"http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b03/arquivos_de_simulacao/processo_questoes");
		// URL url = new
		// URL("http://10.60.152.84:8081/Teste/processoquestoesserv?empresa_id="+
		// empresa +"&data="+ dt);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		return new BufferedInputStream(urlConnection.getInputStream());
	}

	/**
	 * Realiza consulta remota para obter as informações das ações de melhoria
	 * dos processos de TIC se alteradas/inseridas após a última consulta.
	 * 
	 * @param empresa
	 *            Identificador da empresa da consulta.
	 * @param dt
	 *            Momento em que ocorreu a última consulta.
	 * @return Arquivo com a lista das ações no formato <i>Protocol Buffers</i>.
	 * @throws Exception
	 */
	// Método que realiza a consulta remota - Simulado **A ser implementado**
	// Apenas serão retornados desse método os registros das informações das
	// ações modificadas após a data passada por parâmetro
	private InputStream getServRemotoAcoes(int empresa, long dt)
			throws Exception {
		/*
		 * HttpClient client = new DefaultHttpClient(); // Simulação. O web
		 * service remoto receberá também os parâmetros que informam a empresa e
		 * última data de busca HttpGet request = new HttpGet(new
		 * URI("http://177.49.214.167:8082/Teste/processosserv?empresa_id="+
		 * empresa +"&data="+ dt)); //HttpGet request = new HttpGet(new URI(
		 * "http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b01/res/raw/processos_"
		 * + rand)); HttpResponse response = client.execute(request);
		 * 
		 * return response.getEntity().getContent();
		 */

		// /ESTA PARTE DEVERÁ SER REMOVIDA - TRATAMENTO PARA NÃO HAVER
		// SOBRESCRITA NAS ALTERAÇÕES NO BANCO LOCAL
		Cursor cursor = getContentResolver().query(
				DBDefinicoes.Acoes.CONTENT_URI,
				new String[] { DBDefinicoes.Acoes.COLUMN_NAME_ID }, null, null,
				null);

		if (cursor.moveToNext()) {
			cursor.close();
			return null;
		}
		cursor.close();
		// /ESTA PARTE DEVERÁ SER REMOVIDA - TRATAMENTO PARA NÃO HAVER
		// SOBRESCRITA NAS ALTERAÇÕES NO BANCO LOCAL

		URL url = new URL(
				"http://svn.code.sf.net/p/petic/code/branches/mobiPETIC-b03/arquivos_de_simulacao/acoes");
		// URL url = new
		// URL("http://10.60.152.84:8081/Teste/acoesserv?empresa_id="+ empresa
		// +"&data="+ dt);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		return new BufferedInputStream(urlConnection.getInputStream());
	}
}