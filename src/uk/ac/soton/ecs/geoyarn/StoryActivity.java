package uk.ac.soton.ecs.geoyarn;

import java.util.ArrayList;

import uk.ac.soton.ecs.geoyarn.controller.LocationController;
import uk.ac.soton.ecs.geoyarn.controller.StoryController;
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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StoryActivity extends Activity implements ILocationActivity {

	Story story;
	Chapter chapter;
	Page page;

	ArrayList<Button> linkButtons;
	StoryController storyController;
	LocationController locCont;

	SharedPreferences settings;

	boolean democlick;
	
	GeoyarnClientApplication app;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		app = (GeoyarnClientApplication) getApplication();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story);

		democlick = false;

		settings = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		linkButtons = new ArrayList<Button>();
		storyController = new StoryController();
		locCont = new LocationController(this);

		story = ((GeoyarnClientApplication) getApplication()).getStory();

		chapter = ((GeoyarnClientApplication) getApplication()).getChapter();
		if (chapter == null) {
			chapter = storyController.getChapter(story.getId(),
					story.getStartChapter(), app.getCurrentLat(), app.getCurrentLong());
			((GeoyarnClientApplication) getApplication()).setChapter(chapter);
		}

		
		page = ((GeoyarnClientApplication) getApplication()).getPage();

		TextView storyTitleTv = (TextView) findViewById(R.id.StoryTitle);
		storyTitleTv.setText(story.getTitle());

		TextView pageContentTv = (TextView) findViewById(R.id.StoryPageContent);
		if (page != null) {
			pageContentTv.setText(page.getContent());
		} else {
			pageContentTv
					.setText(getResources().getString(R.string.storyBegin));
		}

		LinearLayout storyLinkList = (LinearLayout) findViewById(R.id.StoryLinksList);

		boolean defEnabled = settings.getBoolean("FreeReadingMode", false);
		if (settings.getBoolean("DemoMode", false))
			defEnabled = true;

		for (Page p : chapter.getPages()) {
			// This ||true is a debug trick - change this
			if (p.equals(page) || true) {
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
		}

		// Location l = new Location(LocationManager.GPS_PROVIDER);

		// Fake stag's head data
		// l.setLatitude(50.934601);
		// l.setLongitude(-1.397424);
		// Fake B32 data
		// l.setLatitude(50.93638279940933);
		// l.setLongitude(-1.3961811549961567);

		// Fake interchange kiosk data
		// l.setLatitude(50.9362519);
		// l.setLongitude(-1.3970872);

		// onLocationChanged(l);
		// Toast.makeText(this, "Start!", Toast.LENGTH_SHORT).show();
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
			chapter = storyController.getChapter(story.getId(),
					p.getNextChapter(), app.getCurrentLat(), app.getCurrentLong());
			((GeoyarnClientApplication) getApplication()).setChapter(chapter);
			Intent storyIntent = new Intent(getBaseContext(),
					StoryActivity.class);
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

			Toast.makeText(
					this,
					"Can View? " + storyController.canViewPage(p, location)
							+ " " + location.getLatitude() + " "
							+ location.getLongitude() + " "
							+ p.getDescription() + " " + location.getAccuracy(),
					Toast.LENGTH_SHORT).show();

			button.setBackgroundColor(Color.RED);
			button.setEnabled(defEnabled);

			if (storyController.canViewPage(p, location)) {
				button.setBackgroundColor(Color.GREEN);
				button.setEnabled(true);
			}

		}

	}

}
