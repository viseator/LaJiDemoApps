package xyz.viseator.stayup;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher{
    private Button button;
    boolean bool=false;
    EditText editText;
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
        editText.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!bool) {
            String input=editText.getText().toString();
            String pattern = "(.*)(苟利国家生死以)(.*)(岂因祸福避趋之)(.*)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(input);
            if (m.find()) {
                editText.setText(Html.fromHtml(m.group(0)+"<b>"+m.group(1)+"</b"));
                bool = true;
            }
        } else {
            bool=false;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}