package superp.techpark.ru.lesson2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListActivity extends AppCompatActivity {

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView list = findViewById(R.id.list);

        list.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        );
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);


        new Handler().postDelayed(() -> adapter.updateWith(DataSource.getInstance().getData()), 2000);
    }


    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        private final List<DataSource.MyData> datas = new ArrayList<>();

        public void updateWith(List<DataSource.MyData> newData) {
            datas.clear();
            datas.addAll(newData);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layoutToInflate = viewType == 0 ?
                    R.layout.list_item :
                    R.layout.list_item_2;

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(layoutToInflate, parent, false);
            if (viewType == 0) {
                return new MyHolder(view);
            } else {
                return new MyHolderV2(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            DataSource.MyData data = datas.get(position);
            holder.mImage.setBackgroundColor(data.mColor);
            holder.mTitle.setText(data.mTitle);
            holder.mDate.setText(data.mDate);
            holder.bindClickListener(position);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mTitle;
        private TextView mDate;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.date);
            mImage = itemView.findViewById(R.id.image);
            mTitle = itemView.findViewById(R.id.title);
        }

        void bindClickListener(int position) {
            itemView.findViewById(R.id.save_btn).setOnClickListener(v ->
                    Toast.makeText(getApplicationContext(), "Clicked on item with pos " + position, Toast.LENGTH_SHORT).show()
            );
        }
    }

    class MyHolderV2 extends MyHolder {
        private ImageView mPlayIcon;

        public MyHolderV2(@NonNull View itemView) {
            super(itemView);
            mPlayIcon = itemView.findViewById(R.id.play_icon);
        }

        @Override
        void bindClickListener(int position) {
            itemView.findViewById(R.id.save_btn).setOnClickListener(v -> {
                        Toast.makeText(getApplicationContext(), "Clicked on item with pos " + position, Toast.LENGTH_SHORT).show();
                        if (mPlayIcon.getVisibility() == View.VISIBLE) {
                            mPlayIcon.setVisibility(View.GONE);
                        } else {
                            mPlayIcon.setVisibility(View.VISIBLE);
                        }
                    }
            );
        }
    }
}
