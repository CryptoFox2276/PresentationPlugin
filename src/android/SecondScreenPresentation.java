/*
 * Copyright 2014 Fraunhofer FOKUS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AUTHORS: Louay Bassbouss <louay.bassbouss@fokus.fraunhofer.de>
 *          Martin Lasak <martin.lasak@fokus.fraunhofer.de>
 */
package presentationplugin.PresentationPlugin;


import android.app.Activity;
import android.app.Presentation;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * This class is responsible to display the WebView of the presenting page on the connected Presentation Display.
 *
 */
public class SecondScreenPresentation extends Presentation {
	private static String DEFAULT_DISPLAY_URL="about:blank";
	private static String DEFAULT_DISPLAY_HTML = "<!DOCTYPE html> <html> <head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <style> * { box-sizing: border-box; } body { width: 100%; height: 100%; overflow: hidden; margin: 0; padding: 0; } .mySlides { display: none; } img { vertical-align: middle; width: 100%; height: 100%; max-height: 700px; } .slideshow-container { position: relative; margin: auto; } .text { color: #f2f2f2; font-size: 15px; padding: 8px 12px; position: absolute; bottom: 8px; width: 100%; text-align: center; } .numbertext { color: #f2f2f2; font-size: 12px; padding: 8px 12px; position: absolute; top: 0; } .dot { height: 15px; width: 15px; margin: 0 2px; background-color: #bbb; border-radius: 50%; display: inline-block; transition: background-color 0.6s ease; } .active { background-color: #717171; } .fade { animation-name: fade; animation-duration: 1.5s; } @keyframes fade { from { opacity: .4 } to { opacity: 1 } } </style> </head> <body> <div class=\"slideshow-container\"> <div class=\"mySlides fade\"> <div class=\"numbertext\">1 / 3</div> <img src=\"https://i.postimg.cc/TYc64cH6/landscape1.jpg\" style=\"width:100%\"> </div> <div class=\"mySlides fade\"> <div class=\"numbertext\">2 / 3</div> <img src=\"https://i.postimg.cc/kG6Hqrkw/photo-1499615767948-e6a89ef6060f.jpg\" style=\"width:100%\"> </div> <div class=\"mySlides fade\"> <div class=\"numbertext\">3 / 3</div> <img src=\"https://i.postimg.cc/906nf822/photo-1508690471604-cafd081190a1.jpg\" style=\"width:100%\"> </div> </div> <br> <div style=\"text-align:center\"> <span class=\"dot\"></span> <span class=\"dot\"></span> <span class=\"dot\"></span> </div> <script> let slideIndex = 0; showSlides(); function showSlides() { let i; let slides = document.getElementsByClassName(\"mySlides\"); let dots = document.getElementsByClassName(\"dot\"); for (i = 0; i < slides.length; i++) { slides[i].style.display = \"none\"; } slideIndex++; if (slideIndex > slides.length) { slideIndex = 1 } for (i = 0; i < dots.length; i++) { dots[i].className = dots[i].className.replace(\" active\", \"\"); } slides[slideIndex - 1].style.display = \"block\"; dots[slideIndex - 1].className += \" active\"; setTimeout(showSlides, 5000); } </script> </body> </html>";
	private WebView webView;
	private PresentationSession session;
	private Activity outerContext;
	private String displayUrl;
	/**
	 * @param outerContext the parent activity
	 * @param display the {@link Display} associated to this presentation
	 * @param displayUrl the URL of the display html page to present on the display as default page 
	 */
	public SecondScreenPresentation(Activity outerContext, Display display, String displayUrl) {
		super(outerContext, display);
		this.outerContext = outerContext;
		// this.displayUrl = displayUrl == null? DEFAULT_DISPLAY_URL: displayUrl+"#"+display.getName();
	}
	
