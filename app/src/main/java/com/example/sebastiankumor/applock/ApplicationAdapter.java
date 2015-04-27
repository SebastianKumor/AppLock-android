package com.example.sebastiankumor.applock;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sebastiankumor.
 * retrieve all apps from package manager an populates the list view row
 */

public class ApplicationAdapter extends ArrayAdapter<ApplicationInfo> {
    public List<ApplicationInfo> appsList = null;
    private Context context;
    private PackageManager packageManager;

    public ApplicationAdapter(Context context, int textViewResourceId,
                              List<ApplicationInfo> appsList) {
        super(context, textViewResourceId, appsList);
        this.context = context;
        this.appsList = appsList;
        packageManager = context.getPackageManager();
    }

    @Override
    public int getCount() {
        return ((null != appsList) ? appsList.size() : 0);
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return ((null != appsList) ? appsList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_row, null);
        }
        ApplicationInfo data = appsList.get(position);

        if (null != data) {

            TextView appName = (TextView) view.findViewById(R.id.app_name);
            TextView packageName = (TextView) view.findViewById(R.id.app_package);
            ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);


           //storing default lock value for all apps
                SharedPreferences preferences = context.getSharedPreferences("AUTHENTICATION_FILE_NAME", 0);
                SharedPreferences.Editor editor = preferences.edit();

                final StringBuilder sb = new StringBuilder(data.loadLabel(packageManager).length());
                sb.append(data.loadLabel(packageManager));

                editor.putString( sb.toString(),"no");
                Log.d("App name :", sb.toString());
                editor.apply();

            appName.setText(sb.toString());
            packageName.setText(data.packageName);
            iconview.setImageDrawable(data.loadIcon(packageManager));
        }
        return view;
    }
}