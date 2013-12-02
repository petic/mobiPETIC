package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

public class ListaAcoesMelhoriaFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener,
		ViewGroup.OnHierarchyChangeListener,
		android.widget.AbsListView.OnScrollListener,
		View.OnCreateContextMenuListener {

	private String textFiltro;

	private SimpleCursorAdapter acoesAdapter;

	private String[] projecaoAcao = { DBDefinicoes.Acoes._ID,
			DBDefinicoes.Acoes.COLUMN_NAME_ID,
			DBDefinicoes.Acoes.COLUMN_NAME_NOME,
			DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA };

	private OnListaAcoesMelhoriaItemClickListener mCallback;

	public interface OnListaAcoesMelhoriaItemClickListener {
		public void onAcaoAdotadaClick(String idAcao, String nomeAcao);

		public void onAcaoSugeridaClick(String idAcao, String nomeAcao);

		public void onAcaoLongClick(ContextMenu menu, String idAcao,
				String nomeAcao, boolean adotada);
	}

	/**
	 * Transfere para a <code>Activity</code> que o hospeda a responsabilidade
	 * de tratar as ações do usuário.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mCallback = (OnListaAcoesMelhoriaItemClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " deve implementar OnListaAcoesMelhoriaItemClickListener");
		}
	}

	/**
	 * Carrega o layout do <code>Fragment</code>.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.acoes_melhoria, container, false);
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

			lv.setOnItemClickListener(this);
			lv.setOnCreateContextMenuListener(this);
			if (Build.VERSION.SDK_INT == Build.VERSION_CODES.ECLAIR_MR1)
				lv.setOnHierarchyChangeListener(this);
			else
				lv.setOnScrollListener(this);

			textFiltro = null;
			acoesAdapter = new SimpleCursorAdapter(getActivity(),
					R.layout.list_itens, null, new String[] {
							DBDefinicoes.Acoes.COLUMN_NAME_ID,
							DBDefinicoes.Acoes.COLUMN_NAME_NOME,
							DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA },
					new int[] { R.id.txt_id, R.id.txt_nome, R.id.txt_status },
					0);

			setListAdapter(acoesAdapter);
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
	 * menus e carrega os dados dos componentes de tela.
	 * </p>
	 */
	@Override
	public void onResume() {
		super.onResume();
		getActivity().findViewById(R.id.imageHome).setVisibility(View.VISIBLE);
		((TelaPrincipalActivity) getActivity())
				.setMenuMode(TelaPrincipalActivity.MENU_MODE_LISTA_ACOES);
		((TextView) getActivity().findViewById(R.id.txt_area_detalhes))
				.setText(getArguments().getString("nomeArea"));
		((TextView) getActivity().findViewById(R.id.txt_subarea_detalhes))
				.setText(getArguments().getString("nomeSubarea"));
		((TextView) getActivity().findViewById(R.id.txt_titulo_processo_detalhes))
				.setText(getArguments().getString("nomeProcesso"));
	}

	/**
	 * Tratamento do click em um item da lista é transferido para a
	 * <code>Activity</code> hospedeira.
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			CursorLoader curLoader = new CursorLoader(getActivity());
			curLoader.setUri(ContentUris.withAppendedId(
					DBDefinicoes.Acoes.CONTENT_URI, id));
			Cursor cursor = curLoader.loadInBackground();
			if (cursor.moveToNext()) {
				if (cursor
						.getString(
								cursor.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA))
						.equals("1"))
					mCallback
							.onAcaoAdotadaClick(
									cursor.getString(cursor
											.getColumnIndex(DBDefinicoes.Acoes._ID)),
									cursor.getString(cursor
											.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_NOME)));
				else
					mCallback
							.onAcaoSugeridaClick(
									cursor.getString(cursor
											.getColumnIndex(DBDefinicoes.Acoes._ID)),
									cursor.getString(cursor
											.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_NOME)));
			}
			cursor.close(); //TODO: cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tratamento do click longo em um item da lista é transferido para a
	 * <code>Activity</code> hospedeira.
	 */
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo info;
		try {
			info = (AdapterContextMenuInfo) menuInfo;
		} catch (ClassCastException e) {
			// Log.e("++++ListaAcoesMelhoriaFragment++++",
			// "menuInfo mal sucedido!!!", e);
			return;
		}
		CursorLoader curLoader = new CursorLoader(getActivity());
		curLoader.setUri(ContentUris.withAppendedId(
				DBDefinicoes.Acoes.CONTENT_URI, info.id));
		Cursor cursor = curLoader.loadInBackground();
		if (cursor.moveToNext()) {
			mCallback
					.onAcaoLongClick(
							menu,
							cursor.getString(cursor
									.getColumnIndex(DBDefinicoes.Acoes._ID)),
							cursor.getString(cursor
									.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_NOME)),
							cursor.getString(
									cursor.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA))
									.equals("1"));
		}
		cursor.close(); //TODO: cursor.close();
	}

	/**
	 * Criação do <code>Loader</code>.
	 * <p>
	 * Carrega os dados da lista de ações.
	 * </p>
	 */
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String idProcesso = getArguments().getString("idProcesso");
		if (textFiltro != null) {
			return new CursorLoader(getActivity(),
					DBDefinicoes.Acoes.CONTENT_URI, projecaoAcao, "(("
							+ DBDefinicoes.Acoes.COLUMN_NAME_ID_PROCESSO
							+ " = ?) AND (("
							+ DBDefinicoes.Acoes.COLUMN_NAME_ID
							+ " LIKE ?) OR ("
							+ DBDefinicoes.Acoes.COLUMN_NAME_NOME
							+ " LIKE ?)))", new String[] { idProcesso,
							textFiltro + "%", textFiltro + "%" },
					DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA + " DESC, "
							+ DBDefinicoes.Acoes.COLUMN_NAME_ID + " ASC");
		} else {
			return new CursorLoader(getActivity(),
					DBDefinicoes.Acoes.CONTENT_URI, projecaoAcao, "("
							+ DBDefinicoes.Acoes.COLUMN_NAME_ID_PROCESSO
							+ " = ?)", new String[] { idProcesso },
					DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA + " DESC, "
							+ DBDefinicoes.Acoes.COLUMN_NAME_ID + " ASC");
		}
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		acoesAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		acoesAdapter.swapCursor(null);
	}

	public void onChildViewAdded(View parent, View child) {
		atualizarEstadoItem(child);
		// Log.e("+ListaAcoesMelhoria+", "view " +
		// ((TextView)child.findViewById(R.id.txt_id)).getText() +
		// " adicionada!");
	}

	public void onChildViewRemoved(View parent, View child) {
		//
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// Log.e("+ListaAcoesMelhoria+", "Nº Itens: " + visibleItemCount);
		for (int i = 0; i < visibleItemCount; i++) {
			View child = view.getChildAt(i);
			atualizarEstadoItem(child);
		}
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//
	}

	private void atualizarEstadoItem(View child) {
		if (child != null) {
			// Log.e("+ListaAcoesMelhoria+", "view " +
			// ((TextView)child.findViewById(R.id.txt_id)).getText() +
			// " encontrada e atualizada!");
			CharSequence status = ((TextView) child
					.findViewById(R.id.txt_status)).getText();
			if (status.equals("0")) { // sugestão de ação de melhoria.
				child.setBackgroundColor(Color.TRANSPARENT);
				((TextView) child.findViewById(R.id.txt_nome))
						.setTextColor(Color.LTGRAY);
				((TextView) child.findViewById(R.id.txt_id))
						.setTextColor(Color.LTGRAY);
			} else {
				child.setBackgroundColor(Color.parseColor("#FFFDD0"));
				((TextView) child.findViewById(R.id.txt_nome))
						.setTextColor(Color.BLACK);
				((TextView) child.findViewById(R.id.txt_id))
						.setTextColor(Color.BLACK);
			}
		}
	}
}
