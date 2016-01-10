package brightseer.com.brewhaha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import brightseer.com.brewhaha.R;

/**
 * Created by mccul_000 on 12/6/2014.
 */
public class BrightSeerFragment extends BaseFragment {
    WebView webview;

    public BrightSeerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        initWebview(rootView);
        initGoogleAnalytics(this.getClass().getSimpleName());
        webview.loadUrl("http://brightseerstudios.com/");
        return rootView;
    }

    private void initWebview(View view) {
        webview = (WebView) view.findViewById(R.id.webview);
    }

}
