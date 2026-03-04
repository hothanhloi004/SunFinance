package com.example.fintrack.TransactionService.util;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class CameraAnalyzer implements ImageAnalysis.Analyzer {

    public interface OnTextDetectedListener {
        void onTextDetected(String text);
    }

    private final OnTextDetectedListener listener;

    public CameraAnalyzer(OnTextDetectedListener listener) {
        this.listener = listener;
    }

    @Override
    @SuppressLint("UnsafeOptInUsageError")
    public void analyze(@NonNull ImageProxy imageProxy) {

        if (imageProxy.getImage() != null) {

            InputImage image = InputImage.fromMediaImage(
                    imageProxy.getImage(),
                    imageProxy.getImageInfo().getRotationDegrees()
            );

            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    .process(image)
                    .addOnSuccessListener(result -> {
                        if (listener != null) {
                            listener.onTextDetected(result.getText());
                        }
                    })
                    .addOnCompleteListener(task -> imageProxy.close());
        } else {
            imageProxy.close();
        }
    }
}