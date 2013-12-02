package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Classe <code>Contract</code> com os padrões de nomes para acessos/alterações
 * no banco de dados da aplicação.
 * 
 * <p>
 * <b>Obs.:</b> Classe não pode ser instanciada.
 * </p>
 */
public final class DBDefinicoes {
	public static final String AUTHORITY = "peticwizard.MobiPetic.provider";

	// Esta classe não pode ser instanciada
	private DBDefinicoes() {
	}

	/**
	 * Padrões de nomes para a Tabela de Áreas.
	 * 
	 * <p>
	 * <b>Obs.:</b> Classe não pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class Areas implements BaseColumns {

		// Esta classe não pode ser instanciada
		private Areas() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "area";

		// Definições do URI

		/**
		 * O scheme para a URI do <code>ContentProvider</code>.
		 */
		private static final String SCHEME = "content://";

		// Caminhos para as URIs

		/**
		 * Caminho para o URI de áreas.
		 */
		private static final String PATH = "/areas";

		/**
		 * Caminho para o URI ID uma área específica.
		 */
		private static final String PATH_ID = "/areas/";

		/**
		 * Posição relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para uma simples área. Chamador deve anexar um id
		 * numérico a este Uri para receber uma área.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordenação padrão para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_area ASC";

		// Definições das colunas da tabela areas

		/**
		 * Coluna com o ID da área.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID = "id_area";

		/**
		 * Coluna com o nome da área.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_NOME = "nome_area";
	}

	/**
	 * Padrões de nomes para a Tabela de Sub-áreas.
	 * 
	 * <p>
	 * <b>Observação:</b> Classe não pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class Subareas implements BaseColumns {

		// Esta classe não pode ser instanciada
		private Subareas() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "subarea";

		// Definições do URI

		/**
		 * O scheme para a URI do <code>ContentProvider</code>.
		 */
		private static final String SCHEME = "content://";

		// Caminhos para as URIs

		/**
		 * Caminho para o URI de sub-áreas.
		 */
		private static final String PATH = "/subareas";

		/**
		 * Caminho para o URI ID uma sub-área específica.
		 */
		private static final String PATH_ID = "/subareas/";

		/**
		 * Posição relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para uma simples sub-área. Chamador deve anexar um
		 * id numérico a este Uri para receber uma sub-área.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordenação padrão para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_subarea ASC";

		// Definições das colunas da tabela areas

		/**
		 * Coluna com o ID da sub-área.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID = "id_subarea";

		/**
		 * Coluna com o nome da sub-área.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_NOME = "nome_subarea";

		/**
		 * Coluna com o id da área relacionada.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID_AREA = "id_area_subarea";
	}

	/**
	 * Padrões de nomes para a Tabela de Processos.
	 * 
	 * <p>
	 * <b>Observação:</b> Classe não pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class Processos implements BaseColumns {

		// Esta classe não pode ser instanciada
		private Processos() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "processo";

		// Definições do URI

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
		 * Caminho para o URI ID uma processo específica.
		 */
		private static final String PATH_ID = "/processos/";

		/**
		 * Posição relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para um simples processo. Chamador deve anexar um
		 * id numérico a este Uri para receber um processo.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordenação padrão para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_processo ASC";

		// Definições das colunas da tabela areas

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
		 * Coluna com a descrição do processo.
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
		 * Coluna que indica se o processo é prioritário.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_PRIORITARIO = "e_prioritario_processo";

		/**
		 * Coluna que indica se a prioridade do processo foi definida pelo
		 * usuário.
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
		 * Coluna com as saídas do processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_SAIDAS = "saidas_processo";

		/**
		 * Coluna que indica o responsável pelo processo.
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
		 * Coluna com o id da sub-área relacionada.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID_SUBAREA = "id_subarea_processo";

		/**
		 * Coluna com a data de modificação do registro em timestamp.
		 * <P>
		 * Type: INTEGER (long from System.curentTimeMillis())
		 * </P>
		 */
		public static final String COLUMN_NAME_DATA_MODIFICACAO = "modicacao_processo";

		/**
		 * Coluna que indica a versão de alteração/data de modificação no
		 * registro de marcação do questionário.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_MODIFICACAO_QUESTIONARIO = "modificacao_quest_processo";

		/**
		 * Coluna que indica o último editor do questionário de maturidade do
		 * processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_EDITOR_QUESTIONARIO = "editor_quest_processo";

		/**
		 * Coluna que indica se o questionário foi modificado e aguarda por
		 * sincronização. Apenas para uso da aplicação.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_SINCRONIZAR = "sincronizar_processo";
	}

	/**
	 * Padrões de nomes para a Tabela de Questões.
	 * 
	 * <p>
	 * <b>Observação:</b> Classe não pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class Questoes implements BaseColumns {

		// Esta classe não pode ser instanciada
		private Questoes() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "questao";

		// Definições do URI

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
		 * Caminho para o URI ID uma questao específica.
		 */
		private static final String PATH_ID = "/questoes/";

