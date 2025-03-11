package br.unoeste.fipp.appimc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etNome;
    private RadioButton rb_fem, rb_masc;
    private SeekBar sb_peso, sb_altura;
    private TextView tvAltura, tvPeso;
    private Button bt_calcular;

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
        rb_fem = findViewById(R.id.rb_fem);
        rb_masc = findViewById(R.id.rb_masc);
        sb_altura = findViewById(R.id.sb_altura);
        sb_peso = findViewById(R.id.sb_peso);
        tvAltura = findViewById(R.id.tvAltura);
        tvPeso = findViewById(R.id.tvPeso);
        bt_calcular = findViewById(R.id.bt_calcular);

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

        bt_calcular.setOnClickListener(button -> calcularIMC(sb_peso.getProgress(), sb_altura.getProgress(), etNome.getText()));
    }

    private String calcularIMC(int peso, int altura, CharSequence nome, String sexo) {
        Double imc = (double) (peso/(altura*altura));

        return ""
    }
}