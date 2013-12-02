package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * <code>Fragment</code> que exibe os detalhes de um processo da empresa.
 * 
 * @see Fragment
 */
public class AnaliseMaturidadeFragment extends Fragment implements OnChildClickListener{

	private AdapterQuestoes expListAdapter = null;

	/**
	 * Carrega o layout do <code>Fragment</code>.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.analise_maturidade, container, false);
	}

	/**
	 * Executa automaticamente no momento de criação da <code>Activity</code>
	 * que hospeda este <code>Fragment</code>.
	 * <p>
	 * Carrega os dados nos componentes de tela e insere os dados na lista.
	 * </p>
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ListView lv = (ExpandableListView) getActivity().findViewById(
				R.id.expand_list_questionario);
//		lv.setTextFilterEnabled(true);

		((TextView) getActivity().findViewById(R.id.txt_area_detalhes))
				.setText(getArguments().getString("nomeArea"));
		((TextView) getActivity().findViewById(R.id.txt_subarea_detalhes))
				.setText(getArguments().getString("nomeSubarea"));
		((TextView) getActivity().findViewById(R.id.txt_editor))
				.setText(getArguments().getString("editor"));
		((TextView) getActivity().findViewById(R.id.txt_titulo_processo_detalhes))
				.setText(getArguments().getString("nomeProcesso"));
		int matAnt = getArguments().getInt("maturidadeAnt");

		// instanciando o Cursor com os níveis do questionário.
		Cursor cursorNiveis = new CursorNiveis(getActivity().getApplicationContext());

		// instanciando o Adapter, associando-o ao cursor dos níveis do
		// questionário e layouts.

		expListAdapter = new AdapterQuestoes(cursorNiveis, getActivity().getApplicationContext(), this, matAnt);

		ExpandableListView expList = (ExpandableListView) getActivity().
			findViewById(R.id.expand_list_questionario);
		expList.setAdapter(expListAdapter);
		expList.setOnChildClickListener(this);
	}

	/**
	 * Executado automaticamente com a retomada de foco do <code>Fragment</code>
	 * .
	 * <p>
	 * Atualiza a visibilidade do botão <i>Home</i> e o modo de exibição dos
	 * menus e carrega os dados dos componentes de tela.
	 * </p>
	 */
	@Override
	public void onResume() {
		expListAdapter.carregarLista();
		getActivity().findViewById(R.id.imageHome).setVisibility(View.VISIBLE);
		((TelaPrincipalActivity) getActivity()).setMenuMode(TelaPrincipalActivity.MENU_MODE_MATURIDADE);
		super.onResume();
	}
	
	/**
	 * Quando o <code>Fragment</code> é destruido.
	 * <p>
	 * Salva as alterações, se houver, no banco de dados local.É preciso
	 * sincronizar com o servidor enviar as modificações.
	 * </p> 
	 */
	@Override
	public void onPause() {
		boolean editou = false;
		ArrayList<ContentProviderOperation> operacoes = new ArrayList<ContentProviderOperation>();
		for (int groupPosition = 0; groupPosition < expListAdapter.getGroupCount(); groupPosition++) {
			for (int childPosition = 0; childPosition < expListAdapter.getChildrenCount(groupPosition);
					childPosition++) {
				if (expListAdapter.solucaoFoiModificada(groupPosition, childPosition)) {
					editou = true;
					operacoes.add(ContentProviderOperation.newUpdate(DBDefinicoes.ProcessoQuestoes.CONTENT_URI)
						.withSelection(DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO + " = ? AND "
								+ DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO + " = ?",
							new String[] {getArguments().getString("idProcesso"),
								"" +expListAdapter.getQuestaoId(groupPosition, childPosition)})
						.withValue(DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO, 
							expListAdapter.estadoQuestao(groupPosition, childPosition))
						.build());
				}
			}
			((ExpandableListView) getActivity().findViewById(R.id.expand_list_questionario))
				.collapseGroup(groupPosition);
		}
		if (editou)
			operacoes.add(ContentProviderOperation.newUpdate(DBDefinicoes.Processos.CONTENT_URI)
				.withSelection(DBDefinicoes.Processos.COLUMN_NAME_ID + " = ?",
					new String[] { getArguments().getString("idProcesso") })
				.withValue(DBDefinicoes.Processos.COLUMN_NAME_MODIFICACAO_QUESTIONARIO,
					Long.valueOf(System.currentTimeMillis()))
				.withValue(DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO,
					getArguments().getString("nomeUsuario"))
				.withValue(DBDefinicoes.Processos.COLUMN_NAME_SINCRONIZAR,1)
				.build());
		try {
			getActivity().getContentResolver().applyBatch(DBDefinicoes.AUTHORITY, operacoes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		expListAdapter.finalizarCursors();
				
		super.onPause();
	}

	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
			int childPosition, long id) {
		expListAdapter.toggleSolucaoModificada(groupPosition, childPosition);
		try {
			((ExpandableListView) getActivity().findViewById(R.id.expand_list_questionario))
				.collapseGroup(groupPosition);
			((ExpandableListView) getActivity().findViewById(R.id.expand_list_questionario))
				.expandGroup(groupPosition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}