package br.unoeste.fipp.appimc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText etNome, etResultado;
    private RadioButton rb_fem, rb_masc;
    private SeekBar sb_peso, sb_altura;
    private TextView tvAltura, tvPeso;
    private Button bt_calcular, bt_reset, bt_fechar, bt_historico;
    private String sexo, nome;
    private int peso, altura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNome = findViewById(R.id.etNome);
        etResultado = findViewById(R.id.etResultado);
        rb_fem = findViewById(R.id.rb_fem);
        rb_masc = findViewById(R.id.rb_masc);
        sb_altura = findViewById(R.id.sb_altura);
        sb_peso = findViewById(R.id.sb_peso);
        tvAltura = findViewById(R.id.tvAltura);
        tvPeso = findViewById(R.id.tvPeso);
        bt_calcular = findViewById(R.id.bt_calcular);
        bt_reset = findViewById(R.id.bt_reset);
        bt_fechar = findViewById(R.id.bt_fechar);
        bt_historico = findViewById(R.id.bt_historico);

        sb_peso.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int peso = seekBar.getProgress();
                tvPeso.setText(""+peso+" kg");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_altura.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int altura = seekBar.getProgress();
                tvAltura.setText(""+altura+" cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bt_calcular.setOnClickListener(button -> {
            sexo = rb_fem.isChecked() ? rb_fem.getText().toString() : rb_masc.getText().toString();
            calcularIMC(sb_peso.getProgress(), sb_altura.getProgress(), etNome.getText().toString(), sexo);
        });
        bt_reset.setOnClickListener(button -> limpar());
        bt_fechar.setOnClickListener(button -> fechar());
        bt_historico.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        carregarDados();
    }

    private void calcularIMC(int peso, int altura, String nome, String sexo) {
        Double alturaMetro = altura/100.;
        Double imc = peso/(alturaMetro*alturaMetro);
        String imcTruncado = String.format("%.2f",imc);
        String condicao = determinarCondicao(imc, sexo);
        String resultado = nome+", voce possui "+peso+"Kg e "+alturaMetro+"m de altura, portanto seu IMC é de "+imcTruncado+". Voce está "+condicao;
        etResultado.setText(resultado);
        etResultado.setVisibility(View.VISIBLE);

        // armazenar
        FileOutputStream fout  = null;
        ObjectOutputStream out;
        UserData userData = new UserData(nome, condicao, peso, altura, imc, new Date());
        try {
            FileOutputStream fileOutputStream = openFileOutput("userData.dad", MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userData);
            objectOutputStream.close();
            fileOutputStream.close();

            Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao salvar os dados.", Toast.LENGTH_SHORT).show();
        }
    }

    private String determinarCondicao(Double imc, String sexo) {
        Log.d("sexo: ", sexo);
        if ((imc < 19.1 && Objects.equals(sexo, "Feminino")) || (imc < 20.7 && Objects.equals(sexo, "Masculino"))) {
            return "abaixo do peso";
        } else if ((imc >= 19.1 && imc < 25.8 && sexo.equals("Feminino")) || (imc >= 20.7 && imc < 26.4 && Objects.equals(sexo, "Masculino"))) {
            return "no peso normal";
        } else if ((imc >= 25.8 && imc < 27.3 && Objects.equals(sexo, "Feminino")) || (imc >= 26.4 && imc < 27.8 && Objects.equals(sexo, "Masculino"))) {
            return "marginalmente acima do peso";
        } else if ((imc >= 27.3 && imc < 32.3 && Objects.equals(sexo, "Feminino")) || (imc >= 27.8 && imc < 31.1 && Objects.equals(sexo, "Masculino"))) {
            return "acima do peso ideal";
        } else {
            return "obeso";
        }
    }

    private void limpar () {
        etNome.setText("");
        sb_altura.setProgress(50);
        sb_peso.setProgress(10);
        rb_masc.setChecked(false);
        rb_fem.setChecked(false);
        etResultado.setVisibility(View.GONE);
    }

    private void fechar () {
        super.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences=getSharedPreferences("config",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("nome", etNome.getText().toString());
        editor.putString("sexo", sexo);
        editor.putInt("peso", sb_peso.getProgress());
        editor.putInt("altura", sb_altura.getProgress());
        editor.commit();
    }

    protected void carregarDados() {
        SharedPreferences sharedPreferences=getSharedPreferences("config",MODE_PRIVATE);
        nome=sharedPreferences.getString("nome","");
        sexo=sharedPreferences.getString("sexo","");
        peso=sharedPreferences.getInt("peso",10);
        altura=sharedPreferences.getInt("altura",50);

        etNome.setText(nome);
        sb_peso.setProgress(peso);
        sb_altura.setProgress(altura);
        tvPeso.setText(""+peso);
        tvAltura.setText(""+altura);
        if (Objects.equals(sexo, "Feminino")) {
            rb_fem.setChecked(true);
            rb_masc.setChecked(false);
        } else if (Objects.equals(sexo, "Masculino")) {
            rb_masc.setChecked(true);
            rb_fem.setChecked(false);
        }
    }
}