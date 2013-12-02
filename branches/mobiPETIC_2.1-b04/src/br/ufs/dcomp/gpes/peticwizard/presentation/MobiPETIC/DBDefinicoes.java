package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Classe <code>Contract</code> com os padr�es de nomes para acessos/altera��es
 * no banco de dados da aplica��o.
 * 
 * <p>
 * <b>Obs.:</b> Classe n�o pode ser instanciada.
 * </p>
 */
public final class DBDefinicoes {
	public static final String AUTHORITY = "peticwizard.MobiPetic.provider";

	// Esta classe n�o pode ser instanciada
	private DBDefinicoes() {
	}

	/**
	 * Padr�es de nomes para a Tabela de �reas.
	 * 
	 * <p>
	 * <b>Obs.:</b> Classe n�o pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class Areas implements BaseColumns {

		// Esta classe n�o pode ser instanciada
		private Areas() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "area";

		// Defini��es do URI

		/**
		 * O scheme para a URI do <code>ContentProvider</code>.
		 */
		private static final String SCHEME = "content://";

		// Caminhos para as URIs

		/**
		 * Caminho para o URI de �reas.
		 */
		private static final String PATH = "/areas";

		/**
		 * Caminho para o URI ID uma �rea espec�fica.
		 */
		private static final String PATH_ID = "/areas/";

		/**
		 * Posi��o relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para uma simples �rea. Chamador deve anexar um id
		 * num�rico a este Uri para receber uma �rea.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordena��o padr�o para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_area ASC";

		// Defini��es das colunas da tabela areas

		/**
		 * Coluna com o ID da �rea.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID = "id_area";

		/**
		 * Coluna com o nome da �rea.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_NOME = "nome_area";
	}

	/**
	 * Padr�es de nomes para a Tabela de Sub-�reas.
	 * 
	 * <p>
	 * <b>Observa��o:</b> Classe n�o pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class Subareas implements BaseColumns {

		// Esta classe n�o pode ser instanciada
		private Subareas() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "subarea";

		// Defini��es do URI

		/**
		 * O scheme para a URI do <code>ContentProvider</code>.
		 */
		private static final String SCHEME = "content://";

		// Caminhos para as URIs

		/**
		 * Caminho para o URI de sub-�reas.
		 */
		private static final String PATH = "/subareas";

		/**
		 * Caminho para o URI ID uma sub-�rea espec�fica.
		 */
		private static final String PATH_ID = "/subareas/";

		/**
		 * Posi��o relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para uma simples sub-�rea. Chamador deve anexar um
		 * id num�rico a este Uri para receber uma sub-�rea.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordena��o padr�o para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_subarea ASC";

		// Defini��es das colunas da tabela areas

		/**
		 * Coluna com o ID da sub-�rea.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID = "id_subarea";

		/**
		 * Coluna com o nome da sub-�rea.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_NOME = "nome_subarea";

		/**
		 * Coluna com o id da �rea relacionada.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID_AREA = "id_area_subarea";
	}

	/**
	 * Padr�es de nomes para a Tabela de Processos.
	 * 
	 * <p>
	 * <b>Observa��o:</b> Classe n�o pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class Processos implements BaseColumns {

		// Esta classe n�o pode ser instanciada
		private Processos() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "processo";

		// Defini��es do URI

		/**
		 * O scheme para a URI do <code>Provider</code>.
		 */
		private static final String SCHEME = "content://";

		// Caminhos para as URIs

		/**
		 * Caminho para o URI de processos.
		 */
		private static final String PATH = "/processos";

		/**
		 * Caminho para o URI ID uma processo espec�fica.
		 */
		private static final String PATH_ID = "/processos/";

		/**
		 * Posi��o relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para um simples processo. Chamador deve anexar um
		 * id num�rico a este Uri para receber um processo.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordena��o padr�o para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_processo ASC";

		// Defini��es das colunas da tabela areas

		/**
		 * Coluna com o ID do processo.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID = "id_processo";

		/**
		 * Coluna com o nome do processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_NOME = "nome_processo";

		/**
		 * Coluna com a descri��o do processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_DESCRICAO = "descricao_processo";

		/**
		 * Coluna com o grau de maturidade definido para o processo.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_GRAU_MATURIDADE = "grau_maturidade_processo";

		/**
		 * Coluna com o grau de maturidade definido em artefato anterior.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_GRAU_MATURIDADE_ANT = "grau_maturidade_ant_processo";

		/**
		 * Coluna que indica se o processo � priorit�rio.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_PRIORITARIO = "e_prioritario_processo";

		/**
		 * Coluna que indica se a prioridade do processo foi definida pelo
		 * usu�rio.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_PRIORIDADE_DEF = "prioridade_def_processo";

		/**
		 * Coluna com as entradas para o processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_ENTRADAS = "entradas_processo";

		/**
		 * Coluna com as sa�das do processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_SAIDAS = "saidas_processo";

		/**
		 * Coluna que indica o respons�vel pelo processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_RESPONSAVEL = "responsavel_processo";

		/**
		 * Coluna que indica os stakeholders do processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_STAKEHOLDERS = "stakeholders_processo";

		/**
		 * Coluna com o id da sub-�rea relacionada.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID_SUBAREA = "id_subarea_processo";

		/**
		 * Coluna com a data de modifica��o do registro em timestamp.
		 * <P>
		 * Type: INTEGER (long from System.curentTimeMillis())
		 * </P>
		 */
		public static final String COLUMN_NAME_DATA_MODIFICACAO = "modicacao_processo";

