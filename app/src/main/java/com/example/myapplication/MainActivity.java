package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    public View app_bar_main;
    public Intent intent;
    public RecyclerView sliderview1;
    public ProgressDialog progressDialog;
    public final int REQUEST_LOCATION_PERMISSION =100;
    public final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    public Dialog dialogbox;
    public LinearLayout dialogClose, dialogDone;

    public String USER_ID, productPackage = "0", servicepackage = "0";
    public CardView callBtn, mailBtn, whatupbtn;
    public TextView deliveryDate, status;
    public Context context;

    public NavigationView nav;
    public ActionBarDrawerToggle toggle;
    public RecyclerView home_list, menu_list;
    public AppCompatButton guardrequest;
    public int pos;
    public AdapterDrawer adapterDrawer;
    public ArrayList<MenuListResponse> menuListResponseArrayList;
    public TextView name;
    public ImageView call_btn,mail_btn,wts_btn;
    public CircleImageView circleImageView;
    public String Latitude, Logitude, Lat, Longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        init();

    }


    public void init() {

        toolbar = findViewById(R.id.toolbar);
        nav = findViewById(R.id.navmenu);
        drawerLayout = findViewById(R.id.drawer_layout);
        app_bar_main = findViewById(R.id.app_bar_main);
        drawerLayout.addDrawerListener(toggle);
        home_list = findViewById(R.id.home_list);
        menu_list = findViewById(R.id.menu_list);
        guardrequest = findViewById(R.id.btn_request);
        name = findViewById(R.id.my_name);
        circleImageView = findViewById(R.id.my_profile_img);
        call_btn = findViewById(R.id.call_button);
        mail_btn = findViewById(R.id.mail_btn);
        wts_btn = findViewById(R.id.wtsup_btn);
//        sliderView = findViewById(R.id.view_pager);

        function();
        //  Adslist();

    }


    public void function() {
//        Lat = PrefConnect.readString(context, PrefConnect.LATITUDE, "");
//        Longi = PrefConnect.readString(context, PrefConnect.LOGITUDE, "");

        String id = getIntent().getStringExtra("id");
        Log.e("USER_ID", USER_ID);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        coloredToolbar("");
        menuList();
        // Slidelist();



        call_btn.setOnClickListener(v -> {

            if(callisPermissionGrantedFirst()){

                Log.e("dfoid","fock");
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" +"+91" + mobilenumber));
//                startActivity(callIntent);

                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:911"));
                if(ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(call);
            }
            else{
                callisPermissionGranted();
            }
        });
        mail_btn.setOnClickListener(v ->emailSupportHelp());
        wts_btn.setOnClickListener(v -> {
            onClickWhatsApp();

        });


    }



    private void onClickWhatsApp() {

        try {

            Uri uri = Uri.parse("smsto:" + "+917010782881");
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, ""));

        } catch (Exception e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void emailSupportHelp() {
        String emailAddress = "office@sekugs.com";
        String subject = "Your subject";
        String body = "body of email";

        openEmailClient(emailAddress, subject, body);


    }
    private void openEmailClient(String emailAddress, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }



    public boolean callisPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(Accueil.this, "Permission is granted", Toast.LENGTH_SHORT).show();
                return true;
            } else {

                //Toast.makeText(Accueil.this, "Permission is revoked", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_CONTACTS,
                        android.Manifest.permission.CALL_PHONE}, REQUEST_READ_EXTERNAL_STORAGE);
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            //Toast.makeText(Accueil.this, "Permission is granted", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean callisPermissionGrantedFirst() {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED;
        }
        else {

            return true;
        }
    }



    public void menuList() {


        menuListResponseArrayList = new ArrayList<>();

        // added data to array list
        menuListResponseArrayList.add(new MenuListResponse("1", R.drawable.dashboard_icon, "Home"));
        menuListResponseArrayList.add(new MenuListResponse("2", R.drawable.history_icon, "My History"));
        menuListResponseArrayList.add(new MenuListResponse("4", R.drawable.profile_icon, "My Profile"));
        menuListResponseArrayList.add(new MenuListResponse("5", R.drawable.privacy_icon, "Privacy Policy"));
        menuListResponseArrayList.add(new MenuListResponse("6", R.drawable.terms_icon, "Terms & Condition"));
        menuListResponseArrayList.add(new MenuListResponse("7", R.drawable.logout_icon, "Logout"));
        menuListResponseArrayList.add(new MenuListResponse("8", R.drawable.delete_menu, "Delete Account"));


        //adapterDrawer=new AdapterDrawer(context,menuListResponseArrayList,itemClickListener);

        adapterDrawer = new AdapterDrawer(context, menuListResponseArrayList, item -> {

            if (item.equals("1")) {

                displayView(AppContant.page_DashBoard);

            } else if (item.equals("2")) {

                displayView(AppContant.page_history);

            } else if (item.equals("3")) {

                displayView(AppContant.page_payment_history);

            } else if (item.equals("4")) {

                displayView(AppContant.page_profile);

            } else if (item.equals("5")) {

                displayView(AppContant.page_policy);

            } else if (item.equals("6")) {

                displayView(AppContant.page_terms);

            } else if (item.equals("7")) {

                displayView(AppContant.page_logout);

            }
            else if (item.equals("8")) {

                displayView(AppContant.page_delete);

            }else {

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        menu_list.setHasFixedSize(true);
        menu_list.setLayoutManager(new LinearLayoutManager(context));
        menu_list.setAdapter(adapterDrawer);
    }

    public void displayView(int position) {

        new Handler().post(() -> {

            switch (position) {

                case AppContant.page_DashBoard:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    intent = new Intent(context, MainActivity2.class);
                    startActivity(intent);
                    finish();
                    break;

                case AppContant.page_history:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    intent = new Intent(context, MainActivity2.class);
                    intent.putExtra("type", "upcoming");
                    startActivity(intent);
                    break;

                case AppContant.page_profile:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    intent = new Intent(context, MainActivity2.class);
                    startActivity(intent);
                    break;

                case AppContant.page_policy:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    intent = new Intent(context, MainActivity2.class);
                    startActivity(intent);
                    break;

                case AppContant.page_terms:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    intent = new Intent(context, MainActivity2.class);
                    startActivity(intent);
                    break;


                case AppContant.page_logout:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    logout();
                    break;
                case AppContant.page_delete:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                default:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

            }

        });
    }


    private void coloredToolbar(String text) {

        toolbar.setTitle("");
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout.closeDrawer(GravityCompat.START);
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.app_colur));

    }

    private void logout() {

        dialogbox = new Dialog(context);
        dialogbox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogbox.setContentView(R.layout.dialog_logout);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogbox.show();
        dialogbox.setCancelable(false);

        dialogDone = dialogbox.findViewById(R.id.yes);
        dialogClose = dialogbox.findViewById(R.id.no);

        dialogClose.setOnClickListener(v -> dialogbox.dismiss());

        dialogDone.setOnClickListener(v -> {

            dialogbox.dismiss();




        });

    }



    public boolean isPermissionGrantedFirst() {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;
        }
        else {

            return true;
        }
    }







    }
