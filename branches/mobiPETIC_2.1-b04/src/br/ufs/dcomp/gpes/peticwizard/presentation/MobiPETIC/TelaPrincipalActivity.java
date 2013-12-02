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
 * Hospeda os <code>Fragments</code> da aplica��o.
 * <p>
 * Centraliza os c�digos de a��es do usu�rio e unifica menus, dialogos e barra
 * de a��es.
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
	 * Identifica��o do modo do menu padr�o a maioria das telas (n�o h� a op��o
	 * <i>An�lise de Maturidade</i>).
	 */
	public final static int MENU_MODE_PADRAO = 0;
	/**
	 * Identifica��o do modo do menu padr�o para a tela <i>Descri��o de
	 * Processo</i>.
	 */
	public final static int MENU_MODE_DESCRICAO_PROCESSO = 1;
	/**
	 * Identifica��o do modo do menu padr�o para a tela <i>An�lise de
	 * Maturidade</i>.
	 */
	public final static int MENU_MODE_MATURIDADE = 2;
	/**
	 * Identifica��o do modo do menu padr�o para a tela <i>Lista de A��es</i>.
	 */
	public final static int MENU_MODE_LISTA_ACOES = 3;

	/**
	 * Executado automaticamente no momento de cria��o da <code>Activity</code>.
	 * <p>
	 * Inicia as preferencias, o <code>Service</code> de atualiza��o de dados
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		preferencia = getSharedPreferences("PrefMobiPETIC", MODE_PRIVATE);
		localNomeUsuario = getIntent().getExtras().getString("nomeUsuario");

		if (findViewById(R.id.fragment_container_funcoes) != null) {
			if (savedInstanceState != null) { // Est� retornando ap�s uma longa
												// pausa ou troca de contexto de
												// visualiza��o.
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
												// dados dever�o ser totalmente
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
	 * Define o comportamenta das UI da barra de a��es.
	 * </p>
	 */
	@Override
	public void onResume() {
		super.onResume();
		Button home = (Button) findViewById(R.id.imageHome);
		home.setVisibility(View.INVISIBLE);
		home.setOnClickListener(new View.OnClickListener() { // Comportamento do
																// bot�o HOME
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
			public void onClick(View v) { // Comportamento do bot�o OP��ES
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
	 * Executado automaticamente quando a <code>Activity</code> � destruida.
	 * <p>
	 * Finaliza o <code>Service</code> de atualiza��o de dados.
	 * </p>
	 */
	@Override
	public void onDestroy() {
		if (isFinishing()) {
			// Suspendendo as atualiza��es de dados com o fim da aplica��o, caso
			// ainda n�o conclu�da por completo.
			Editor editor = preferencia.edit();
			editor.putBoolean("fim", true);
			editor.commit();
		}
		super.onDestroy();
	}

	/**
	 * Chamado no momento em que o pressiona o bot�o menu do aparelho durante a
	 * execu��o desta <code>Activity</code> (criar o menu).
	 * <p>
	 * Executado uma �nica vez.
	 * </p>
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_options, menu);

		return true;
	}

	/**
	 * Chamado sempre que pressiona o bot�o menu do aparelho durante a execu��o
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
	 * Chamado no momento da sele��o de um item do menu exibido a partir do
	 * bot�o menu do aparelho.
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
	 * Define o modo de visualiza��o do menu a ser exibido na tela.
	 * 
	 * @param mode
	 *            C�digo do mode. (Modos:
	 *            {@link TelaPrincipalActivity.MENU_MODE_PADRAO},
	 *            {@link TelaPrincipalActivity.MENU_MODE_DESCRICAO_PROCESSO})
	 */
	public void setMenuMode(int mode) {
		menuMode = mode;
	}

	/**
	 * Transmite a a��o de atualizar a imagem da logo do <code>Fragment</code>
	 * <i>Fun��es principais</i> ao escopo da <code>Activity</code>.
	 */
	public void onCarregarLogoEmpresa() {
		ImageView img_logo = (ImageView) findViewById(R.id.imageViewCompanyLogo);
		img_logo.setImageBitmap(BitmapFactory.decodeByteArray(getIntent()
				.getExtras().getByteArray("logo"), 0, getIntent().getExtras()
				.getInt("logoLength")));
	}

	/**
	 * A��o decorrente ao clique no bot�o <i>Cat�logos</i> no
	 * <code>Fragment</code> <i>Fun��es Principais</i>.
	 */
	public void onButtonCatalogoClick() {
		ListaAreasFragment listaAreas = new ListaAreasFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
			.beginTransaction();
		
		if (findViewById(R.id.fragment_container_catalogo) != null) { // Se
		// existir este container de fragments (isso se o cat�logo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas a��es aqui;
			transaction.replace(R.id.fragment_container_catalogo, listaAreas);
			if (fragDetalhes != null)
				transaction.remove(fragDetalhes);
		} else // � um handset, o novo fragment ser� inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, listaAreas);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * A��o decorrente ao clique no bot�o <i>A��es</i> no <code>Fragment</code>
	 * <i>Fun��es Principais</i>.
	 */
	public void onButtonAcoesClick() {
		// if (findViewById(R.id.fragment_container_catalogo != null)) { // Se
		// existir este container de fragments (isso se o cat�logo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas a��es aqui;
		// } else { // � um handset, o novo fragment ser� inserido no
		// fragment_container_funcoes

		// }
	}

	/**
	 * A��o decorrente � sele��o de uma �rea no <code>Fragment</code> <i>Lista
	 * de �reas</i>.
	 * 
	 * @param idArea
	 *            String c�digo da �rea selecionada.
	 * @param nomeArea
	 *            T�tulo da �rea selecionada.
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
		// existir este container de fragments (isso se o cat�logo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas a��es aqui;
			transaction.replace(R.id.fragment_container_catalogo, listaSubareas);
		} else // � um handset, o novo fragment ser� inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, listaSubareas);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * A��o decorrente � sele��o de uma sub�rea no <code>Fragment</code>
	 * <i>Lista de Sub�reas</i>.
	 * 
	 * @param idSubarea
	 *            String c�digo da sub�rea selecionada (no formato: num_area + .
	 *            + num_subarea).
	 * @param nomeSubarea
	 *            T�tulo da sub�rea selecionada.
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
		// existir este container de fragments (isso se o cat�logo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas a��es aqui;
			transaction.replace(R.id.fragment_container_catalogo, listaProcessos);
		} else // � um handset, o novo fragment ser� inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, listaProcessos);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * A��o decorrente � sele��o de um processo no <code>Fragment</code>
	 * <i>Lista de Processos</i>.
	 * 
	 * @param idProcesso
	 *            String c�digo do processo selecionado (no formato: num_area +
	 *            . + num_subarea + . + num_processo).
	 * @param nomeProcesso
	 *            T�tulo do processo selecionado.
	 * @param maturidadeAnt
	 *            Grau de maturidade definido para o processo selecionado no
	 *            <i>Artefato PETIC</i> anterior ao corrente (inicia o atributo
	 *            presente neste escopo para ser usado na tela de <i>An�lise de
	 *            Maturidade</i>).
	 * @param editor
	 *            Nome do �ltimo usu�rio a ter editado o processo selecionado
	 *            (inicia o atributo presente neste escopo para ser usado na
	 *            tela de <i>An�lise de Maturidade</i>).
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
		// existir este container de fragments (isso se o cat�logo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas a��es aqui;
			transaction.replace(R.id.fragment_container_detalhes, descProcesso);
			fragDetalhes = descProcesso;
		} else // � um handset, o novo fragment ser� inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, descProcesso);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * A��o decorrente � solicita��o de exibi��o da tela de <i>An�lise de
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
		// existir este container de fragments (isso se o cat�logo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas a��es aqui;
			transaction.replace(R.id.fragment_container_detalhes, analiseMaturidade);
			fragDetalhes = analiseMaturidade;
		} else // � um handset, o novo fragment ser� inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, analiseMaturidade);
		transaction.addToBackStack(null);

		transaction.commit();
		// }
	}

	/**
	 * A��o decorrente � sele��o de visualiza��o das op��es de um processo.
	 * 
	 * @param menu
	 *            Menu que exibir� as op��es.
	 * @param idProcesso
	 *            String c�digo do processo selecionado (no formato: num_area +
	 *            . + num_subarea + . + num_processo).
	 * @param nomeProcesso
	 *            T�tulo do processo selecionado.
	 * @param maturidadeAnt
	 *            Grau de maturidade definido para o processo selecionado no
	 *            <i>Artefato PETIC</i> anterior ao corrente (inicia o atributo
	 *            presente neste escopo para ser usado na tela de <i>An�lise de
	 *            Maturidade</i>).
	 * @param editor
	 *            Nome do �ltimo usu�rio a ter editado o processo selecionado
	 *            (inicia o atributo presente neste escopo para ser usado na
	 *            tela de <i>An�lise de Maturidade</i>).
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
	 * Chamado no momento da sele��o de um item do menu de um processo de TIC.
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
		// existir este container de fragments (isso se o cat�logo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas a��es aqui;
			transaction.replace(R.id.fragment_container_detalhes, editorAcao);
			fragDetalhes = editorAcao;
		} else // � um handset, o novo fragment ser� inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, editorAcao);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * A��o decorrente � sele��o da op��o <i>Analisar Maturidade</i> no dialogo
	 * de op��es.
	 */
	public void onMenuOpcoesAnalisarMaturidadeClick() {
		onAnaliseMaturidadeSelected();
	}

	/**
	 * A��o decorrente � sele��o da op��o <i>Sincronizar Modifica��es</i> no
	 * dialogo de op��es.
	 */
	public void onMenuOpcoesSincronizarClick() {
		Intent intentSincronizacao = new Intent(this, Sincronizador.class);
		startService(intentSincronizacao);
	}

	/**
	 * A��o decorrente � sele��o da op��o <i>Sobre</i> no dialogo de op��es.
	 */
	public void onMenuOpcoesSobreClick() {
		// TODO Auto-generated method stub

	}

	/**
	 * A��o decorrente � sele��o da op��o <i>Sair</i> no dialogo de op��es.
	 */
	public void onMenuOpcoesLogoutClick() {
		Intent intentSair = new Intent(this, LoginActivity.class);
		intentSair.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intentSair);
	}

	/**
	 * A��o decorrente � sele��o de uma a��o de melhoria na tela <i>Lista de
	 * A��es de Melhoria</i> de um processo.
	 * 
	 * @param Identificador
	 *            da a��o selecionada.
	 * @param Nome
	 *            da a��o selecionada.
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
		// existir este container de fragments (isso se o cat�logo tiver de ser
		// aberto em outro container)
		// trata-se de um tablet
		// fazer as devidas a��es aqui;
			transaction.replace(R.id.fragment_container_detalhes, listaAcoes);
			fragDetalhes = listaAcoes;
		} else // � um handset, o novo fragment ser� inserido no
		// fragment_container_funcoes
			transaction.replace(R.id.fragment_container_funcoes, listaAcoes);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * A��o decorrente � sele��o de visualiza��o das op��es de uma acao,
	 * sugerida ou adotada.
	 * 
	 * @param menu
	 *            Menu que exibir� as op��es.
	 * @param idAcao
	 *            String c�digo da a��o selecionada.
	 * @param nomeAcao
	 *            T�tulo da a��o selecionada.
	 * @param adotada
	 *            flag que indica se a a��o pertence � empresa ou � uma
	 *            sugest�o.
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