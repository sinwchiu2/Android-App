package com.example.user.kraken;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Blank extends Activity implements
        RecognitionListener {

    Dialog match_text_dialog;
    ListView text_list;
    ArrayList<String> matches_text;
    EditText textMessage;

    private Button button_cancel;
    private Button button_save;
    private ToggleButton toggleButton;
    private AlertDialog.Builder saveDialog;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";

    private SharedPreferences settings;
    private static final String data = "DATA";
    private static final String messageField = "Message";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        initToolbar();
        initComponent();
        initAlertDia();
        initSpeechRec();
        setEventListener();
        readData();
    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(R.string.blank);
    }

    public void initComponent() {
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_save = (Button) findViewById(R.id.button_save);
        textMessage = (EditText)findViewById(R.id.etText);
    }

    public void initSpeechRec() {
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
    }

    public void setEventListener() {
        textMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textMessage.setHint(null);
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    speech.startListening(recognizerIntent);
                } else {
                    speech.stopListening();
                }
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textMessage.getText().toString().matches("")) {
                    finish();
                } else {
                   Dialog d = saveDialog.show();
                }
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textMessage.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                //email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
                //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
                //email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }

    public void readData(){
        settings = getSharedPreferences(data,0);
        textMessage.setText(settings.getString(messageField, ""));
    }

    public void saveData(){
        settings = getSharedPreferences(data,0);
        settings.edit()
                .putString(messageField, textMessage.getText().toString())
                .commit();
    }

    public void clrData() {
        settings = getSharedPreferences(data,0);
        settings.edit()
                .putString(messageField, "")
                .commit();
    }

    public void initAlertDia() {
        saveDialog = new AlertDialog.Builder(Blank.this);
        saveDialog.setTitle("離開?");
        saveDialog.setMessage("是否保留信件作下次修改?");
        saveDialog.setPositiveButton("保留", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                saveData();
                dialog.dismiss();
                finish();
            }
        });
        saveDialog.setNegativeButton("離開", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                clrData();
                dialog.cancel();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (textMessage.getText().toString().matches("")) {
            finish();
        } else {
            saveDialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        Toast.makeText(this, errorMessage,
                Toast.LENGTH_LONG).show();
        toggleButton.setChecked(false);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");

        match_text_dialog = new Dialog(Blank.this);
        match_text_dialog.setContentView(R.layout.dialog_matches_frag);
        match_text_dialog.setTitle(R.string.selectOne);
        text_list = (ListView)match_text_dialog.findViewById(R.id.list);
        matches_text = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, matches_text);
        text_list.setAdapter(adapter);
        text_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                textMessage.append(matches_text.get(position));
                match_text_dialog.hide();
            }
        });
        match_text_dialog.show();
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
}