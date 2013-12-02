package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * <code>Activity</code> que define o comportamento do Layout Principal da
 * aplicação.
 * 
 * @see Activity
 */
public class MobiPETICActivity extends Activity {

	private final int DURACAO_DA_TELA = 4495;

	/**
	 * Executado automaticamente no momento de criação da <code>Activity</code>.
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tela_abertura);

		final ImageView logo1 = (ImageView) findViewById(R.id.imageViewLogo);
		Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		logo1.startAnimation(fade1);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				getWindow().setWindowAnimations(0);
				Intent myAction = new Intent(getApplicationContext(),
						LoginActivity.class);
				logo1.setVisibility(View.INVISIBLE);
				startActivity(myAction);
				finish();
			}

		}, DURACAO_DA_TELA);
	}

	/**
	 * Executado automaticamente quando a <code>Activity</code> é destruida.
	 * 
	 */
	@Override
	public void onDestroy() {
		overridePendingTransition(0, 0);
		super.onDestroy();
	}
}