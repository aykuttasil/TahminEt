package com.aka.tahminet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.text.Html;
import android.view.inputmethod.InputMethodManager;

public class Kutuphane {

	String flurryID = "K4DRB4T2CM8DY746HB5Q";

	public void getAlertDialog(Context context, String baslik, String mesaj) {
		// AlertDialog.Builder ile AlertDialog nesnesini düzenle
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle(baslik);
		alertDialogBuilder
				.setMessage(mesaj)
				.setCancelable(false)
				.setPositiveButton("Tamam",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();

							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public ArrayList<String> getDosya(Context context, String dosyaadi) {
		ArrayList<String> dizi = new ArrayList<String>();

		try {
			InputStream dosya = context.getAssets().open(dosyaadi);

			// InputStream dss =
			// context.getResources().openRawResource(R.raw.qwerty);

			BufferedReader qq = new BufferedReader(new InputStreamReader(dosya));
			String receivestring = qq.readLine();

			while (receivestring != null) {

				dizi.add(receivestring);

				receivestring = qq.readLine();
			}

			qq.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return dizi;

	}

	public String TemizString(String deger) {
		String yenideger = deger.trim().replace(".", "").replace(" ", "")
				.replace("ö", "o").replace("ü", "u").replace(",", "")
				.replace("ý", "i");
		return "_" + yenideger;
	}

	// android.permission.ACCESS_NETWORK_STATE iznini almayý unutma
	public boolean internetErisimi(Context context) {

		ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (conMgr.getActiveNetworkInfo() != null
				&& conMgr.getActiveNetworkInfo().isAvailable()
				&& conMgr.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public String TagTemizle(String deger) {

		String sonuc = deger.trim();
		sonuc = Html.fromHtml(sonuc).toString();
		while (sonuc.contains("<")) {

			StringBuilder strbuild = new StringBuilder(sonuc);

			int tagBaslangicIndis = strbuild.indexOf("<");
			int tagBitisIndis = strbuild.indexOf(">", tagBaslangicIndis);

			strbuild.delete(tagBaslangicIndis, tagBitisIndis + 1);

			sonuc = strbuild.toString();

		}
		return sonuc;
	}


	public void sharedPreferencesEdit(Context context, String key, String value) {

		SharedPreferences shr = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = shr.edit();
		editor.putString(key, value);
		editor.commit();

	}

	public String getsharedPreference(Context context, String key) {
		SharedPreferences shr = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);

		return shr.getString(key, null);

	}

	public String getPackageVersionName(Context context) {
		try {
			String versionname = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName.toString();
			return versionname;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public int getPackageVersionCode(Context context) {
		try {
			int versioncode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
			return versioncode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	public int getAndroidSdkVersionCode() {
		int androidverisonsdkint = android.os.Build.VERSION.SDK_INT;
		return androidverisonsdkint;
	}

	public String getAndroidSdkVersionName() {
		String androidverisonsdkint = android.os.Build.VERSION.CODENAME;
		return androidverisonsdkint;
	}

	public static void hideKeyboard(Activity activity) {
		InputMethodManager inputManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (activity.getCurrentFocus() != null) {
			inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	
	
}
