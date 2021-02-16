package com.example.lyma;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class DownloadTaskLyrics  extends AsyncTask<String, Void, Void>
{
    static String divS = " ";
    @Override
    protected Void doInBackground(String... url)
    {
        Document doc;
        try
        {
            doc = Jsoup.connect(url[0]) .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.38 Safari/537.36").get();
            Element div = doc.getElementsByClass("col-xs-12 col-lg-8 text-center").first().select("div:not([class])").first();
            divS = div.toString();
            divS = divS.replaceAll("<p>", "");
            divS = divS.replaceAll("</p>", "");
            divS = divS.replaceAll("<div>", "");
            divS = divS.replaceAll("</div>", "");
            divS = divS.replaceAll("<br>", "\n");
            divS = divS.replaceAll("<!-- Usage of azlyrics.com content by any third-party lyrics provider is prohibited by our licensing agreement. Sorry about that. -->", "");
            System.out.println(divS);

            //METER AQUI LO DE QUE LO META EN EL LISTVIEW DE LYRICS

            Thread.sleep(3000);
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
