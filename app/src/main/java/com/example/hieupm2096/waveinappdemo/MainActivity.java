package com.example.hieupm2096.waveinappdemo;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.airbnb.lottie.LottieAnimationView;
import com.cleveroad.audiovisualization.AudioVisualization;
import com.cleveroad.audiovisualization.DbmHandler;
import com.cleveroad.audiovisualization.SpeechRecognizerDbmHandler;

public class MainActivity extends AppCompatActivity {

    private AudioVisualization audioVisualization;
    private SpeechRecognizerDbmHandler dbmHandler;

    private ImageButton btnSpeak;
    private LottieAnimationView animSpeak;

    private Intent intent;

    private boolean recognizing = false;

    private void log1(String message) {
        Log.d("moo", ": " + message);
    }

    private void log2(String message) {
        Log.d("cow", ": " + message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSpeak = findViewById(R.id.btn_speak);
        animSpeak = findViewById(R.id.anim_btn_speak);

        audioVisualization = findViewById(R.id.visualizer_view);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!recognizing) {

                    intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplicationContext().getPackageName());

                    dbmHandler.startListening(intent);
                } else {
                    dbmHandler.stopListening();
                }
            }
        });

        dbmHandler = DbmHandler.Factory.newSpeechRecognizerDbmHandler(getApplicationContext(), 0, 1);
        dbmHandler.innerRecognitionListener(new SimpleRecognitionListener());
        audioVisualization.linkTo(dbmHandler);
    }

    private void onStopRecognizing() {
        recognizing = false;
        Log.i("STOP_LISTENING", "Stop listening");
        animSpeak.cancelAnimation();
    }

    private void onStartRecognizing() {
        Log.i("START_LISTENING", "Start listening");
        recognizing = true;
        animSpeak.playAnimation();
    }

    private class SimpleRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {
            log1("onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            log1("onBeginningOfSpeech");
        }

        @Override
        public void onRmsChanged(float rms_dB) {
            log2("onRmsChanged");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            log1("onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            log1("onEndOfSpeech");
        }

        @Override
        public void onResults(Bundle results) {
            log1("onResults");
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            log1("onPartialResults");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            log1("onEvent");
        }

        @Override
        public void onError(int error) {
            String message = "";

            if (error == SpeechRecognizer.ERROR_AUDIO) message = "audio";
            else if (error == SpeechRecognizer.ERROR_CLIENT) message = "client";
            else if (error == SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS)
                message = "insufficient permissions";
            else if (error == SpeechRecognizer.ERROR_NETWORK) message = "network";
            else if (error == SpeechRecognizer.ERROR_NETWORK_TIMEOUT) message = "network timeout";
            else if (error == SpeechRecognizer.ERROR_NO_MATCH) message = "no match found";
            else if (error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) message = "recognizer busy";
            else if (error == SpeechRecognizer.ERROR_SERVER) message = "server";
            else if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) message = "speech timeout";

            log1("error " + message);
        }

//        @Override
//        public void onReadyForSpeech(Bundle bundle) {
//            onStartRecognizing();
//        }
//
//        @Override
//        public void onBeginningOfSpeech() {
//
//        }
//
//        @Override
//        public void onRmsChanged(float v) {
//
//        }
//
//        @Override
//        public void onBufferReceived(byte[] bytes) {
//
//        }
//
//        @Override
//        public void onEndOfSpeech() {
//
//        }
//
//        @Override
//        public void onError(int i) {
//            onStopRecognizing();
//        }
//
//        @Override
//        public void onResults(Bundle bundle) {
//            onStopRecognizing();
//        }
//
//        @Override
//        public void onPartialResults(Bundle bundle) {
//
//        }
//
//        @Override
//        public void onEvent(int i, Bundle bundle) {
//
//        }
    }


}
