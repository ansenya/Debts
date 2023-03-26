package ru.ivanmurzin.debts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.ivanmurzin.debts.database.room.DebtDAO;
import ru.ivanmurzin.debts.database.room.DebtEntity;

public class MyDialogFragment extends DialogFragment {
    Boolean agree;
    DebtDAO debtDAO;
    List<DebtEntity> data;
    int position;
    RecyclerView.Adapter adapter;

    public MyDialogFragment(DebtDAO debtDAO, List<DebtEntity> data, int position, RecyclerView.Adapter adapter) {
        this.debtDAO = debtDAO;
        this.data = data;
        this.position = position;
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Удаление!")
                .setMessage("Вы уверены?!")
                .setIcon(null)
                .setPositiveButton("да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new Thread(() -> {
                            try {
                                debtDAO.deleteByUserId(data.get(position).getId());
                            } catch (Exception e) {}
                        }).start();
                        data.remove(position);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("нет", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        agree = false;
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public Boolean getAgree() {
        return agree;
    }
}
