package vn.com.sthink.demoqrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

public class MainActivity extends  AppCompatActivity {

    private ImageButton qrImageButton;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrImageButton = (ImageButton) this.findViewById(R.id.qr_image_button);
        qrImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQRScanner();
            }
        });
    }

    private void initQRScanner(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Point to QR code to scan");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    private void sendSMS(String content){
        Intent sendSMSIntent = new Intent(Intent.ACTION_VIEW);
        sendSMSIntent.putExtra("sms_body", content);
        sendSMSIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendSMSIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            if (IntentIntegrator.QR_CODE_TYPES.contains(scanResult.getFormatName())) {
                //Toast.makeText(this,scanResult.getContents(), Toast.LENGTH_SHORT).show();
                sendSMS(scanResult.getContents());
//                new android.os.Handler().postDelayed(
//                        new Runnable() {
//                            public void run() {
//                                initQRScanner();
//                            }
//                        },
//                        2000);
            } else {
                Toast.makeText(this, "Bad Format", Toast.LENGTH_SHORT).show();
                initQRScanner();
            }
        }
    }
}
