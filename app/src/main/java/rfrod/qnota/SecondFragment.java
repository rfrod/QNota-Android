package rfrod.qnota;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


public class SecondFragment extends Fragment {
    private Camera mCamera;
    private CameraPreviewView mPreview;

    public static final int QNOTA_REQUEST_CODE = 2303;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        requestCameraPermission(getContext());
        // Inflate the layout for this fragment
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create an instance of Camera
        mCamera = null;

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreviewView(getContext(), mCamera);
        FrameLayout preview = (FrameLayout) view.findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        // Add a listener to the Capture button
        Button captureButton = (Button) view.findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
            new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    // get an image from the camera
                    mCamera.takePicture(null, null, mPicture);

                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
                }
            }
        );
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        private static final String TAG = "PictureCallback";

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            /*if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }*/
            /*File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }*/
        }
    };

    /**
     *  Request permissions for the camera use.
     */

    public void requestCameraPermission(Context context){
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            return;
        } else {
            // You can directly ask for the permission.
            requestPermissions(new String[] { Manifest.permission.CAMERA}, QNOTA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("fragment");
    }

    /*
    private boolean checkPermission(Context context){
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            return true;
        } else if (shouldShowRequestPermissionRationale(...)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.

        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            SecondFragment.requestPermissions()
            requestPermissionLauncher.launch(
                    Manifest.permission.REQUESTED_PERMISSION);
        }
        return true;
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult
                    (new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });*/
}