package xyz.viseator.v2ex.http;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import xyz.viseator.v2ex.data.DetailContent;

/**
 * Created by viseator on 2016/11/19.
 */

public class GetHTMLMainContentTask extends AsyncTask<String, Void, Document> {

    private List<DetailContent> detailContents;
    private RecyclerView recyclerView;

    public GetHTMLMainContentTask(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
    
    @Override
    protected Document doInBackground(String... urls) {
        Document doc;
        try {
            doc = Jsoup.connect(urls[0]).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Document document) {
// TODO: 2016/11/19 Parse HTML and put result into detailContents 
    }
}
