// ResultFragment.java
package com.example.mystudybigdata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Rotation;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.xml.KonfettiView;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.Position;
public class FragmentValidée extends Fragment {

    public FragmentValidée() {
            // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_validee, container, false);

        TextView textViewResult = view.findViewById(R.id.textViewResult);

        // Confetti animation
        KonfettiView konfettiView = view.findViewById(R.id.konfettiView);  // make sure you added this in your XML

        // For Sizes (correct way)
        List<Integer> colors = Arrays.asList(
                0xFFFF0000, // Red
                0xFF00FF00, // Green
                0xFF0000FF  // Blue
        );


        List<Shape> shapes = Arrays.asList(
                Shape.Square.INSTANCE,  // Use the INSTANCE field
                Shape.Circle.INSTANCE
        );

        // Confetti with varied masses
        List<Size> sizes = Arrays.asList(
                new Size(6, 0.2f,0.4f),   // Tiny & floaty
                new Size(10, 0.5f,0.4f),  // Standard
                new Size(14, 0.8f,0.4f)   // Large & fast
        );

        Party party = new Party(
                /* angle */ 0,
                /* spread */ 360,
                /* speed */ 0f,
                /* maxSpeed */ 20f,
                /* damping */ 0.9f,  // Slight bounce
                sizes,
                /* colors */ Arrays.asList(0xFFFF0000, 0xFF00FF00),
                /* shapes */ Arrays.asList(Shape.Circle.INSTANCE),
                /* ttl */ 4000L,
                /* fadeOut */ true,
                /* position */ new Position.Relative(0.5, 0.1),  // Top center
                /* delay */ 0,
                /* rotation */ new Rotation(),
                /* emitter */ new Emitter(3, TimeUnit.SECONDS).perSecond(100)
        );

        konfettiView.start(party);

        konfettiView.start(party);

        // Get the number from arguments
        Bundle args = getArguments();
        if (args != null) {
            double result = args.getDouble("result", 0.0);
            textViewResult.setText("Semestre Validée !!"+ result);
        }

        return view;
    }




}

