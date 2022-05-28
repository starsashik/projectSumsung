package com.example.project.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project.Activity.MainActivity2;
import com.example.project.OurData;
import com.example.project.R;
import com.example.project.databinding.FragmentSecondBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FirebaseAuth mAuth;
//    FirebaseAuth mAuth = OurData.myAuth;
    private final String TAG = "project";
    private EditText ETemail;
    private EditText ETpassword;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
//        OurData.myAuth = mAuth;

        ETemail = (EditText) binding.TextEmail2;
        ETpassword = (EditText) binding.TextPassword2;


        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ETemail.getText().toString();
                String password = ETpassword.getText().toString();
                if (!email.contains("@") && !email.contains(".")) {
                    binding.TextView2.setText(R.string.error_reg_1);
                } else if (password.length() < 6) {
                    binding.TextView2.setText(R.string.error_reg_2);
                } else {
                    login(email, password, view);
                }
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_zeroFragment);
            }
        });
    }

    public void login(String email, String password, @NonNull View view) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Авторизация успешна", Toast.LENGTH_LONG).show();
                    FirebaseUser user = mAuth.getInstance().getCurrentUser();
                    OurData.favor.put(user.getUid(), "");
                    completeLogin(view);
                } else {
                    Toast.makeText(view.getContext(), "Авторизация провалена", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void completeLogin(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity2.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}