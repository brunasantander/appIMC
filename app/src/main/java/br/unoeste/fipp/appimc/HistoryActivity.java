package br.unoeste.fipp.appimc;

import android.os.Bundle;
import android.widget.ListView;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyListView;
    private HistoryAdapter historyAdapter;
    private List<UserData> userDataList = new ArrayList<>();
    private Button bt_exluirTudo;

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
            deleteAllUserData();
            loadUserData();
        });
    }

    private void loadUserData() {
    userDataList.clear();
    try {
        FileInputStream fileInputStream = openFileInput("userData.dad");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        userDataList = (List<UserData>) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    if (historyAdapter != null) {
        historyAdapter.notifyDataSetChanged();
    }
}

    private void deleteAllUserData() {
        userDataList.clear();
        deleteFile("userData.dad");
        historyAdapter.notifyDataSetChanged();
    }

}