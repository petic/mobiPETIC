package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Hospeda os <code>Fragments</code> da aplicação.
 * <p>
 * Centraliza os códigos de ações do usuário e unifica menus, dialogos e barra
 * de ações.
 * </p>
 * 
 * @see FragmentActivity
 */
public class TelaPrincipalActivity extends FragmentActivity implements
		FuncoesPrincipaisFragment.OnFuncoesPrincipaisClickListener,
		ListaAreasFragment.OnListaAreasItemClickListener,
		ListaSubareasFragment.OnListaSubareasItemClickListener,
		ListaProcessosFragment.OnListaProcessosItemClickListener,
		OpcoesDialogFragment.OnOpcoesDialogItemClickListener,
		ListaAcoesMelhoriaFragment.OnListaAcoesMelhoriaItemClickListener {
	private Fragment fragDetalhes;
	
	private Intent service;
	private SharedPreferences preferencia;

	private String localNomeUsuario;
	private String localNomeArea;
	private String localNomeSubarea;
	private String localNomeProcesso;
	private String localIdProcesso;
	private String localIdAcao;
	private String localEditor;
	private int localMaturidadeAnt;

	private int menuMode;

	/**
	 * Identificação do modo do menu padrão a maioria das telas (não há a opção
	 * <i>Análise de Maturidade</i>).
	 */
	public final static int MENU_MODE_PADRAO = 0;
	/**
	 * Identificação do modo do menu padrão para a tela <i>Descrição de
	 * Processo</i>.
	 */
	public final static int MENU_MODE_DESCRICAO_PROCESSO = 1;
	/**
	 * Identificação do modo do menu padrão para a tela <i>Análise de
	 * Maturidade</i>.
	 */
	public final static int MENU_MODE_MATURIDADE = 2;
	/**
	 * Identificação do modo do menu padrão para a tela <i>Lista de Ações</i>.
	 */
	public final static int MENU_MODE_LISTA_ACOES = 3;

	/**
	 * Executado automaticamente no momento de criação da <code>Activity</code>.
	 * <p>
	 * Inicia as preferencias, o <code>Service</code> de atualização de dados
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		preferencia = getSharedPreferences("PrefMobiPETIC", MODE_PRIVATE);
		localNomeUsuario = getIntent().getExtras().getString("nomeUsuario");

		if (findViewById(R.id.fragment_container_funcoes) != null) {
			if (savedInstanceState != null) { // Está retornando após uma longa
												// pausa ou troca de contexto de
												// visualização.
				getIntent().getExtras().putInt("logoLength",
						savedInstanceState.getInt("logoLength"));
				getIntent().getExtras().putByteArray("logo",
						savedInstanceState.getByteArray("logo"));
				localNomeUsuario = savedInstanceState.getString("nomeUsuario");
				localNomeArea = savedInstanceState.getString("nomeArea");
				localNomeSubarea = savedInstanceState.getString("nomeSubarea");
				localNomeProcesso = savedInstanceState
						.getString("nomeProcesso");
				localIdProcesso = savedInstanceState.getString("idProcesso");
				localIdAcao = savedInstanceState.getString("idAcao");
				localEditor = savedInstanceState.getString("nomeEditor");
				localMaturidadeAnt = savedInstanceState.getInt("maturidadeAnt");

				menuMode = savedInstanceState.getInt("menuMode");
				return; // A tela deve ser preservada.
			}

			FuncoesPrincipaisFragment funcoesPrincipais = new FuncoesPrincipaisFragment();

			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container_funcoes, funcoesPrincipais)
					.commit();
		}

		// Compartilhando a empresa logada entre as Ativities e Services
		Editor editor = preferencia.edit();
		if (getIntent().getExtras().getInt("idEmpresa") != preferencia.getInt(
				"idEmpresa", 0))
			editor.putLong("alteradoEm", -1); // caso o aparelho esteja sendo
												// usado para visualizar a conta
												// de
												// uma empresa diferente, os
												// dados deverão ser totalmente
												// atualizados
		editor.putInt("idEmpresa", getIntent().getExtras().getInt("idEmpresa"));
		editor.putBoolean("fim", false);
		editor.commit();

		service = new Intent(this, DownloadArquivos.class);
		startService(service);
	}

	/**
	 * Executado automaticamente com a retomada de foco da <code>Activity</code>
	 * .
	 * <p>
	 * Define o comportamenta das UI da barra de ações.
	 * </p>
	 */
	@Override
	public void onResume() {
		super.onResume();
		Button home = (Button) findViewById(R.id.imageHome);
		home.setVisibility(View.INVISIBLE);
		home.setOnClickListener(new View.OnClickListener() { // Comportamento do
																// botão HOME
			public void onClick(View v) {
				if (findViewById(R.id.fragment_container_catalogo) == null) {
					FragmentManager fm = getSupportFragmentManager();
					// Apagando o back stack de Fragments
					for (int k = 0; k < fm.getBackStackEntryCount(); k++) {
						fm.popBackStack();
					}
				}
			}
		});

		Button opcoes = (Button) findViewById(R.id.imageOptions);
		opcoes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { // Comportamento do botão OPÇÕES
				OpcoesDialogFragment opcoesFragment = new OpcoesDialogFragment();
				Bundle args = new Bundle();
				args.putInt("menuMode", menuMode);
				opcoesFragment.setArguments(args);
				opcoesFragment.show(getSupportFragmentManager(), "menuOpcoes");
			}
		});
	}

	/**
	 * Executado automaticamente quando se precisa salvar os dados da
	 * <code>Activity</code> para preservar seu estado.
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("logoLength",
				getIntent().getExtras().getInt("logoLength"));
		outState.putByteArray("logo",
				getIntent().getExtras().getByteArray("logo"));
		outState.putString("nomeUsuario", localNomeUsuario);
		outState.putString("nomeArea", localNomeArea);
		outState.putString("nomeSubarea", localNomeSubarea);
		outState.putString("nomeProcesso", localNomeProcesso);
		outState.putString("idProcesso", localIdProcesso);
		outState.putString("idAcao", localIdAcao);
		outState.putString("nomeEditor", localEditor);
		outState.putInt("maturidadeAnt", localMaturidadeAnt);
		outState.putInt("menuMode", menuMode);
		super.onSaveInstanceState(outState);
	}

	/**
	 * Executado automaticamente quando a <code>Activity</code> é destruida.
	 * <p>
	 * Finaliza o <code>Service</code> de atualização de dados.
	 * </p>
	 */
	@Override
	public void onDestroy() {
		if (isFinishing()) {
			// Suspendendo as atualizações de dados com o fim da aplicação, caso
			// ainda não concluída por completo.
			Editor editor = preferencia.edit();
			editor.putBoolean("fim", true);
			editor.commit();
		}
		super.onDestroy();
	}

	/**
	 * Chamado no momento em que o pressiona o botão menu do aparelho durante a
	 * execução desta <code>Activity</code> (criar o menu).
	 * <p>
	 * Executado uma única vez.
	 * </p>
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_options, menu);

		return true;
	}

	/**
	 * Chamado sempre que pressiona o botão menu do aparelho durante a execução
	 * desta <code>Activity</code> (preparar o menu).
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.item_option_maturidade).setVisible(
				menuMode == MENU_MODE_DESCRICAO_PROCESSO
						|| menuMode == MENU_MODE_LISTA_ACOES);
		menu.findItem(R.id.item_option_acoes).setVisible(
				menuMode == MENU_MODE_DESCRICAO_PROCESSO
						|| menuMode == MENU_MODE_MATURIDADE);
		menu.findItem(R.id.item_criar_acao).setVisible(
				menuMode == MENU_MODE_LISTA_ACOES);
		menu.findItem(R.id.item_sincronizar).setVisible(true);
		menu.findItem(R.id.item_sobre).setVisible(true);
		menu.findItem(R.id.item_logout).setVisible(true);

		Intent intent = new Intent().addCategory(Intent.CATEGORY_ALTERNATIVE);
		menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
				new ComponentName(this, TelaPrincipalActivity.class), null,
				intent, 0, null);
		return true;
	}

	/**
	 * Chamado no momento da seleção de um item do menu exibido a partir do
	 * botão menu do aparelho.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_sincronizar:
			onMenuOpcoesSincronizarClick();
			return true;
		case R.id.item_option_maturidade:
			onMenuOpcoesAnalisarMaturidadeClick();
			return true;
		case R.id.item_option_acoes:
			onMenuOpcoesAcoesClick();
			return true;
		case R.id.item_criar_acao:
			onMenuOpcoesCriarAcaoClick();
			return true;
		case R.id.item_sobre:
			onMenuOpcoesSobreClick();
			return true;
		case R.id.item_logout:
			onMenuOpcoesLogoutClick();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onMenuOpcoesCriarAcaoClick() {
		localIdAcao = "";
		onEditarAcao(true);
	}

	/**
	 * Define o modo de visualização do menu a ser exibido na tela.
	 * 
	 * @param mode
	 *            Código do mode. (Modos:
	 *            {@link TelaPrincipalActivity.MENU_MODE_PADRAO},
	 *            {@link TelaPrincipalActivity.MENU_MODE_DESCRICAO_PROCESSO})
	 */
	public void setMenuMode(int mode) {
		menuMode = mode;
	}

	/**
	 * Transmite a ação de atualizar a imagem da logo do <code>Fragment</code>
	 * <i>Funções principais</i> ao escopo da <code>Activity</code>.
	 */
	public void onCarregarLogoEmpresa() {
		ImageView img_logo = (ImageView) findViewById(R.id.imageViewCompanyLogo);
		img_logo.setImageBitmap(BitmapFactory.decodeByteArray(getIntent()
				.getExtras().getByteArray("logo"), 0, getIntent().getExtras()
				.getInt("logoLength")));
	}

	/**
	 * Ação decorrente ao clique no botão <i>Catálogos</i> no
	 * <code>Fragment</code> <i>Funções Principais</i>.
	 */
	public void onButtonCatalogoClick() {
		ListaAreasFragment listaAreas = new ListaAreasFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
			.beginTransaction();
		
		if (findViewById(R.id.fragment_container_catalogo) != null) { // Se
		// existir este container de fragments (isso se o catálogo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas ações aqui;
			transaction.replace(R.id.fragment_container_catalogo, listaAreas);
			if (fragDetalhes != null)
				transaction.remove(fragDetalhes);
		} else // é um handset, o novo fragment será inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, listaAreas);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * Ação decorrente ao clique no botão <i>Ações</i> no <code>Fragment</code>
	 * <i>Funções Principais</i>.
	 */
	public void onButtonAcoesClick() {
		// if (findViewById(R.id.fragment_container_catalogo != null)) { // Se
		// existir este container de fragments (isso se o catálogo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas ações aqui;
		// } else { // é um handset, o novo fragment será inserido no
		// fragment_container_funcoes

		// }
	}

	/**
	 * Ação decorrente à seleção de uma área no <code>Fragment</code> <i>Lista
	 * de Áreas</i>.
	 * 
	 * @param idArea
	 *            String código da área selecionada.
	 * @param nomeArea
	 *            Título da área selecionada.
	 */
	public void onAreaClick(String idArea, String nomeArea) {
		localNomeArea = nomeArea;
		ListaSubareasFragment listaSubareas = new ListaSubareasFragment();
		Bundle args = new Bundle();
		args.putString("idArea", idArea);
		args.putString("nomeArea", nomeArea);
		listaSubareas.setArguments(args);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		
		if (findViewById(R.id.fragment_container_catalogo) != null) { // Se
		// existir este container de fragments (isso se o catálogo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas ações aqui;
			transaction.replace(R.id.fragment_container_catalogo, listaSubareas);
		} else // é um handset, o novo fragment será inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, listaSubareas);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * Ação decorrente à seleção de uma subárea no <code>Fragment</code>
	 * <i>Lista de Subáreas</i>.
	 * 
	 * @param idSubarea
	 *            String código da subárea selecionada (no formato: num_area + .
	 *            + num_subarea).
	 * @param nomeSubarea
	 *            Título da subárea selecionada.
	 */
	public void onSubareaClick(String idSubarea, String nomeSubarea) {
		localNomeSubarea = nomeSubarea;
		ListaProcessosFragment listaProcessos = new ListaProcessosFragment();
		Bundle args = new Bundle();
		args.putString("idSubarea", idSubarea);
		args.putString("nomeSubarea", nomeSubarea);
		args.putString("nomeArea", localNomeArea);
		listaProcessos.setArguments(args);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		
		if (findViewById(R.id.fragment_container_catalogo) != null) { // Se
		// existir este container de fragments (isso se o catálogo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas ações aqui;
			transaction.replace(R.id.fragment_container_catalogo, listaProcessos);
		} else // é um handset, o novo fragment será inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, listaProcessos);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * Ação decorrente à seleção de um processo no <code>Fragment</code>
	 * <i>Lista de Processos</i>.
	 * 
	 * @param idProcesso
	 *            String código do processo selecionado (no formato: num_area +
	 *            . + num_subarea + . + num_processo).
	 * @param nomeProcesso
	 *            Título do processo selecionado.
	 * @param maturidadeAnt
	 *            Grau de maturidade definido para o processo selecionado no
	 *            <i>Artefato PETIC</i> anterior ao corrente (inicia o atributo
	 *            presente neste escopo para ser usado na tela de <i>Análise de
	 *            Maturidade</i>).
	 * @param editor
	 *            Nome do último usuário a ter editado o processo selecionado
	 *            (inicia o atributo presente neste escopo para ser usado na
	 *            tela de <i>Análise de Maturidade</i>).
	 */
	public void onProcessoClick(String idProcesso, String nomeProcesso,
			int maturidadeAnt, String editor) {
		localIdProcesso = idProcesso;
		localNomeProcesso = nomeProcesso;
		localMaturidadeAnt = maturidadeAnt;
		localEditor = editor;
		
		DescricaoProcessoFragment descProcesso = new DescricaoProcessoFragment();
		Bundle args = new Bundle();
		args.putString("idProcesso", idProcesso);
		args.putString("nomeProcesso", nomeProcesso);
		args.putString("nomeArea", localNomeArea);
		args.putString("nomeSubarea", localNomeSubarea);
		descProcesso.setArguments(args);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		
		if (findViewById(R.id.fragment_container_catalogo) != null) { // Se
		// existir este container de fragments (isso se o catálogo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas ações aqui;
			transaction.replace(R.id.fragment_container_detalhes, descProcesso);
			fragDetalhes = descProcesso;
		} else // é um handset, o novo fragment será inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, descProcesso);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * Ação decorrente à solicitação de exibição da tela de <i>Análise de
	 * Maturidade</i> de um processo.
	 */
	private void onAnaliseMaturidadeSelected() {
		AnaliseMaturidadeFragment analiseMaturidade = new AnaliseMaturidadeFragment();
		Bundle args = new Bundle();
		args.putString("idProcesso", localIdProcesso);
		args.putString("nomeProcesso", localNomeProcesso);
		args.putString("nomeArea", localNomeArea);
		args.putString("nomeSubarea", localNomeSubarea);
		args.putString("editor", localEditor);
		args.putString("nomeUsuario", localNomeUsuario);
		args.putInt("maturidadeAnt", localMaturidadeAnt);
		analiseMaturidade.setArguments(args);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		
		if (findViewById(R.id.fragment_container_catalogo) != null) { // Se
		// existir este container de fragments (isso se o catálogo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas ações aqui;
			transaction.replace(R.id.fragment_container_detalhes, analiseMaturidade);
			fragDetalhes = analiseMaturidade;
		} else // é um handset, o novo fragment será inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, analiseMaturidade);
		transaction.addToBackStack(null);

		transaction.commit();
		// }
	}

	/**
	 * Ação decorrente à seleção de visualização das opções de um processo.
	 * 
	 * @param menu
	 *            Menu que exibirá as opções.
	 * @param idProcesso
	 *            String código do processo selecionado (no formato: num_area +
	 *            . + num_subarea + . + num_processo).
	 * @param nomeProcesso
	 *            Título do processo selecionado.
	 * @param maturidadeAnt
	 *            Grau de maturidade definido para o processo selecionado no
	 *            <i>Artefato PETIC</i> anterior ao corrente (inicia o atributo
	 *            presente neste escopo para ser usado na tela de <i>Análise de
	 *            Maturidade</i>).
	 * @param editor
	 *            Nome do último usuário a ter editado o processo selecionado
	 *            (inicia o atributo presente neste escopo para ser usado na
	 *            tela de <i>Análise de Maturidade</i>).
	 */
	public void onProcessoLongClick(ContextMenu menu, String idProcesso,
			String nomeProcesso, int maturidadeAnt, String editor) {
		localIdProcesso = idProcesso;
		localNomeProcesso = nomeProcesso;
		localMaturidadeAnt = maturidadeAnt;
		localEditor = editor;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_processo, menu);
		menu.setHeaderTitle(nomeProcesso);

		Intent intent = new Intent();
		intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
				new ComponentName(this, TelaPrincipalActivity.class), null,
				intent, 0, null);
	}

	/**
	 * Chamado no momento da seleção de um item do menu de um processo de TIC.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_descricao:
			onProcessoClick(localIdProcesso, localNomeProcesso,
					localMaturidadeAnt, localEditor);
			return true;
		case R.id.item_maturidade:
			onAnaliseMaturidadeSelected();
			return true;
		case R.id.item_acoes:
			onMenuOpcoesAcoesClick();
			return true;
		case R.id.item_editar_acao:
			onEditarAcao(false);
			return true;
		case R.id.item_adicionar_acao:
			onAdicionarAcao();
			return true;
		case R.id.item_remover_acao:
			onRemoverAcao();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void onRemoverAcao() {
		ContentValues values = new ContentValues();
		values.put(DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA, 0);
		values.put(DBDefinicoes.Acoes.COLUMN_NAME_SINCRONIZAR, 1);
		getContentResolver().update(DBDefinicoes.Acoes.CONTENT_URI, values,
				DBDefinicoes.Acoes._ID + " = ?", new String[] { localIdAcao });
	}

	private void onAdicionarAcao() {
		ContentValues values = new ContentValues();
		values.put(DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA, 1);
		values.put(DBDefinicoes.Acoes.COLUMN_NAME_SINCRONIZAR, 1);
		getContentResolver().update(DBDefinicoes.Acoes.CONTENT_URI, values,
				DBDefinicoes.Acoes._ID + " = ?", new String[] { localIdAcao });
	}

	private void onEditarAcao(boolean criar) {
		EditorAcaoFragment editorAcao = new EditorAcaoFragment();
		Bundle args = new Bundle();
		args.putString("idProcesso", localIdProcesso);
		args.putString("nomeProcesso", localNomeProcesso);
		args.putString("nomeArea", localNomeArea);
		args.putString("nomeSubarea", localNomeSubarea);
		args.putString("idAcao", localIdAcao);
		args.putBoolean("criarAcao", criar);
		editorAcao.setArguments(args);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		
		if (findViewById(R.id.fragment_container_catalogo) != null) { // Se
		// existir este container de fragments (isso se o catálogo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas ações aqui;
			transaction.replace(R.id.fragment_container_detalhes, editorAcao);
			fragDetalhes = editorAcao;
		} else // é um handset, o novo fragment será inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, editorAcao);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * Ação decorrente à seleção da opção <i>Analisar Maturidade</i> no dialogo
	 * de opções.
	 */
	public void onMenuOpcoesAnalisarMaturidadeClick() {
		onAnaliseMaturidadeSelected();
	}

	/**
	 * Ação decorrente à seleção da opção <i>Sincronizar Modificações</i> no
	 * dialogo de opções.
	 */
	public void onMenuOpcoesSincronizarClick() {
		Intent intentSincronizacao = new Intent(this, Sincronizador.class);
		startService(intentSincronizacao);
	}

	/**
	 * Ação decorrente à seleção da opção <i>Sobre</i> no dialogo de opções.
	 */
	public void onMenuOpcoesSobreClick() {
		// TODO Auto-generated method stub

	}

	/**
	 * Ação decorrente à seleção da opção <i>Sair</i> no dialogo de opções.
	 */
	public void onMenuOpcoesLogoutClick() {
		Intent intentSair = new Intent(this, LoginActivity.class);
		intentSair.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intentSair);
	}

	/**
	 * Ação decorrente à seleção de uma ação de melhoria na tela <i>Lista de
	 * Ações de Melhoria</i> de um processo.
	 * 
	 * @param Identificador
	 *            da ação selecionada.
	 * @param Nome
	 *            da ação selecionada.
	 */
	public void onAcaoAdotadaClick(String idAcao, String nomeAcao) {
		localIdAcao = idAcao;
		onEditarAcao(false);
	}

	public void onMenuOpcoesAcoesClick() {
		ListaAcoesMelhoriaFragment listaAcoes = new ListaAcoesMelhoriaFragment();
		Bundle args = new Bundle();
		args.putString("idProcesso", localIdProcesso);
		args.putString("nomeProcesso", localNomeProcesso);
		args.putString("nomeArea", localNomeArea);
		args.putString("nomeSubarea", localNomeSubarea);
		listaAcoes.setArguments(args);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		
		if (findViewById(R.id.fragment_container_catalogo) != null) { // Se
		// existir este container de fragments (isso se o catálogo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas ações aqui;
			transaction.replace(R.id.fragment_container_detalhes, listaAcoes);
			fragDetalhes = listaAcoes;
		} else // é um handset, o novo fragment será inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, listaAcoes);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * Ação decorrente à seleção de visualização das opções de uma acao,
	 * sugerida ou adotada.
	 * 
	 * @param menu
	 *            Menu que exibirá as opções.
	 * @param idAcao
	 *            String código da ação selecionada.
	 * @param nomeAcao
	 *            Título da ação selecionada.
	 * @param adotada
	 *            flag que indica se a ação pertence à empresa ou é uma
	 *            sugestão.
	 */
	public void onAcaoLongClick(ContextMenu menu, String idAcao,
			String nomeAcao, boolean adotada) {
		localIdAcao = idAcao;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_acao, menu);
		menu.setHeaderTitle(nomeAcao);
		menu.findItem(R.id.item_adicionar_acao).setVisible(!adotada);
		menu.findItem(R.id.item_editar_acao).setVisible(adotada);
		menu.findItem(R.id.item_remover_acao).setVisible(adotada);

		Intent intent = new Intent();
		intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
				new ComponentName(this, TelaPrincipalActivity.class), null,
				intent, 0, null);
	}

	public void onAcaoSugeridaClick(String idAcao, String nomeAcao) {
		// TODO Auto-generated method stub
	}
}