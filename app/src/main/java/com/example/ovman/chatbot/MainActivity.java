package com.example.ovman.chatbot;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.util.Map;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;

import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;


public class MainActivity extends AppCompatActivity implements AIListener {
    private EditText editText;
    private Button button;
    private TextView textView;

    private AIService aiService;
    private AIDataService aiDataService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AIConfiguration config = new AIConfiguration("a3bcd8a5ecc54b3fbb2bd50f4c759366",
                AIConfiguration.SupportedLanguages.Spanish,
                AIConfiguration.RecognitionEngine.System);

        aiDataService = new AIDataService(config);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        editText = (EditText)findViewById(R.id.mensaje);
        button = (Button)findViewById(R.id.enviar);
        textView = (TextView)findViewById(R.id.chatView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendRequestTask(aiDataService).execute(editText.getText().toString());
            }
        });
    }

    public class SendRequestTask extends AsyncTask<String, String, AIResponse>{

        private AIDataService aiDataService;

        public SendRequestTask(AIDataService aiDataService) {
            this.aiDataService = aiDataService;
        }

        @Override
        protected AIResponse doInBackground(String... strings) {
            AIRequest aiRequest = new AIRequest();
            AIResponse aiResponse = null;
            aiRequest.setQuery(strings[0]);
            try {
                aiResponse = aiDataService.request(aiRequest);
            } catch (AIServiceException e) {
                e.printStackTrace();
            }
            return aiResponse;
        }

        @Override
        protected void onPostExecute(AIResponse aiResponse) {
            super.onPostExecute(aiResponse);
            Result result = aiResponse.getResult();
            textView.append("You: " + result.getResolvedQuery() + "\r\n");
            textView.append("Bot: " + result.getFulfillment().getSpeech() + "\r\n");
        }
    }

    @Override
    public void onResult(AIResponse result1) {
        Result result = result1.getResult();
        /*
        String parameterString = "";
        if(result.getParameters() != null && !result.getParameters().isEmpty()){
            for(final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()){
                parameterString += ""
            }

        }*/
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