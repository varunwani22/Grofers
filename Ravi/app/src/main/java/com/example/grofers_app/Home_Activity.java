package com.example.grofers_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;
import com.example.grofers_app.DiscountFragments.DicountMainActivity;
import com.example.grofers_app.listners.FragmentCommunication;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Home_Activity extends AppCompatActivity implements FragmentCommunication,OpenDiscountMain{
    private AppBarConfiguration mAppBarConfiguration;
    private static final int MULTIPLE_REQ_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActivityCompat.requestPermissions(Home_Activity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MULTIPLE_REQ_CODE);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void commincation(Bundle bundle) {
        if (bundle != null) {
            Intent intent = new Intent(Home_Activity.this, ProductDetailsActivity.class);
            intent.putExtra("name", bundle.getString("name"));
            intent.putExtra("Url", bundle.getString("Url"));
            intent.putExtra("selling", bundle.getString("selling"));
            intent.putExtra("Mrp", bundle.getString("Mrp"));
            intent.putExtra("unit", bundle.getString("unit"));
            intent.putExtra("des", bundle.getString("des"));
            startActivity(intent);
        }
    }

    @Override
    public void sendTOCart(Bundle bundle) {
        if (bundle != null) {
            Intent intent = new Intent(Home_Activity.this, ProductCart_Activity.class);
            intent.putExtra("name", bundle.getString("name"));
            intent.putExtra("Url", bundle.getString("Url"));
            intent.putExtra("selling", bundle.getString("selling"));
            intent.putExtra("Mrp", bundle.getString("Mrp"));
            intent.putExtra("unit", bundle.getString("unit"));
            Toast.makeText(this,"Product is Added To Cart",Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    @Override
    public void openDiscount(String data) {
        Intent intent=new Intent(Home_Activity.this, DicountMainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_REQ_CODE:
                if (grantResults.length >= 2) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        showToast("Camera and Storage permissions Granted");
                    } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_DENIED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Home_Activity.this, permissions[1])) {
                            showToast("Camera granted , Storage denied");
                        } else {
                            showToast("Camera granted , storage denied by selecting do not show again");
                        }

                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Home_Activity.this, permissions[0])) {
                            showToast("storage granted , camera denied");
                        } else {
                            showToast("storage granted , camera denied by selecting do not show again");
                        }

                    } else {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(Home_Activity.this, permissions[0])
                                && ActivityCompat.shouldShowRequestPermissionRationale(Home_Activity.this, permissions[1])) {
                            showToast("Both Denied");

                        } else {
                            showToast("Camera & Storage denied by clicking on Don't show it again");
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }


                }
        }

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}