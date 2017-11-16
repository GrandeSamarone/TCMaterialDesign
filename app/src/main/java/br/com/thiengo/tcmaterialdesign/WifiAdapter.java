package br.com.thiengo.tcmaterialdesign;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.thiengo.tcmaterialdesign.Wifi.Item_Wifi;

/**
 * Created by fulanoeciclano on 13/11/2017.
 */
public class WifiAdapter extends RecyclerView.Adapter {

    List<Item_Wifi> dados;

    public WifiAdapter(List<Item_Wifi> dados){
        this.dados = dados;
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class ViewHolderWifi extends RecyclerView.ViewHolder{

        public ViewHolderWifi(View itemView) {
            super(itemView);
        }
    }



}
