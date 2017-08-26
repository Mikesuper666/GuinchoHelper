package onuse.com.br.ghinchohelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by maico on 11/08/17.
 */

public class SelectLocationFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{

    View v;
    TextView homeTextView, workTextView;
    String home, work;
    double latitude, longitude;
    String location;
    public SelectLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_selecionar_local, container, false);

        //AutoCompleteTextView autoCompView = (AutoCompleteTextView) v.findViewById(R.id.select_pickup_location_autocomplete);
        //autoCompView.setAdapter(new LugaresAutoCompleteAdapter(getActivity(), R.layout.list_item));
        //;autoCompView.setOnItemClickListener(this);

        SharedPreferences sp = getActivity().getSharedPreferences("Session", Context.MODE_PRIVATE);
        home = sp.getString("home", "");
        work = sp.getString("work", "");

        homeTextView = (TextView) v.findViewById(R.id.select_location_home);
        workTextView = (TextView) v.findViewById(R.id.select_location_work);

        if(!home.equals(""))
        {
            homeTextView.setOnClickListener(this);
            homeTextView.setText(home);
        }
        else
        {
            homeTextView.setVisibility(View.GONE);
        }

        if(!work.equals(""))
        {
            workTextView.setOnClickListener(this);
            workTextView.setText(work);
        }
        else
        {
            workTextView.setVisibility(View.GONE);
        }

        return v;
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            default:
            case R.id.select_location_home:
                location = home;
                //Toast.makeText(getActivity(), "Home: "+home, Toast.LENGTH_SHORT).show();
                getCoordinates();
                break;
            case R.id.select_location_work:
                location = work;
                //Toast.makeText(getActivity(), "Work: "+work, Toast.LENGTH_SHORT).show();
                getCoordinates();
                break;
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        String str = (String) adapterView.getItemAtPosition(position);
        location = str;
        getCoordinates();
        //Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

    }

    public void getCoordinates()
    {
    }
    public void centerMap()
    {

        SharedPreferences sp = getActivity().getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //Log.d("lat = ",latitude+"");
        //Log.d("long = ",longitude+"");
        //Log.d("Location = ", location);
        editor.putString("location", location);
        editor.putString("locationlat", latitude + "");
        editor.putString("locationlong", longitude+"");
        editor.commit();

        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }

}
