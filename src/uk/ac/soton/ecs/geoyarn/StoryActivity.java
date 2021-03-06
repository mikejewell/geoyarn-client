package uk.ac.soton.ecs.geoyarn;

import java.util.ArrayList;

import uk.ac.soton.ecs.geoyarn.controller.LocationController;
import uk.ac.soton.ecs.geoyarn.controller.StoryEngine;
import uk.ac.soton.ecs.geoyarn.controller.TriggerController;
import uk.ac.soton.ecs.geoyarn.model.Chapter;
import uk.ac.soton.ecs.geoyarn.model.Page;
import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StoryActivity extends Activity implements ILocationActivity {

	Story story;
	Chapter chapter;
	Page page;

	ArrayList<Button> linkButtons;
	StoryEngine storyController;
	LocationController locCont;
	TriggerController trigCont;

	SharedPreferences settings;

	boolean democlick;
	
	GeoyarnClientApplication app;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		Log.i("GeoYarn: ","STARTING STORY ACTIVITY");
		
		app = (GeoyarnClientApplication) getApplication();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story);

		democlick = false;

		settings = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		linkButtons = new ArrayList<Button>();
		storyController = new StoryEngine();
		locCont = new LocationController(this);
		

		story = ((GeoyarnClientApplication) getApplication()).getStory();

		
		
		chapter = ((GeoyarnClientApplication) getApplication()).getChapter();
		if (chapter == null) {
			chapter = storyController.getChapter(story.getId(),
					story.getStartChapterURI(), app.getCurrentLat(), app.getCurrentLong());
			((GeoyarnClientApplication) getApplication()).setChapter(chapter);
		}
		else{
			Log.i("GeoYarn: ","CHAP NOT NULL");
		}
		
		trigCont = new TriggerController(this);
		
		page = ((GeoyarnClientApplication) getApplication()).getPage();
		
		
		//Log.i("GeoYarn: ","PAGE "+page.getContent());
		//Toast.makeText(this, "Start! Page:"+page.getId()+" Chapter:"+chapter.getId()+" Story:"+story.getId(), Toast.LENGTH_SHORT).show();

		TextView storyTitleWv = (TextView) findViewById(R.id.StoryTitle);
		storyTitleWv.setText(story.getTitle());

		WebView pageContentWv = (WebView) findViewById(R.id.StoryPageContent);
		if (page != null) {
			pageContentWv.loadData(page.getContent(), "text/html", null);
		} else {
			pageContentWv.loadData(getResources().getString(R.string.storyBegin), "text/html", null);
		}

		LinearLayout storyLinkList = (LinearLayout) findViewById(R.id.StoryLinksList);

		boolean defEnabled = settings.getBoolean("FreeReadingMode", false);
		if (settings.getBoolean("DemoMode", false))
			defEnabled = true;

		for (Page p : chapter.getPages()) {
			// This ||true is a debug trick - change this
			Log.i("GeoYarn: ","Making Buttons "+chapter.getId()+" "+chapter.getPages().size());
			if (page == null || page.getId() != p.getId()) {				
				Button linkButton = new Button(this);
				linkButton.setText(p.getDescription());
				linkButton.setTag(p);
				linkButton.setBackgroundColor(Color.RED);
				linkButton.setEnabled(defEnabled);
				linkButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Page page = (Page) v.getTag();
						followLink(page);
					}
				});

				linkButtons.add(linkButton);
				storyLinkList.addView(linkButton);

			}
			
			if(linkButtons.size()==0){
				Button linkButton = new Button(this);
				linkButton.setText("Story End");
				linkButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						backToMenu();
					}
				});
				
				storyLinkList.addView(linkButton);
			}
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		locCont.removeUpdates();
	}

	@Override
	protected void onResume() {
		super.onResume();
		locCont.resumeUpdates();
	}

	public void followLink(Page p) {
		if (settings.getBoolean("DemoMode", false) && !democlick) {
			democlick = true;
			for (Button button : linkButtons) {
				Page bp = (Page) button.getTag();
				if (bp.equals(p)) {
					button.setBackgroundColor(Color.GREEN);
				}
			}
		} else {

			((GeoyarnClientApplication) getApplication()).setPage(p);
			chapter = storyController.getChapter(story.getId(),	p.getNextChapterURI(), app.getCurrentLat(), app.getCurrentLong());
			Log.i("GeoYarn: ","SETTING CHAPTER "+story.getId()+" "+p.getNextChapterURI()+" "+app.getCurrentLat()+" "+app.getCurrentLong());
			((GeoyarnClientApplication) getApplication()).setChapter(chapter);
			Intent storyIntent = new Intent(getBaseContext(), StoryActivity.class);
			startActivity(storyIntent);
		}
	}

	public void onLocationChanged(Location location) {

		// Toast.makeText(this, "Loc Changed!", Toast.LENGTH_SHORT).show();

		boolean defEnabled = settings.getBoolean("FreeReadingMode", false);
		if (settings.getBoolean("DemoMode", false))
			;
		defEnabled = true;
		for (Button button : linkButtons) {
			Page p = (Page) button.getTag();

			/*Toast.makeText(
					this,
					"Can View? " + storyController.canViewPage(p, location)
							+ " " + location.getLatitude() + " "
							+ location.getLongitude() + " "
							+ p.getDescription() + " " + location.getAccuracy(),
					Toast.LENGTH_SHORT).show();*/

			button.setBackgroundColor(Color.RED);
			button.setEnabled(defEnabled);

			if (storyController.canViewPage(p, location)) {
				button.setBackgroundColor(Color.GREEN);
				button.setEnabled(true);
			}

		}

	}
	
	public void backToMenu(){
		Intent storiesIntent = new Intent(getBaseContext(), GeoyarnClientActivity.class);
    	startActivity(storiesIntent);
	}

}
