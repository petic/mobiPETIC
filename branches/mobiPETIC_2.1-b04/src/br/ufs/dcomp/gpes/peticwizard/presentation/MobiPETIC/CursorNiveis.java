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
 * Classe <code>Cursor</code> que inicia um Cursor com os n�veis que
 * agrupar�o as quest�es do question�rio de maturidade no ExpandableList.
 * <p>
 * Este <code>Cursor</code> iniciar� seus pr�prios dados e ter� as seguites
 * caracter�sticas:
 * <li>duas colunas - identificador do n�vel (�ndice 0) e descri��o do n�vel
 * (�ndice 1);
 * <li>quatro linhas - para acomodar os quatro n�veis do question�rio de
 * maturidade da metodologia PETIC.
 * </p>
 * 
 * @see Cursor
 */
public class CursorNiveis implements Cursor {
	private String[] lista;
	private int position = -1; // ponteiro do cursor. Inicia apontando para uma posi��o -1 inv�lida.
							   // Usa-se a fun��o moveToNext() antes de acessar os dados.
	
	public CursorNiveis(Context context) {
		lista = new String [] {// lista dos dados do cursor (n�veis dos question�rio). Carrega as strings do
		                       // resource para poder ter suporte a m�ltiplas linguagens.
				context.getResources().getString(R.string.analise_maturidade_nivel_1),
				context.getResources().getString(R.string.analise_maturidade_nivel_2),
				context.getResources().getString(R.string.analise_maturidade_nivel_3),
				context.getResources().getString(R.string.analise_maturidade_nivel_4) };
	}
	
	/**
	 * N�o possue a��es.
	 */
	public void close() { }

	/**
	 * Copia a informa��o contida em uma c�lula referente � posi��o corrente
	 * e coluna informada para o objeto <code>CharArrayBuffer</code> tamb�m
	 * informado.
	 * 
	 * @param columnIndex
	 *            �ndice da coluna que est� localizada a c�lula a ser
	 *            copiada (apenas 0 ou 1 para c�pia do ID ou descri��o do
	 *            n�vel do question�rio apontado).
	 * @param buffer
	 *            Objeto onde ser� armazenada a informa��o copiada.
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
	 * N�o possue a��es.
	 */
	public void deactivate() {}

	/**
	 * N�o possue a��es.
	 */
	public byte[] getBlob(int columnIndex) {return null;}

	/**
	 * Retorna o n�mero total de colunas.
	 * 
	 * @return N�mero de colunas
	 */
	public int getColumnCount() {
		return 2;
	}

	/**
	 * Retorna o �ndice da coluna informada.
	 * 
	 * @param columnName
	 *            Nome da coluna.
	 * @return �ndice da coluna informada.
	 */
	public int getColumnIndex(String columnName) {
		return (columnName == "titulo") ? 1 : 0;
	}

	/**
	 * Retorna o �ndice da coluna informada.
	 * 
	 * @param columnName
	 *            Nome da coluna.
	 * @return �ndice da coluna informada.
	 */
	public int getColumnIndexOrThrow(String columnName)
			throws IllegalArgumentException {
		return (columnName == "titulo") ? 1 : 0;
	}

	/**
	 * Retorna o nome da coluna informada.
	 * 
	 * @param columnIndex
	 *            �ndice da coluna.
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
	 * N�o possue a��es.
	 */
	public double getDouble(int columnIndex) {return 0;}

	/**
	 * N�o possue a��es.
	 */
	public Bundle getExtras() {return null;}

	/**
	 * N�o possue a��es.
	 */
	public float getFloat(int columnIndex) {return 0;}

	/**
	 * Retorna o valor contido na c�lula cuja coluna � informada, quando o
	 * valor � inteiro.
	 * 
	 * @param columnIndex
	 *            �ndice da coluna.
	 * @return Valor da contido na c�lula.
	 */
	public int getInt(int columnIndex) {
		return (columnIndex == 0) ? (position + 1) : 0;
	}

	/**
	 * Retorna o valor contido na c�lula cuja coluna � informada, quando o
	 * valor pode ser convertido para <code>long</code>.
	 * 
	 * @param columnIndex
	 *            �ndice da coluna.
	 * @return Valor da contido na c�lula.
	 */
	public long getLong(int columnIndex) {
		return (columnIndex == 0) ? (position + 1) : 0;
	}

	/**
	 * Retorna a posi��o do ponteiro do <code>Cursor</code>.
	 * 
	 * @return Posi��o apontada pelo ponteiro do <code>Cursor</code>.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * N�o possue a��es.
	 */
	public short getShort(int columnIndex) {return 0;}

	/**
	 * Retorna o valor contido na c�lula cuja coluna � informada, quando o
	 * valor pode ser convertido para <code>String</code>.
	 * 
	 * @param columnIndex
	 *            �ndice da coluna.
	 * @return Valor da contido na c�lula.
	 */
	public String getString(int columnIndex) {
		if (columnIndex == 0)
			return "" + (position + 1);
		return lista[position];
	}

