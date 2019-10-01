package superp.techpark.ru.lesson2;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListActivity extends AppCompatActivity {

    private MyDataAdapter mAdapter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = findViewById(R.id.list);

        mAdapter = new MyDataAdapter(DataSource.getInstance().getData());
        int horizontal = getResources().getBoolean(R.bool.is_horizontal) ?
                LinearLayoutManager.HORIZONTAL : LinearLayout.VERTICAL;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, horizontal, false));
        recyclerView.setAdapter(mAdapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(mAdapter.TYPE_FIRST, 10);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(mAdapter.TYPE_SECOND, 10);
    }

    class MyDataAdapter extends RecyclerView.Adapter<MyViewHolder> {

        final int TYPE_FIRST = 0;
        final int TYPE_SECOND = 1;
        List<DataSource.MyData> mData;

        public MyDataAdapter(List<DataSource.MyData> data) {
            mData = data;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("ListActivity", "onCreateViewHolder " + viewType);
            switch (viewType) {
                case TYPE_FIRST:
                    View view1 = LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.list_item, parent, false);
                    return new MyViewHolder(view1);
                case TYPE_SECOND:
                    View view2 = LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.list_item_2, parent, false);
                    return new MyViewHolder2(view2);
                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            DataSource.MyData data = mData.get(position);
            holder.mTitle.setText(data.mTitle);
            holder.mDate.setText(data.mDate);

            Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(data.mColor);
            holder.mImageView.setImageBitmap(bitmap);

            Log.d("ListActivity", "onBindViewHolder " + position);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position % 2 == 0) ? TYPE_FIRST : TYPE_SECOND;
        }
    }


    class MyViewHolder2 extends MyViewHolder {
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;
        private final TextView mTitle;
        private final TextView mDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mTitle = itemView.findViewById(R.id.title);
            mDate = itemView.findViewById(R.id.date);
            mImageView.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                DataSource.MyData myData = mAdapter.mData.get(pos);
                Toast.makeText(getApplicationContext(),
                        myData.mTitle,
                        Toast.LENGTH_LONG)
                        .show();
            });
        }
    }
}
