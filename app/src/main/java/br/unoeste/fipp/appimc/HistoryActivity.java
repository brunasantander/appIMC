package br.unoeste.fipp.appimc;

import android.os.Bundle;
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
    private List<UserData> userDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyListView = findViewById(R.id.historyListView);

        loadUserData();

        historyAdapter = new HistoryAdapter(this, userDataList);
        historyListView.setAdapter(historyAdapter);
    }

    private void loadUserData() {
        try {
            FileInputStream fileInputStream = openFileInput("userData.dad");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while (true) {
                try {
                    UserData userData = (UserData) objectInputStream.readObject();
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
}