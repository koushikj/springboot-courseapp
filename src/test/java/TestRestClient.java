import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;

public class TestRestClient {
    public static void main(String[] args) throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
/*

        */
/*
        normal httpclient
         */


  /*      HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://10.126.68.199/admin/rs/wap/registry?id=navigation-secondary");
        HttpResponse response = null;
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("APPSESSIONID","18291704837FD0A1E68A4642C6BD82D1");

        cookieStore.addCookie(cookie);
        ((DefaultHttpClient) client).setCookieStore(cookieStore);
        //response = client.execute(httppost);

        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }*/

/*
        Spring RestTemplate
         */

        RestTemplate restTemplate = restTemplate();
        final String uri = "http://localhost:8080/topics/";
        final String uri2 = "https://10.126.68.192/admin/rs/wap/registry?id=navigation-secondary";
        final String url3 = "https://search-kojayaku-elasticsearch-6zmxwvx3577muld7cz6fy3reji.eu-west-3.es.amazonaws.com/telemetry*/_search?size=4567";
        //Topic newTopic = new Topic("j2ee","Java EE","Java Enterprise Edition");
        //restTemplate.put ( uri, newTopic);
        System.out.println("___________________________________________________");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "APPSESSIONID=18291704837FD0A1E68A4642C6BD82D1\"" );
        headers.setAccept(Collections.singletonList(new MediaType("application","xml")));
        HttpEntity requestEntity = new HttpEntity(null, headers);
        ResponseEntity rssResponse = restTemplate.exchange(
                uri2,
                HttpMethod.GET,
                requestEntity,
                String.class);

        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>("{\\n\" +\n" +
                "                            \"    \\\"query\\\" : {\\n\" +\n" +
                "                            \"     \\\"match_all\\\":{} \\n\" +\n" +
                "                            \"} \\n\"+\n" +
                "                            \"}", headers);

        System.out.println("Response :"+ rssResponse.getBody());
        System.out.println("___________________________________________________");


        ResponseEntity<String> response = restTemplate.exchange(url3,HttpMethod.GET,entity,String.class);

        System.out.println("Response :"+response.getBody());
        System.out.println("___________________________________________________");
        String resp = restTemplate.getForObject(uri2,String.class);
        System.out.println("Response :"+resp);

        System.out.println("___________________________________________________");

/*
        try {
            String navXMLReq = "https://10.126.68.192/admin/rs/wap/registry?id=navigation-secondary";
            System.out.println("getting nav xml"+navXMLReq);
            System.out.println("response 2 :: "+getNavXMLInfraApi(navXMLReq));
        } catch (Exception carsException) {
            carsException.printStackTrace();
        }*/
    }

    public static RestTemplate restTemplate()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        });

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();


        SSLConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(builder.build(),
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslSF).build();



/*        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();*/

        CloseableHttpClient httpClient1 = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

/*
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
*/


        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }


    public static String getNavXMLInfraApi (String getURL){
        System.out.println("URL :: "+getURL);
        HttpsURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        int count = 0;

        try {
            URL u = new URL(getURL);

            connection = (HttpsURLConnection) u.openConnection();
            //connection.setSSLSocketFactory(SSLManagerFactory.getInstance(true, false).getSocketFactory());

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/xml");

            System.out.println("setting the defaultHostnameVerifier");

            connection.setHostnameVerifier(new VerifyAll());
            connection.setRequestProperty("Content-Language", "en-US");
            //System.out.println("session id is : "+session.getId());
            connection.setRequestProperty("Cookie", "APPSESSIONID=18291704837FD0A1E68A4642C6BD82D1");

            connection.setUseCaches(true);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(3000);

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.flush();
            wr.close();

            System.out.println("getting resp code..");

            int code = connection.getResponseCode();

            if (code >= 200 && code < 400) {
                is = new BufferedInputStream(connection.getInputStream());
            } else {
                is = new BufferedInputStream(connection.getErrorStream());
            }

            br = new BufferedReader(new InputStreamReader(is));

            String inputLine = "";
            StringBuffer sb = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }

            result = sb.toString();

            System.out.println("response from Xenia API: " + result);

        }
        catch(Exception ex){
            System.out.println("Failed to construct URL object"+ ex);
        }

        return result;
    }

    private static class VerifyAll implements HostnameVerifier
    {
        public boolean verify( String hostname, SSLSession session )
        {
            System.out.println("VerifyAll->verify: hostname:" + hostname + " session:" +session.getPeerHost());
            return true;
        }
    }
}
