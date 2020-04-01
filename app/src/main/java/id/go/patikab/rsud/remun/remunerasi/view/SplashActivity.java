package id.go.patikab.rsud.remun.remunerasi.view;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.VersionCek;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar intro_progress;
    private static final String LOG_TAG = "iabv3";
    ApiInterface apiInterface;
    // put your Google merchant id here (as stated in public profile of your Payments Merchant Center)
    // if filled library will provide protection against Freedom alike Play Market simulators
    private static final String MERCHANT_ID=null;


    private boolean readyToPurchase = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        intro_progress=(ProgressBar)  findViewById(R.id.intro_progress);
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // If you want to modify a view in your Activity
                SplashActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        checkAccount();

                    }
                });
            }
        }, 3000);
        ImageView imageView = (ImageView) findViewById(R.id.logo_Secur);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
        // imageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void checkAccount() {

         Integer versioni = 0;
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versioni = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(versioni != -1){
//            Toast.makeText(this, versioni.toString(), Toast.LENGTH_SHORT).show();
//            Log.d("packag2e",getPackageName());
            apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<VersionCek> call = apiInterface.getVersion(getPackageName(),versioni.toString(),getString(R.string.app_key));
            final Integer finalVersioni = versioni;
            call.enqueue(new Callback<VersionCek>() {
                @Override
                public void onResponse(Call<VersionCek> call, Response<VersionCek> response) {
                    if(response.isSuccessful()){
                        Integer versione = response.body().getVersi();
                        String featurs_update=response.body().getMessage();
                        String title_update=response.body().getTitle();
                       if(finalVersioni > versione){

                           View v = (View)  getLayoutInflater().inflate(R.layout.update_message,null);
                           TextView update_text_view_title=(TextView) v.findViewById(R.id.update_text_view_title);
                           TextView update_text_view_updates=(TextView) v.findViewById(R.id.update_text_view_updates);
                           update_text_view_title.setText(title_update);
                           update_text_view_updates.setText(featurs_update);
                           AlertDialog.Builder builder;
                           builder = new AlertDialog.Builder(SplashActivity.this);
                           builder.setTitle("New Update")
                                   //.setMessage(response.body().getValue())
                                   .setView(v)
                                   .setPositiveButton(getResources().getString(R.string.update_now), new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int which) {
                                           final String appPackageName=getApplication().getPackageName();
                                           try {
                                               startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                           } catch (android.content.ActivityNotFoundException anfe) {
                                               startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                                           }
                                           finish();
                                       }
                                   })
                                   .setNegativeButton(getResources().getString(R.string.skip), new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int which) {

                                               Intent intent = new Intent(SplashActivity.this,MainApps.class);
                                               startActivity(intent);
                                               overridePendingTransition(R.anim.enter, R.anim.exit);
                                               finish();

                                       }
                                   })
                                   .setCancelable(false)
                                   .setIcon(R.drawable.ic_update)
                                   .show();
                       }else {
//                           Toast.makeText(SplashActivity.this, "Masuk", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(SplashActivity.this, MainApps.class);
                           startActivity(intent);
                           overridePendingTransition(R.anim.enter, R.anim.exit);
                           finish();
                       }
                    }
                }

                @Override
                public void onFailure(Call<VersionCek> call, Throwable t) {
                    Toast.makeText(SplashActivity.this, "Cek Koneksi Anda !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashActivity.this, MainApps.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }
            });
        }
    }
}