	/**
	 * set webview as content view of the presentation 
	 * @see android.app.Dialog#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(getWebView());
			// loadUrl(getDisplayUrl());
    	loadData(getDefaultHtmlContent());
	}
	
	/**
	 * destroy webview on stop
	 * @see android.app.Presentation#onStop()
	 */
	protected void onStop() {
		getWebView().destroy();
		super.onStop();
	}
	
	/**
	 * initialize the {@link WebView}: Add JavaScript interface <code>NavigatorPresentationJavascriptInterface</code>, inject the receiver JavaScript code from {@link NavigatorPresentationJS} in the webview after page load is finished and fire <code>deviceready</code> event.
	 * @return the webview of the presenting page.
	 */
	public WebView getWebView() {
		if (webView == null) {
			webView = new WebView(this.getContext());
			webView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			webView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings().setLoadsImagesAutomatically(true);
			webView.getSettings().setAllowFileAccess(true);
			webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
			webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
			webView.getSettings().setDomStorageEnabled(true);
			webView.getSettings().setDatabaseEnabled(true);
			webView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36");
			webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
			webView.getSettings().setLoadWithOverviewMode(true);
		    webView.getSettings().setUseWideViewPort(true);
			webView.setWebViewClient(new WebViewClient() {	
				@Override
				public void onPageStarted(WebView view, String url,Bitmap favicon) {
					super.onPageStarted(view, url, favicon);
				}
				@Override
				public void onPageFinished(WebView view, String url) {
					view.loadUrl(NavigatorPresentationJS.RECEIVER);
					view.loadUrl("javascript:document.dispatchEvent(new Event('deviceready'));");
					super.onPageFinished(view, url);
				}
			});
			webView.addJavascriptInterface(new Object(){
				@JavascriptInterface
				public void setOnPresent() {
					if(getSession() != null){
						getSession().getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if(getSession() != null){
									webView.loadUrl("javascript:NavigatorPresentationJavascriptInterface.onsession({id: '"+getSession().getId()+"', state: '"+getSession().getState()+"'})");
									getSession().setState(PresentationSession.CONNECTED);
								}
							}
						});
					}
					
				}
				@JavascriptInterface
				public void close(String sessId) {
					if(getSession() != null && getSession().getId().equals(sessId)){
						getSession().setState(PresentationSession.DISCONNECTED);
					}
				}
				@JavascriptInterface
				public void postMessage(String sessId, String msg) {
					if(getSession() != null && getSession().getId().equals(sessId)){
						getSession().postMessage(false, msg);
					}
				}
			}, "NavigatorPresentationJavascriptInterface");
			
		}
		return webView;
	}
	
	/**
	 * @return the {@link PresentationSession} associated with this presentation or <code>null</code>
	 */
	public PresentationSession getSession() {
		return session;
	}
	
	/**
	 * @param session the {@link PresentationSession} to set. if <code>null</code> the default display html page will be displayed instead of the presenting page.
	 */
	public void setSession(PresentationSession session) {
		this.session = session;
		if (session == null) {
			// loadUrl(getDisplayUrl());
			loadData(getDefaultHtmlContent());
		}
		else {
			// loadUrl(session.getUrl());
			loadData(session.getHtmlContent());
		}
	}
	
	/**
	 * @return the parent {@link Activity} associated with this presentation
	 */
	public Activity getOuterContext() {
		return outerContext;
	}
	
	/**
	 * @param url the url of the page to load
	 */
	public void loadUrl(final String url){
		if (getDisplay() != null) {
			getOuterContext().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					getWebView().loadUrl(url);
				}
			});
		}
	}

  public void loadData(final String htmlData) {
    if (getDisplay() != null) {
      getOuterContext().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          getWebView().loadData(htmlData, "text/html", null);
        }
      });
    }
  }

	/**
	 * @return the URL of the display html page
	 */
	public String getDisplayUrl() {
		return displayUrl;
	}

  /**
   * @return the URL of the display html page
   */
  public String getDefaultHtmlContent() {
    return DEFAULT_DISPLAY_HTML;
  }
}
