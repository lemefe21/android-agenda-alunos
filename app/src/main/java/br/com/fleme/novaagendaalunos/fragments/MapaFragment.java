package br.com.fleme.novaagendaalunos.fragments;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.fleme.novaagendaalunos.dao.AlunoDAO;
import br.com.fleme.novaagendaalunos.gps.GPS;
import br.com.fleme.novaagendaalunos.gps.GPSDelegate;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback, GPSDelegate {

    private GoogleMap googleMap;
    private GPS gps;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        //prepara uma instância do google maps
        getMapAsync(this);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();

        gps = new GPS(this);
        gps.iniciaBusca();
        Toast.makeText(getContext(), "GPS mode: On", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPause() {
        super.onPause();
        gps.cancelaBusca();
        Toast.makeText(getContext(), "GPS mode: Off", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //quando o mapa estiver pronto, centraliza no endereço
        this.googleMap = googleMap;

        LatLng posicaoEscola = pegaCoordenada("Rua Vergueiro, 3185, Vila Mariana, São Paulo");
        if(posicaoEscola != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoEscola, 17);
            googleMap.moveCamera(update);
        }

        AlunoDAO dao = new AlunoDAO(getContext());
        for(Aluno aluno : dao.buscaAlunos()) {

            LatLng coordenada = pegaCoordenada(aluno.getEndereco());

            if(coordenada != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        dao.close();
    }

    private LatLng pegaCoordenada(String endereco) {

        //converte o endereco em uma coordenada

        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);
            if(!resultados.isEmpty()) {
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void centralizaEm(LatLng coordenada) {
        if (googleMap != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(coordenada, 17);
            googleMap.moveCamera(update);
        }
    }

    @Override
    public void realizaBuscaNa(Location lastLocation) {

        LatLng latlng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        MarkerOptions marker = new MarkerOptions();
        marker.position(latlng);

        centralizaEm(latlng);

        googleMap.addMarker(marker);

    }
}
