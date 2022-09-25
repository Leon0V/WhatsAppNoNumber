package up.edu.whatsappnonumber;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText phone, message;
    private Button sendButton;
    private String messageText, phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryCodePicker = findViewById(R.id.countryCode);
        phone = findViewById(R.id.phoneNo);
        message = findViewById(R.id.message);
        sendButton = findViewById(R.id.sendBtn);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                messageText = message.getText().toString();
                phoneNumber = phone.getText().toString();

                //checando se as strings não estão vazias para envio da mensagem

                if (!messageText.isEmpty() && !phoneNumber.isEmpty()) {

                    countryCodePicker.registerCarrierNumberEditText(phone);
                    phoneNumber = countryCodePicker.getFullNumber();


                    if (whatsAppCaller()) {

                        Intent openWpp = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + messageText));
                        startActivity(openWpp);
                        message.setText("");
                        phone.setText("");

                    } else {
                        Toast.makeText(MainActivity.this, "O Aplicativo de mensagens não está instalado.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Preencha os campos de número e mensagem.", LENGTH_LONG).show();
                }
            }
        });
    }
    //nova função fora do código para verificar os pacotes e procurar o aplicativo de mensagens Whatsapp

        private boolean whatsAppCaller() {

            //busca por pacotes

            PackageManager packageManager = getPackageManager();
            boolean whatsappInstalled;

            try {

                packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                whatsappInstalled = true;

            } catch (PackageManager.NameNotFoundException e) {
                whatsappInstalled = false;
            }
            return whatsappInstalled;
        }
}