package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * <code>Fragment</code> que exibe os detalhes de um processo da empresa.
 * 
 * @see Fragment
 */
public class DescricaoProcessoFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private Cursor cursorProcesso;
	private String[] projecaoDescricaoProcesso = { DBDefinicoes.Processos._ID, // 0
			DBDefinicoes.Processos.COLUMN_NAME_NOME, // 1
			DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE, // 2
			DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT, // 3
			DBDefinicoes.Processos.COLUMN_NAME_PRIORITARIO, // 4
			DBDefinicoes.Processos.COLUMN_NAME_PRIORIDADE_DEF, // 5
			DBDefinicoes.Processos.COLUMN_NAME_DESCRICAO, // 6
			DBDefinicoes.Processos.COLUMN_NAME_ENTRADAS, // 7
			DBDefinicoes.Processos.COLUMN_NAME_SAIDAS, // 8
			DBDefinicoes.Processos.COLUMN_NAME_RESPONSAVEL, // 9
			DBDefinicoes.Processos.COLUMN_NAME_STAKEHOLDERS }; // 10

	private String idProcesso;

	/**
	 * Carrega o layout do <code>Fragment</code>.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Adicionando o layout funcoes_pricipais ao Fragment
		return inflater.inflate(R.layout.descricao_processo, container, false);
	}

	/**
	 * Executado automaticamente no momento de criação da <code>Activity</code>
	 * que hospeda este <code>Fragment</code>.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// iniciando variáveis e objetos
		idProcesso = getArguments().getString("idProcesso");
		getLoaderManager().initLoader(0, null, this);
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
		super.onResume();
		getActivity().findViewById(R.id.imageHome).setVisibility(View.VISIBLE);
		((TelaPrincipalActivity) getActivity())
				.setMenuMode(TelaPrincipalActivity.MENU_MODE_DESCRICAO_PROCESSO);
		((TextView) getActivity().findViewById(R.id.txt_area_detalhes))
				.setText(getArguments().getString("nomeArea"));
		((TextView) getActivity().findViewById(R.id.txt_subarea_detalhes))
				.setText(getArguments().getString("nomeSubarea"));
		if (cursorProcesso != null)
			cursorProcesso.requery();
		getLoaderManager().restartLoader(0, null, this);
	}
	
	@Override
	public void onStop() {
		if (cursorProcesso != null)
			cursorProcesso.deactivate();
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		if (cursorProcesso != null)
			cursorProcesso.close();
		super.onDestroy();
	}

	/**
	 * Criação do <code>Loader</code>.
	 * <p>
	 * Carrega os dados do processo.
	 * </p>
	 */
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(),
				DBDefinicoes.Processos.CONTENT_URI, projecaoDescricaoProcesso,
				DBDefinicoes.Processos.COLUMN_NAME_ID + " = ?",
				new String[] { idProcesso }, null);
	}

	/**
	 * Ao final de cada carregamento.
	 * <p>
	 * O resultado é exibido na tela.
	 * </p>
	 */
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (data.moveToNext()) {
			((TextView) getActivity().findViewById(R.id.txt_titulo_processo_detalhes))
					.setText(data.getString(data
							.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_NOME)));

			((TextView) getActivity().findViewById(R.id.txtDescricao))
					.setText(data.getString(data
							.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_DESCRICAO)));

			((TextView) getActivity().findViewById(R.id.txtEntradas))
					.setText(data.getString(data
							.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_ENTRADAS)));

			((TextView) getActivity().findViewById(R.id.txtSaidas))
					.setText(data.getString(data
							.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_SAIDAS)));

			((TextView) getActivity().findViewById(R.id.txtResponsavel))
					.setText(data.getString(data
							.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_RESPONSAVEL)));

			((TextView) getActivity().findViewById(R.id.txtStakeholders))
					.setText(data.getString(data
							.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_STAKEHOLDERS)));
		}
		cursorProcesso = data;
	}

	public void onLoaderReset(Loader<Cursor> loader) {}
}