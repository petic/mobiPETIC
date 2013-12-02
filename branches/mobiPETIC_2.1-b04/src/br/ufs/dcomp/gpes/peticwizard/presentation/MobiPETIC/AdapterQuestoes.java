package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Classe <code>Adapter</code> que prepara os dados a serem exibidos na
 * lista expansível (questões do processo agrupadas por níveis).
 * 
 * @see BaseExpandableListAdapter
 */
public class AdapterQuestoes extends BaseExpandableListAdapter {

	private boolean primeiroNivelNaoCompleto;
	private Cursor groupCursor;
	private ArrayList<Cursor> childrenCursor = new ArrayList<Cursor>();
	private ArrayList<ArrayList<Boolean>> solucaoModificada = new ArrayList<ArrayList<Boolean>>();
	private ArrayList<Boolean> nivelCompleto = new ArrayList<Boolean>();
	private Context myContext;
	private Fragment myFragment;
	private int maturidadeAnterior;
	private String[] projecao = { // projeção dos campos que serão consultados na tabela questoes
			DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO, // 0
			DBDefinicoes.Questoes.COLUMN_NAME_NIVEL, // 1
			DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO, // 2
			DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO }; // 3
	
	public AdapterQuestoes(Cursor cursor, Context context, Fragment frag, int matAnterior) {
		groupCursor = cursor;
		myContext = context;
		myFragment = frag;
		maturidadeAnterior = matAnterior;
	}
	
	public void carregarLista() {
		groupCursor.moveToFirst();

		int groupPosition = 0;
		while (groupCursor.moveToNext()) {
			Cursor childCursor = getChildrenCursor(groupCursor);
			childrenCursor.add(childCursor);
			ArrayList<Boolean> childMod = new ArrayList<Boolean>();
			solucaoModificada.add(childMod);
			nivelCompleto.add(true);
			while (childCursor.moveToNext()) {
				childMod.add(false);
				if (!(childCursor.getInt(3)==1)) // coluna DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO
					nivelCompleto.set(groupPosition, false);
			}
			groupPosition++;
		}
		//TODO Talvez precise editar para compatibilidade com versões posteriores do Android (4.2.2+)
		if (myFragment.isVisible()) { // por incrível que pareça, quando o fragment está visível o adapter está sendo criado pela primeira vez
			primeiroNivelNaoCompleto = true;
		} else { // quando o fragment está invisível o adapter irá carregar o estado anterior do ExpandableList, troca de orientação (ou uma interrupção)
			primeiroNivelNaoCompleto = false;
		}
		atualizarImagemMaturidade();
	}
	
