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
import com.example.project.R;
import com.example.project.databinding.FragmentFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private FirebaseAuth mAuth;
    private final String TAG = "project";
    private EditText ETemail;
    private EditText ETpassword;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        ETemail = (EditText) binding.TextEmail1;
        ETpassword = (EditText) binding.TextPassword1;


        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ETemail.getText().toString();
                String password = ETpassword.getText().toString();
                if (!email.contains("@") && !email.contains(".")) {
                    binding.TextView1.setText(R.string.error_reg_1);
                }else if (password.length() < 6){
                    binding.TextView1.setText(R.string.error_reg_2);
                }else{
                    register(email, password, view);
                }

            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_zeroFragment);
            }
        });
    }

    public void register(String email, String password, @NonNull View view) {
        boolean check = false;
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Регистрация успешна", Toast.LENGTH_LONG).show();
                    completeRegister(view);
                } else {
                    Toast.makeText(view.getContext(), "Регистрация провалена", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void completeRegister(View view){
        Intent intent = new Intent(view.getContext(), MainActivity2.class);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}