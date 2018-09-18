package br.com.fleme.novaagendaalunos.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

public class OrientadorDeFoto {

    public static Bitmap carrega(String caminhoFoto) {

        //toda câmera digital salva as imagens no formato Exif - permite armazenar algumas tags com informações
        //como data, hora, geolocalização e também a orientação da câmera no momento da captura

        try {

            ExifInterface exif = new ExifInterface(caminhoFoto);
            String orientacao = exif.getAttribute(ExifInterface.TAG_ORIENTATION);

            int codigoOrientacao = Integer.parseInt(orientacao);

            switch (codigoOrientacao) {
                case ExifInterface.ORIENTATION_NORMAL: // rotaciona 0 graus no sentido horário
                    return rotacionaFoto(caminhoFoto, 0);

                case ExifInterface.ORIENTATION_ROTATE_90: // rotaciona 90 graus no sentido horário
                    return rotacionaFoto(caminhoFoto, 90);

                case ExifInterface.ORIENTATION_ROTATE_180: // rotaciona 180 graus no sentido horário
                    return rotacionaFoto(caminhoFoto, 180);

                case ExifInterface.ORIENTATION_ROTATE_270: // rotaciona 270 graus no sentido horário
                    return rotacionaFoto(caminhoFoto, 270);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Bitmap rotacionaFoto(String caminhoFoto, int angulo) {

        //abre a imagem
        Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

        //prepara a operação de rotacionar
        Matrix matrix = new Matrix();
        matrix.postRotate(angulo);

        //criamos um novo bitmap já com a rotação aplicada

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

}
