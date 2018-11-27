package br.com.fleme.novaagendaalunos.gps;

import android.content.Context;
import android.location.Location;

public interface GPSDelegate {

    Context getContext();

    void realizaBuscaNa(Location lastLocation);

}
