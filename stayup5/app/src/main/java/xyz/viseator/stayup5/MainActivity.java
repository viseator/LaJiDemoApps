package xyz.viseator.stayup5;

import android.app.Activity;
import android.os.StrictMode;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends Activity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_main, null);
        setContentView(view);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        textView = (TextView) view.findViewById(R.id.thetext);
        URL url = null;
        try {
            url = new URL("https://www.v2ex.com/api/topics/hot.json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpsURLConnection connection= null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        InputStream in = null;
        try {
            in = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder response =new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {   
            e.printStackTrace();
        }
        Type listType = new TypeToken<LinkedList<Theme>>(){}.getType();
        Gson gson = new Gson();
        LinkedList<Theme> themes = gson.fromJson(String.valueOf(response), listType);
        StringBuilder text = new StringBuilder();
        for (Iterator iterator = themes.iterator(); iterator.hasNext(); ) {
            Theme theme = (Theme) iterator.next();
            text.append(theme.getId());
            text.append("\n\n");
            text.append(theme.getTitle() );
            text.append("\n\n");
            text.append(theme.getUrl());
            text.append("\n\n");
            text.append(theme.getContent());
            text.append("\n\n");
            text.append(theme.getReplies());
            text.append("\n\n");
            text.append(theme.getMember().getId());
            text.append("\n\n");
            text.append(theme.getMember().getUsername());
            text.append("\n\n");
            text.append(theme.getMember().getTagline());
            text.append("\n\n");
            text.append(theme.getMember().getAvatar_mini());
            text.append("\n\n");
            text.append(theme.getMember().getAvatar_normal());
            text.append("\n\n");
            text.append(theme.getMember().getAvatar_large());
            text.append("\n\n");
            text.append(theme.getNode().getId());
            text.append("\n\n");
            text.append(theme.getNode().getName());
            text.append("\n\n");
            text.append(theme.getNode().getTitle());
            text.append("\n\n");
            text.append(theme.getNode().getTitle_alternative());
            text.append("\n\n");
            text.append(theme.getNode().getUrl());
            text.append("\n\n");
            text.append(theme.getNode().getTopics());
            text.append("\n\n");
            text.append(theme.getNode().getAvatar_mini());
            text.append("\n\n");
            text.append(theme.getNode().getAvatar_normal());
            text.append("\n\n");
            text.append(theme.getNode().getAvatar_large());
            text.append("\n\n");
            text.append(theme.getCreated());
            text.append("\n\n");
            text.append(theme.getLast_modified());
            text.append("\n\n");
            text.append(theme.getLast_touched());
            text.append("\n\n");
        }
        textView.setText(text);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
