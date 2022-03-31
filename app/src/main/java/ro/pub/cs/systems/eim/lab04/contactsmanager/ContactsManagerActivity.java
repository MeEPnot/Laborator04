package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    Button visibilityButton;
    Button saveButton;
    Button cancelButton;

    EditText nameField;
    EditText numberField;
    EditText emailField;
    EditText addressField;
    EditText titleField;
    EditText companyField;
    EditText websiteField;
    EditText imField;

    LinearLayout secondLayout;

    private class VisibilityClickListener implements View.OnClickListener {
        public void onClick(View view) {
            if (secondLayout.getVisibility() == View.VISIBLE) {
                ((Button) view).setText("View additional fields");
                secondLayout.setVisibility(View.INVISIBLE);
            } else {
                ((Button) view).setText("Hide additional fields");
                secondLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private VisibilityClickListener visibilityClickListener = new VisibilityClickListener();

    private class CancelClickListener implements View.OnClickListener {
        public void onClick(View view) {
            setResult(Activity.RESULT_CANCELED, new Intent());
            finish();
        }
    }

    private CancelClickListener cancelClickListener = new CancelClickListener();

    private class SaveClickListener implements View.OnClickListener {
        public void onClick(View view) {
            String name = nameField.getText().toString();
            String phone = numberField.getText().toString();
            String email = emailField.getText().toString();
            String address = addressField.getText().toString();
            String jobTitle = titleField.getText().toString();
            String company = companyField.getText().toString();
            String website = websiteField.getText().toString();
            String im = imField.getText().toString();

            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            if (name != null) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
            }
            if (phone != null) {
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
            }
            if (email != null) {
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
            }
            if (address != null) {
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
            }
            if (jobTitle != null) {
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
            }
            if (company != null) {
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
            }
            ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
            if (website != null) {
                ContentValues websiteRow = new ContentValues();
                websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                contactData.add(websiteRow);
            }
            if (im != null) {
                ContentValues imRow = new ContentValues();
                imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                contactData.add(imRow);
            }
            intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
            startActivityForResult(intent, 20220);
        }
    }

    private SaveClickListener saveClickListener = new SaveClickListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        visibilityButton = (Button) findViewById(R.id.button_show_additional_fields);
        visibilityButton.setOnClickListener(visibilityClickListener);
        saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(saveClickListener);
        cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(cancelClickListener);

        nameField = (EditText) findViewById(R.id.editText_name);
        numberField = (EditText) findViewById(R.id.editText_number);
        emailField = (EditText) findViewById(R.id.editText_email);
        addressField = (EditText) findViewById(R.id.editText_address);
        titleField = (EditText) findViewById(R.id.editText_job);
        companyField = (EditText) findViewById(R.id.editText_company);
        websiteField = (EditText) findViewById(R.id.editText_website);
        imField = (EditText) findViewById(R.id.editText_im);

        secondLayout = (LinearLayout) findViewById(R.id.layout_additional);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                numberField.setText(phone);
            } else {
                Toast.makeText(this, "No number given", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 20220:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }

}