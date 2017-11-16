package br.com.thiengo.tcmaterialdesign;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.thiengo.tcmaterialdesign.Wifi.Wifi;


public class SecondActivity extends ActionBarActivity  implements View.OnClickListener, Wifi.WiFiListener, AdapterView.OnItemClickListener   {
    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;
    private Wifi wiFi;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itens;
    private List<ScanResult> scans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.botao).setOnClickListener((View.OnClickListener) this);
        list = (ListView) findViewById(R.id.list);

        itens = new ArrayList<String>();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listitem,itens);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
        list.setAdapter(adapter);
        list.setOnItemClickListener((AdapterView.OnItemClickListener) this);




        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Second Activity");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbarBottom = (Toolbar) findViewById(R.id.inc_tb_bottom);
        mToolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent it = null;

                switch(menuItem.getItemId()){
                    case R.id.action_facebook:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.facebook.com"));
                        break;
                    case R.id.action_youtube:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.youtube.com"));
                        break;
                    case R.id.action_google_plus:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://plus.google.com"));
                        break;
                    case R.id.action_linkedin:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.linkedin.com"));
                        break;
                    case R.id.action_whatsapp:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.whatsapp.com"));
                        break;
                }

                startActivity(it);
                return true;
            }
        });
        mToolbarBottom.inflateMenu(R.menu.menu_bottom);

        mToolbarBottom.findViewById(R.id.iv_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "Settings pressed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mToolbar.setBackgroundResource(R.drawable.toolbar_rounded_corners);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return true;
    }




    public void onClick(View view) {
        Toast.makeText(this, "Buscando....", Toast.LENGTH_LONG).show();
        //Inicia a Busca...
        wiFi = Wifi.startScanWIFI(this, (Wifi.WiFiListener) this);
    }

    public void onResultScan(Context arg0, Intent arg1, List<ScanResult> results) {
        scans = results;
        itens.clear();
        for (ScanResult scanResult : results) {
            itens.add(scanResult.SSID + " - " + scanResult.BSSID);
        }
        adapter.notifyDataSetChanged();
    }
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        //Configuro uma rede baseada nos dados encontrados.
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.BSSID = scans.get(arg2).BSSID;
        wifiConfiguration.SSID = "\"" + scans.get(arg2).SSID + "\"";
        wifiConfiguration.preSharedKey = "\"mpt211992manaus\"";

        //Conecto na nova rede criada.
        WifiManager wifiManager = wiFi.getWifiManager(this);
        int netId = wifiManager.addNetwork(wifiConfiguration);
        wifiManager.saveConfiguration();
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }
}
