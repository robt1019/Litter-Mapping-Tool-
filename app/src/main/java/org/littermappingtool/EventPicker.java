package org.littermappingtool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.littermappingtool.backend.eventApi.EventApi;
import org.littermappingtool.backend.eventApi.model.CollectionResponseEvent;
import org.littermappingtool.backend.eventApi.model.Event;

/**
 * Created by rob on 13/08/15.
 */
public class EventPicker extends Activity{

    public static final String EXTRA_EVENT_ID = "org.littermappingtool.EventPicker.event_id";

    private static final String TAG = EventPicker.class.getSimpleName();
    private ListView eventsList;
    private TextView eventListTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_picker);

        eventListTitle = (TextView) findViewById(R.id.EventsList_title);
        eventsList = (ListView) findViewById(R.id.EventsList);

        new ListOfEventsAsyncRetriever().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * AsyncTask for retrieving the list of events and updating the
     * corresponding results list.
     */
    private class ListOfEventsAsyncRetriever extends AsyncTask<Void, Void, CollectionResponseEvent> {

        private ProgressDialog dialog = new ProgressDialog(EventPicker.this);

        @Override
        protected CollectionResponseEvent doInBackground(Void... params) {

            EventApi.Builder endpointBuilder = new EventApi.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);
            EventApi EventApiService = endpointBuilder.build();

            try {
                return EventApiService.list().execute();
            } catch (IOException e) {
                Log.w(TAG, e);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(CollectionResponseEvent result) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            ListAdapter eventsListAdapter = createEventListAdapter(result.getItems());
            eventsList.setAdapter(eventsListAdapter);

            if (eventsList.getCount() == 0) {
                eventListTitle.setText("Listing event items: no items found");
            }

            // Set on click listener for each event
            eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    TextView eventId = (TextView) view.findViewById(R.id.event_id);
                    String eventIdString =  eventId.getText().toString();
                    Long eventIdLong = Long.parseLong(eventIdString);
                    Intent intent = new Intent(EventPicker.this, LitterMapperActivity.class);
                    intent.putExtra(EXTRA_EVENT_ID, eventIdLong);
                    startActivity(intent);
                }
            });
        }

        private ListAdapter createEventListAdapter(List<Event> events) {
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            if (events != null && events.size() > 0) {
                for (Event event : events) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("eventTitle", event.getTitle());
                    map.put("eventCity", event.getCity());
                    map.put("eventDate", (new Date(event.getOccurrenceDate())));
                    map.put("eventId", event.getId());
                    data.add(map);
                }
            }

            return new SimpleAdapter(EventPicker.this, data, R.layout.event, new String[]{"eventTitle", "eventCity", "eventDate", "eventId"}, new int[]{
                    R.id.event_title, R.id.event_date, R.id.event_city, R.id.event_id});
        }
    }

}
