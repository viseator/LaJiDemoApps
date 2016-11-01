package xyz.viseator.stayup;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher{

    private Button button;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_main, null);
        setContentView(view);
        editText=(EditText)view.findViewById(R.id.Edit);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        editText.addTextChangedListener(this);

    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String input=editText.getText().toString();
        String pattern = ".*苟利国家生死以.*岂因祸福避趋之.*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);
        if (m.find()) {
            editText.getPaint().setFakeBoldText(true);
        } else {
            editText.getPaint().setFakeBoldText(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}