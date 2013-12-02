package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * <code>ContentProvider</code> para realização do CRUD no banco de dados da
 * aplicação.
 * 
 * @see ContentProvider
 */
public class DBProvider extends ContentProvider {

	private MobiPETICDatabaseHelper DB;

	private static final UriMatcher uriMatcher;
	private static final HashMap<String, String> projecaoArea;
	private static final HashMap<String, String> projecaoSubarea;
	private static final HashMap<String, String> projecaoProcesso;
	private static final HashMap<String, String> projecaoQuestao;
	private static final HashMap<String, String> projecaoProcessoQuestao;
	private static final HashMap<String, String> projecaoAcao;

	private static final int AREAS = 1;
	private static final int AREA_ID = 2;
	private static final int SUBAREAS = 3;
	private static final int SUBAREA_ID = 4;
	private static final int PROCESSOS = 5;
	private static final int PROCESSO_ID = 6;
	private static final int QUESTOES = 7;
	private static final int QUESTAO_ID = 8;
	private static final int PROCESSO_QUESTOES = 9;
	private static final int PROCESSO_QUESTAO_ID = 10;
	private static final int ACOES = 11;
	private static final int ACAO_ID = 12;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		// Definindo URI válidas para o CRUD das tabelas
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "areas", AREAS);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "areas/#", AREA_ID);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "subareas", SUBAREAS);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "subareas/#", SUBAREA_ID);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "processos", PROCESSOS);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "processos/#", PROCESSO_ID);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "questoes", QUESTOES);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "questoes/#", QUESTAO_ID);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "processo_questoes",
				PROCESSO_QUESTOES);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "processo_questoes/#",
				PROCESSO_QUESTAO_ID);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "acoes", ACOES);
		uriMatcher.addURI(DBDefinicoes.AUTHORITY, "acoes/#", ACAO_ID);

		// Definindo colunas de projeção para a tabela area
		projecaoArea = new HashMap<String, String>();
		projecaoArea.put(DBDefinicoes.Areas._ID, DBDefinicoes.Areas._ID);
		projecaoArea.put(DBDefinicoes.Areas.COLUMN_NAME_ID,
				DBDefinicoes.Areas.COLUMN_NAME_ID);
		projecaoArea.put(DBDefinicoes.Areas.COLUMN_NAME_NOME,
				DBDefinicoes.Areas.COLUMN_NAME_NOME);

		// Definindo colunas de projeção para a tabela subarea
		projecaoSubarea = new HashMap<String, String>();
		projecaoSubarea.put(DBDefinicoes.Subareas._ID,
				DBDefinicoes.Subareas._ID);
		projecaoSubarea.put(DBDefinicoes.Subareas.COLUMN_NAME_ID,
				DBDefinicoes.Subareas.COLUMN_NAME_ID);
		projecaoSubarea.put(DBDefinicoes.Subareas.COLUMN_NAME_NOME,
				DBDefinicoes.Subareas.COLUMN_NAME_NOME);
		projecaoSubarea.put(DBDefinicoes.Subareas.COLUMN_NAME_ID_AREA,
				DBDefinicoes.Subareas.COLUMN_NAME_ID_AREA);

		// Definindo colunas de projeção para a tabela processo
		projecaoProcesso = new HashMap<String, String>();
		projecaoProcesso.put(DBDefinicoes.Processos._ID,
				DBDefinicoes.Processos._ID);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_ID,
				DBDefinicoes.Processos.COLUMN_NAME_ID);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_NOME,
				DBDefinicoes.Processos.COLUMN_NAME_NOME);
		projecaoProcesso.put(
				DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE,
				DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE);
		projecaoProcesso.put(
				DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT,
				DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_PRIORITARIO,
				DBDefinicoes.Processos.COLUMN_NAME_PRIORITARIO);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_PRIORIDADE_DEF,
				DBDefinicoes.Processos.COLUMN_NAME_PRIORIDADE_DEF);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_DESCRICAO,
				DBDefinicoes.Processos.COLUMN_NAME_DESCRICAO);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_ENTRADAS,
				DBDefinicoes.Processos.COLUMN_NAME_ENTRADAS);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_SAIDAS,
				DBDefinicoes.Processos.COLUMN_NAME_SAIDAS);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_RESPONSAVEL,
				DBDefinicoes.Processos.COLUMN_NAME_RESPONSAVEL);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_STAKEHOLDERS,
				DBDefinicoes.Processos.COLUMN_NAME_STAKEHOLDERS);
		projecaoProcesso.put(
				DBDefinicoes.Processos.COLUMN_NAME_DATA_MODIFICACAO,
				DBDefinicoes.Processos.COLUMN_NAME_DATA_MODIFICACAO);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_ID_SUBAREA,
				DBDefinicoes.Processos.COLUMN_NAME_ID_SUBAREA);
		projecaoProcesso.put(
				DBDefinicoes.Processos.COLUMN_NAME_MODIFICACAO_QUESTIONARIO,
				DBDefinicoes.Processos.COLUMN_NAME_MODIFICACAO_QUESTIONARIO);
		projecaoProcesso.put(
				DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO,
				DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO);
		projecaoProcesso.put(DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR,
				DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR);

		// Definindo colunas de projeção para a tabela questao
		projecaoQuestao = new HashMap<String, String>();
		projecaoQuestao.put(DBDefinicoes.Questoes._ID,
				DBDefinicoes.Questoes._ID);
		projecaoQuestao.put(DBDefinicoes.Questoes.COLUMN_NAME_ID,
				DBDefinicoes.Questoes.COLUMN_NAME_ID);
		projecaoQuestao.put(DBDefinicoes.Questoes.COLUMN_NAME_NIVEL,
				DBDefinicoes.Questoes.COLUMN_NAME_NIVEL);
		projecaoQuestao.put(DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO,
				DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO);

		// Definindo colunas de projeção para a tabela processo_questoes
		projecaoProcessoQuestao = new HashMap<String, String>();
		projecaoProcessoQuestao.put(DBDefinicoes.ProcessoQuestoes.TABLE_NAME
				+ "." + DBDefinicoes.ProcessoQuestoes._ID,
				DBDefinicoes.ProcessoQuestoes.TABLE_NAME + "."
						+ DBDefinicoes.ProcessoQuestoes._ID);
		projecaoProcessoQuestao.put(
				DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO,
				DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO);
		projecaoProcessoQuestao.put(
				DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO,
				DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO);
		projecaoProcessoQuestao.put(
				DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO,
				DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO);
		projecaoProcessoQuestao.put(
				DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO,
				DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO);
		projecaoProcessoQuestao.put(DBDefinicoes.Questoes.COLUMN_NAME_NIVEL,
				DBDefinicoes.Questoes.COLUMN_NAME_NIVEL);

		projecaoAcao = new HashMap<String, String>();
		projecaoAcao.put(DBDefinicoes.Acoes._ID, DBDefinicoes.Acoes._ID);
		projecaoAcao.put(DBDefinicoes.Acoes.COLUMN_NAME_ID,
				DBDefinicoes.Acoes.COLUMN_NAME_ID);
		projecaoAcao.put(DBDefinicoes.Acoes.COLUMN_NAME_NOME,
				DBDefinicoes.Acoes.COLUMN_NAME_NOME);
		projecaoAcao.put(DBDefinicoes.Acoes.COLUMN_NAME_RESPONSAVEL,
				DBDefinicoes.Acoes.COLUMN_NAME_RESPONSAVEL);
		projecaoAcao.put(DBDefinicoes.Acoes.COLUMN_NAME_DATA_INICIO,
				DBDefinicoes.Acoes.COLUMN_NAME_DATA_INICIO);
		projecaoAcao.put(DBDefinicoes.Acoes.COLUMN_NAME_DATA_FIM,
				DBDefinicoes.Acoes.COLUMN_NAME_DATA_FIM);
		projecaoAcao.put(DBDefinicoes.Acoes.COLUMN_NAME_CUSTO,
				DBDefinicoes.Acoes.COLUMN_NAME_CUSTO);
		projecaoAcao.put(DBDefinicoes.Acoes.COLUMN_NAME_ESFORCO,
				DBDefinicoes.Acoes.COLUMN_NAME_ESFORCO);
		projecaoAcao.put(DBDefinicoes.Acoes.COLUMN_NAME_ID_PROCESSO,
				DBDefinicoes.Acoes.COLUMN_NAME_ID_PROCESSO);
		projecaoAcao.put(DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA,
				DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA);
	}

	/**
	 * Realiza a remoção de um simples ou múltiplos registros nas tabelas do
	 * banco de dados da aplicação.
	 * 
	 * @param uri
	 *            Caminho que identifica a tabela e a forma de remoção (simples
	 *            ou múltiplos registros).
	 * @param selection
	 *            Cláusula WHERE.
	 * @param selectionArgs
	 *            Valores de filtro da cláusula WHERE.
	 * @return Quantidade de registros removidos.
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Abrindo o objeto de banco de dados: modo "write"
		SQLiteDatabase db = DB.getWritableDatabase();
		String finalWhere;
		String[] finalArgs;

		int count;

		// Fazer a remoção baseado na URI parâmetro
		switch (uriMatcher.match(uri)) {
		// Se for remoção de múltilas linhas de areas
		case AREAS:
			count = db.delete(DBDefinicoes.Areas.TABLE_NAME, // Nome da tabela
					selection, // campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // valores de seleção referentes à cláusula
									// "where"
			break;

		// Se for remoção de uma linha de areas
		case AREA_ID:
			// Adiciona mais um condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.Areas._ID
					+ " = ?";
			
			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Areas.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Areas.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Removendo
			count = db.delete(DBDefinicoes.Areas.TABLE_NAME, // Nome da tabela
					finalWhere, // campos de seleção referentes à cláusula
								// "where"
					finalArgs); // valores de seleção referentes à cláusula
									// "where"
			break;
		// Se for remoção de múltiplas linhas de subareas
		case SUBAREAS:
			count = db.delete(DBDefinicoes.Subareas.TABLE_NAME, // Nome da
																// tabela
					selection, // campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // valores de seleção referentes à cláusula
									// "where"
			break;

		// Se for remoção de uma linha de subareas
		case SUBAREA_ID:
			// Adiciona mais um condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.Subareas._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Subareas.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Subareas.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Removendo
			count = db.delete(DBDefinicoes.Subareas.TABLE_NAME, // Nome da
																// tabela
					finalWhere, // campos de seleção referentes à cláusula
								// "where"
					finalArgs); // valores de seleção referentes à cláusula
									// "where"
			break;
		// Se for remoção de múltiplas linhas de processos
		case PROCESSOS:
			count = db.delete(DBDefinicoes.Processos.TABLE_NAME, // Nome da
																	// tabela
					selection, // campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // valores de seleção referentes à cláusula
									// "where"
			break;

		// Se for remoção de uma linha de processos
		case PROCESSO_ID:
			// Adiciona mais um condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.Processos._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Processos.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Processos.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Removendo
			count = db.delete(DBDefinicoes.Processos.TABLE_NAME, // Nome da
																	// tabela
					finalWhere, // campos de seleção referentes à cláusula
								// "where"
					finalArgs); // valores de seleção referentes à cláusula
									// "where"
			break;
		// Se for remoção de múltiplas linhas de questoes
		case QUESTOES:
			count = db.delete(DBDefinicoes.Questoes.TABLE_NAME, // Nome da
																// tabela
					selection, // campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // valores de seleção referentes à cláusula
									// "where"
			break;

		// Se for remoção de uma linha de questoes
		case QUESTAO_ID:
			// Adiciona mais um condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.Questoes._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Questoes.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Questoes.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Removendo
			count = db.delete(DBDefinicoes.Questoes.TABLE_NAME, // Nome da
																// tabela
					finalWhere, // campos de seleção referentes à cláusula
								// "where"
					finalArgs); // valores de seleção referentes à cláusula
									// "where"
			break;
		// Se for remoção de múltiplas linhas de processo_questoes
		case PROCESSO_QUESTOES:
			count = db.delete(DBDefinicoes.ProcessoQuestoes.TABLE_NAME, // Nome
																		// da
																		// tabela
					selection, // campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // valores de seleção referentes à cláusula
									// "where"
			break;

		// Se for remoção de uma linha de processo_questoes
		case PROCESSO_QUESTAO_ID:
			// Adiciona mais um condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.ProcessoQuestoes._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.ProcessoQuestoes.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.ProcessoQuestoes.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Removendo
			count = db.delete(DBDefinicoes.ProcessoQuestoes.TABLE_NAME, // Nome
																		// da
																		// tabela
					finalWhere, // campos de seleção referentes à cláusula
								// "where"
					finalArgs); // valores de seleção referentes à cláusula
									// "where"
			break;
		// Se for remoção de múltiplas linhas de processo_questoes
		case ACOES:
			count = db.delete(DBDefinicoes.Acoes.TABLE_NAME, // Nome da tabela
					selection, // campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // valores de seleção referentes à cláusula
									// "where"
			break;

		// Se for remoção de uma linha de processo_questoes
		case ACAO_ID:
			// Adiciona mais um condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.Acoes._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Acoes.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Acoes.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Removendo
			count = db.delete(DBDefinicoes.Acoes.TABLE_NAME, // Nome da tabela
					finalWhere, // campos de seleção referentes à cláusula
								// "where"
					finalArgs); // valores de seleção referentes à cláusula
									// "where"
			break;

		// Caso a URI seja desconhecida, lança uma exceção.
		default:
			throw new IllegalArgumentException("URI desconhecida " + uri);
		}
		
		SQLiteDatabase.releaseMemory();

		// Notificando ao ContentResolver do Context corrente a URI alterada.
		getContext().getContentResolver().notifyChange(uri, null);

		// Retorna o número de linhas removidas
		return count;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	/**
	 * Realiza a inserção de registros nas tabelas do banco de dados da
	 * aplicação (caso o registro de inserção já exista na tabela, será
	 * realizada uma atualização).
	 * 
	 * @param uri
	 *            Caminho que identifica a tabela e a forma de remoção (apenas
	 *            simples registros serão aceitos).
	 * @param initialValues
	 *            Valores de inserção.
	 * @return Caminho do novo registro inserido (ou atualizado).
	 */
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		// Validação da URI. Somente URI para múltiplas linhas são permitidas
		// para inserção.
		if (uriMatcher.match(uri) != AREAS && uriMatcher.match(uri) != SUBAREAS
				&& uriMatcher.match(uri) != PROCESSOS
				&& uriMatcher.match(uri) != QUESTOES
				&& uriMatcher.match(uri) != PROCESSO_QUESTOES
				&& uriMatcher.match(uri) != ACOES) {
			throw new IllegalArgumentException("URI Desconhecida " + uri);
		}

		// Objeto que armazenará os novos dados
		ContentValues values;
		// Variável que armazenará o ID do novo registro
		long rowId = 0;

		// Se o objeto com os valores passados não for nulo preencha o
		// ContentValues
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			// Caso contrário, inicie com valores vazios
			values = new ContentValues();
		}

		// Pegando a data corrente em milisegundos
		Long now = Long.valueOf(System.currentTimeMillis());

		// Abrindo o objeto de banco de dados: modo "write"
		SQLiteDatabase db = DB.getWritableDatabase();

		Uri changedUri = null;

		if (uriMatcher.match(uri) == AREAS) {
			// Adicionando valores padrões aos campos nulos
			if (!values.containsKey(DBDefinicoes.Areas.COLUMN_NAME_NOME)) {
				values.put(DBDefinicoes.Areas.COLUMN_NAME_NOME, "");
			}

			if (!values.containsKey(DBDefinicoes.Areas.COLUMN_NAME_ID)) {
				values.put(DBDefinicoes.Areas.COLUMN_NAME_ID, "0");
			} else {
				Cursor cur = DB
						.getReadableDatabase()
						.query(DBDefinicoes.Areas.TABLE_NAME,
								new String[] {
										DBDefinicoes.Areas.COLUMN_NAME_ID,
										DBDefinicoes.Areas._ID },
								DBDefinicoes.Areas.COLUMN_NAME_ID + " = ?",
								new String[] { values
										.getAsString(DBDefinicoes.Areas.COLUMN_NAME_ID) },
								null, null,
								DBDefinicoes.Areas.DEFAULT_SORT_ORDER);
				if (cur.moveToNext()) {
					// Reencaminhar a inserção para uma atualização
					// Log.e("++DBProvider++","Resgistro duplicado redirecionando para atualização - Área id "
					// + cur.getInt(0));
					update(ContentUris.withAppendedId(
							DBDefinicoes.Areas.CONTENT_URI, cur.getInt(1)),
							values, null, null);
					int id = cur.getInt(1);
					cur.close();
					return ContentUris.withAppendedId(
							DBDefinicoes.Areas.CONTENT_URI, id);
				}
				cur.close();
			}

			// Realizando inserção e recebendo o id do novo registro
			rowId = db.insert(DBDefinicoes.Areas.TABLE_NAME, // Nome da tabela
					DBDefinicoes.Areas.COLUMN_NAME_NOME, // Coluna "not null"
					values // Campos e valores de inserção
					);

			if (rowId > 0)
				// Cria um URI para o novo registro
				changedUri = ContentUris.withAppendedId(
						DBDefinicoes.Areas.CONTENT_ID_URI_BASE, rowId);

		} else if (uriMatcher.match(uri) == SUBAREAS) {
			// Adicionando valores padrões aos campos nulos
			if (!values.containsKey(DBDefinicoes.Subareas.COLUMN_NAME_NOME)) {
				values.put(DBDefinicoes.Subareas.COLUMN_NAME_NOME, "");
			}

			if (!values.containsKey(DBDefinicoes.Subareas.COLUMN_NAME_ID_AREA)) {
				values.put(DBDefinicoes.Subareas.COLUMN_NAME_ID_AREA, 0);
			}

			if (!values.containsKey(DBDefinicoes.Subareas.COLUMN_NAME_ID)) {
				values.put(DBDefinicoes.Subareas.COLUMN_NAME_ID, "0");
			} else {
				Cursor cur = DB
						.getReadableDatabase()
						.query(DBDefinicoes.Subareas.TABLE_NAME,
								new String[] {
										DBDefinicoes.Subareas.COLUMN_NAME_ID,
										DBDefinicoes.Subareas._ID },
								DBDefinicoes.Subareas.COLUMN_NAME_ID + " = ?",
								new String[] { values
										.getAsString(DBDefinicoes.Subareas.COLUMN_NAME_ID) },
								null, null,
								DBDefinicoes.Subareas.DEFAULT_SORT_ORDER);
				if (cur.moveToNext()) {
					// Reencaminhar a inserção para uma atualização
					// Log.e("++DBProvider++","Resgistro duplicado redirecionando para atualização - Sub-área id "
					// + cur.getInt(0));
					update(ContentUris.withAppendedId(
							DBDefinicoes.Subareas.CONTENT_URI, cur.getInt(1)),
							values, null, null);
					int id = cur.getInt(1);
					cur.close();
					return ContentUris.withAppendedId(
							DBDefinicoes.Subareas.CONTENT_URI, id);
				}
				cur.close();
			}

			// Realizando inserção e recebendo o id do novo registro
			rowId = db.insert(DBDefinicoes.Subareas.TABLE_NAME, // Nome da
																// tabela
					DBDefinicoes.Subareas.COLUMN_NAME_NOME, // Coluna "not null"
					values // Campos e valores de inserção
					);

			if (rowId > 0)
				// Cria um URI para o novo registro
				changedUri = ContentUris.withAppendedId(
						DBDefinicoes.Subareas.CONTENT_ID_URI_BASE, rowId);
		} else if (uriMatcher.match(uri) == PROCESSOS) {
			// Adicionando valores padrões aos campos nulos
			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_DATA_MODIFICACAO)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_DATA_MODIFICACAO,
						now);
			}

			if (!values.containsKey(DBDefinicoes.Processos.COLUMN_NAME_NOME)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_NOME, "");
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE,
						-1);
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT)) {
				values.put(
						DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT,
						0);
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_PRIORITARIO)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_PRIORITARIO, 0);
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_PRIORIDADE_DEF)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_PRIORIDADE_DEF, 0);
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_DESCRICAO)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_DESCRICAO, "");
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_ENTRADAS)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_ENTRADAS, "");
			}

			if (!values.containsKey(DBDefinicoes.Processos.COLUMN_NAME_SAIDAS)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_SAIDAS, "");
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_RESPONSAVEL)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_RESPONSAVEL, "");
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_STAKEHOLDERS)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_STAKEHOLDERS, "");
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_ID_SUBAREA)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_ID_SUBAREA, 0);
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_MODIFICACAO_QUESTIONARIO)) {
				values.put(
						DBDefinicoes.Processos.COLUMN_NAME_MODIFICACAO_QUESTIONARIO,
						0);
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO)) {
				values.put(
						DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO,
						"");
			}

			if (!values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR, 0);
			}

			if (!values.containsKey(DBDefinicoes.Processos.COLUMN_NAME_ID)) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_ID, "0");
			} else {
				Cursor cur = DB
						.getReadableDatabase()
						.query(DBDefinicoes.Processos.TABLE_NAME,
								new String[] {
										DBDefinicoes.Processos.COLUMN_NAME_ID,
										DBDefinicoes.Processos._ID },
								DBDefinicoes.Processos.COLUMN_NAME_ID + " = ?",
								new String[] { values
										.getAsString(DBDefinicoes.Processos.COLUMN_NAME_ID) },
								null, null,
								DBDefinicoes.Processos.DEFAULT_SORT_ORDER);
				if (cur.moveToNext()) {
					// Reencaminhar a inserção para uma atualização
					// Log.e("++DBProvider++","Resgistro duplicado redirecionando para atualização - Processo id "
					// + cur.getInt(0));
					update(ContentUris.withAppendedId(
							DBDefinicoes.Processos.CONTENT_URI, cur.getInt(1)),
							values, null, null);
					int id = cur.getInt(1);
					cur.close();
					return ContentUris.withAppendedId(
							DBDefinicoes.Processos.CONTENT_URI, id);
				}
				cur.close();
			}

			// Realizando inserção e recebendo o id do novo registro
			rowId = db.insert(DBDefinicoes.Processos.TABLE_NAME, // Nome da
																	// tabela
					DBDefinicoes.Processos.COLUMN_NAME_NOME, // Coluna
																// "not null"
					values // Campos e valores de inserção
					);

			if (rowId > 0)
				// Cria um URI para o novo registro
				changedUri = ContentUris.withAppendedId(
						DBDefinicoes.Processos.CONTENT_ID_URI_BASE, rowId);
		} else if (uriMatcher.match(uri) == QUESTOES) {
			// Adicionando valores padrões aos campos nulos
			if (!values
					.containsKey(DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO)) {
				values.put(DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO, "");
			}

			if (!values.containsKey(DBDefinicoes.Questoes.COLUMN_NAME_NIVEL)) {
				values.put(DBDefinicoes.Questoes.COLUMN_NAME_NIVEL, 0);
			}

			if (!values.containsKey(DBDefinicoes.Questoes.COLUMN_NAME_ID)) {
				values.put(DBDefinicoes.Questoes.COLUMN_NAME_ID, 0);
			} else {
				Cursor cur = DB
						.getReadableDatabase()
						.query(DBDefinicoes.Questoes.TABLE_NAME,
								new String[] {
										DBDefinicoes.Questoes.COLUMN_NAME_ID,
										DBDefinicoes.Questoes._ID },
								DBDefinicoes.Questoes.COLUMN_NAME_ID + " = ?",
								new String[] { values
										.getAsString(DBDefinicoes.Questoes.COLUMN_NAME_ID) },
								null, null,
								DBDefinicoes.Questoes.DEFAULT_SORT_ORDER);
				if (cur.moveToNext()) {
					// Reencaminhar a inserção para uma atualização
					// Log.e("++DBProvider++","Resgistro duplicado redirecionando para atualização - Questão id "
					// + cur.getInt(0));
					update(ContentUris.withAppendedId(
							DBDefinicoes.Questoes.CONTENT_URI, cur.getInt(1)),
							values, null, null);
					int id = cur.getInt(1);
					cur.close();
					return ContentUris.withAppendedId(
							DBDefinicoes.Questoes.CONTENT_URI, id);
				}
				cur.close();
			}

			// Realizando inserção e recebendo o id do novo registro
			rowId = db.insert(DBDefinicoes.Questoes.TABLE_NAME, // Nome da
																// tabela
					DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO, // Coluna
																	// "not null"
					values // Campos e valores de inserção
					);

			if (rowId > 0)
				// Cria um URI para o novo registro
				changedUri = ContentUris.withAppendedId(
						DBDefinicoes.Questoes.CONTENT_ID_URI_BASE, rowId);
		} else if (uriMatcher.match(uri) == PROCESSO_QUESTOES) {
			// Adicionando valores padrões aos campos nulos
			if (!values
					.containsKey(DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO)) {
				values.put(DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO, 0);
			}

			if (!values
					.containsKey(DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO)) {
				values.put(
						DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO,
						"0");
			}

			if (!values
					.containsKey(DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO)) {
				values.put(
						DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO, 0);
			} else if (!values.getAsString(
					DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO)
					.equals("0")) {
				Cursor cur = DB
						.getReadableDatabase()
						.query(DBDefinicoes.ProcessoQuestoes.TABLE_NAME,
								new String[] {
										DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO,
										DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO,
										DBDefinicoes.ProcessoQuestoes.TABLE_NAME
												+ "."
												+ DBDefinicoes.ProcessoQuestoes._ID },
								DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO
										+ " = ? AND "
										+ DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO
										+ " = ?",
								new String[] {
										values.getAsString(DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO),
										values.getAsString(DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO) },
								null,
								null,
								DBDefinicoes.ProcessoQuestoes.DEFAULT_SORT_ORDER);
				if (cur.moveToNext()) {
					// Reencaminhar a inserção para uma atualização
					// Log.e("++DBProvider++","Resgistro duplicado redirecionando para atualização - ProcessoQuestao ids (p,q) "
					// +
					// cur.getInt(0) + ", " + cur.getInt(1));
					update(ContentUris.withAppendedId(
							DBDefinicoes.ProcessoQuestoes.CONTENT_URI,
							cur.getInt(2)), values, null, null);
					int id = cur.getInt(2);
					cur.close();
					return ContentUris.withAppendedId(
							DBDefinicoes.ProcessoQuestoes.CONTENT_URI, id);
				}
				cur.close();
			}

			// Realizando inserção e recebendo o id do novo registro
			rowId = db.insert(DBDefinicoes.ProcessoQuestoes.TABLE_NAME, // Nome
																		// da
																		// tabela
					DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO, // Coluna
																		// "not null"
					values // Campos e valores de inserção
					);

			if (rowId > 0)
				// Cria um URI para o novo registro
				changedUri = ContentUris.withAppendedId(
						DBDefinicoes.ProcessoQuestoes.CONTENT_ID_URI_BASE,
						rowId);
		} else if (uriMatcher.match(uri) == ACOES) {
			// Adicionando valores padrões aos campos nulos
			if (!values.containsKey(DBDefinicoes.Acoes.COLUMN_NAME_RESPONSAVEL)) {
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_RESPONSAVEL, "");
			}

			if (!values.containsKey(DBDefinicoes.Acoes.COLUMN_NAME_DATA_INICIO)) {
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_DATA_INICIO, 0);
			}

			if (!values.containsKey(DBDefinicoes.Acoes.COLUMN_NAME_DATA_FIM)) {
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_DATA_FIM, 0);
			}

			if (!values.containsKey(DBDefinicoes.Acoes.COLUMN_NAME_CUSTO)) {
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_CUSTO, 0);
			}

			if (!values.containsKey(DBDefinicoes.Acoes.COLUMN_NAME_ESFORCO)) {
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_ESFORCO, 0);
			}

			if (!values.containsKey(DBDefinicoes.Acoes.COLUMN_NAME_ID_PROCESSO)) {
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_ID_PROCESSO, "0");
			}

			if (!values.containsKey(DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA)) {
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA, 0);
			}

			if (!values.containsKey(DBDefinicoes.Acoes.COLUMN_NAME_ID)) {
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_ID, "0");
			} else {
				Cursor cur = DB
						.getReadableDatabase()
						.query(DBDefinicoes.Acoes.TABLE_NAME,
								new String[] {
										DBDefinicoes.Acoes.COLUMN_NAME_ID,
										DBDefinicoes.Acoes._ID },
								DBDefinicoes.Acoes.COLUMN_NAME_ID + " = ?",
								new String[] { values
										.getAsString(DBDefinicoes.Acoes.COLUMN_NAME_ID) },
								null, null,
								DBDefinicoes.Acoes.DEFAULT_SORT_ORDER);
				if (cur.moveToNext()) {
					// Reencaminhar a inserção para uma atualização
					// Log.e("++DBProvider++","Resgistro duplicado redirecionando para atualização - Acao id "
					// + cur.getInt(0));
					update(ContentUris.withAppendedId(
							DBDefinicoes.Acoes.CONTENT_URI, cur.getInt(1)),
							values, null, null);
					int id = cur.getInt(1);
					cur.close();
					return ContentUris.withAppendedId(
							DBDefinicoes.Acoes.CONTENT_URI, id);
				}
				cur.close();
			}

			// Realizando inserção e recebendo o id do novo registro
			rowId = db.insert(DBDefinicoes.Acoes.TABLE_NAME, // Nome da tabela
					DBDefinicoes.Acoes.COLUMN_NAME_ID, // Coluna "not null"
					values // Campos e valores de inserção
					);

			if (rowId > 0)
				// Cria um URI para o novo registro
				changedUri = ContentUris.withAppendedId(
						DBDefinicoes.Acoes.CONTENT_ID_URI_BASE, rowId);
		} else {
			throw new IllegalArgumentException("URI Desconhecida " + uri);
		}

		// Verificando se a inserção foi bem sucedida
		if (rowId > 0) {
			// E notifica ao ContentRelsover o URI alterado
			getContext().getContentResolver().notifyChange(changedUri, null);
			return changedUri;
		}
		SQLiteDatabase.releaseMemory();
		// Se a inserção não for bem sucedida, exceção
		throw new SQLException("Falha ao tentar inserir " + uri);
	}

	/**
	 * Executado automaticamente no momento de criação do
	 * <code>ContentProvider</code>.
	 * <p>
	 * Inicia o banco de dados da aplicação.
	 * </p>
	 */
	@Override
	public boolean onCreate() {
		DB = new MobiPETICDatabaseHelper(getContext());
		return true;
	}

	/**
	 * Realiza consultas de simples ou múltiplos registros nas tabelas do banco
	 * de dados da aplicação.
	 * 
	 * @param uri
	 *            Caminho que identifica a tabela e a forma de remoção (simples
	 *            ou múltiplos registros).
	 * @param projection
	 *            Campos de retorno da consulta.
	 * @param selection
	 *            Cláusula WHERE.
	 * @param selectionArgs
	 *            Valores de filtro da cláusula WHERE.
	 * @param sortOrder
	 *            Forma de ordenação.
	 * @return Cursor com os registros resultados da consulta.
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// Construindo um novo query builder
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		// Abrindo o objeto de banco de dados: modo "read"

		SQLiteDatabase db = DB.getReadableDatabase();
		String[] finalArgs;

		String orderBy = "_id ASC";
		switch (uriMatcher.match(uri)) {
		// Se a URI consultada se referir a um conjunto de linhas da tabela area
		case AREAS:
			// definindo a tabela
			qb.setTables(DBDefinicoes.Areas.TABLE_NAME);
			qb.setProjectionMap(projecaoArea);
			orderBy = DBDefinicoes.Areas.DEFAULT_SORT_ORDER;
			break;
		// Se a URI consultada se referir a uma única linha tabela area
		case AREA_ID:
			// definindo a tabela
			qb.setTables(DBDefinicoes.Areas.TABLE_NAME);
			qb.setProjectionMap(projecaoArea);
			qb.appendWhere(DBDefinicoes.Areas._ID
					+ " = ?");

			if (selectionArgs != null) {
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Areas.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
				selectionArgs = finalArgs;
			} else {
				selectionArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Areas.ID_PATH_POSITION)};
			}
			break;
		// Se a URI consultada se referir a um conjunto de linhas da tabela
		// subarea
		case SUBAREAS:
			// definindo a tabela
			qb.setTables(DBDefinicoes.Subareas.TABLE_NAME);
			qb.setProjectionMap(projecaoSubarea);
			orderBy = DBDefinicoes.Subareas.DEFAULT_SORT_ORDER;
			break;

		// Se a URI consultada se referir a uma única linha tabela subarea
		case SUBAREA_ID:
			// definindo a tabela
			qb.setTables(DBDefinicoes.Subareas.TABLE_NAME);
			qb.setProjectionMap(projecaoSubarea);
			qb.appendWhere(DBDefinicoes.Subareas._ID
					+ " = ?");

			if (selectionArgs != null) {
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Subareas.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
				selectionArgs = finalArgs;
			} else {
				selectionArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Subareas.ID_PATH_POSITION)};
			}
			break;
		// Se a URI consultada se referir a um conjunto de linhas da tabela
		// processo
		case PROCESSOS:
			// definindo a tabela
			qb.setTables(DBDefinicoes.Processos.TABLE_NAME);
			qb.setProjectionMap(projecaoProcesso);
			orderBy = DBDefinicoes.Processos.DEFAULT_SORT_ORDER;
			break;

		// Se a URI consultada se referir a uma única linha tabela processo
		case PROCESSO_ID:
			// definindo a tabela
			qb.setTables(DBDefinicoes.Processos.TABLE_NAME);
			qb.setProjectionMap(projecaoProcesso);
			qb.appendWhere(DBDefinicoes.Processos._ID
					+ " = ?");
			
			if (selectionArgs != null) {
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Processos.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
				selectionArgs = finalArgs;
			} else {
				selectionArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Processos.ID_PATH_POSITION)};
			}
			break;
		// Se a URI consultada se referir a um conjunto de linhas da tabela
		// questao
		case QUESTOES:
			// definindo a tabela
			qb.setTables(DBDefinicoes.Questoes.TABLE_NAME);
			qb.setProjectionMap(projecaoQuestao);
			orderBy = DBDefinicoes.Questoes.DEFAULT_SORT_ORDER;
			break;

		// Se a URI consultada se referir a uma única linha tabela questao
		case QUESTAO_ID:
			// definindo a tabela
			qb.setTables(DBDefinicoes.Questoes.TABLE_NAME);
			qb.setProjectionMap(projecaoQuestao);
			qb.appendWhere(DBDefinicoes.Questoes._ID
					+ " = ?");

			if (selectionArgs != null) {
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Questoes.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
				selectionArgs = finalArgs;
			} else {
				selectionArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Questoes.ID_PATH_POSITION)};
			}
			break;
		// Se a URI consultada se referir a um conjunto de linhas da tabela
		// processo_questao
		case PROCESSO_QUESTOES:
			// definindo a tabela
			qb.setTables(DBDefinicoes.ProcessoQuestoes.TABLE_NAME + " JOIN "
					+ DBDefinicoes.Questoes.TABLE_NAME + " ON ("
					+ DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO
					+ " = " + DBDefinicoes.Questoes.COLUMN_NAME_ID + ")");
			qb.setProjectionMap(projecaoProcessoQuestao);
			orderBy = DBDefinicoes.ProcessoQuestoes.DEFAULT_SORT_ORDER;
			break;

		// Se a URI consultada se referir a uma única linha tabela
		// processo_questao
		case PROCESSO_QUESTAO_ID:
			// definindo a tabela
			qb.setTables(DBDefinicoes.ProcessoQuestoes.TABLE_NAME);
			qb.setProjectionMap(projecaoProcessoQuestao);
			qb.appendWhere(DBDefinicoes.ProcessoQuestoes._ID
					+ " = ?");

			if (selectionArgs != null) {
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.ProcessoQuestoes.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
				selectionArgs = finalArgs;
			} else {
				selectionArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.ProcessoQuestoes.ID_PATH_POSITION)};
			}
			break;
		// Se a URI consultada se referir a um conjunto de linhas da tabela acao
		case ACOES:
			// definindo a tabela
			qb.setTables(DBDefinicoes.Acoes.TABLE_NAME);
			qb.setProjectionMap(projecaoAcao);
			orderBy = DBDefinicoes.Acoes.DEFAULT_SORT_ORDER;
			break;

		// Se a URI consultada se referir a uma única linha tabela acao
		case ACAO_ID:
			// definindo a tabela
			qb.setTables(DBDefinicoes.Acoes.TABLE_NAME);
			qb.setProjectionMap(projecaoAcao);
			qb.appendWhere(DBDefinicoes.Acoes._ID
					+ " = ?");

			if (selectionArgs != null) {
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Acoes.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
				selectionArgs = finalArgs;
			} else {
				selectionArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Acoes.ID_PATH_POSITION)};
			}
			break;
		default:
			// Se não reconhecer a URI, exceção
			throw new IllegalArgumentException("URI Desconhecida " + uri);
		}

		// Caso a ordem não seja especificada, usa a ordenação padrão (a mesma
		// para todas as tabelas)
		if (!TextUtils.isEmpty(sortOrder)) {
			orderBy = sortOrder;
		}

		// Gerando resultado da consulta
		Cursor cursor = qb.query(db, // Banco de dados da consulta
				projection, // Colunas para retornar na consulta
				selection, // Campos de seleção referentes à cláusula "where"
				selectionArgs, // Valores de seleção referentes à cláusula
								// "where"
				null, // reservado para a cláusula "group by" (campos)
				null, // reservado para a cláusula "group by" (valores)
				orderBy); // A ordenação definida

		SQLiteDatabase.releaseMemory();
		// Notificando a consulta ao ContentRelsover
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	/**
	 * Realiza a atualização de um simples ou múltiplos registros nas tabelas do
	 * banco de dados da aplicação.
	 * 
	 * @param uri
	 *            Caminho que identifica a tabela e a forma de remoção (simples
	 *            ou múltiplos registros).
	 * @param values
	 *            Valores de atualização.
	 * @param selection
	 *            Cláusula WHERE.
	 * @param selectionArgs
	 *            Valores de filtro da cláusula WHERE.
	 * @return Quantidade de registros atualizados.
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// Abrindo o objeto de banco de dados: modo "write"
		SQLiteDatabase db = DB.getWritableDatabase();
		int count;
		String finalWhere;
		String[] finalArgs;

		// Pegando a data corrente em milisegundos
		Long now = Long.valueOf(System.currentTimeMillis());

		switch (uriMatcher.match(uri)) {
		// Alterações para um conjunto de linhas da tabela area
		case AREAS:
			// Atualizando
			count = db.update(DBDefinicoes.Areas.TABLE_NAME, // Nome da tabela
					values, // Campos e valores de atualização
					selection, // Campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Alteraçãos de apenas uma linha da tabela area
		case AREA_ID:
			// Adiciona mais uma condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.Areas._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Areas.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Areas.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Atualizando
			count = db.update(DBDefinicoes.Areas.TABLE_NAME, // Nome da tabela
					values, // Campos e valores de atualização
					finalWhere, // Campos de seleção referentes à cláusula
								// "where"
					finalArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Alterações para um conjunto de linhas da tabela subarea
		case SUBAREAS:
			// Atualizando
			count = db.update(DBDefinicoes.Subareas.TABLE_NAME, // Nome da
																// tabela
					values, // Campos e valores de atualização
					selection, // Campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Alteraçãos de apenas uma linha da tabela subarea
		case SUBAREA_ID:
			// Adiciona mais uma condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.Subareas._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Subareas.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Subareas.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Atualizando
			count = db.update(DBDefinicoes.Subareas.TABLE_NAME, // Nome da
																// tabela
					values, // Campos e valores de atualização
					finalWhere, // Campos de seleção referentes à cláusula
								// "where"
					finalArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Alterações para um conjunto de linhas da tabela processo
		case PROCESSOS:
			// Atualizando a data de modificação caso esteja sendo atualizada
			if (values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_DATA_MODIFICACAO) == false) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_DATA_MODIFICACAO,
						now);
			}
			// Atualizando
			count = db.update(DBDefinicoes.Processos.TABLE_NAME, // Nome da
																	// tabela
					values, // Campos e valores de atualização
					selection, // Campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Alteraçãos de apenas uma linha da tabela processo
		case PROCESSO_ID:
			// Atualizando a data de modificação caso esteja sendo atualizada
			if (values
					.containsKey(DBDefinicoes.Processos.COLUMN_NAME_DATA_MODIFICACAO) == false) {
				values.put(DBDefinicoes.Processos.COLUMN_NAME_DATA_MODIFICACAO,
						now);
			}
			// Adiciona mais um condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.Processos._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Processos.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Processos.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Atualizando
			count = db.update(DBDefinicoes.Processos.TABLE_NAME, // Nome da
																	// tabela
					values, // Campos e valores de atualização
					finalWhere, // Campos de seleção referentes à cláusula
								// "where"
					finalArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Alterações para um conjunto de linhas da tabela questao
		case QUESTOES:
			// Atualizando
			count = db.update(DBDefinicoes.Questoes.TABLE_NAME, // Nome da
																// tabela
					values, // Campos e valores de atualização
					selection, // Campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Alteraçãos de apenas uma linha da tabela questao
		case QUESTAO_ID:
			// Adiciona mais uma condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.Questoes._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Questoes.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Questoes.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Atualizando
			count = db.update(DBDefinicoes.Questoes.TABLE_NAME, // Nome da
																// tabela
					values, // Campos e valores de atualização
					finalWhere, // Campos de seleção referentes à cláusula
								// "where"
					finalArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Alterações para um conjunto de linhas da tabela processo_questao
		case PROCESSO_QUESTOES:
			// Atualizando
			count = db.update(DBDefinicoes.ProcessoQuestoes.TABLE_NAME, // Nome
																		// da
																		// tabela
					values, // Campos e valores de atualização
					selection, // Campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Alteraçãos de apenas uma linha da tabela processo_questao
		case PROCESSO_QUESTAO_ID:
			// Adiciona mais uma condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.ProcessoQuestoes._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.ProcessoQuestoes.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.ProcessoQuestoes.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Atualizando
			count = db.update(DBDefinicoes.ProcessoQuestoes.TABLE_NAME, // Nome
																		// da
																		// tabela
					values, // Campos e valores de atualização
					finalWhere, // Campos de seleção referentes à cláusula
								// "where"
					finalArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Se a URI não for reconhecida, exceção
		// Alterações para um conjunto de linhas da tabela acao
		case ACOES:
			// Atualizando
			count = db.update(DBDefinicoes.Acoes.TABLE_NAME, // Nome da tabela
					values, // Campos e valores de atualização
					selection, // Campos de seleção referentes à cláusula
								// "where"
					selectionArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		// Alteraçãos de apenas uma linha da tabela processo
		case ACAO_ID:
			// Adiciona mais um condição de remoção à cláusula "where"
			finalWhere = DBDefinicoes.Acoes._ID
					+ " = ?";

			finalArgs = new String[]{uri.getPathSegments().get(DBDefinicoes.Acoes.ID_PATH_POSITION)};

			// Unindo à seleção passada por parâmetro
			if (selection != null) {
				finalWhere = finalWhere + " AND " + selection;
				finalArgs = new String[selectionArgs.length + 1];
				finalArgs[0] = uri.getPathSegments().get(DBDefinicoes.Acoes.ID_PATH_POSITION);
				for (int k = 1; k < (selectionArgs.length + 1); k++) {
					finalArgs[k] = selectionArgs[k-1];
				}
			}

			// Atualizando
			count = db.update(DBDefinicoes.Acoes.TABLE_NAME, // Nome da tabela
					values, // Campos e valores de atualização
					finalWhere, // Campos de seleção referentes à cláusula
								// "where"
					finalArgs); // Valores de seleção referentes à cláusula
									// "where"
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		SQLiteDatabase.releaseMemory();
		// Notifica as alterações ao ContentResolver
		getContext().getContentResolver().notifyChange(uri, null);

		// Retorna o número de registros atualizados
		return count;
	}
}