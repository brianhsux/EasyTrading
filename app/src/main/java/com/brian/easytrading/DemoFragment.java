package com.brian.easytrading;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

public class DemoFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_demo, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      int position = FragmentPagerItem.getPosition(getArguments());
      TextView title = (TextView) view.findViewById(R.id.item_title);
      title.setText(String.valueOf(position));

      ListView list = (ListView) view.findViewById(R.id.listView);
      final ArrayAdapter<String> adapter =
              new ArrayAdapter<String>(getActivity(),
                      android.R.layout.simple_list_item_1,
                      android.R.id.text1);
      list.setAdapter(adapter);

      String url = "";

      switch (position) {
          case 0:
              url = "https://easytrading-b6f09.firebaseio.com/AllProducts";
              break;
          case 1:
              url = "https://easytrading-b6f09.firebaseio.com/Foods";
              break;
          case 2:
              url = "https://easytrading-b6f09.firebaseio.com/3C";
              break;
          case 3:
              url = "https://easytrading-b6f09.firebaseio.com/Others";
              break;
          default:
              url = "https://easytrading-b6f09.firebaseio.com/AllProducts";
              break;
      }

      Firebase.setAndroidContext(getActivity());
//      String url = "https://easytrading-b6f09.firebaseio.com/AllProducts";

      Log.d("------BBB------", "onViewCreated()->url:" + url);

      new Firebase(url).addChildEventListener(
              new ChildEventListener() {
                  @Override
                  public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                      adapter.add(
                              (String) dataSnapshot.child("productName").getValue());
                  }

                  @Override
                  public void onChildRemoved(DataSnapshot dataSnapshot) {
                      adapter.remove(
                              (String) dataSnapshot.child("productName").getValue());
                  }

                  @Override
                  public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                  }

                  @Override
                  public void onCancelled(FirebaseError firebaseError) {

                  }

                  @Override
                  public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                  }
              });
  }

}
