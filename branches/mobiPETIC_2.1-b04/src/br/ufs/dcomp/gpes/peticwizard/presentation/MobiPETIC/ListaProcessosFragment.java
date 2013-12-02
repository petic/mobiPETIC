package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

/**
 * <code>Fragment</code> que exibe a lista de processos adotadas pela empresa.
 * 
 * @see ListFragment
 */
public class ListaProcessosFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener,
		View.OnCreateContextMenuListener {

	private String textFiltro;

	private SimpleCursorAdapter processosAdapter;

	private String[] projecaoProcesso = { DBDefinicoes.Processos._ID,
			DBDefinicoes.Processos.COLUMN_NAME_ID,
			DBDefinicoes.Processos.COLUMN_NAME_NOME,
			DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT,
			DBDefinicoes.Processos.COLUMN_NAME_ID_SUBAREA };

	private OnListaProcessosItemClickListener mCallback;

	public interface OnListaProcessosItemClickListener {
		public void onProcessoClick(String idProcesso, String nomeProcesso,
				int maturidadeAnt, String editor);

		public void onProcessoLongClick(ContextMenu menu, String idProcesso,
				String nomeProcesso, int maturidadeAnt, String editor);
	}

	/**
	 * Transfere para a <code>Activity</code> que o hospeda a responsabilidade
	 * de tratar as ações do usuário.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mCallback = (OnListaProcessosItemClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " deve implementar OnListaProcessosItemClickListener");
		}
	}

	/**
	 * Carrega o layout do <code>Fragment</code>.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.header_bar, container, false);
	}

	/**
	 * Executado automaticamente no momento de criação da <code>Activity</code>
	 * que hospeda este <code>Fragment</code>.
	 * <p>
	 * Carrega dados nos componentes de tela e define o comportamento destes.
	 * </p>
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		try {
			ListView lv = getListView();
			lv.setTextFilterEnabled(true);

			((TextView) getActivity().findViewById(R.id.txt_area))
					.setText(getArguments().getString("nomeArea"));
			((TextView) getActivity().findViewById(R.id.txt_area))
					.setTextColor(Color.BLACK);
			((TextView) getActivity().findViewById(R.id.txt_separador1))
					.setTextColor(Color.BLACK);
			((TextView) getActivity().findViewById(R.id.txt_subarea))
					.setText(getArguments().getString("nomeSubarea"));
			((TextView) getActivity().findViewById(R.id.txt_subarea))
					.setTextColor(Color.BLACK);
			((TextView) getActivity().findViewById(R.id.txt_separador2))
					.setTextColor(Color.BLACK);
			((TextView) getActivity().findViewById(R.id.txt_processo))
					.setTextColor(Color.BLACK);

			lv.setOnItemClickListener(this);
			lv.setOnCreateContextMenuListener(this);

			textFiltro = null;
			processosAdapter = new SimpleCursorAdapter(getActivity(),
					R.layout.list_itens, null, new String[] {
							DBDefinicoes.Processos.COLUMN_NAME_ID,
							DBDefinicoes.Processos.COLUMN_NAME_NOME },
					new int[] { R.id.txt_id, R.id.txt_nome }, 0);

			setListAdapter(processosAdapter);
			getLoaderManager().initLoader(0, null, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executado automaticamente com a retomada de foco do <code>Fragment</code>
	 * .
	 * <p>
	 * Atualiza a visibilidade do botão <i>Home</i> e o modo de exibição dos
	 * menus
	 * </p>
	 */
	@Override
	public void onResume() {
		super.onResume();
		getActivity().findViewById(R.id.imageHome).setVisibility(View.VISIBLE);
		((TelaPrincipalActivity) getActivity())
				.setMenuMode(TelaPrincipalActivity.MENU_MODE_PADRAO);
	}

	/**
	 * Criação do <code>Loader</code>.
	 * <p>
	 * Carrega os dados da lista de processos.
	 * </p>
	 */
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String idSubarea = getArguments().getString("idSubarea");
		if (textFiltro != null) {
			return new CursorLoader(getActivity(),
					DBDefinicoes.Processos.CONTENT_URI, projecaoProcesso, "(("
							+ DBDefinicoes.Processos.COLUMN_NAME_ID
							+ " LIKE ?) OR ("
							+ DBDefinicoes.Processos.COLUMN_NAME_NOME
							+ " LIKE ?))", new String[] {
							idSubarea + "." + textFiltro + "%",
							textFiltro + "%" },
					DBDefinicoes.Processos.DEFAULT_SORT_ORDER);
		} else {
			return new CursorLoader(getActivity(),
					DBDefinicoes.Processos.CONTENT_URI, projecaoProcesso,
					DBDefinicoes.Processos.COLUMN_NAME_ID + " LIKE ?",
					new String[] { idSubarea + "%" },
					DBDefinicoes.Processos.DEFAULT_SORT_ORDER);
		}
	}

	/**
	 * Ao final de cada carregamento.
	 * <p>
	 * O resultado é exibido na tela.
	 * </p>
	 */
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		processosAdapter.swapCursor(data);
	}

	/**
	 * Limpa o resultado da tela.
	 */
	public void onLoaderReset(Loader<Cursor> loader) {
		processosAdapter.swapCursor(null);
	}

	/**
	 * Tratamento do click em um item da lista é transferido para a
	 * <code>Activity</code> hospedeira.
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CursorLoader curLoader = new CursorLoader(getActivity());
		curLoader.setUri(ContentUris.withAppendedId(
				DBDefinicoes.Processos.CONTENT_URI, id));
		Cursor cursor = curLoader.loadInBackground();
		if (cursor.moveToNext()) {
			mCallback
					.onProcessoClick(
							cursor.getString(cursor
									.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_ID)),
							cursor.getString(cursor
									.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_NOME)),
							cursor.getInt(cursor
									.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT)),
							cursor.getString(cursor
									.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO)));
		}
		cursor.close(); //TODO: cursor.close();
	}

	/**
	 * Tratamento do click longo em um item da lista é transferido para a
	 * <code>Activity</code> hospedeira.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo info;
		try {
			info = (AdapterContextMenuInfo) menuInfo;
		} catch (ClassCastException e) {
			// Log.e("++++ListaProcessosFragment++++",
			// "menuInfo mal sucedido!!!", e);
			return;
		}
		CursorLoader curLoader = new CursorLoader(getActivity());
		curLoader.setUri(ContentUris.withAppendedId(
				DBDefinicoes.Processos.CONTENT_URI, info.id));
		Cursor cursor = curLoader.loadInBackground();
		if (cursor.moveToNext()) {
			mCallback
					.onProcessoLongClick(
							menu,
							cursor.getString(cursor
									.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_ID)),
							cursor.getString(cursor
									.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_NOME)),
							cursor.getInt(cursor
									.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_GRAU_MATURIDADE_ANT)),
							cursor.getString(cursor
									.getColumnIndex(DBDefinicoes.Processos.COLUMN_NAME_EDITOR_QUESTIONARIO)));
		}
		cursor.close(); //TODO: cursor.close();
	}
}