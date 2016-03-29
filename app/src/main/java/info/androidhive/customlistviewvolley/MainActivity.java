package info.androidhive.customlistviewvolley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.customlistviewvolley.adater.ServiceListAdapter;
import info.androidhive.customlistviewvolley.app.AppController;
import info.androidhive.customlistviewvolley.model.Services;
import info.androidhive.customlistviewvolley.util.XMLParser;

public class MainActivity extends Activity {
	// Log tag
//	private static final String TAG = MainActivity.class.getSimpleName ();
	private static final String TAG = "KARMAN";


	//Karman singh isjkahsdkjhasdkjhaskjasdkajsndkajsn
	private static final String url2 = "http://10.0.3.2/callsikandar/new/getallservices.php";
	private static String response_type = "xml";
	JSONArray jsonArrayGetAllServices = null;
	JSONObject jsonObjectGetAllServices;
	ArrayList<HashMap<String, String>> arrayListGetAllServices = new ArrayList<HashMap<String, String>> ();
	int json_array_len = 0;  //  0 => default
	int is_data_received = 0;   //  0 => default(no data received)
	private List<Services> servicesList = new ArrayList<Services> ();
	// Movies json url
	private ProgressDialog pDialog;
	private ListView listView;
	private ServiceListAdapter adapter;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_main);

		listView = (ListView) findViewById (R.id.list);
		adapter = new ServiceListAdapter (this, servicesList);
		listView.setAdapter (adapter);

		pDialog = new ProgressDialog (this);
		// Showing progress dialog before making http request
		pDialog.setMessage ("Loading...");
		pDialog.show ();

		// changing action bar color
		getActionBar ().setBackgroundDrawable (new ColorDrawable (Color.parseColor ("#1b1b1b")));


		// Creating volley request obj
		Log.d ("URL", url2);

		StringRequest strRequest = new StringRequest (Request.Method.PUT, url2,
				new Response.Listener<String> () {

					@Override
					public void onResponse (String response) {
						hidePDialog ();
						Log.d ("SERVER RESPONSE", response);

						if (response_type.equals ("xml")) {
							XMLParser parser = new XMLParser ();
							Document doc = parser.getDomElement (response); // getting DOM element
							NodeList nl = doc.getElementsByTagName ("service");
							for (int i = 0; i < nl.getLength (); i++) {
								Element e = (Element) nl.item (i);
								Services service = new Services ();
								service.setApp_flow (Integer.parseInt (parser.getValue (e, "app_flow")));
								service.setRole_id (Integer.parseInt (parser.getValue (e, "role_id")));
								service.setRole_name (parser.getValue (e, "role_name"));
								service.setImage (parser.getValue (e, "image"));
								servicesList.add (service);
							}
							adapter.notifyDataSetChanged ();
						} else {
							try {
								JSONObject jsonObj = new JSONObject (response);
								JSONArray jsonArray = jsonObj.getJSONArray ("results");
								json_array_len = jsonArray.length ();
								for (int i = 0; i < json_array_len; i++) {
									JSONObject jsonObject = jsonArray.getJSONObject (i);
									Services service = new Services ();
									service.setApp_flow (jsonObject.getInt ("app_flow"));
									service.setRole_id (jsonObject.getInt ("role_id"));
									service.setRole_name (jsonObject.getString ("role_name"));
									service.setImage (jsonObject.getString ("image"));
									servicesList.add (service);
								}
							} catch (JSONException e) {
								e.printStackTrace ();
							}
							adapter.notifyDataSetChanged ();
						}
					}
				},
				new Response.ErrorListener () {
					@Override
					public void onErrorResponse (VolleyError error) {
						Log.d (TAG, error.toString ());
					}
				}) {
			@Override
			protected Map<String, String> getParams () {
				Map<String, String> params = new HashMap<String, String> ();
				params.put ("city_id", "1");
				params.put ("locality_id", "1");
				params.put ("response_type", response_type);
				return params;
			}
		};

		AppController.getInstance ().addToRequestQueue (strRequest);

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder (this).addApi (AppIndex.API).build ();
	}

	@Override
	public void onDestroy () {
		super.onDestroy ();
		hidePDialog ();
	}

	private void hidePDialog () {
		if (pDialog != null) {
			pDialog.dismiss ();
			pDialog = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater ().inflate (R.menu.main, menu);
		return true;
	}

	@Override
	public void onStart () {
		super.onStart ();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect ();
		Action viewAction = Action.newAction (
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse ("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse ("android-app://info.androidhive.customlistviewvolley/http/host/path")
		);
		AppIndex.AppIndexApi.start (client, viewAction);
	}

	@Override
	public void onStop () {
		super.onStop ();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction (
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse ("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse ("android-app://info.androidhive.customlistviewvolley/http/host/path")
		);
		AppIndex.AppIndexApi.end (client, viewAction);
		client.disconnect ();
	}
}
