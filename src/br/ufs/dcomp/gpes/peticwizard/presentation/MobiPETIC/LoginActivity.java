package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import java.io.InputStream;

import org.apache.http.conn.HttpHostConnectException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.protobuf.ByteString;

/**
 * <code>Activity</code> que define o comportamento do Login Principal da
 * aplicação.
 * 
 * @see Activity
 */
public class LoginActivity extends Activity {

	/**
	 * Executado automaticamente no momento de criação da <code>Activity</code>.
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// overridePendingTransition(R.anim.appear, R.anim.donothing);
		try {
			setContentView(R.layout.login);

			LinearLayout layout = (LinearLayout) findViewById(R.id.layoutLoginInterior);
			Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.appear);
			// Animation fade2 = AnimationUtils.loadAnimation(this,
			// R.anim.disappear);
			layout.startAnimation(fade1);

			final EditText editUsuario = (EditText) findViewById(R.id.editTextUsuario);
			final EditText editSenha = (EditText) findViewById(R.id.editTextSenha);
			final ProgressBar progressLoad = (ProgressBar) findViewById(R.id.progressBarLoad);

			final Button login = (Button) findViewById(R.id.buttonLogin);
			login.setOnClickListener(new View.OnClickListener() {
				public void onClick(final View v) {
					final String usuario = editUsuario.getText().toString();
					final String senha = editSenha.getText().toString();

					if (!usuario.equals("") && !senha.equals("")) {
						final Intent myIntent = new Intent(v.getContext(),
								TelaPrincipalActivity.class);
						progressLoad.setVisibility(View.VISIBLE);
						new Thread(new Runnable() {
							public void run() {
								DadosIniciais.Usuario dados = null;
								try {
									// dados =
									// DadosIniciais.Usuario.parseFrom(validarUsuario(usuario,
									// senha));
									dados = validarUsuario(usuario, senha);
								} catch (HttpHostConnectException e) {
									e.printStackTrace();
									runOnUiThread(new Runnable() {
										public void run() {
											AlertDialog.Builder builder = new AlertDialog.Builder(
													v.getContext());
											builder.setTitle(R.string.titulo_mensagem_erro_sem_resposta);
											builder.setMessage(R.string.mensagem_erro_sem_resposta);
											builder.setPositiveButton(
													getResources()
															.getString(
																	R.string.botao_confirmacao),
													null);
											AlertDialog alert = builder
													.create();
											alert.show();
										}
									});
								} catch (Exception e) {
									e.printStackTrace();
									runOnUiThread(new Runnable() {
										public void run() {
											AlertDialog.Builder builder = new AlertDialog.Builder(
													v.getContext());
											builder.setTitle(R.string.titulo_mensagem_erro_sem_conexao);
											builder.setMessage(R.string.mensagem_erro_sem_conexao);
											builder.setPositiveButton(
													getResources()
															.getString(
																	R.string.botao_confirmacao),
													null);
											AlertDialog alert = builder
													.create();
											alert.show();
										}
									});
								}
								if (dados != null && dados.getEmpresaId() != -1) {
									final DadosIniciais.Usuario dadosValidos = dados;
									runOnUiThread(new Runnable() {
										public void run() {
											myIntent.putExtra("idEmpresa",
													dadosValidos.getEmpresaId())
													.putExtra(
															"logo",
															dadosValidos
																	.getEmpresaLogo()
																	.toByteArray())
													.putExtra(
															"logoLength",
															dadosValidos
																	.getId())
													.putExtra("nomeUsuario",
															usuario);
											startActivity(myIntent);
										}
									});
								} else
									runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(
													v.getContext(),
													R.string.mensagem_erro_usuario_senha_incorreta,
													Toast.LENGTH_LONG).show();
											((ProgressBar) findViewById(R.id.progressBarLoad))
													.setVisibility(View.INVISIBLE);
										}
									});
							}
						}).start();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executado automaticamente quando a <code>Activity</code> é destruida.
	 * 
	 */
	@Override
	public void onResume() {
		super.onResume();
		((ProgressBar) findViewById(R.id.progressBarLoad))
				.setVisibility(View.INVISIBLE);
	}

	/**
	 * Interage com o servidor para confirmar a autenticidade do usuário.
	 * 
	 * @param usuario
	 *            nome do usuário.
	 * @param senha
	 *            senha do usuário.
	 * @return Objeto <code>InputStream</code> com um arquivo binário Protocol
	 *         Buffers com os dados iniciais para a aplicação, caso o usuário
	 *         seja válido, ou um código especial que identifique sua
	 *         invalidade.
	 * @throws Exception
	 */
	// private InputStream validarUsuario(String usuario, String senha) throws
	// Exception {
	private DadosIniciais.Usuario validarUsuario(String usuario, String senha)
			throws Exception {
		/*
		 * final HttpClient client = new DefaultHttpClient(); final HttpGet
		 * request = new HttpGet(new
		 * URI("http://177.49.214.167:8082/Teste/validarusuario?usuario="
		 * +usuario+"&senha="+senha)); HttpResponse response =
		 * client.execute(request); return response.getEntity().getContent();
		 */
		DadosIniciais.Usuario.Builder dados = DadosIniciais.Usuario
				.newBuilder();
		if (usuario.equals("usuario1") && senha.equals("empresa1")) {
			InputStream logo = getResources().openRawResource(
					R.drawable.company1);
			byte bff[] = new byte[20000];
			int x = logo.read(bff);
			dados.setId(x).setEmpresaId(1)
					.setEmpresaLogo(ByteString.copyFrom(bff));
		} else if (usuario.equals("usuario2") && senha.equals("empresa2")) {
			InputStream logo = getResources().openRawResource(
					R.drawable.company2);
			byte bff[] = new byte[20000];
			int x = logo.read(bff);
			dados.setId(x).setEmpresaId(2)
					.setEmpresaLogo(ByteString.copyFrom(bff));
		} else if (usuario.equals("usuario3") && senha.equals("empresa3")) {
			InputStream logo = getResources().openRawResource(
					R.drawable.company3);
			byte bff[] = new byte[20000];
			int x = logo.read(bff);
			dados.setId(x).setEmpresaId(3)
					.setEmpresaLogo(ByteString.copyFrom(bff));
		} else if (usuario.equals("u") && senha.equals("e")) {
			InputStream logo = getResources().openRawResource(
					R.drawable.company1);
			byte bff[] = new byte[20000];
			int x = logo.read(bff);
			dados.setId(x).setEmpresaId(1)
					.setEmpresaLogo(ByteString.copyFrom(bff));
		} else
			dados.setId(-1).setEmpresaId(-1);
		return dados.build();
	}
}