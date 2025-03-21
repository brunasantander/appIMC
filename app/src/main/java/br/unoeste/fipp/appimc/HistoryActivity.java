package br.unoeste.fipp.appimc;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyListView;
    private HistoryAdapter historyAdapter;
    private Button bt_exluirTudo;
    private Button bt_apagar;
    private List<UserData> userDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyListView = findViewById(R.id.historyListView);

        loadUserData();

        historyAdapter = new HistoryAdapter(this, userDataList);
        historyListView.setAdapter(historyAdapter);

        bt_exluirTudo = findViewById(R.id.bt_excluirTudo);
        bt_exluirTudo.setOnClickListener(v -> {
            Log.d("Teste", "Deletando toda essa xana");
            deleteAllUserData();
            loadUserData();
        });

//        bt_apagar = historyListView.findViewById(R.id.bt_apagar);
//        bt_apagar.setOnClickListener(v -> {
//            int position = historyListView.getPositionForView(v);
//            deleteUserData(position);
//            loadUserData();
//        });

    }

    private void loadUserData() {
        try {
            Log.d("Teste", "LENDO A XERECA DO ARQUIVO");
            FileInputStream fileInputStream = openFileInput("userData.dad");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while (true) {
                try {
                    UserData userData = (UserData) objectInputStream.readObject();
                    Log.d("Teste", "Nome: " + userData.getNome());
                    userDataList.add(userData);
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }

            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAllUserData() {
        userDataList.clear();
        deleteFile("userData.dad");
        historyAdapter.notifyDataSetChanged();
    }

//    public void deleteUserData(int position) {
//        userDataList.remove(position);
//        historyAdapter.notifyDataSetChanged();
//        loadUserData();
//    }


}