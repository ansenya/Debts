package ru.ivanmurzin.debts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.ivanmurzin.debts.database.room.DebtDAO;
import ru.ivanmurzin.debts.database.room.DebtEntity;
import ru.ivanmurzin.debts.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DebtDAO debtDAO = App.getDatabase().debtDAO();
        List<DebtEntity> data = new ArrayList<>();
        new Thread(() -> {
            List<DebtEntity> debts = debtDAO.getAll();
            runOnUiThread(() -> {
                data.addAll(debts);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            });
        }).start();

        binding.recyclerView.setAdapter(new MyDebtsAdapter(data, new MyDebtsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (v.getId() == R.id.button){
                    FragmentManager manager = getSupportFragmentManager();
                    MyDialogFragment myDialogFragment = new MyDialogFragment(debtDAO, data, position, binding.recyclerView.getAdapter());
                    myDialogFragment.show(manager, "myDialog");
                    }
                else {
                    Toast.makeText(MainActivity.this, "Тут типа изменение должно быть", Toast.LENGTH_SHORT).show();
                }

            }
        }));

        binding.add.setOnClickListener(view -> {
            DebtEntity debt = new DebtEntity("123", (int) (Math.random() * 1000));
            data.add(debt);
            new Thread(() -> {
                debtDAO.save(debt);
            }).start();
            binding.recyclerView.getAdapter().notifyItemChanged(data.size() - 1);
        });
    }
}