		/**
		 * Coluna que indica a vers�o de altera��o/data de modifica��o no
		 * registro de marca��o do question�rio.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_MODIFICACAO_QUESTIONARIO = "modificacao_quest_processo";

		/**
		 * Coluna que indica o �ltimo editor do question�rio de maturidade do
		 * processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_EDITOR_QUESTIONARIO = "editor_quest_processo";

		/**
		 * Coluna que indica se o question�rio foi modificado e aguarda por
		 * sincroniza��o. Apenas para uso da aplica��o.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_SINCRONIZAR = "sincronizar_processo";
	}

	/**
	 * Padr�es de nomes para a Tabela de Quest�es.
	 * 
	 * <p>
	 * <b>Observa��o:</b> Classe n�o pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class Questoes implements BaseColumns {

		// Esta classe n�o pode ser instanciada
		private Questoes() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "questao";

		// Defini��es do URI

		/**
		 * O scheme para a URI do <code>Provider</code>.
		 */
		private static final String SCHEME = "content://";

		// Caminhos para as URIs

		/**
		 * Caminho para o URI de questoes.
		 */
		private static final String PATH = "/questoes";

		/**
		 * Caminho para o URI ID uma questao espec�fica.
		 */
		private static final String PATH_ID = "/questoes/";

		/**
		 * Posi��o relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para uma simples questao. Chamador deve anexar um
		 * id num�rico a este Uri para receber uma questao.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordena��o padr�o para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_questao ASC";

		// Defini��es das colunas da tabela questoes

		/**
		 * Coluna com o ID do quest�o.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID = "id_questao";

		/**
		 * Coluna com o n�vel da quest�o.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_NIVEL = "nivel_questao";

		/**
		 * Coluna com a descri��o da questao.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_DESCRICAO = "descricao_questao";
	}

	/**
	 * Padr�es de nomes para a Tabela de Quest�es de Processos.
	 * 
	 * <p>
	 * <b>Observa��o:</b> Classe n�o pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class ProcessoQuestoes implements BaseColumns {

		// Esta classe n�o pode ser instanciada
		private ProcessoQuestoes() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "processo_questao";

		// Defini��es do URI

		/**
		 * O scheme para a URI do <code>Provider</code>.
		 */
		private static final String SCHEME = "content://";

		// Caminhos para as URIs

		/**
		 * Caminho para o URI das quest�es dos processos.
		 */
		private static final String PATH = "/processo_questoes";

		/**
		 * Caminho para o URI ID uma quest�o de processo espec�fica.
		 */
		private static final String PATH_ID = "/processo_questoes/";

		/**
		 * Posi��o relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para uma simples quest�o de processo. Chamador
		 * deve anexar um id num�rico a este Uri para receber uma quest�o de
		 * processo.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordena��o padr�o para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_questao_processo_questao ASC";

		// Defini��es das colunas da tabela processo_questoes

		/**
		 * Coluna com o ID do processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_ID_PROCESSO = "id_processo_processo_questao";

		/**
		 * Coluna com o ID da quest�o.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID_QUESTAO = "id_questao_processo_questao";

		/**
		 * Coluna que indica se a quest�o est� marcada ou n�o.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_MARCADO = "marcado_processo_questao";
	}

	/**
	 * Padr�es de nomes para a Tabela de A��es.
	 * 
	 * <p>
	 * <b>Observa��o:</b> Classe n�o pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class Acoes implements BaseColumns {

		// Esta classe n�o pode ser instanciada
		private Acoes() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "acao";

		// Defini��es do URI

		/**
		 * O scheme para a URI do <code>Provider</code>.
		 */
		private static final String SCHEME = "content://";

		// Caminhos para as URIs

		/**
		 * Caminho para o URI das a��es.
		 */
		private static final String PATH = "/acoes";

		/**
		 * Caminho para o URI ID uma a��o espec�fica.
		 */
		private static final String PATH_ID = "/acoes/";

		/**
		 * Posi��o relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para uma simples a��o. Chamador deve anexar um id
		 * num�rico a este Uri para receber uma a��o.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordena��o padr�o para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_acao ASC";

		// Defini��es das colunas da tabela processo_questoes

		/**
		 * Coluna com o ID da a��o.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_ID = "id_acao";

		/**
		 * Coluna com o nome da a��o.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_NOME = "nome_acao";

		/**
		 * Coluna com o nome do respons�vel pela a��o.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_RESPONSAVEL = "responsavel_acao";

		/**
		 * Coluna com a data de in�cio da a��o.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_DATA_INICIO = "data_inicio_acao";

		/**
		 * Coluna com a data prevista de t�rmino da a��o.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_DATA_FIM = "data_fim_acao";

		/**
		 * Coluna com o custo estimado da a��o.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_CUSTO = "custo_acao";

		/**
		 * Coluna com a informa��o de esfor�o (hora/pessoa) estimado para a
		 * execu��o da a��o.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ESFORCO = "esforco_acao";

		/**
		 * Coluna com o id do processo associado � a��o.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_ID_PROCESSO = "id_processo_acao";

		/**
		 * Coluna com o flag que indica se a a��o foi adotada pela empresa ou se
		 * � uma sugest�o.
		 * <P>
		 * Type: Integer
		 * </P>
		 */
		public static final String COLUMN_NAME_ADOTADA = "adotada_acao";

		/**
		 * Coluna que indica se a a��o foi modificada ou criada, e se aguarda
		 * por sincroniza��o. Apenas para uso da aplica��o.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_SINCRONIZAR = "sincronizar_acao";
	}
}