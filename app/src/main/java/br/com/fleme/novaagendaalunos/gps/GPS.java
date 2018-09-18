package br.com.fleme.novaagendaalunos.gps;

import android.annotation.SuppressLint;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class GPS extends LocationCallback {

    private FusedLocationProviderClient client;
    private GPSDelegate delegate;

    public GPS(GPSDelegate delegate) {
        this.delegate = delegate;
        this.client = LocationServices.getFusedLocationProviderClient(delegate.getContext());
    }

    @SuppressLint("MissingPermission")
    public void iniciaBusca() {

        LocationRequest req = new LocationRequest();
        req.setInterval(1000); //1 segundo
        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        req.setSmallestDisplacement(10); //10 metros

        client.requestLocationUpdates(req, this, null);

    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);

        delegate.realizaBuscaNa(locationResult.getLastLocation());

    }

    public void cancelaBusca() {
        client.removeLocationUpdates(this);
    }
}
