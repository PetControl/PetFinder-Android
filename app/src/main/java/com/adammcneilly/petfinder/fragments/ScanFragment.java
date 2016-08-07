package com.adammcneilly.petfinder.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.core.CoreFragment;
import com.adammcneilly.petfinder.service.TwilioService;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.twilio.client.TwilioClientService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class ScanFragment extends CoreFragment implements QRCodeReaderView.OnQRCodeReadListener {
    //region UI Elements
    private QRCodeReaderView qrCodeReaderView;
    //endregion

    //region Constants
    public static final String FRAGMENT_NAME = "ScanFragment";
    private static final int CAMERA_PERMISSON = 0;
    private static final String LOG_TAG = ScanFragment.class.getSimpleName();
    //endregion

    public static ScanFragment newInstance() {
        Bundle args = new Bundle();

        ScanFragment fragment = new ScanFragment();
        fragment.setArguments(args);

        return fragment;
    }

    //region Lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (CoordinatorLayout) inflater.inflate(R.layout.fragment_scan, container, false);

        qrCodeReaderView = (QRCodeReaderView) root.findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        setupToolbar(getString(R.string.scan_tag), true);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupToolbar(getString(R.string.scan_tag), true);
        if(hasCameraPermission()) {
            qrCodeReaderView.getCameraManager().startPreview();
        } else {
            permissionCheck();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(hasCameraPermission()) {
            qrCodeReaderView.getCameraManager().stopPreview();
        }
    }
    //endregion

    //region QR
    @Override
    public void onQRCodeRead(final String text, PointF[] points) {
        Log.v(LOG_TAG, text);
        snackbar = Snackbar.make(root, text, Snackbar.LENGTH_INDEFINITE)
                .setAction("View Info", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showFragment(PetInfoFragment.FRAGMENT_NAME, text);
                    }
                });
        snackbar.show();
        qrCodeReaderView.getCameraManager().stopPreview();
    }

    @Override
    public void cameraNotFound() {
        Log.v(LOG_TAG, getString(R.string.camera_not_found));
    }

    @Override
    public void QRCodeNotFoundOnCamImage() {
        Log.v(LOG_TAG, getString(R.string.not_found));
    }
    //endregion

    //region Permissions
    private boolean hasCameraPermission() {
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)) == PackageManager.PERMISSION_GRANTED;
    }

    private void permissionCheck() {
        if(!hasCameraPermission()) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                //TODO:
                // Show an explanation to the user
            } else {
                // No explanation needed, request
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSON);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case CAMERA_PERMISSON:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, do stuff!
                    // Since this would really only be done for onResume, start thingy.
                    qrCodeReaderView.getCameraManager().startPreview();
                } else {
                    // Denied, boo! Disable functionality.
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
    //endregion
}