	/**
	 * Retorna o objeto <code>{@link Cursor}</code> contendo a lista de
	 * questões com as marcações para o processo pertencentes ao nível
	 * apontado pelo <code>Cursor</code> informado.
	 * <p>
	 * Este método é chamado automaticamente quando um grupo é expandido.
	 * </p>
	 * 
	 * @param cursor
	 *            <code>Cursor</code> dos níveis apontando para um nível
	 *            válido.
	 * @return Objeto <code>Cursor</code> contendo a listas de questões
	 *         pertencentes ao nível apontado.
	 */
	private Cursor getChildrenCursor(Cursor cursor) {
		Cursor childrenCursor = null;
		try {
			childrenCursor = myContext.getContentResolver()
					.query(DBDefinicoes.ProcessoQuestoes.CONTENT_URI, projecao,
				DBDefinicoes.Questoes.COLUMN_NAME_NIVEL + " = ? AND "
					+ DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_PROCESSO
					+ " = ?",
				new String[] {cursor.getString(0), myFragment.getArguments().getString("idProcesso") },
				DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO + " ASC");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return childrenCursor;
	}

	/**
	 * Atualiza a imagem que identifica o nível de maturidade atual do processo.
	 * <p>
	 * Padronizada para cada nível e evolução quanto à maturidade final.
	 * </p>
	 */
	public void atualizarImagemMaturidade() {
		int novaMaturidade = getNivelMaturidade();

		// definindo imagem de acordo com o nível de maturidade atual e o anterior.
		// Mensagens utilizam strings do resource para o suporte à vários idiomas.
		switch (novaMaturidade) {
		case 0:
			myFragment.getActivity().findViewById(R.id.image_maturidade).setContentDescription(
					myContext.getString(R.string.mensagem_maturidade_0));
			if (novaMaturidade < maturidadeAnterior)
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade0_menos);
			else
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade0);
			break;
		case 1:
			myFragment.getActivity().findViewById(R.id.image_maturidade).setContentDescription(
					myContext.getString(R.string.mensagem_maturidade_1));
			if (novaMaturidade < maturidadeAnterior)
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade1_menos);
			else if (novaMaturidade > maturidadeAnterior)
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade1_mais);
			else
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade1);
			break;
		case 2:
			myFragment.getActivity().findViewById(R.id.image_maturidade).setContentDescription(
					myContext.getString(R.string.mensagem_maturidade_2));
			if (novaMaturidade < maturidadeAnterior)
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade2_menos);
			else if (novaMaturidade > maturidadeAnterior)
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade2_mais);
			else
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade2);
			break;
		case 3:
			myFragment.getActivity().findViewById(R.id.image_maturidade).setContentDescription(
					myContext.getString(R.string.mensagem_maturidade_3));
			if (novaMaturidade < maturidadeAnterior)
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade3_menos);
			else if (novaMaturidade > maturidadeAnterior)
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade3_mais);
			else
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade3);
			break;
		default:
			myFragment.getActivity().findViewById(R.id.image_maturidade).setContentDescription(
					myContext.getString(R.string.mensagem_maturidade_4));
			if (novaMaturidade > maturidadeAnterior)
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade4_mais);
			else
				myFragment.getActivity().findViewById(R.id.image_maturidade)
					.setBackgroundResource(R.drawable.maturidade4);
			break;
		}
	}
	
	/**
	 * Calcula o nível de maturidade do questionário.
	 * 
	 * @return Nível atual de maturidade do questionário.
	 */
	private int getNivelMaturidade() {
		int novaMaturidade = 0;
		try {
			// Obtendo o nível de maturidade final do processo.
			while ((novaMaturidade < getGroupCount()) && nivelCompleto.get(novaMaturidade)) {
				novaMaturidade++;
			}
			return novaMaturidade;
		} catch (Exception e) {
			return 4;
		}
	}

	public Object getChild(int groupPosition, int childPosition) {return null;}

	public long getChildId(int groupPosition, int childPositio) {return 0;}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)
				myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.maturidade_filho_relative, null);
		}
		CheckedTextView questaoStatus = (CheckedTextView)
			convertView.findViewById(R.id.txt_check_questao_maturidade);
		if (childrenCursor.get(groupPosition).moveToPosition(childPosition)) {
			questaoStatus.setText(childrenCursor.get(groupPosition).getString(2)); // coluna DBDefinicoes.Questoes.COLUMN_NAME_DESCRICAO
			
			if ((childrenCursor.get(groupPosition).getInt(3)==1) !=  // coluna DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO
					solucaoModificada.get(groupPosition).get(childPosition))
		    	questaoStatus.setCheckMarkDrawable(R.drawable.check);
		    else
		    	questaoStatus.setCheckMarkDrawable(R.drawable.delete4);
		}
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		if ((childrenCursor != null) && (childrenCursor.size() > groupPosition))
			return childrenCursor.get(groupPosition).getCount();
		return 0;
	}

	public Object getGroup(int groupPosition) {
		return null;
	}

	public int getGroupCount() {
		return groupCursor.getCount();
	}

	public long getGroupId(int groupPosition) {return 0;}

	public View getGroupView(int groupPosition, boolean isExpanded,	View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)
				myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.maturidade_grupo_collapsed, null);
		}
		TextView descricaoNivel = (TextView)
			convertView.findViewById(R.id.txt_maturidade_nivel);
		if (groupCursor.moveToPosition(groupPosition)) {
			descricaoNivel.setText(groupCursor.getString(1)); // coluna "titulo"
			if (nivelCompleto.get(groupPosition)) {
				if (nivelAtingido(groupPosition))
					convertView.setBackgroundResource(R.drawable.nivel_ok);
				else
					convertView.setBackgroundResource(R.drawable.nivel_nao_atingido);
			} else {
				convertView.setBackgroundResource(R.drawable.nivel_nao_ok);
				if (primeiroNivelNaoCompleto) {
					((ExpandableListView) myFragment.getActivity().findViewById(R.id.expand_list_questionario))
						.expandGroup(groupPosition);
					primeiroNivelNaoCompleto = false;
				}
			}
		}
		return convertView;
	}

	private boolean nivelAtingido(int groupPosition) {
		for (int i = 0; i < groupPosition; i++) {
			if (!nivelCompleto.get(i))
				return false;
		}
		return true;
	}

	public boolean hasStableIds() {return false;}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public void finalizarCursors() {
		groupCursor.close();
		for (int i = 0; i < childrenCursor.size(); i++) {
			childrenCursor.get(i).close();
		}
		//childrenCursor.clear();
	}
	
	// Esta função está partindo do princípio de que solucaoModificada já foi conferida e seu valor é "true"
	// para usá-la em outra situação será preciso alterá-la
	public int estadoQuestao(int groupPosition, int childPosition) {
		childrenCursor.get(groupPosition).moveToPosition(childPosition);
		return (childrenCursor.get(groupPosition).getInt(3) == 1)? 0: 1; // coluna DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO
	}
	
	public int getQuestaoId(int groupPosition, int childPosition) {
		childrenCursor.get(groupPosition).moveToPosition(childPosition);
		return childrenCursor.get(groupPosition).getInt(0); // coluna DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_ID_QUESTAO
	}
	
	public boolean solucaoFoiModificada(int groupPosition, int childPosition) {
		return solucaoModificada.get(groupPosition).get(childPosition);
	}
	
	public void toggleSolucaoModificada(int groupPosition, int childPosition) {
		solucaoModificada.get(groupPosition).set(childPosition,
				!solucaoModificada.get(groupPosition).get(childPosition));
		childrenCursor.get(groupPosition).moveToPosition(childPosition);
		if (nivelCompleto.get(groupPosition)) {
			nivelCompleto.set(groupPosition, false);
			atualizarImagemMaturidade();
		} else if ((childrenCursor.get(groupPosition).getInt(3)==1) !=  // coluna DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO
				solucaoModificada.get(groupPosition).get(childPosition)) {
			if (completouNivel(groupPosition)) {
				nivelCompleto.set(groupPosition, true);
				atualizarImagemMaturidade();
			}
		}
	}
	
	private boolean completouNivel(int groupPosition) {
		int childPosition = 0;
		if (childrenCursor.get(groupPosition).moveToFirst()) {
			if ((childrenCursor.get(groupPosition).getInt(3)==1) ==  // coluna DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO
					solucaoModificada.get(groupPosition).get(childPosition))
				return false;
			childPosition++;
			while (childrenCursor.get(groupPosition).moveToNext()) {
				if ((childrenCursor.get(groupPosition).getInt(3)==1) ==  // coluna DBDefinicoes.ProcessoQuestoes.COLUMN_NAME_MARCADO
						solucaoModificada.get(groupPosition).get(childPosition))
					return false;
				childPosition++;
			}
		}
		return true;
	}

	/**
	 * Chamado no momento em que um grupo é expandido.
	 * <p>
	 * Comportamento atual: quando um grupo é expandido todos os outros são colapsados
	 * </p>
	 */
	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);

		for (int k = 0; k < ((ExpandableListView) myFragment.getActivity()
				.findViewById(R.id.expand_list_questionario)).getExpandableListAdapter().getGroupCount(); k++) {
			if (k != groupPosition)
				((ExpandableListView) myFragment.getActivity().findViewById(
						R.id.expand_list_questionario)).collapseGroup(k);
		}
	}

}