	/**
	 * N�o possue a��es.
	 */
	public boolean getWantsAllOnMoveCalls() {return false;}

	/**
	 * Verifica se o ponteiro do <code>Cursor</code> aponta para al�m da
	 * �ltima posi��o.
	 * 
	 * @return <code>True</code> caso o ponteiro esteja apontando para al�m
	 *         da �ltima posi��o e <code>False</code> no caso contr�rio.
	 */
	public boolean isAfterLast() {
		return (position > 3);
	}

	/**
	 * Verifica se o ponteiro do <code>Cursor</code> aponta para posi��es
	 * anteriores � primeira linha.
	 * 
	 * @return <code>True</code> caso o ponteiro esteja apontando para antes
	 *         da primeira posi��o e <code>False</code> no caso contr�rio.
	 */
	public boolean isBeforeFirst() {
		return (position < 0);
	}

	/**
	 * N�o possue a��es.
	 */
	public boolean isClosed() {return false;}

	/**
	 * Verifica se o ponteiro do <code>Cursor</code> aponta para a primeira
	 * posi��o.
	 * 
	 * @return <code>True</code> caso o ponteiro esteja apontando para a
	 *         primeira posi��o e <code>False</code> no caso contr�rio.
	 */
	public boolean isFirst() {
		return (position == 0);
	}

	/**
	 * Verifica se o ponteiro do <code>Cursor</code> aponta para a �ltima
	 * posi��o.
	 * 
	 * @return <code>True</code> caso o ponteiro esteja apontando para a
	 *         �ltima posi��o e <code>False</code> no caso contr�rio.
	 */
	public boolean isLast() {
		return (position == 3);
	}

	/**
	 * N�o possue a��es.
	 */
	public boolean isNull(int columnIndex) {return false;}

	/**
	 * Move o ponteiro do <code>Cursor</code> a quantidade de posi��es
	 * informada.
	 * 
	 * @param offset
	 *            N�mero de posi��es que ser�o puladas pelo pontiero.
	 * @return <code>True</code> caso a nova posi��o seja v�lida e
	 *         <code>False</code> no caso contr�rio.
	 */
	public boolean move(int offset) {
		position = +offset;
		return !(isAfterLast() || isBeforeFirst());
	}

	/**
	 * Move o ponteiro do <code>Cursor</code> para a primeira posi��o.
	 * 
	 * @return <code>True</code> caso a nova posi��o seja v�lida e
	 *         <code>False</code> no caso contr�rio.
	 */
	public boolean moveToFirst() {
		position = -1;
		return true;
	}

	/**
	 * Move o ponteiro do <code>Cursor</code> para a �ltima posi��o.
	 * 
	 * @return <code>True</code> caso a nova posi��o seja v�lida e
	 *         <code>False</code> no caso contr�rio.
	 */
	public boolean moveToLast() {
		position = 3;
		return true;
	}

	/**
	 * Move o ponteiro do <code>Cursor</code> para a pr�xima posi��o.
	 * 
	 * @return <code>True</code> caso a nova posi��o seja v�lida e
	 *         <code>False</code> no caso contr�rio.
	 */
	public boolean moveToNext() {
		position++;
		return !isAfterLast();
	}

	/**
	 * Move o ponteiro do <code>Cursor</code> para a posi��o indicada.
	 * 
	 * @param position
	 *            Posi��o do ponteiro.
	 * @return <code>True</code> caso a nova posi��o seja v�lida e
	 *         <code>False</code> no caso contr�rio.
	 */
	public boolean moveToPosition(int position) {
		this.position = position;
		return !(isAfterLast() || isBeforeFirst());
	}

	/**
	 * Move o ponteiro do <code>Cursor</code> para a posi��o anterior.
	 * 
	 * @return <code>True</code> caso a nova posi��o seja v�lida e
	 *         <code>False</code> no caso contr�rio.
	 */
	public boolean moveToPrevious() {
		position--;
		return !isBeforeFirst();
	}

	/**
	 * N�o possue a��es.
	 */
	public void registerContentObserver(ContentObserver observer) {}

	/**
	 * N�o possue a��es.
	 */
	public void registerDataSetObserver(DataSetObserver observer) {}

	/**
	 * N�o possue a��es.
	 */
	public boolean requery() {return false;}

	/**
	 * N�o possue a��es.
	 */
	public Bundle respond(Bundle extras) {return null;}

	/**
	 * N�o possue a��es.
	 */
	public void setNotificationUri(ContentResolver cr, Uri uri) {}

	/**
	 * N�o possue a��es.
	 */
	public void unregisterContentObserver(ContentObserver observer) {}

	/**
	 * N�o possue a��es.
	 */
	public void unregisterDataSetObserver(DataSetObserver observer) {}
}
