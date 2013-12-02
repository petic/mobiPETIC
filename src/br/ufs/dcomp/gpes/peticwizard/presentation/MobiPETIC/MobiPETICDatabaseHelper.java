package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe de criação e controle de versão do banco de dados da aplicação.
 * 
 * @see SQLiteOpenHelper
 */
public class MobiPETICDatabaseHelper extends SQLiteOpenHelper {

	public final static String DBNAME = "mobipetic.db";
	// Tabela Area
	private final String SQL_CREATE_AREA = "CREATE TABLE "
			+ DBDefinicoes.Areas.TABLE_NAME + " ("
			+ DBDefinicoes.Areas._ID + " INTEGER PRIMARY KEY, "
			+ DBDefinicoes.Areas.COLUMN_NAME_ID + " TEXT, "
			+ DBDefinicoes.Areas.COLUMN_NAME_NOME + " TEXT );";
	// Tabela Subarea
	private final String SQL_CREATE_SUBAREA = "CREATE TABLE "
			+ DBDefinicoes.Subareas.TABLE_NAME + " ("
			+ DBDefinicoes.Subareas._ID + " INTEGER PRIMARY KEY, "
			+ DBDefinicoes.Subareas.COLUMN_NAME_ID + " TEXT, "
			+ DBDefinicoes.Subareas.COLUMN_NAME_NOME + " TEXT, "
			+ DBDefinicoes.Subareas.COLUMN_NAME_ID_AREA + " TEXT );";
	// Tabela Processo
	private final String SQL_CREATE_PROCESSO = "CREATE TABLE "
			+ DBDefinicoes.Processos.TABLE_NAME + " ("
			+ DBDefinicoes.Processos._ID + " INTEGER PRIMARY KEY, "
			+ DBDefinicoes.Processos.COLUMN_NAME_ID + " TEXT, "
			+ DBDefinicoes.Processos.COLUMN_NAME_NOME + " TEXT, "
			+ DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE + " INTEGER, "
			+ DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT + " INTEGER, "
			+ DBDefinicoes.Processos.COLUMN_NAME_PRIORITARIO + " INTEGER, "
			+ DBDefinicoes.Processos.COLUMN_NAME_PRIORIDADE_DEF + " INTEGER, "
			+ DBDefinicoes.Processos.COLUMN_NAME_DESCRICAO + " TEXT, "
			+ DBDefinicoes.Processos.COLUMN_NAME_ENTRADAS + " TEXT, "
			+ DBDefinicoes.Processos.COLUMN_NAME_SAIDAS + " TEXT, "
			+ DBDefinicoes.Processos.COLUMN_NAME_RESPONSAVEL + " TEXT, "
			+ DBDefinicoes.Processos.COLUMN_NAME_STAKEHOLDERS + " TEXT, "
			+ DBDefinicoes.Processos.COLUMN_NAME_DATA_MODIFICACAO + " INTEGER, "
			+ DBDefinicoes.Processos.COLUMN_NAME_MODIFICACAO_QUESTIONARIO + " INTEGER, "
			+ DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO + " TEXT,"
			+ DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR + " INTEGER,"
			+ DBDefinicoes.Processos.COLUMN_NAME_ID_SUBAREA + " TEXT );";
	// Tabela Questao
	private final String SQL_CREATE_QUESTAO = "CREATE TABLE "
			+ DBDefinicoes.Questoes.TABLE_NAME + " ("
			+ DBDefinicoes.Questoes._ID + " INTEGER PRIMARY KEY, "
			+ DBDefinicoes.Questoes.COLUMN_NAME_ID + " INTEGER, "
			+ DBDefinicoes.Questoes.COLUMN_NAME_NIVEL + " INTEGER, "
			+ DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO + " TEXT );";
	// Tabela ProcessoQuestao
	private final String SQL_CREATE_PROCESSO_QUESTAO = "CREATE TABLE "
			+ DBDefinicoes.ProcessoQuestoes.TABLE_NAME + " ("
			+ DBDefinicoes.ProcessoQuestoes._ID + " INTEGER PRIMARY KEY, "
			+ DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO + " INTEGER, "
			+ DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO + " TEXT, "
			+ DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO + " INTEGER );";
	// Tabela Acao
	private final String SQL_CREATE_ACAO = "CREATE TABLE "
			+ DBDefinicoes.Acoes.TABLE_NAME + " ("
			+ DBDefinicoes.Acoes._ID + " INTEGER PRIMARY KEY, "
			+ DBDefinicoes.Acoes.COLUMN_NAME_ID + " TEXT, "
			+ DBDefinicoes.Acoes.COLUMN_NAME_NOME + " TEXT, "
			+ DBDefinicoes.Acoes.COLUMN_NAME_RESPONSAVEL + " TEXT, "
			+ DBDefinicoes.Acoes.COLUMN_NAME_DATA_INICIO + " INTEGER, "
			+ DBDefinicoes.Acoes.COLUMN_NAME_DATA_FIM + " INTEGER, "
			+ DBDefinicoes.Acoes.COLUMN_NAME_CUSTO + " INTEGER, "
			+ DBDefinicoes.Acoes.COLUMN_NAME_ESFORCO + " INTEGER, "
			+ DBDefinicoes.Acoes.COLUMN_NAME_ID_PROCESSO + " TEXT, "
			+ DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA + " INTEGER, "
			+ DBDefinicoes.Acoes.COLUMN_NAME_SINCRONIZAR + " INTEGER);";

	public MobiPETICDatabaseHelper(Context context) {
		super(context, DBNAME, null, 16);
	}

	/**
	 * Executado automaticamente quando o banco de dados é criado pela primeira
	 * vez.
	 * <p>
	 * Cria as tabelas do banco de dados
	 * </p>
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_AREA);
		db.execSQL(SQL_CREATE_SUBAREA);
		db.execSQL(SQL_CREATE_PROCESSO);
		db.execSQL(SQL_CREATE_QUESTAO);
		db.execSQL(SQL_CREATE_PROCESSO_QUESTAO);
		db.execSQL(SQL_CREATE_ACAO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Log.i("+++MobiPETICDatabaseHelper+++","Atualizando base de dados");
		db.execSQL("DROP TABLE IF EXISTS " + DBDefinicoes.Areas.TABLE_NAME
				+ ";");
		db.execSQL("DROP TABLE IF EXISTS " + DBDefinicoes.Subareas.TABLE_NAME
				+ ";");
		db.execSQL("DROP TABLE IF EXISTS " + DBDefinicoes.Processos.TABLE_NAME
				+ ";");
		db.execSQL("DROP TABLE IF EXISTS " + DBDefinicoes.Questoes.TABLE_NAME
				+ ";");
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBDefinicoes.ProcessoQuestoes.TABLE_NAME + ";");
		db.execSQL("DROP TABLE IF EXISTS " + DBDefinicoes.Acoes.TABLE_NAME
				+ ";");
		this.onCreate(db);
	}

}