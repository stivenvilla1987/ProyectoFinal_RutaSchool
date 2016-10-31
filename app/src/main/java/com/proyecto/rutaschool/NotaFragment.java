package com.proyecto.rutaschool;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotaFragment extends Fragment {
    ExpandableListView notaLista;


    public NotaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        inflater.inflate(R.layout.fragment_nota, container, false);

        notaLista = (ExpandableListView) view.findViewById(R.id.ex_lista);
       // notaLista.setAdapter(new notaAdaptador(this));

        return view;
    }

}
