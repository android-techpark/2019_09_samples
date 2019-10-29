package superp.techpark.ru.lesson5;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AsyncTaskActivity extends AppCompatActivity implements ProgressListener {

    private ProgressBar mProgressBar;
    private MyTask mTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int maxProgress = 10;
        setContentView(R.layout.activity_asynctask);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTask != null) {
                    mTask.cancel(true);
                }
                mTask = new MyTask(AsyncTaskActivity.this);
                mTask.execute(maxProgress);
                mProgressBar.setProgress(0);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });

        mProgressBar = findViewById(R.id.progress);
        mProgressBar.setMax(maxProgress);

        if (getLastCustomNonConfigurationInstance() != null) {
            mTask = (MyTask) getLastCustomNonConfigurationInstance();
            mTask.mProgressListener = this;
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mTask;
    }

    @Override
    public void onProgressUpdate(int progress) {
        mProgressBar.setProgress(progress);
    }

    @Override
    public void onFinish() {
        mProgressBar.setVisibility(View.GONE);
        mTask = null;
        TextView res = findViewById(R.id.result);
        res.setText("Done!");
    }

    static class MyTask extends AsyncTask<Integer, Integer, Boolean> {

        @SuppressLint("StaticFieldLeak")
        private ProgressListener mProgressListener;

        public MyTask(ProgressListener ref) {
            mProgressListener = ref;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (mProgressListener != null && !isCancelled()) {
                mProgressListener.onProgressUpdate(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (mProgressListener != null) {
                mProgressListener.onFinish();
            }
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                for (int i = 0; i < params[0]; i++) {
                    Thread.sleep(1000);
                    publishProgress(i);
                    if (isCancelled()) {
                        return false;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

}

