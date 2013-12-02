package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * <code>Fragment</code> que exibe as principais tarefas da aplicação e
 * caracteriza a tela principal.
 * 
 * @see Fragment
 */
public class FuncoesPrincipaisFragment extends Fragment {

	private OnFuncoesPrincipaisClickListener mCallback;

	public interface OnFuncoesPrincipaisClickListener {
		public void onCarregarLogoEmpresa();

		public void onButtonCatalogoClick();

		public void onButtonAcoesClick();
	}

	/**
	 * Transfere para a <code>Activity</code> que o hospeda a responsabilidade
	 * de tratar as ações do usuário.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mCallback = (OnFuncoesPrincipaisClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " deve implementar OnFuncoesPrincipaisClickListener");
		}
	}

	/**
	 * Carrega o layout do <code>Fragment</code>.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Adicionando o layout funcoes_pricipais ao Fragment
		return inflater.inflate(R.layout.funcoes_principais, container, false);
	}

	/**
	 * Executado automaticamente no momento de criação da <code>Activity</code>
	 * que hospeda este <code>Fragment</code>.
	 * <p>
	 * Carrega dados do usuário/empresa e define o comportamento das UI.
	 * </p>
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mCallback.onCarregarLogoEmpresa();

		// Botão de Catálogos
		final ImageButton catalogos = (ImageButton) getActivity().findViewById(
				R.id.imageButtonProcessos);
		catalogos.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mCallback.onButtonCatalogoClick();
			}
		});

		// Botão de Ações
		final ImageButton acoes = (ImageButton) getActivity().findViewById(
				R.id.imageButtonAcoes);
		acoes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mCallback.onButtonAcoesClick();
			}
		});
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
		getActivity().findViewById(R.id.imageHome)
				.setVisibility(View.INVISIBLE);
		((TelaPrincipalActivity) getActivity())
				.setMenuMode(TelaPrincipalActivity.MENU_MODE_PADRAO);
	}
}