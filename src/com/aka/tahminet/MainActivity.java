package com.aka.tahminet;

import java.util.Random;

import android.content.res.Resources;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private static String HIGH_SCORE = "highscore";

	Kutuphane kutuphane = new Kutuphane();

	Resources resource = null;

	Random rnd = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		resource = getResources();
		denemesayisi = resource.getInteger(R.integer.denemesayisi);
	}

	int tahminHakki = 0;
	int tahminSayi;
	int denemesayisi = 0;

	TextView textview_higscore;

	public void btn_DogruMu_Click(View vi) {

		TextView textviewkalanhak = (TextView) findViewById(R.id.textViewkalanHak);

		TextView textview_girilensayi = (TextView) findViewById(R.id.textview_girilensayi);
		TextView textview_girilensayi_title = (TextView) findViewById(R.id.textview_girilensayi_title);
		textview_girilensayi_title.setVisibility(ViewGroup.VISIBLE);

		EditText girilensayi = (EditText) findViewById(R.id.edittext_girilenSayi);
		if (girilensayi.getText().toString().equals(""))
			return;

		if (tahminHakki == 0) {
			tahminSayi = rnd.nextInt(100);
		}

		if (tahminHakki == denemesayisi) {
			SonucYaz(resource.getString(R.string.kaybettin));
			tahminHakki = 0;
			textviewkalanhak.setText(resource.getString(R.string.kalanhak)
					+ (denemesayisi - tahminHakki));
			return;
		}

		int sayi = Integer.parseInt(girilensayi.getText().toString());

		if (sayi == tahminSayi) {

			TextView textview_higscore_title = (TextView) findViewById(R.id.textview_higscore_title);
			textview_higscore_title.setVisibility(ViewGroup.VISIBLE);
			HighScoreGoster(tahminHakki);

			SonucYaz(resource.getString(R.string.kazandin));
			tahminHakki = 0;
			textviewkalanhak.setText(resource.getString(R.string.kalanhak)
					+ (denemesayisi));

			return;
		} else if (Math.abs(tahminSayi - sayi) <= 5) {
			SonucYaz(resource.getString(R.string.coksicak));
		} else if (Math.abs(tahminSayi - sayi) <= 10) {
			SonucYaz(resource.getString(R.string.sicak));
		} else if (Math.abs(tahminSayi - sayi) <= 20) {
			SonucYaz(resource.getString(R.string.soguk));
		} else if (Math.abs(tahminSayi - sayi) <= 30) {
			SonucYaz(resource.getString(R.string.coksoguk));
		} else {
			SonucYaz("yuh");
		}

		tahminHakki++;

		textviewkalanhak.setText(resource.getString(R.string.kalanhak)
				+ (denemesayisi - tahminHakki));

		textview_girilensayi.setText(girilensayi.getText().toString());
		girilensayi.setText("");

	}

	private void HighScoreGoster(int score) {

		String currentscore = kutuphane.getsharedPreference(this, HIGH_SCORE);
		if (currentscore != null) {
			int highscore = Integer.parseInt(currentscore);

			if (score < highscore) {

				HighScoreDuzenle(score);
			}
		} else {
			HighScoreDuzenle(score);
		}
	}

	private void HighScoreDuzenle(int score) {
		String scr = Integer.toString(score);
		kutuphane.sharedPreferencesEdit(this, HIGH_SCORE, scr);

		textview_higscore = (TextView) findViewById(R.id.textview_higscore);
		textview_higscore.setText(scr);
	}

	private void SonucYaz(String derece) {
		TextView textviewSonuc = (TextView) findViewById(R.id.textViewSonuc);
		if (derece == resource.getString(R.string.kaybettin)) {
			textviewSonuc.setTextColor(resource.getColor(R.color.kaybettin));
			textviewSonuc.setText(resource.getString(R.string.kaybettin));
		} else if (derece == resource.getString(R.string.kazandin)) {
			textviewSonuc.setTextColor(resource.getColor(R.color.kazandin));
			textviewSonuc.setText(resource.getString(R.string.kazandin));
		} else if (derece == resource.getString(R.string.coksicak)) {
			textviewSonuc.setTextColor(resource.getColor(R.color.coksicak));
			textviewSonuc.setText(resource.getString(R.string.coksicak));
		} else if (derece == resource.getString(R.string.sicak)) {
			textviewSonuc.setTextColor(resource.getColor(R.color.sicak));
			textviewSonuc.setText(resource.getString(R.string.sicak));
		} else if (derece == resource.getString(R.string.soguk)) {
			textviewSonuc.setTextColor(resource.getColor(R.color.soguk));
			textviewSonuc.setText(resource.getString(R.string.soguk));
		} else if (derece == resource.getString(R.string.coksoguk)) {
			textviewSonuc.setTextColor(resource.getColor(R.color.coksoguk));
			textviewSonuc.setText(resource.getString(R.string.coksoguk));
		} else {
			textviewSonuc.setTextColor(resource.getColor(R.color.coksoguk));
			textviewSonuc.setText("Ben ne diyorum sen ne diyosun !");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.action_sifirla) {

			kutuphane.sharedPreferencesEdit(this, HIGH_SCORE, null);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
