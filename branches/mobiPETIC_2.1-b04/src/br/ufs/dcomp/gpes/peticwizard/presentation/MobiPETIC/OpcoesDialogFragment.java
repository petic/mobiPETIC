package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * <code>Fragment</code> que prepara o Diálogo de opções.
 * 
 * @see DialogFragment
 */
public class OpcoesDialogFragment extends DialogFragment implements
		DialogInterface.OnClickListener {
	public static final int OPCAO_CRIAR_ACAO = 0;
	public static final int OPCAO_ANALISAR_MATURIDADE = 1;
	public static final int OPCAO_ACOES = 2;
	public static final int OPCAO_SINCRONIZAR = 3;
	public static final int OPCAO_SOBRE = 4;
	public static final int OPCAO_LOGOUT = 5;

	private int[] itens;

	private OnOpcoesDialogItemClickListener mCallback;

	public interface OnOpcoesDialogItemClickListener {
		public void onMenuOpcoesAnalisarMaturidadeClick();

		public void onMenuOpcoesSincronizarClick();

		public void onMenuOpcoesSobreClick();

		public void onMenuOpcoesLogoutClick();

		public void onMenuOpcoesAcoesClick();

		public void onMenuOpcoesCriarAcaoClick();
	}

	/**
	 * Transfere para a <code>Activity</code> que o hospeda a responsabilidade
	 * de tratar as ações do usuário.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mCallback = (OnOpcoesDialogItemClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " deve implementar OnOpcoesDialogItemClickListener");
		}
	}

	/**
	 * Criação do Diálogo.
	 * <p>
	 * Prepara o diálogo baseado no modo do menu informado pela
	 * <code>Activity</code> hospedeira
	 * </p>
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Opções");
		String[] textoItens;

		if (getArguments().getInt("menuMode") == TelaPrincipalActivity.MENU_MODE_PADRAO) {
			textoItens = new String[] {
					getString(R.string.menu_condens_sincronizar),
					getString(R.string.menu_sobre),
					getString(R.string.menu_logout) };
			itens = new int[] { OPCAO_SINCRONIZAR, OPCAO_SOBRE, OPCAO_LOGOUT };
		} else if (getArguments().getInt("menuMode") == TelaPrincipalActivity.MENU_MODE_DESCRICAO_PROCESSO) {
			textoItens = new String[] {
					getString(R.string.menu_condens_maturidade),
					getString(R.string.menu_acoes),
					getString(R.string.menu_condens_sincronizar),
					getString(R.string.menu_sobre),
					getString(R.string.menu_logout) };
			itens = new int[] { OPCAO_ANALISAR_MATURIDADE, OPCAO_ACOES,
					OPCAO_SINCRONIZAR, OPCAO_SOBRE, OPCAO_LOGOUT };
		} else if (getArguments().getInt("menuMode") == TelaPrincipalActivity.MENU_MODE_MATURIDADE) {
			textoItens = new String[] { getString(R.string.menu_acoes),
					getString(R.string.menu_condens_sincronizar),
					getString(R.string.menu_sobre),
					getString(R.string.menu_logout) };
			itens = new int[] { OPCAO_ACOES, OPCAO_SINCRONIZAR, OPCAO_SOBRE,
					OPCAO_LOGOUT };
		} else {
			textoItens = new String[] { getString(R.string.menu_criar_acao),
					getString(R.string.menu_condens_maturidade),
					getString(R.string.menu_condens_sincronizar),
					getString(R.string.menu_sobre),
					getString(R.string.menu_logout) };
			itens = new int[] { OPCAO_CRIAR_ACAO, OPCAO_ANALISAR_MATURIDADE,
					OPCAO_SINCRONIZAR, OPCAO_SOBRE, OPCAO_LOGOUT };
		}

		builder.setItems(textoItens, this);
		return builder.create();
	}

	/**
	 * Tratamento do click em um item do diálogo é transferido para a
	 * <code>Activity</code> hospedeira.
	 */
	public void onClick(DialogInterface dialog, int item) {
		switch (itens[item]) {
		case OPCAO_ANALISAR_MATURIDADE:
			mCallback.onMenuOpcoesAnalisarMaturidadeClick();
			break;
		case OPCAO_ACOES:
			mCallback.onMenuOpcoesAcoesClick();
			break;
		case OPCAO_CRIAR_ACAO:
			mCallback.onMenuOpcoesCriarAcaoClick();
			break;
		case OPCAO_SINCRONIZAR:
			mCallback.onMenuOpcoesSincronizarClick();
			break;
		case OPCAO_SOBRE:
			mCallback.onMenuOpcoesSobreClick();
			break;
		case OPCAO_LOGOUT:
			mCallback.onMenuOpcoesLogoutClick();
			break;
		default:
			break;
		}
	}
}
