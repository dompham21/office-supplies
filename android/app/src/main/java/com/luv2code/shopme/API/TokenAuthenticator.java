package com.luv2code.shopme.API;

import android.util.Log;

import com.luv2code.shopme.App;
import com.luv2code.shopme.Response.RefreshTokenResponse;
import com.luv2code.shopme.Utils.LocalStorage;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

public class TokenAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        LocalStorage localStorage = new LocalStorage(App.getContext());

        String refreshToken = "Bearer ".concat(localStorage.getKeyRefreshToken());
        Call<RefreshTokenResponse> call = RestClient.getRestService().refreshAccessToken(refreshToken);
        retrofit2.Response<RefreshTokenResponse> refreshResponse = call.execute();

        if (refreshResponse != null && refreshResponse.code() == 200) {
            //read new JWT value from response body or interceptor depending upon your JWT availability logic
            if(refreshResponse.isSuccessful() && refreshResponse.body() != null) {
                RefreshTokenResponse refreshTokenResponse = refreshResponse.body();
                Log.d("Response refresh :=>", refreshTokenResponse.toString());
                localStorage.setKeyAccessToken(refreshTokenResponse.getAccessToken());
                localStorage.setKeyRefreshToken(refreshTokenResponse.getRefreshToken());

                String accessToken = "Bearer ".concat(localStorage.getKeyAccessToken());
                return response.request().newBuilder()
                        .header("Authorization", accessToken)
                        .build();
            }
            else {
                localStorage.logoutUser();
                return null;
            }

        } else {
            localStorage.logoutUser();
            return null;
        }

    }
}
