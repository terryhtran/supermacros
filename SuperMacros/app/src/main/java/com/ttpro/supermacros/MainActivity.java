//Terry Tran
//terry.h.tran@gmail.com

package com.ttpro.supermacros;

import android.os.Bundle;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//mport com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Environment;

// Permissions
//import android.support.v4.app.ActivityCompat;  // deprecated
import androidx.core.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity; // deprecated
import androidx.core.content.ContextCompat;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Toast;

//import android.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import java.text.SimpleDateFormat;
import java.util.Date;
 
public class MainActivity extends AppCompatActivity {

    private static final int requestExternalStorage = 1;
    private static String[] permissionStorage = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    final String PREFS_NAME = "MyPrefsFile";
    final Boolean pro = true;
    Boolean addMode = true;
    Boolean addItem = true;
    Boolean catalogMode = true;
    DatabaseHandler databaseH;
    String[] items = new String[]{"Search"};
    String databaseName = "SuperMacrosDatabase";
    String date;
    String entryName;
    String entryTime;
    String itemName;
    String packageName;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packageName = getApplicationContext().getPackageName();
        setContentView(R.layout.activity_main);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(MainActivity.this, "Mind Body Soul", Toast.LENGTH_LONG).show();
        this.macrosToolbar();
        databaseH = new DatabaseHandler(MainActivity.this);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("first_run", true)) {
            databaseH.dropTables();
            insertSampleData();
            if (pro == false) {
                showProMsg();
            }
            tutorial();
            settings.edit().putBoolean("first_run", false).commit();
        } else {
            add();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        databaseH.closeDb();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_export_database) {
            exportDB();
            return true;
        }
        if (id == R.id.action_import_database) {
            importDB();
            return true;
        }
        if (id == R.id.action_set_targets) {
            if (pro != true) {
                showProMsg();
            }
            setTargets();
            return true;
        }
        if (id == R.id.action_contact_developer) {
            emailMe();
            return true;
        }
        if (id == R.id.action_tutorial) {
            if (pro != true) {
                showProMsg();
            }
            tutorial();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void add() {
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        toolbar.setTitle("Add To Log");
        AddFragment addFragment = new AddFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, addFragment).addToBackStack(null).commit();
    }

    public void notebook() {
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        toolbar.setTitle("Log");
        NotebookFragment notebookFragment = new NotebookFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, notebookFragment).addToBackStack(null).commit();
    }

    public void calendar() {
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        toolbar.setTitle("Calendar");
        CalendarFragment calendarFragment = new CalendarFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, calendarFragment).addToBackStack(null).commit();
    }

    public void catalog() {
        toolbar.setTitle("Your Database");
        CatalogFragment catalogFragment = new CatalogFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, catalogFragment).addToBackStack(null).commit();
    }

    public void setTargets() {
        toolbar.setTitle("Targets");
        TargetFragment targetFragment = new TargetFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, targetFragment).addToBackStack(null).commit();
    }

    private void tutorial() {
        toolbar.setTitle("Quick Tutorial");
        TutorialFragment tutorialFragment = new TutorialFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, tutorialFragment).addToBackStack(null).commit();
        return;
    }

    public void macrosToolbar() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        androidx.appcompat.widget.Toolbar toolbarBottom = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_bottom);
        toolbarBottom.inflateMenu(R.menu.macro_menu);
        toolbarBottom.setContentInsetsAbsolute(-100, 10);
        int childCount = toolbarBottom.getChildCount();
        int screenWidth = metrics.widthPixels;
        androidx.appcompat.widget.Toolbar.LayoutParams toolbarParams = new androidx.appcompat.widget.Toolbar.LayoutParams(screenWidth, ActionBar.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < childCount; i++) {
            View childView = toolbarBottom.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setLayoutParams(toolbarParams);
            }
        }

        toolbarBottom.setOnMenuItemClickListener(new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                String itemTitle = menuItem.getTitle().toString();
                switch (itemTitle) {
                    case "Add":
                        add();
                        break;
                    case "Notebook":
                        notebook();
                        break;
                    case "Catalog":
                        catalog();
                        break;
                    case "Calendar":
                        calendar();
                        break;
                }
                return true;
            }
        });
    }

    public void insertSampleData(){
        databaseH.create(new MyObject("Chicken Breast", 122, 2, 0, 26, 113));
        databaseH.create(new MyObject("Greek Yogurt", 120, 9, 0, 22, 225));
        databaseH.create(new MyObject("Mushrooms Sliced", 24, 3, 0, 3, 85));
    }

    public void showProMsg(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                //.setTitle("Pro Version Required")
                .setTitle("Hi there gentle soul,")
                .setMessage("Kindly support this hungry programmer with the pro version. " +
                        "The pro version is $3.69 and works the same as the free version.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                //.setNegativeButton("No", null)
                .show();
    }
    private void exportDB(){
        verifyStoragePermissions(MainActivity.this);
        File download = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File root = Environment.getRootDirectory();
        File data = Environment.getDataDirectory();
        String currentDbPath = "/data/" + packageName +"/databases/" + databaseName;
        String backupDbName = databaseName + ".db";
        final File currentDb = new File(data, currentDbPath);
        final File backupDb = new File(download, backupDbName);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Backup database to Download Directory?")
                .setMessage(backupDb.getAbsolutePath())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            FileChannel source = new FileInputStream(currentDb).getChannel();
                            FileChannel destination = new FileOutputStream(backupDb).getChannel();
                            destination.transferFrom(source, 0, source.size());
                            source.close();
                            destination.close();
                            Toast.makeText(MainActivity.this, "Database exported to: " + backupDb.getAbsolutePath(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error exporting to: " + backupDb.getAbsolutePath(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void importDB(){
        verifyStoragePermissions(MainActivity.this);
        File download = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File root = Environment.getRootDirectory();
        File data = Environment.getDataDirectory();
        String currentDbPath = "/data/" + packageName +"/databases/" + databaseName;
        String backupDbName = databaseName + ".db";
        final File currentDb = new File(data, currentDbPath);
        final File backupDb = new File(download, backupDbName);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Restore Database from Download directory?")
                .setMessage(backupDb.getAbsolutePath())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            FileChannel source = new FileInputStream(backupDb).getChannel();
                            FileChannel destination = new FileOutputStream(currentDb).getChannel();
                            destination.transferFrom(source, 0, source.size());
                            source.close();
                            destination.close();
                            Toast.makeText(MainActivity.this, "Database imported from: " + backupDb.getAbsolutePath() , Toast.LENGTH_LONG).show();
                        } catch(IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error importing from: " + backupDb.getAbsolutePath(), Toast.LENGTH_LONG).show();
                        }
                        catalog();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, permissionStorage, requestExternalStorage);

        }
    }

    public void emailMe() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"terry.h.tran@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Macros Feedback");
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose email client:"));
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}