package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

/**
 * 
 * @deprecated
 * 
 */
@Deprecated
public class SmartTablet {

	private String[] projecaoArea = { DBDefinicoes.Areas._ID,
			DBDefinicoes.Areas.COLUMN_NAME_ID,
			DBDefinicoes.Areas.COLUMN_NAME_NOME };

	private String[] projecaoSubArea = { DBDefinicoes.Subareas._ID,
			DBDefinicoes.Subareas.COLUMN_NAME_ID,
			DBDefinicoes.Subareas.COLUMN_NAME_NOME,
			DBDefinicoes.Subareas.COLUMN_NAME_ID_AREA };

	private String[] projecaoProcesso = { DBDefinicoes.Processos._ID,
			DBDefinicoes.Processos.COLUMN_NAME_ID,
			DBDefinicoes.Processos.COLUMN_NAME_NOME,
			DBDefinicoes.Processos.COLUMN_NAME_ID_SUBAREA };

	private String[] projecaoDescricaoProcesso = { DBDefinicoes.Processos._ID, // 0
			DBDefinicoes.Processos.COLUMN_NAME_ID, // 1
			DBDefinicoes.Processos.COLUMN_NAME_NOME, // 2
			DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE, // 3
			DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT, // 4
			DBDefinicoes.Processos.COLUMN_NAME_PRIORITARIO, // 5
			DBDefinicoes.Processos.COLUMN_NAME_PRIORIDADE_DEF, // 6
			DBDefinicoes.Processos.COLUMN_NAME_DESCRICAO, // 7
			DBDefinicoes.Processos.COLUMN_NAME_ENTRADAS, // 8
			DBDefinicoes.Processos.COLUMN_NAME_SAIDAS, // 9
			DBDefinicoes.Processos.COLUMN_NAME_RESPONSAVEL, // 10
			DBDefinicoes.Processos.COLUMN_NAME_STAKEHOLDERS, // 11
			DBDefinicoes.Processos.COLUMN_NAME_ID_SUBAREA }; // 12

	// constantes para a criação do AlertDialog de opções das telas da aplicação
	public static final int OPCAO_ANALISAR_MATURIDADE = 0;
	public static final int OPCAO_SINCRONIZAR = 1;
	public static final int OPCAO_SOBRE = 2;
	public static final int OPCAO_LOGOUT = 3;

	public SmartTablet() {
	}

	public SimpleCursorAdapter listViewArea(Context contexto) {
		// Adquirindo dados do banco
		Cursor cursor = contexto.getContentResolver().query(
				DBDefinicoes.Areas.CONTENT_URI, projecaoArea, null, null,
				DBDefinicoes.Areas.DEFAULT_SORT_ORDER);

		// Colunas que serão exibidas
		String[] dataColumns = { DBDefinicoes.Areas.COLUMN_NAME_ID,
				DBDefinicoes.Areas.COLUMN_NAME_NOME };
		// Views que exibirão as colunas
		int[] viewIDs = { R.id.txt_id, R.id.txt_nome };
		// Preparando o adapter
		return new SimpleCursorAdapter(contexto, R.layout.list_itens, cursor,
				dataColumns, viewIDs);
	}

	// RETORNA UM ADAPTER PARA CONSTRUIR O LISTVIEW DA SUBAREA, É CHAMADO QUANDO
	// OCORRE
	// ALGUM CLIQUE NO LISTVIEW DA AREA
	// RECEBE O ID DA AREA (cursor.getInt(1)) E O CONTEXTO DA APLICAÇÃO
	// (getContextAplication())
	public SimpleCursorAdapter listViewSubArea(String idArea, Context contexto) {

		String[] valoresSelecao = { idArea + "%" };
		// Adquirindo dados do banco
		Cursor cursor = contexto.getContentResolver().query(
				DBDefinicoes.Subareas.CONTENT_URI, projecaoSubArea,
				DBDefinicoes.Subareas.COLUMN_NAME_ID + " LIKE ?",
				valoresSelecao, DBDefinicoes.Subareas.DEFAULT_SORT_ORDER);
		// Colunas que serão exebidas
		String[] dataColumns = { DBDefinicoes.Subareas.COLUMN_NAME_ID,
				DBDefinicoes.Subareas.COLUMN_NAME_NOME };
		// Views que exibirão as colunas
		int[] viewIDs = { R.id.txt_id, R.id.txt_nome };
		// Preparando o adapter
		return new SimpleCursorAdapter(contexto, R.layout.list_itens, cursor,
				dataColumns, viewIDs);
	}

