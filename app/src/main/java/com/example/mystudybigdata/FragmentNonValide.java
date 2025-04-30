package com.example.mystudybigdata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class FragmentNonValide extends Fragment {

    public FragmentNonValide() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentnonvalide, container, false);

        TextView textViewResult = view.findViewById(R.id.textViewResult2);

        // Get the number from arguments
        Bundle args = getArguments();
        if (args != null) {
            double result = args.getDouble("result", 0.0);
            textViewResult.setText("Failure: Your result is " + result);
        }

        return view;
    }
}
