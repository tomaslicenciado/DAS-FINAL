package ar.edu.ubp.das.mss.repositories;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ar.edu.ubp.das.mss.models.RespuestaBean;

public class RestServicesRepository {

    public static RespuestaBean RestCall(String urlBase, String operationName, Map<String, String> parameters, MediaType tipoOperacion) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        URI url = URI.create(urlBase+"/"+operationName);
        HttpPost post = new HttpPost();
        if (tipoOperacion == MediaType.APPLICATION_FORM_URLENCODED){
            List<NameValuePair> urlParams = new ArrayList<NameValuePair>(parameters.size());
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                urlParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            post.setEntity(new UrlEncodedFormEntity(urlParams));
        }
        else if (tipoOperacion == MediaType.APPLICATION_JSON){
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-type", MediaType.APPLICATION_JSON_VALUE);
            post.setEntity(new StringEntity(new Gson().toJson(parameters), StandardCharsets.UTF_8));
        }
        post.setURI(url);
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        HttpResponse resp = client.execute(post);
        HttpEntity respEntity = resp.getEntity();
        StatusLine respStatus = resp.getStatusLine();
        String respuesta = EntityUtils.toString(respEntity);
        if (respStatus.getStatusCode() == HttpStatus.SC_OK)
            return new Gson().fromJson(respuesta, new TypeToken<RespuestaBean>(){});
        else
            throw new Exception(respStatus.toString() + "- Error en la consulta rest: " + respuesta);
    }
}
