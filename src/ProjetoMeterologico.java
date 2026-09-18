import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONObject;

public class ProjetoMeterologico {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome da cidade: ");
        String cidade = scanner.nextLine(); //lê a cidade informada

        try {
            String dadosClimaticos = getDadosClimaticos(cidade); //retorna um json
            
            //código 1006 indica que a localização não foi encontrada
            //o uso da \" é para impedir que o " no "code" acabe dando algum problema 
            //por estar dentro de uma string, possibilitando que o código vá atrás desse 
            //erro que está dentro da string que retorna o json
            if (dadosClimaticos.contains("\"code\":1006")){ 
                System.out.println("Localização não encontrada. Por favor, tente novamente.");
            } else{
                imprimirDadosClimaticos(dadosClimaticos);
            } 
        } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

    public static String getDadosClimaticos(String cidade) throws Exception{
        //o trim serve para remover espaços no texto
        String apiKey = Files.readString( Paths.get("api-key.txt")).trim();

        String formataNomeCidade = URLEncoder.encode(cidade, StandardCharsets.UTF_8);
        String apiUrl = "http://api.weatherapi.com/v1/current.json?key="+ apiKey + "&q=" + formataNomeCidade + "&lang=pt";
        //começa a contrução de uma nova solicitação HTTP
        //"uri" define o uri, que seria um "nome" para o link, da solicitação HTTP
        //o ".build" finaliza a construção da solicitação
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();

        //criando objeto que envia solicitações HTTP e recebe respostas HTTP, para acessar o sit weahteAPI
        HttpClient client = HttpClient.newHttpClient();

        //Criando o "response" para envia solicitações HTTP e recebe respostas HTTP, comunicando com o site weahterAPI
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        //retorna os dados obtidos no site da weatherAPI
        return response.body(); 
    }

    //método para imprimir os dados meteorológicos de forma organizada
    public static void imprimirDadosClimaticos(String dados){
        //System.out.println("Dados originais (JSON) obtidos no site meteorológico" + dados);

        JSONObject dadosJson = new JSONObject(dados);
        JSONObject informacoesMeteorologicas = dadosJson.getJSONObject("current");

        //Extrai os dados da localização que o site vai retornar
        String cidade = dadosJson.getJSONObject("location").getString("name");
        String pais = dadosJson.getJSONObject("location").getString("country");

        //Extrai dados adicionais
        String condicaoTempo = informacoesMeteorologicas.getJSONObject("condition").getString("text");
        int umidade = informacoesMeteorologicas.getInt("humidity");
        float velocidadeVento = informacoesMeteorologicas.getFloat("wind_kph");
        float pressaoAtmosferica = informacoesMeteorologicas.getFloat("pressure_mb");
        float sensacaoTermica = informacoesMeteorologicas.getFloat("feelslike_c");
        float temperaturaAtual = informacoesMeteorologicas.getFloat("temp_c");

        //extrai data e hora da string retornada pela API
        String dataHora = informacoesMeteorologicas.getString("last_updated");

        //Imprime as informações atuais
        System.out.println("Informações meteorológicas para " + cidade + ", " + pais);
        System.out.println("Data e hora: " + dataHora);
        System.out.println("Temperatura atual: " + temperaturaAtual);
        System.out.println("Sensação térmica: " + sensacaoTermica);
        System.out.println("Condição do tempo: " +condicaoTempo);
        System.out.println("Umidade: " +  umidade);
        System.out.println("Velocidade do vento: " + velocidadeVento);
        System.out.println("Pressão atomosférica: " +pressaoAtmosferica);
    }
}
