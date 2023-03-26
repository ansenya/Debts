package ru.ivanmurzin.debts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.ivanmurzin.debts.database.room.DebtEntity;
import ru.ivanmurzin.debts.databinding.ItemDebtBinding;

public class MyDebtsAdapter extends RecyclerView.Adapter<MyDebtsAdapter.MyHolder> {
    private final ClickListener clickListener;
    List<DebtEntity> data;

    ItemDebtBinding binding;

    public MyDebtsAdapter(List<DebtEntity> data, ClickListener listener) {
        this.data = data;
        clickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_debt, parent, false);
        binding = ItemDebtBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(data.get(position).name);
        holder.money.setText(String.valueOf(data.get(position).money));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView money;
        public Button button;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.button).setOnClickListener(this);
            itemView.findViewById(R.id.button2).setOnClickListener(this);
            name = itemView.findViewById(R.id.name);
            money = itemView.findViewById(R.id.money);
        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
