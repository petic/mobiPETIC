package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

/**
 * Classe <code>Cursor</code> que inicia um Cursor com os níveis que
 * agruparão as questões do questionário de maturidade no ExpandableList.
 * <p>
 * Este <code>Cursor</code> iniciará seus próprios dados e terá as seguites
 * características:
 * <li>duas colunas - identificador do nível (índice 0) e descrição do nível
 * (índice 1);
 * <li>quatro linhas - para acomodar os quatro níveis do questionário de
 * maturidade da metodologia PETIC.
 * </p>
 * 
 * @see Cursor
 */
public class CursorNiveis implements Cursor {
	private String[] lista;
	private int position = -1; // ponteiro do cursor. Inicia apontando para uma posição -1 inválida.
							   // Usa-se a função moveToNext() antes de acessar os dados.
	
	public CursorNiveis(Context context) {
		lista = new String [] {// lista dos dados do cursor (níveis dos questionário). Carrega as strings do
		                       // resource para poder ter suporte a múltiplas linguagens.
				context.getResources().getString(R.string.analise_maturidade_nivel_1),
				context.getResources().getString(R.string.analise_maturidade_nivel_2),
				context.getResources().getString(R.string.analise_maturidade_nivel_3),
				context.getResources().getString(R.string.analise_maturidade_nivel_4) };
	}
	
	/**
	 * Não possue ações.
	 */
	public void close() { }

