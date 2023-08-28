package com.mhealth.nishauri.Activities;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;

public class EmailValidator implements TextWatcher {

    private EditText editText;

    public EmailValidator(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        validateEmail();
    }

    @Override
    public void afterTextChanged(Editable editable) {}

    private void validateEmail() {
        String email = editText.getText().toString().trim();

        if (email.isEmpty()) {
            editText.setError(null);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError("Invalid email address");
        } else {
            editText.setError(null);
        }
    }
}