		/**
		 * Posição relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para uma simples questao. Chamador deve anexar um
		 * id numérico a este Uri para receber uma questao.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordenação padrão para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_questao ASC";

		// Definições das colunas da tabela questoes

		/**
		 * Coluna com o ID do questão.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID = "id_questao";

		/**
		 * Coluna com o nível da questão.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_NIVEL = "nivel_questao";

		/**
		 * Coluna com a descrição da questao.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_DESCRICAO = "descricao_questao";
	}

	/**
	 * Padrões de nomes para a Tabela de Questões de Processos.
	 * 
	 * <p>
	 * <b>Observação:</b> Classe não pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class ProcessoQuestoes implements BaseColumns {

		// Esta classe não pode ser instanciada
		private ProcessoQuestoes() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "processo_questao";

		// Definições do URI

		/**
		 * O scheme para a URI do <code>Provider</code>.
		 */
		private static final String SCHEME = "content://";

		// Caminhos para as URIs

		/**
		 * Caminho para o URI das questões dos processos.
		 */
		private static final String PATH = "/processo_questoes";

		/**
		 * Caminho para o URI ID uma questão de processo específica.
		 */
		private static final String PATH_ID = "/processo_questoes/";

		/**
		 * Posição relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para uma simples questão de processo. Chamador
		 * deve anexar um id numérico a este Uri para receber uma questão de
		 * processo.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordenação padrão para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_questao_processo_questao ASC";

		// Definições das colunas da tabela processo_questoes

		/**
		 * Coluna com o ID do processo.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_ID_PROCESSO = "id_processo_processo_questao";

		/**
		 * Coluna com o ID da questão.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ID_QUESTAO = "id_questao_processo_questao";

		/**
		 * Coluna que indica se a questão está marcada ou não.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_MARCADO = "marcado_processo_questao";
	}

	/**
	 * Padrões de nomes para a Tabela de Ações.
	 * 
	 * <p>
	 * <b>Observação:</b> Classe não pode ser instanciada.
	 * </p>
	 * 
	 * @see BaseColumns
	 */
	public static final class Acoes implements BaseColumns {

		// Esta classe não pode ser instanciada
		private Acoes() {
		}

		/**
		 * O nome da tabela usado no <code>ContentProvider</code>
		 * {@link DBProvider}.
		 */
		public static final String TABLE_NAME = "acao";

		// Definições do URI

		/**
		 * O scheme para a URI do <code>Provider</code>.
		 */
		private static final String SCHEME = "content://";

		// Caminhos para as URIs

		/**
		 * Caminho para o URI das ações.
		 */
		private static final String PATH = "/acoes";

		/**
		 * Caminho para o URI ID uma ação específica.
		 */
		private static final String PATH_ID = "/acoes/";

		/**
		 * Posição relativa do ID no URI.
		 */
		public static final int ID_PATH_POSITION = 1;

		/**
		 * O estilo de URI content:// para esta tabela.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
				+ PATH);

		/**
		 * O content URI base para uma simples ação. Chamador deve anexar um id
		 * numérico a este Uri para receber uma ação.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
				+ AUTHORITY + PATH_ID);

		/**
		 * A forma de ordenação padrão para esta tabela.
		 */
		public static final String DEFAULT_SORT_ORDER = "id_acao ASC";

		// Definições das colunas da tabela processo_questoes

		/**
		 * Coluna com o ID da ação.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_ID = "id_acao";

		/**
		 * Coluna com o nome da ação.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_NOME = "nome_acao";

		/**
		 * Coluna com o nome do responsável pela ação.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_RESPONSAVEL = "responsavel_acao";

		/**
		 * Coluna com a data de início da ação.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_DATA_INICIO = "data_inicio_acao";

		/**
		 * Coluna com a data prevista de término da ação.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_DATA_FIM = "data_fim_acao";

		/**
		 * Coluna com o custo estimado da ação.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_CUSTO = "custo_acao";

		/**
		 * Coluna com a informação de esforço (hora/pessoa) estimado para a
		 * execução da ação.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_ESFORCO = "esforco_acao";

		/**
		 * Coluna com o id do processo associado à ação.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_ID_PROCESSO = "id_processo_acao";

		/**
		 * Coluna com o flag que indica se a ação foi adotada pela empresa ou se
		 * é uma sugestão.
		 * <P>
		 * Type: Integer
		 * </P>
		 */
		public static final String COLUMN_NAME_ADOTADA = "adotada_acao";

		/**
		 * Coluna que indica se a ação foi modificada ou criada, e se aguarda
		 * por sincronização. Apenas para uso da aplicação.
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final String COLUMN_NAME_SINCRONIZAR = "sincronizar_acao";
	}
}