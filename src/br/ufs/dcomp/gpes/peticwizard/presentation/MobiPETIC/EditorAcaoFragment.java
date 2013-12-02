package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <code>Fragment</code> de edição dos dados de uma ação de melhoria da empresa.
 * 
 * @see Fragment
 */
public class EditorAcaoFragment extends Fragment implements
		LoaderCallbacks<Cursor>, OnKeyListener {

	private Cursor cursorAcao;
	private String[] projecaoDescricaoAcao = { DBDefinicoes.Acoes._ID, // 0
			DBDefinicoes.Acoes.COLUMN_NAME_NOME, // 1
			DBDefinicoes.Acoes.COLUMN_NAME_RESPONSAVEL, // 2
			DBDefinicoes.Acoes.COLUMN_NAME_DATA_INICIO, // 3
			DBDefinicoes.Acoes.COLUMN_NAME_DATA_FIM, // 4
			DBDefinicoes.Acoes.COLUMN_NAME_CUSTO, // 5
			DBDefinicoes.Acoes.COLUMN_NAME_ESFORCO }; // 6

	private boolean modificado;

	/**
	 * Carrega o layout do <code>Fragment</code>.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Adicionando o layout funcoes_pricipais ao Fragment
		return inflater.inflate(R.layout.editar_acao, container, false);
	}

	/**
	 * Executado automaticamente no momento de criação da <code>Activity</code>
	 * que hospeda este <code>Fragment</code>.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(0, null, this);
		((EditText) getActivity().findViewById(R.id.edit_nome_acao))
				.setOnKeyListener(this);
		((EditText) getActivity().findViewById(R.id.edit_responsavel_acao))
				.setOnKeyListener(this);
		((EditText) getActivity().findViewById(R.id.edit_data_inicio_acao))
				.setOnKeyListener(this);
		((EditText) getActivity().findViewById(R.id.edit_data_fim_acao))
				.setOnKeyListener(this);
		((EditText) getActivity().findViewById(R.id.edit_esforco_acao))
				.setOnKeyListener(this);
		((EditText) getActivity().findViewById(R.id.edit_custo_acao))
				.setOnKeyListener(this);
	}

	/**
	 * Executado automaticamente com a retomada de foco do <code>Fragment</code>
	 * .
	 * <p>
	 * Atualiza a visibilidade do botão <i>Home</i> e o modo de exibição dos
	 * menu.
	 * </p>
	 */
	@Override
	public void onResume() {
		super.onResume();
		getActivity().findViewById(R.id.imageHome).setVisibility(View.VISIBLE);
		((TelaPrincipalActivity) getActivity())
				.setMenuMode(TelaPrincipalActivity.MENU_MODE_PADRAO);
		((TextView) getActivity().findViewById(R.id.txt_area_detalhes))
				.setText(getArguments().getString("nomeArea"));
		((TextView) getActivity().findViewById(R.id.txt_subarea_detalhes))
				.setText(getArguments().getString("nomeSubarea"));
		((TextView) getActivity().findViewById(R.id.txt_titulo_processo_detalhes))
				.setText(getArguments().getString("nomeProcesso"));
		((EditText) getActivity().findViewById(R.id.edit_nome_acao))
				.setEnabled(getArguments().getBoolean("criarAcao"));
		if (cursorAcao != null)
			cursorAcao.requery();
		getLoaderManager().restartLoader(0, null, this);
	}

	private boolean camposRequeridosPreenchidos() {
		if (!((EditText) getActivity().findViewById(R.id.edit_nome_acao))
				.getText().toString().trim().equals(""))
			return true;
		return false;
	}

	@Override
	public void onStop() {
		try {
			if (modificado) {
				ContentValues values = new ContentValues();
				values.put(
						DBDefinicoes.Acoes.COLUMN_NAME_NOME,
						((EditText) getActivity().findViewById(
								R.id.edit_nome_acao)).getText().toString());
				values.put(
						DBDefinicoes.Acoes.COLUMN_NAME_RESPONSAVEL,
						((EditText) getActivity().findViewById(
								R.id.edit_responsavel_acao)).getText()
								.toString());
				values.put(
						DBDefinicoes.Acoes.COLUMN_NAME_DATA_INICIO,
						((EditText) getActivity().findViewById(
								R.id.edit_data_inicio_acao)).getText()
								.toString());
				values.put(
						DBDefinicoes.Acoes.COLUMN_NAME_DATA_FIM,
						((EditText) getActivity().findViewById(
								R.id.edit_data_fim_acao)).getText().toString());
				values.put(
						DBDefinicoes.Acoes.COLUMN_NAME_CUSTO,
						((EditText) getActivity().findViewById(
								R.id.edit_custo_acao)).getText().toString());
				values.put(
						DBDefinicoes.Acoes.COLUMN_NAME_ESFORCO,
						((EditText) getActivity().findViewById(
								R.id.edit_esforco_acao)).getText().toString());
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_ADOTADA, "1");
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_ID_PROCESSO,
						getArguments().getString("idProcesso"));
				values.put(DBDefinicoes.Acoes.COLUMN_NAME_SINCRONIZAR, "1");

				if (getArguments().getBoolean("criarAcao")) {
					if (camposRequeridosPreenchidos()) {
						getActivity().getContentResolver().insert(
								DBDefinicoes.Acoes.CONTENT_URI, values);
					} else {
						Toast.makeText(getActivity(),
								"Nova ação não criada! Nome requerido.",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					getActivity().getContentResolver()
							.update(DBDefinicoes.Acoes.CONTENT_URI,
									values,
									DBDefinicoes.Acoes._ID + " = ?",
									new String[] { getArguments().getString(
											"idAcao") });
					Toast.makeText(getActivity(), "Alterações salvas!",
							Toast.LENGTH_SHORT).show();
				}
			}
			if (cursorAcao != null)
				cursorAcao.deactivate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.onStop();
		}
	}
	
	@Override
	public void onDestroy() {
		if (cursorAcao != null)
			cursorAcao.close();
		super.onDestroy();
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String idAcao = getArguments().getString("idAcao");
		return new CursorLoader(getActivity(), DBDefinicoes.Acoes.CONTENT_URI,
				projecaoDescricaoAcao, DBDefinicoes.Acoes._ID + " = ?",
				new String[] { idAcao }, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor.moveToNext()) {
			((EditText) getActivity().findViewById(R.id.edit_nome_acao))
					.setText(cursor.getString(cursor
							.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_NOME)));

			((EditText) getActivity().findViewById(R.id.edit_responsavel_acao))
					.setText(cursor.getString(cursor
							.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_RESPONSAVEL)));

			((EditText) getActivity().findViewById(R.id.edit_data_inicio_acao))
					.setText(cursor.getString(cursor
							.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_DATA_INICIO)));

			((EditText) getActivity().findViewById(R.id.edit_data_fim_acao))
					.setText(cursor.getString(cursor
							.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_DATA_FIM)));

			((EditText) getActivity().findViewById(R.id.edit_custo_acao))
					.setText(cursor.getString(cursor
							.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_CUSTO)));

			((EditText) getActivity().findViewById(R.id.edit_esforco_acao))
					.setText(cursor.getString(cursor
							.getColumnIndex(DBDefinicoes.Acoes.COLUMN_NAME_ESFORCO)));
		}
		cursorAcao = cursor;
	}

	public void onLoaderReset(Loader<Cursor> cursor) {}

	public boolean onKey(View view, int keyCode, KeyEvent event) {
		modificado = true;
		return false;
	}
}
