package com.kevoroid.andhackernews.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.kevoroid.andhackernews.R;

import org.json.JSONArray;

/**
 * Created by kevin on 5/31/17.
 */

public class StoryWebViewFragment extends BaseFragment {

    public static StoryWebViewFragment newInstance(String title, String url, int commentsCount, String commentList) {
        Bundle args = new Bundle();
        StoryWebViewFragment fragment = new StoryWebViewFragment();
        args.putString("storyTitle", title);
        args.putString("storyUrl", url);
        args.putInt("storyCommentsCount", commentsCount);
        args.putString("storyCommentsList", commentList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.story_webview_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.story_progressbar);
        WebView webView = (WebView) view.findViewById(R.id.story_webview);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.INVISIBLE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }

                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            }
        });
        webView.loadUrl(getArguments().getString("storyUrl"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.comments, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.comments_menu:
                StoryDetailFragment storyDetailFragment = StoryDetailFragment.newInstance(
                        getArguments().getString("storyTitle"),
                        getArguments().getInt("storyCommentsCount"),
                        getArguments().getString("storyCommentsList"));
                getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.main_activity_fragment_container, storyDetailFragment).addToBackStack(storyDetailFragment.getTag()).commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
