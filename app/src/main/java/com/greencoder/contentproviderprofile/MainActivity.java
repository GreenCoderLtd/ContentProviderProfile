package com.greencoder.contentproviderprofile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import  android.provider.ContactsContract.CommonDataKinds.*;
import android.view.View;

public class MainActivity extends Activity {

    public static int PICK_REQUEST=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Profile", "Created");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void readClick(View v)
    {
        Cursor cursor=getContentResolver().query(Phone.CONTENT_URI,new String[]{Phone.NUMBER,Phone.DISPLAY_NAME},Phone.DISPLAY_NAME+" like '%avi%' ",null,null);

        String names="";

        while(cursor.moveToNext())
        {
            names=names+cursor.getString(1)+","+cursor.getString(0)+"\n";
        }

        AlertDialog aler= new AlertDialog.Builder(this)
                .setTitle("Contact")
                .setMessage(names)
                .setPositiveButton("Ok", null)
                .show();

    }

    public void profileClick(View v)
    {
        Cursor cursor=getContentResolver().query(Profile.CONTENT_URI,new String[]{Profile.DISPLAY_NAME},null,null,null);

        if(cursor.moveToFirst())
        {
            String name=cursor.getString(0);

            AlertDialog aler= new AlertDialog.Builder(this)
                    .setTitle("Contact")
                    .setMessage(name)
                    .setPositiveButton("Ok", null)
                    .show();

        }
    }

    public void pickClick(View v)
    {
        Intent i=new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        i.setType(Phone.CONTENT_TYPE);
        startActivityForResult(i, PICK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==PICK_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                Uri contactUri=data.getData();

                Cursor cursor=getContentResolver().query(contactUri,new String[]{Phone.DISPLAY_NAME_PRIMARY,Phone.NUMBER},null,null,null);

                if(cursor.moveToFirst())
                {
                    AlertDialog aler= new AlertDialog.Builder(this)
                                            .setTitle("Contact")
                                            .setMessage(cursor.getString(0) + "," + cursor.getString(1))
                                            .setCancelable(true)
                                            .setPositiveButton("Ok", null)
                                            .show();
                }

                cursor.close();
            }
        }
    }
}
