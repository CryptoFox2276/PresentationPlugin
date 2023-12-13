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
	private static String DEFAULT_DISPLAY_HTML = "<!DOCTYPE html>\r\n" + //
			"<html>\r\n" + //
			"\r\n" + //
			"<head>\r\n" + //
			"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + //
			"  <style>\r\n" + //
			"    * {\r\n" + //
			"      box-sizing: border-box;\r\n" + //
			"    }\r\n" + //
			"\r\n" + //
			"    body {\r\n" + //
			"      width: 100%;\r\n" + //
			"      height: 100%;\r\n" + //
			"      overflow: hidden;\r\n" + //
			"      margin: 0;\r\n" + //
			"      padding: 0;\r\n" + //
			"    }\r\n" + //
			"\r\n" + //
			"    .mySlides {\r\n" + //
			"      display: none;\r\n" + //
			"    }\r\n" + //
			"\r\n" + //
			"    img {\r\n" + //
			"      vertical-align: middle;\r\n" + //
			"      width: 100%;\r\n" + //
			"      height: 100%;\r\n" + //
			"      max-height: 700px;\r\n" + //
			"    }\r\n" + //
			"\r\n" + //
			"    /* Slideshow container */\r\n" + //
			"    .slideshow-container {\r\n" + //
			"      position: relative;\r\n" + //
			"      margin: auto;\r\n" + //
			"    }\r\n" + //
			"\r\n" + //
			"    /* Caption text */\r\n" + //
			"    .text {\r\n" + //
			"      color: #f2f2f2;\r\n" + //
			"      font-size: 15px;\r\n" + //
			"      padding: 8px 12px;\r\n" + //
			"      position: absolute;\r\n" + //
			"      bottom: 8px;\r\n" + //
			"      width: 100%;\r\n" + //
			"      text-align: center;\r\n" + //
			"    }\r\n" + //
			"\r\n" + //
			"    .numbertext {\r\n" + //
			"      color: #f2f2f2;\r\n" + //
			"      font-size: 12px;\r\n" + //
			"      padding: 8px 12px;\r\n" + //
			"      position: absolute;\r\n" + //
			"      top: 0;\r\n" + //
			"    }\r\n" + //
			"\r\n" + //
			"    .dot {\r\n" + //
			"      height: 15px;\r\n" + //
			"      width: 15px;\r\n" + //
			"      margin: 0 2px;\r\n" + //
			"      background-color: #bbb;\r\n" + //
			"      border-radius: 50%;\r\n" + //
			"      display: inline-block;\r\n" + //
			"      transition: background-color 0.6s ease;\r\n" + //
			"    }\r\n" + //
			"\r\n" + //
			"    .active {\r\n" + //
			"      background-color: #717171;\r\n" + //
			"    }\r\n" + //
			"\r\n" + //
			"    .fade {\r\n" + //
			"      animation-name: fade;\r\n" + //
			"      animation-duration: 1.5s;\r\n" + //
			"    }\r\n" + //
			"\r\n" + //
			"    @keyframes fade {\r\n" + //
			"      from {\r\n" + //
			"        opacity: .4\r\n" + //
			"      }\r\n" + //
			"\r\n" + //
			"      to {\r\n" + //
			"        opacity: 1\r\n" + //
			"      }\r\n" + //
			"    }\r\n" + //
			"  </style>\r\n" + //
			"</head>\r\n" + //
			"\r\n" + //
			"<body>\r\n" + //
			"  <div class=\"slideshow-container\">\r\n" + //
			"    <div class=\"mySlides fade\">\r\n" + //
			"      <div class=\"numbertext\">1 / 3</div>\r\n" + //
			"      <img src=\"https://i.postimg.cc/TYc64cH6/landscape1.jpg\" style=\"width:100%\">\r\n" + //
			"    </div>\r\n" + //
			"\r\n" + //
			"    <div class=\"mySlides fade\">\r\n" + //
			"      <div class=\"numbertext\">2 / 3</div>\r\n" + //
			"      <img src=\"https://i.postimg.cc/kG6Hqrkw/photo-1499615767948-e6a89ef6060f.jpg\" style=\"width:100%\">\r\n" + //
			"    </div>\r\n" + //
			"\r\n" + //
			"    <div class=\"mySlides fade\">\r\n" + //
			"      <div class=\"numbertext\">3 / 3</div>\r\n" + //
			"      <img src=\"https://i.postimg.cc/906nf822/photo-1508690471604-cafd081190a1.jpg\" style=\"width:100%\">\r\n" + //
			"    </div>\r\n" + //
			"  </div>\r\n" + //
			"  <br>\r\n" + //
			"  <div style=\"text-align:center\">\r\n" + //
			"    <span class=\"dot\"></span>\r\n" + //
			"    <span class=\"dot\"></span>\r\n" + //
			"    <span class=\"dot\"></span>\r\n" + //
			"  </div>\r\n" + //
			"\r\n" + //
			"  <script>\r\n" + //
			"    let slideIndex = 0;\r\n" + //
			"    showSlides();\r\n" + //
			"\r\n" + //
			"    function showSlides() {\r\n" + //
			"      let i;\r\n" + //
			"      let slides = document.getElementsByClassName(\"mySlides\");\r\n" + //
			"      let dots = document.getElementsByClassName(\"dot\");\r\n" + //
			"      for (i = 0; i < slides.length; i++) {\r\n" + //
			"        slides[i].style.display = \"none\";\r\n" + //
			"      }\r\n" + //
			"      slideIndex++;\r\n" + //
			"      if (slideIndex > slides.length) { slideIndex = 1 }\r\n" + //
			"      for (i = 0; i < dots.length; i++) {\r\n" + //
			"        dots[i].className = dots[i].className.replace(\" active\", \"\");\r\n" + //
			"      }\r\n" + //
			"      slides[slideIndex - 1].style.display = \"block\";\r\n" + //
			"      dots[slideIndex - 1].className += \" active\";\r\n" + //
			"      setTimeout(showSlides, 10000);\r\n" + //
			"    }\r\n" + //
			"  </script>\r\n" + //
			"\r\n" + //
			"</body>\r\n" + //
			"\r\n" + //
			"</html>";
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
	File f = new File(getOuterContext().getFilesDir(), "display.html");
	if (f.exists()) {
		try {
			return FileUtils.readFileToString(f);
		} catch (IOException e) {
		}
	}
    return DEFAULT_DISPLAY_HTML;
  }
}
