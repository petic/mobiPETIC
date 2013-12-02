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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * <code>Fragment</code> que exibe a lista de áreas adotadas pela empresa.
 * 
 * @see ListFragment
 */
public class ListaAreasFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

	private String textFiltro;

	private SimpleCursorAdapter areasAdapter;

	private String[] projecaoArea = { DBDefinicoes.Areas._ID,
			DBDefinicoes.Areas.COLUMN_NAME_ID,
			DBDefinicoes.Areas.COLUMN_NAME_NOME };

	private OnListaAreasItemClickListener mCallback;

	public interface OnListaAreasItemClickListener {
		public void onAreaClick(String idArea, String nomeArea);
	}

	/**
	 * Transfere para a <code>Activity</code> que o hospeda a responsabilidade
	 * de tratar as ações do usuário.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mCallback = (OnListaAreasItemClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " deve implementar OnListaAreasItemClickListener");
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
					.setTextColor(Color.BLACK);
			((TextView) getActivity().findViewById(R.id.txt_separador1))
					.setTextColor(Color.LTGRAY);
			((TextView) getActivity().findViewById(R.id.txt_subarea))
					.setTextColor(Color.LTGRAY);
			((TextView) getActivity().findViewById(R.id.txt_separador2))
					.setTextColor(Color.LTGRAY);
			((TextView) getActivity().findViewById(R.id.txt_processo))
					.setTextColor(Color.LTGRAY);

			lv.setOnItemClickListener(this);

			textFiltro = null;
			areasAdapter = new SimpleCursorAdapter(getActivity(),
					R.layout.list_itens, null, new String[] {
							DBDefinicoes.Areas.COLUMN_NAME_ID,
							DBDefinicoes.Areas.COLUMN_NAME_NOME }, new int[] {
							R.id.txt_id, R.id.txt_nome }, 0);

			setListAdapter(areasAdapter);
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
	 * Carrega os dados da lista de áreas.
	 * </p>
	 */
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (textFiltro != null) {
			return new CursorLoader(
					getActivity(),
					DBDefinicoes.Areas.CONTENT_URI,
					projecaoArea,
					"((" + DBDefinicoes.Areas.COLUMN_NAME_ID + " LIKE ?) OR ("
							+ DBDefinicoes.Areas.COLUMN_NAME_NOME + " LIKE ?))",
					new String[] { textFiltro + "%", textFiltro + "%" },
					DBDefinicoes.Areas.DEFAULT_SORT_ORDER);
		} else {
			return new CursorLoader(getActivity(),
					DBDefinicoes.Areas.CONTENT_URI, projecaoArea, null, null,
					DBDefinicoes.Areas.DEFAULT_SORT_ORDER);
		}
	}

	/**
	 * Ao final de cada carregamento.
	 * <p>
	 * O resultado é exibido na tela.
	 * </p>
	 */
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		areasAdapter.swapCursor(data);
	}

	/**
	 * Limpa o resultado da tela.
	 */
	public void onLoaderReset(Loader<Cursor> loader) {
		areasAdapter.swapCursor(null);
	}

	/**
	 * Tratamento do click em um item da lista é transferido para a
	 * <code>Activity</code> hospedeira.
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CursorLoader curLoader = new CursorLoader(getActivity());
		curLoader.setUri(ContentUris.withAppendedId(
				DBDefinicoes.Areas.CONTENT_URI, id));
		Cursor cursor = curLoader.loadInBackground();
		if (cursor.moveToNext()) {
			mCallback
					.onAreaClick(
							cursor.getString(cursor
									.getColumnIndex(DBDefinicoes.Areas.COLUMN_NAME_ID)),
							cursor.getString(cursor
									.getColumnIndex(DBDefinicoes.Areas.COLUMN_NAME_NOME)));
		}
		cursor.close(); //TODO: cursor.close();
	}
}