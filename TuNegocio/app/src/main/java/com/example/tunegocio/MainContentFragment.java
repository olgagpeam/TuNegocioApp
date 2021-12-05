package com.example.tunegocio;
//ccontenedor después del navegador

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainContentFragment extends Fragment { //ojo esto va servir la nombre la base

  private static final String TEXT = "text";

  public static MainContentFragment newInstance(String text) {
    MainContentFragment frag = new MainContentFragment();

    Bundle args = new Bundle();
    args.putString(TEXT, text); //recibira un texto de otra clase
    frag.setArguments(args);

    return frag;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
          Bundle savedInstanceState) {
    View layout = inflater.inflate(R.layout.main_fragment, container, false);

    if (getArguments() != null) {
      ((TextView) layout.findViewById(R.id.text)).setText(getArguments().getString(TEXT));
    }

    return layout;
  }
}