	// RETORNA UM ADAPTER PARA CONSTRUIR O LISTVIEW DO PROCESSO, É CHAMADO
	// QUANDO OCORRE
	// ALGUM CLIQUE NO LISTVIEW DA SUBAREA
	// RECEBE O ID DA SUBAREA(cursor.getInt(1)) E O CONTEXTO DA APLICAÇÃO
	// (getContextAplication())
	public SimpleCursorAdapter listViewProcesso(String idSubArea,
			Context contexto) {
		String[] valoresSelecao = { idSubArea + "%" };
		// Adquirindo dados do banco
		Cursor cursor = contexto.getContentResolver().query(
				DBDefinicoes.Processos.CONTENT_URI, projecaoProcesso,
				DBDefinicoes.Processos.COLUMN_NAME_ID + " LIKE ?",
				valoresSelecao, DBDefinicoes.Processos.DEFAULT_SORT_ORDER);
		// Colunas que serão exebidas
		String[] dataColumns = { DBDefinicoes.Processos.COLUMN_NAME_ID,
				DBDefinicoes.Processos.COLUMN_NAME_NOME };
		// Views que exibirão as colunas
		int[] viewIDs = { R.id.txt_id, R.id.txt_nome };
		// Preparando o adapter
		return new SimpleCursorAdapter(contexto, R.layout.list_itens, cursor,
				dataColumns, viewIDs);
	}

	// RETORNA UM VETOR DE STRING COM OS DADOS PARA CONSTRUIR A TELA DE
	// DESCRIÇÃO DO PROCESSO
	// É CHAMADO QUANDO OCORRE ALGUM CLIQUE NO LISTVIEW DO PROCESSO
	// RECEBE O ID DO PROCESSO(cursor.getInt(0)) E O CONTEXTO DA APLICAÇÃO
	// (getContextAplication())

	public String[] dadosDescricaoProcesso(String idProcesso, Context contexto) {
		String tituloProcesso = null, descricaoProcesso = null, descricaoEntrada = null, descricaoSaida = null, descricaoResponsavel = null, descricaoStakeholders = null;
		String[] valoresSelecao = { idProcesso };
		Cursor cursor = contexto.getContentResolver().query(
				DBDefinicoes.Processos.CONTENT_URI, projecaoDescricaoProcesso,
				DBDefinicoes.Processos.COLUMN_NAME_ID + " = ?", valoresSelecao,
				null);
		if (cursor.moveToNext()) {
			tituloProcesso = cursor.getString(2);
			descricaoProcesso = cursor.getString(7);
			descricaoEntrada = cursor.getString(8);
			descricaoSaida = cursor.getString(9);
			descricaoResponsavel = cursor.getString(10);
			descricaoStakeholders = cursor.getString(11);
		}
		String[] dados = { tituloProcesso, // 0
				descricaoProcesso, // 1
				descricaoEntrada, // 2
				descricaoSaida, // 3
				descricaoResponsavel, // 4
				descricaoStakeholders }; // 5
		return dados;
	}

	private CharSequence getTextoItem(Context contexto, int item) {
		if (item == OPCAO_ANALISAR_MATURIDADE)
			return contexto.getResources().getString(R.string.menu_maturidade);
		if (item == OPCAO_SINCRONIZAR)
			return contexto.getResources().getString(R.string.menu_sincronizar);
		if (item == OPCAO_SOBRE)
			return contexto.getResources().getString(R.string.menu_sobre);
		if (item == OPCAO_LOGOUT)
			return contexto.getResources().getString(R.string.menu_logout);
		return null;
	}

	public AlertDialog getDialogOpcoes(final Context contexto,
			final int[] itens, final String idProcesso) {
		CharSequence[] textoItens = new CharSequence[itens.length];

		for (int k = 0; k < itens.length; k++) {
			textoItens[k] = getTextoItem(contexto, itens[k]);
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
		builder.setTitle("Opções");
		builder.setItems(textoItens, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				// Escolha do item pressionado
				switch (itens[item]) {
				case OPCAO_ANALISAR_MATURIDADE:
					Intent intentMaturidade = new Intent(contexto,
							AnaliseMaturidadeFragment.class);
					intentMaturidade.putExtra("processo", idProcesso);
					contexto.startActivity(intentMaturidade);
					break;
				case OPCAO_SINCRONIZAR:
					Intent intentSincronizacao = new Intent(contexto,
							Sincronizador.class);
					contexto.startService(intentSincronizacao);
					break;
				case OPCAO_SOBRE:
					// Colocar intent do sobre
					// Intent intentSobre = new Intent(null, ListaAreas.class);
					// startActivity(intentSobre);
					break;
				case OPCAO_LOGOUT:
					// Colocar intent da tela inicial
					Intent intentSair = new Intent(contexto,
							LoginActivity.class);
					intentSair.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					contexto.startActivity(intentSair);
					break;
				default:
					break;
				}
			}
		});

		return builder.create();
	}

	public String[] getprojecaoArea() {
		return projecaoArea;
	}

	public String[] getprojecaoSubArea() {
		return projecaoSubArea;
	}

	public String[] getprojecaoProcesso() {
		return projecaoProcesso;
	}
}