	/**
	 * Copia a informação contida em uma célula referente à posição corrente
	 * e coluna informada para o objeto <code>CharArrayBuffer</code> também
	 * informado.
	 * 
	 * @param columnIndex
	 *            Índice da coluna que está localizada a célula a ser
	 *            copiada (apenas 0 ou 1 para cópia do ID ou descrição do
	 *            nível do questionário apontado).
	 * @param buffer
	 *            Objeto onde será armazenada a informação copiada.
	 */
	public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
		if (columnIndex == 0) {
			buffer.sizeCopied = 1;
			switch (position) {
			case 0:
				buffer.data[0] = '1';
				break;
			case 1:
				buffer.data[0] = '2';
				break;
			case 2:
				buffer.data[0] = '3';
				break;
			case 3:
				buffer.data[0] = '4';
				break;
			default:
				buffer.sizeCopied = 0;
			}
		} else {
			buffer.data = lista[position].toCharArray();
			buffer.sizeCopied = lista[position].length();
		}
	}

	/**
	 * Não possue ações.
	 */
	public void deactivate() {}

	/**
	 * Não possue ações.
	 */
	public byte[] getBlob(int columnIndex) {return null;}

	/**
	 * Retorna o número total de colunas.
	 * 
	 * @return Número de colunas
	 */
	public int getColumnCount() {
		return 2;
	}

	/**
	 * Retorna o índice da coluna informada.
	 * 
	 * @param columnName
	 *            Nome da coluna.
	 * @return Índice da coluna informada.
	 */
	public int getColumnIndex(String columnName) {
		return (columnName == "titulo") ? 1 : 0;
	}

	/**
	 * Retorna o índice da coluna informada.
	 * 
	 * @param columnName
	 *            Nome da coluna.
	 * @return Índice da coluna informada.
	 */
	public int getColumnIndexOrThrow(String columnName)
			throws IllegalArgumentException {
		return (columnName == "titulo") ? 1 : 0;
	}

	/**
	 * Retorna o nome da coluna informada.
	 * 
	 * @param columnIndex
	 *            Índice da coluna.
	 * @return Nome da coluna informada.
	 */
	public String getColumnName(int columnIndex) {
		return (columnIndex == 1) ? "titulo" : "_id";
	}

	/**
	 * Retorna a lista de nomes das colunas <code>Cursor</code>.
	 * 
	 * @return Lista de nomes das colunas do <code>Cursor</code>.
	 */
	public String[] getColumnNames() {
		return new String[] { "_id", "titulo" };
	}

	/**
	 * Retorna a quantidade de linhas do <code>Cursor</code>.
	 * 
	 * @return quantidade de linhas do <code>Cursor</code>.
	 */
	public int getCount() {
		return 4;
	}

	/**
	 * Não possue ações.
	 */
	public double getDouble(int columnIndex) {return 0;}

	/**
	 * Não possue ações.
	 */
	public Bundle getExtras() {return null;}

	/**
	 * Não possue ações.
	 */
	public float getFloat(int columnIndex) {return 0;}

	/**
	 * Retorna o valor contido na célula cuja coluna é informada, quando o
	 * valor é inteiro.
	 * 
	 * @param columnIndex
	 *            Índice da coluna.
	 * @return Valor da contido na célula.
	 */
	public int getInt(int columnIndex) {
		return (columnIndex == 0) ? (position + 1) : 0;
	}

	/**
	 * Retorna o valor contido na célula cuja coluna é informada, quando o
	 * valor pode ser convertido para <code>long</code>.
	 * 
	 * @param columnIndex
	 *            Índice da coluna.
	 * @return Valor da contido na célula.
	 */
	public long getLong(int columnIndex) {
		return (columnIndex == 0) ? (position + 1) : 0;
	}

	/**
	 * Retorna a posição do ponteiro do <code>Cursor</code>.
	 * 
	 * @return Posição apontada pelo ponteiro do <code>Cursor</code>.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Não possue ações.
	 */
	public short getShort(int columnIndex) {return 0;}

	/**
	 * Retorna o valor contido na célula cuja coluna é informada, quando o
	 * valor pode ser convertido para <code>String</code>.
	 * 
	 * @param columnIndex
	 *            Índice da coluna.
	 * @return Valor da contido na célula.
	 */
	public String getString(int columnIndex) {
		if (columnIndex == 0)
			return "" + (position + 1);
		return lista[position];
	}

	/**
	 * Não possue ações.
	 */
	public boolean getWantsAllOnMoveCalls() {return false;}

	/**
	 * Verifica se o ponteiro do <code>Cursor</code> aponta para além da
	 * última posição.
	 * 
	 * @return <code>True</code> caso o ponteiro esteja apontando para além
	 *         da última posição e <code>False</code> no caso contrário.
	 */
	public boolean isAfterLast() {
		return (position > 3);
	}

	/**
	 * Verifica se o ponteiro do <code>Cursor</code> aponta para posições
	 * anteriores à primeira linha.
	 * 
	 * @return <code>True</code> caso o ponteiro esteja apontando para antes
	 *         da primeira posição e <code>False</code> no caso contrário.
	 */
	public boolean isBeforeFirst() {
		return (position < 0);
	}

	/**
	 * Não possue ações.
	 */
	public boolean isClosed() {return false;}

	/**
	 * Verifica se o ponteiro do <code>Cursor</code> aponta para a primeira
	 * posição.
	 * 
	 * @return <code>True</code> caso o ponteiro esteja apontando para a
	 *         primeira posição e <code>False</code> no caso contrário.
	 */
	public boolean isFirst() {
		return (position == 0);
	}

	/**
	 * Verifica se o ponteiro do <code>Cursor</code> aponta para a última
	 * posição.
	 * 
	 * @return <code>True</code> caso o ponteiro esteja apontando para a
	 *         última posição e <code>False</code> no caso contrário.
	 */
	public boolean isLast() {
		return (position == 3);
	}

	/**
	 * Não possue ações.
	 */
	public boolean isNull(int columnIndex) {return false;}

	/**
	 * Move o ponteiro do <code>Cursor</code> a quantidade de posições
	 * informada.
	 * 
	 * @param offset
	 *            Número de posições que serão puladas pelo pontiero.
	 * @return <code>True</code> caso a nova posição seja válida e
	 *         <code>False</code> no caso contrário.
	 */
	public boolean move(int offset) {
		position = +offset;
		return !(isAfterLast() || isBeforeFirst());
	}

	/**
	 * Move o ponteiro do <code>Cursor</code> para a primeira posição.
	 * 
	 * @return <code>True</code> caso a nova posição seja válida e
	 *         <code>False</code> no caso contrário.
	 */
	public boolean moveToFirst() {
		position = -1;
		return true;
	}

	/**
	 * Move o ponteiro do <code>Cursor</code> para a última posição.
	 * 
	 * @return <code>True</code> caso a nova posição seja válida e
	 *         <code>False</code> no caso contrário.
	 */
	public boolean moveToLast() {
		position = 3;
		return true;
	}

	/**
	 * Move o ponteiro do <code>Cursor</code> para a próxima posição.
	 * 
	 * @return <code>True</code> caso a nova posição seja válida e
	 *         <code>False</code> no caso contrário.
	 */
	public boolean moveToNext() {
		position++;
		return !isAfterLast();
	}

	/**
	 * Move o ponteiro do <code>Cursor</code> para a posição indicada.
	 * 
	 * @param position
	 *            Posição do ponteiro.
	 * @return <code>True</code> caso a nova posição seja válida e
	 *         <code>False</code> no caso contrário.
	 */
	public boolean moveToPosition(int position) {
		this.position = position;
		return !(isAfterLast() || isBeforeFirst());
	}

	/**
	 * Move o ponteiro do <code>Cursor</code> para a posição anterior.
	 * 
	 * @return <code>True</code> caso a nova posição seja válida e
	 *         <code>False</code> no caso contrário.
	 */
	public boolean moveToPrevious() {
		position--;
		return !isBeforeFirst();
	}

	/**
	 * Não possue ações.
	 */
	public void registerContentObserver(ContentObserver observer) {}

	/**
	 * Não possue ações.
	 */
	public void registerDataSetObserver(DataSetObserver observer) {}

	/**
	 * Não possue ações.
	 */
	public boolean requery() {return false;}

	/**
	 * Não possue ações.
	 */
	public Bundle respond(Bundle extras) {return null;}

	/**
	 * Não possue ações.
	 */
	public void setNotificationUri(ContentResolver cr, Uri uri) {}

	/**
	 * Não possue ações.
	 */
	public void unregisterContentObserver(ContentObserver observer) {}

	/**
	 * Não possue ações.
	 */
	public void unregisterDataSetObserver(DataSetObserver observer) {}
}
