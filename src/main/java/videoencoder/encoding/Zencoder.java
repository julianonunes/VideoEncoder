package videoencoder.encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

import videoencoder.beans.ZencoderInput;
import videoencoder.beans.ZencoderResponse;
import videoencoder.encoding.base.IEncoder;

public class Zencoder implements IEncoder {

	private static final String BASE_URL = "https://app.zencoder.com/api/v2/jobs";
	/**
	 * Utilizando chave de integração. Neste caso os jobs de 
	 * conversão serão limitados a 5 segundos para testes apenas. 
	 */
	private static final String API_KEY = "32c53c9fda593a7f91dfa11ca2e3f41b";
	
		
	@Override
	public String encode(String fileUrl) {
		try {
			// Configurando a conexão
			URL url = new URL(BASE_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Zencoder-Api-Key", API_KEY);
			connection.setDoOutput(true);
			
			// Envio de parâmetros
			ZencoderInput input = new ZencoderInput();
			input.setInput(fileUrl);
			
			OutputStream output = connection.getOutputStream();
			output.write(new Gson().toJson(input).getBytes());
			output.flush();
			output.close();
			
			// Verificando retorno
			int responseCode = connection.getResponseCode();
			
			// Se retornou status 201 (HTTP_CREATED), hora de retornar o output ID
			if (responseCode == HttpURLConnection.HTTP_CREATED) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
				
	            String inputLine;
	            StringBuffer responseBuffer = new StringBuffer();
	 
	            while ((inputLine = in.readLine()) != null) {
	                responseBuffer.append(inputLine);
	            }
	            in.close();
	            
	            // Convertendo string JSON em objeto
	            ZencoderResponse response = new Gson().fromJson(responseBuffer.toString(), ZencoderResponse.class);
	            
	            if (response != null) {
	            	return response.getId();
	            }
			}
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	

}
