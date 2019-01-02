package pl.lodz.p.interiordesignapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pl.lodz.p.interiordesignapp.model.DesignObject;
import pl.lodz.p.interiordesignapp.utils.AppConst;

// usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
public class DownloadTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;
    private ProgressDialog mProgressDialog;
    private DesignObject designObject;
    private static final String JPEG_EXT = ".jpg";
    private static final String SFB_EXT = ".sfb";

    public DownloadTask(Context context, ProgressDialog progressDialog, DesignObject designObject) {
        this.context = context;
        this.mProgressDialog = progressDialog;
        this.designObject = designObject;
    }

    @Override
    protected String doInBackground(String... urls) {
        if(urls.length != 2 || designObject.getName() == null ||
                designObject.getName().equals("") || designObject.getCategory() == null ||
                designObject.getCategory().equals("")) {
            return null;
        }
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connectionJPG = null;
        HttpURLConnection connectionSFB = null;
        try {
            URL urlJPG = new URL(urls[0]);
            URL urlSFB = new URL(urls[1]);
            connectionJPG = (HttpURLConnection) urlJPG.openConnection();
            connectionJPG.connect();
            connectionSFB = (HttpURLConnection) urlSFB.openConnection();
            connectionSFB.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connectionJPG.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connectionJPG.getResponseCode()
                        + " " + connectionJPG.getResponseMessage();
            }

            if (connectionSFB.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connectionSFB.getResponseCode()
                        + " " + connectionSFB.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int filesLength = connectionJPG.getContentLength() + connectionSFB.getContentLength();
            if(downloadFile(JPEG_EXT, connectionJPG, filesLength, 0) == null) {
                return null;
            }
            if(downloadFile(SFB_EXT, connectionSFB, filesLength, connectionJPG.getContentLength()) == null) {
                return null;
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connectionJPG != null) {
                connectionJPG.disconnect();
            }
            if (connectionSFB != null) {
                connectionSFB.disconnect();
            }
        }
        return null;
    }

    private String downloadFile(String extension, HttpURLConnection connection, int filesLength, long totalDownload) throws IOException{
        InputStream input = null;
        OutputStream output = null;
        input = connection.getInputStream();


        File file = new File( context.getFilesDir() + "/" + AppConst.MODELS_DIR + "/" + designObject.getCategory() + "/" + designObject.getName() + extension);
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        file.createNewFile();
        output = new FileOutputStream(file);

        byte data[] = new byte[4096];
        long total = totalDownload;
        int count;
        while ((count = input.read(data)) != -1) {
            // allow canceling with back button
            if (isCancelled()) {
                input.close();
                return null;
            }
            total += count;
            // publishing the progress....
            if (filesLength > 0) { // only if total length is known
                publishProgress((int) (total * 100 / filesLength));
            }
            output.write(data, 0, count);
        }

        return "";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        mProgressDialog.dismiss();
        if (result != null) {
            Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }
    }
}