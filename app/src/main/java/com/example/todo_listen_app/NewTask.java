package com.example.todo_listen_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NewTask extends AppCompatActivity
{
    EditText tasknameInputText;
    EditText descriptionInputText;
    EditText timeInputText;
    EditText dateInputText;

    static final int REQUEST_CODE_SPEECH_INPUT_NAME = 101;
    static final int REQUEST_CODE_SPEECH_INPUT_DESC = 102;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);

        tasknameInputText = findViewById(R.id.tasknameInput);
        descriptionInputText = findViewById(R.id.descriptonInput);
        timeInputText = findViewById(R.id.timeInput);
        dateInputText = findViewById(R.id.dateInput);

        // Back button view to return to your main activity.
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Return to main activity
                onBackPressed();
            }
        });

        // Date input opens date picker to select a date.
        dateInputText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(NewTask.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        // Set your selected date
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Change date format and insert date in dateInput textview
                        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
                        dateInputText.setText(df.format(myCalendar.getTime()));
                    }
                }, myCalendar.get(Calendar.YEAR),  myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Button listener saves your spoken words and turns them to text to save on taskname textview.
        ImageButton tasknameVoiceBtn = findViewById(R.id.taskNameVoiceBtn);
        tasknameVoiceBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StartVoiceRecognition(REQUEST_CODE_SPEECH_INPUT_NAME);
            }
        });

        // Button listener saves your spoken words and turns them to text to save on description textview.
        ImageButton taskDescVoiceBtn = findViewById(R.id.taskDescVoiceBtn);
        taskDescVoiceBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StartVoiceRecognition(REQUEST_CODE_SPEECH_INPUT_DESC);
            }
        });

        // Save button view to save your data on a new task instance and save it in the storage.
        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (tasknameInputText.getText().toString().isEmpty())
                {
                    Toast.makeText(NewTask.this, getString(R.string.requiredNameInput), Toast.LENGTH_SHORT).show();
                }
                else if (dateInputText.getText().toString().isEmpty())
                {
                    Toast.makeText(NewTask.this, getString(R.string.requiredDateInput), Toast.LENGTH_SHORT).show();
                }
                else {
                    String taskName = tasknameInputText.getText().toString();
                    String descText = descriptionInputText.getText().toString();
                    String time = timeInputText.getText().toString();
                    String date = dateInputText.getText().toString();

                    // Create and save new task
                    Task_Item task = new Task_Item(date, taskName, descText, time);
                    DataStorage.SaveTask(task);

                    startActivity(new Intent(NewTask.this, MainActivity.class));
                }

            }
        });

        // Clear button view to clear all of content of your text views.
        Button clearBtn = findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Clear your input_view(s) here
                tasknameInputText.getText().clear();
                descriptionInputText.getText().clear();
                timeInputText.getText().clear();
                dateInputText.getText().clear();
            }
        });
    }

    /**
     * This methods starts to listen to your spoken words and turns it to text.
     * @param req_code Identfier of which button was clicked.
     */
    void StartVoiceRecognition (int req_code)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.VoiceHintText));

        try
        {
            startActivityForResult(intent, req_code);
        } catch (Exception e)
        {
            Toast.makeText(this, getString(R.string.VoiceAppRequired), Toast.LENGTH_SHORT).show();

            String appPackageName = "com.google.android.googlequicksearchbox";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }

    /**
     * This methods allows to get access to the informations of your words that was turned into text.
     * @param requestCode Identifier (KEY) of your data.
     * @param resultCode Identifier (KEY) of your data.
     * @param data Your words saved as Intent.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQUEST_CODE_SPEECH_INPUT_NAME:
                if (resultCode == RESULT_OK && null != data)
                {
                    // Result of your all of your words.
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    // Sets your spoken words into taskname textview.
                    tasknameInputText.setText(tasknameInputText.getText() + result.get(0));
                }
                break;

            case REQUEST_CODE_SPEECH_INPUT_DESC:
                if (resultCode == RESULT_OK && null != data)
                {
                    // Result of your all of your words.
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    // Sets your spoken words into taskname textview.
                    descriptionInputText.setText(descriptionInputText.getText() + result.get(0));
                }
                break;
        }
    }
}
