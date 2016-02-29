package com.frozenfrost.frozenfrost;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.frozenfrost.frozenfrost.SendMailTask;
import java.util.Arrays;
import java.util.List;


public class ContactUs extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contact_us_layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        final Button send =(Button)view.findViewById(R.id.contact_us_button);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("ContactUs", "Send Button Clicked.");
                String fromEmail = getResources().getString(R.string.from_email_id);
                String fromPassword = getResources().getString(R.string.from_email_password);
                String toEmails = ((TextView) view.findViewById(R.id.contact_us_edittext1)).getText().toString();
                List toEmailList = Arrays.asList(toEmails.split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList);
                String emailSubject = "New Query Generated..";
                String emailBody = ((TextView) view.findViewById(R.id.contact_us_edittext2)).getText().toString();
                new SendMailTask(getActivity()).execute(fromEmail,fromPassword, toEmailList, emailSubject, emailBody);
            }
        });
    }


}
