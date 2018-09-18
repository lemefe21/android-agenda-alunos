package br.com.fleme.novaagendaalunos.gps.deprecated;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import br.com.fleme.novaagendaalunos.fragments.MapaFragment;

public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    //classe não utilizado pois o FusedLocationApi está depreciado
    //funcionalidades implementadas no classe GPS

    private final GoogleApiClient client;
    private final MapaFragment mapaFragment;

    public Localizador(Context context, MapaFragment mapaFragment) {

        client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

        client.connect();
        this.mapaFragment = mapaFragment;

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationRequest request = new LocationRequest();

        //atualização do gps quando ultrapassar o raio de X metros
        request.setSmallestDisplacement(7);
        //e com intervalo de 1s
        request.setInterval(1000);
        //mehor precisão de acordo com a ultima tecnologia utilizada no dispositivo
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        //localização alterada
        LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());
        mapaFragment.centralizaEm(coordenada);

    }
}
