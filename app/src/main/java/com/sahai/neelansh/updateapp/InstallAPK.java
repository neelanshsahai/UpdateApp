package com.sahai.neelansh.updateapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InstallAPK extends AsyncTask<String, Integer, Boolean> {

    private Context context;
    private ProgressDialog progressBar;

    public void setContext(Context c, ProgressDialog bar) {
        this.context = c;
        this.progressBar = bar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setMessage("DOWNLOADING...");
        progressBar.setIndeterminate(true);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.setProgress(values[0]);
        String msg = "";
        if(values[0]>99)
            msg = "Finalising...";
        else
            msg = "Downloading...";
        progressBar.setMessage(msg);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressBar.dismiss();
        if (aBoolean)
            Toast.makeText(context, "Update Done", Toast.LENGTH_SHORT).show();
        else {
            Log.e("e", "error");
            Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        InputStream inputStream = null;
        OutputStream output = null;
        HttpURLConnection c = null;
        Boolean flag = true;
        try {
            //Download the APK from the link

            //TODO: <@Praksh2403> app install karne se pehle app settings me jaake storage permisions de dena

            URL url = new URL("https://www.appsapk.com/downloading/latest/SHAREit%20-%20Transfer%20&%20Share-3.8.8_ww.apk");
            c = (HttpURLConnection) url.openConnection();
            c.connect();

            int fileSize = c.getContentLength();

            String PATH = Environment.getExternalStorageDirectory()+"/NEELANSH/";
            File file = new File(PATH);
            File outFile = new File(file, "app.apk");
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            inputStream = c.getInputStream();
            output = new FileOutputStream(outFile.getAbsolutePath());

            byte[] data = new byte[4096];
            long total = 0;
            int count, i=0;

            while((count = inputStream.read(data)) != -1) {
                total += count;
                if(fileSize > 0)
                    publishProgress((int) (total*100)/fileSize);
                output.write(data, 0, count);
            }

            installApkFile(PATH, outFile);

        } catch (Exception e) {
            Log.e("doInBackground()...", String.valueOf(e));
            flag = false;
        }
        return flag;
    }

    private void installApkFile(String location, File f) {
        Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".provider", f);
        Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        install.setDataAndType(apkUri, "application/vnd.android.package-archive");
//        install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
//        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }
}
