package ru.android.polenova;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class NewNoteActivity extends AppCompatActivity {

    private EditText editTextName;
    private MultiAutoCompleteTextView editTextBody;
    private EditText editTextDate;
    private CheckBox checkBoxSelect;

    private String textName;
    private String textBody;
    private String textDate;
    private String deadLineDate;
    private boolean checkIsChecked;

    private Note getNote;
    private Bundle bundle;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 22;

    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    final Calendar dateDeadLine = Calendar.getInstance();
    private NoteRepository noteRepository = App.getNoteRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        this.setTitle(R.string.title_new_note);
        initView();
        initBundle();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void initBundle() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.setTitle(R.string.title_edit_note);
            getNote = (Note) bundle.getSerializable(Note.class.getSimpleName());
            if (getNote == null) {
                return;
            }
            editTextName.setText(getNote.getTextNameNote().toString());
            editTextBody.setText(getNote.getTextBodyNote().toString());
            if (getNote.getDeadLineDate() != null) {
                deadLineDate = format.format(getNote.getDeadLineDate());
            } else {
                deadLineDate = "";
            }
            editTextDate.setText(deadLineDate);
            checkBoxSelect.setChecked(getNote.isChecked());
        }
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextName = findViewById(R.id.editNameNote);
        editTextBody = findViewById(R.id.multiTextNote);
        editTextDate = findViewById(R.id.editDeadLine);
        checkBoxSelect = findViewById(R.id.checkBoxSelectDeadLine);
        checkBoxSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if ("".equals(editTextDate.getText().toString())) {
                        setDate();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.toast_deadline_no, Toast.LENGTH_SHORT).show();
                    String convertText = editTextDate.getText().toString();
                    editTextDate.setHint(convertText);
                    editTextDate.getText().clear();
                }
            }
        });
        ImageButton buttonDate = findViewById(R.id.imageButtonCalendar);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
    }

    public void setDate() {
        new DatePickerDialog(NewNoteActivity.this, date,
                dateDeadLine.get(Calendar.YEAR),
                dateDeadLine.get(Calendar.MONTH),
                dateDeadLine.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateDeadLine.set(Calendar.YEAR, year);
            dateDeadLine.set(Calendar.MONTH, monthOfYear);
            dateDeadLine.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

    private void setInitialDate() {
        editTextDate.setText(DateUtils.formatDateTime(this,
                dateDeadLine.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        checkBoxSelect.setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            saveInfoNote();
            return false;
        } else if (id == R.id.action_clear) {
            Toast.makeText(NewNoteActivity.this, R.string.toast_clear, Toast.LENGTH_LONG).show();
            editTextName.getText().clear();
            editTextBody.getText().clear();
            editTextDate.getText().clear();
            checkBoxSelect.setChecked(false);
            return false;
        } else if (id == android.R.id.home) {
            Intent intent = new Intent(NewNoteActivity.this, ListNoteActivity.class);
            startActivity(intent);
            return false;
        } else if (id == R.id.action_share) {
            shareText();
            return false;
        }
        return true;
    }

    private void shareText() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            SmsManager smgr = SmsManager.getDefault();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, editTextName.getText().toString() + "\n" + editTextBody.getText().toString());
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.item_share)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            shareText();
        } else {
            Toast.makeText(this, R.string.share_no, Toast.LENGTH_SHORT).show();
        }
    }


    private void saveInfoNote() {
        textName = editTextName.getText().toString();
        textBody = editTextBody.getText().toString();
        textDate = editTextDate.getText().toString();
        checkIsChecked = checkBoxSelect.isChecked();
        Date deadLineDate;
        try {
            deadLineDate = format.parse(textDate);
        } catch (ParseException e) {
            deadLineDate = null;
        }
        Note noteNewInfo;
        if (getNote != null) {
            noteRepository.deleteById(getNote);
            noteNewInfo = new Note(getNote.getId(), textName, textBody, checkIsChecked, new Date(), deadLineDate);
        } else {
            noteNewInfo = NoteFactory.createNote(textName, textBody, checkIsChecked, deadLineDate);
        }
        try {
            noteRepository.saveNote(noteNewInfo);
            Toast.makeText(this, R.string.toast_saved, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.toast_not_saved, Toast.LENGTH_SHORT).show();
        }
    }
}

