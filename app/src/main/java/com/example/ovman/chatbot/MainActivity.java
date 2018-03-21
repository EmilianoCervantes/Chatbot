package com.example.ovman.chatbot;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MainActivity extends Activity implements AIListener {
    private TextView textView;
    private EditText editText;
    private Button button;

    //Chatbox
    private AIService aiService;
    private AIDataService aiDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.chatView);
        editText = (EditText)findViewById(R.id.mensaje);
        button = (Button)findViewById(R.id.enviar);

        final AIConfiguration configuration = new AIConfiguration("a3bcd8a5ecc54b3fbb2bd50f4c759366",
                AIConfiguration.SupportedLanguages.Spanish,
                AIConfiguration.RecognitionEngine.System
        );

        aiDataService = new AIDataService(configuration);
        aiService = AIService.getService(this, configuration);
        aiService.setListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
    }

    public class SendRequestTask extends AsyncTask<String, String, AIResponse>{

        @Override
        protected AIResponse doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(AIResponse aiResponse) {
            super.onPostExecute(aiResponse);
            Result result = aiResponse.getResult();
            textView.append("You :"+result.getResolvedQuery()+"\r\n");
            textView.append("Bot :"+result.getFulfillment().getSpeech()+"\r\n");
        }
    }

    @Override
    public void onResult(AIResponse result) {
        Result result1 = result.getResult();

        //Obtener parámtetros

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
