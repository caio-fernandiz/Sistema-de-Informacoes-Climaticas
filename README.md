# Projeto: Sistema De Informações Climáticas

O objetivo desse projeto é criar um código backend que pode ser utilizado em mais de uma aplicação, seja ela web, parte de um sistema  ou então para alguma empresa. Ele irá trazer informações do clima para que os usuários realizem previsões com maior precisão.

## Detalhes Técnicos

Irei falar sobre as bibliotecas utilizadas, dando destaque para suas classes, nesse projeto e sobrea a Weather API.

### Bibliotecas

- java.net.

URI: representa um endereço na web.

URLEnconder: Codifica strings para uso na web. Serve para corrigir alguns erros de digitação do usuário.

- java.net.http.

HttpClient: Envia e recebe dados via HTTP. Ele que recebe os dados da HttpResponse e envia para a HttpRequest.

HttpRequest: Solicita os dados via HTTP.

HttpResponse: Responde as solicitações via HTTP.

- java.nio.chraset.StandardCharsets: Define opadrões de codificação de caracteres
- org.json.JSONObject: Formato de dados leves para a troca de informações

### Weather API

Essa será a API é o elemento mais importante para se utilizar, ela que irá trazer as informações climáticas que o projeto irá repassar para outras aplicações ou então para projetos fronts que irão se comunicar com o back.

#### Sobre a Weather API e seu funcionamento

Ela capta vários dados meteorológicos de diferentes fontes do mundo todo, retornando os dados em tempo real, sejam previsões, históricos e como está o tempo no atual momento. 

#### Possíveis razões para dados não atualizados

Por ser um projeto iniciante e com o foco em estudos, é bom deixar claro que pode ocorrer de não atualizar os dados conforme for testando o projeto.

1. **Atraso de dados** : Mesmo que em outro site os dados estejam diferentes do projeto, é possível que o problema não esteja no projeto e sim os dados que não foram atualizados ou o projeto não recebeu eles ainda.
2. **Problemas com a estação meteorológica** : Dependendo da estação metereológica, o dado pode ser diferente de algum site que possui dados que serão comparados. Algo que pode ocorrer também é a manutenção ou desligamento de alguma estação que estava sendo utilizada, então a API irá utilizar os dados de outra estação.
3. **Problemas com a chave API** : O principal problema, além de algumas falhas de conexão, é a limitação que tem no projeto. Por estar utilizando a versão gratuita, muitas coisas acabam ficando em falta, fazendo com que limite o uso de ferramentas que a API possui.

## Funcionamento do Código

Para prosseguir, irei explicar como o código funciona, seus métodos, entradas, e saídas das informações.

## Informando cidade

O código inicia com a classe *main* pedindo para que o usuário informe a cidade que ele deseja ver as informações climáticas. O usuário pode digitar o nome de qualquer cidade do mundo, porém, é necessário que seja escrito da maneira que escrevem naquele país. Caso o usuário digite “Nova Yorke” ele pode não receber informações da cidade esperada, mas, se o usuário digitar “New York”, ai sim ele irá receber os dados da cidade dos Estados Unidos da América.
Após o usuário informar a cidade, o código irá ler o nome e entrar em um *try catch*.  Em seguida o código irá chamar o método **“getDadosClimaticos”**, para então enviar uma solicitação dos dados daquela cidade e em seguida poder receber esses dados para mostrar para o usuário. Para que o usuário consiga ver o código passa por um *if else*, o *if* é ativado caso o código de erro 1006 seja ativo. Esse código de erro sinaliza que a localização não foi encontrada, e ele irá enviar uma mensagem para o usuário avisando sobre isso. Se o *if* não for ativado, o código vai para o *else*, assim revelando para o usuário os dados sobre o clima daquela cidade, porém, os dados são organizado de uma maneira mais limpa e legível para o usuário usando o método **“imprimirDadosClimaticos”**. O *catch* só será chamado em algum erro que seja no código mesmo, não alguma informação errada entregue pelo usuário.

## Puxando os dados

O código usa o método **“getDadosClimaticos”** para buscar esses dados é usado um link, o link é formado por  *“http://api.weatherapi.com/v1/”* mais a forma que os dados estão sendo entregues, que por ser um json seria assim *“current.json?key=”*, mais a chave da api, que é fornecida atráves de um arquivo txt que está no projeto (caso queira testar, recomendo criar sua própria conta nesse site e colocar no arquivo txt que vai estar vazio), mais *“&q=’cidade’”*, mais *"&lang=pt”* para definir em que língua virão os dados da cidade requisitada. 
Mas antes do link ser formado, o método faz um processo de formatação do nome da cidade, para que  assim seja mais fácil do link ler o nome da cidade. Ele formata de uma maneira que links consigam ler, por exemplo, se digitar “São Paulo”, ele vai codificar para algo como "S%EF%BF%BDo+Paulo”, já que o espaço é trocado por um + e o caractere **‘ã’** não é lido normalmente. Agora, após a formatação, é que o link é enviado para realizar a solicitação HTTP.
Logo abaixo temos a o objeto “request”, que prepara a construção para de como será a requisição, mostrando para qual link deve ser enviada a requisição e de onde deve ser recebida a resposta. Então depois é criado o objeto “client”, esse objeto vai ser responsável para que seja possível acessar o link que a requisição será realizada. Então temos o objeto “response”, que ele envia a requisição e recebe os dados solicitados para entregar em “response.body” em formato json.

## Formatando os dados

E agora a parte da formatação dos dados, essa parte é essencial pois um JSON possui uma maneira diferente de mostrar os dados, não é uma maneira muito comum para usuários normais lerem e encontrarem as informações com facilidade. Para iniciar, é bom entender como os dados estão chegando até o método *“imprimirDadosClimaticos"*. No método *“main”*  a variável *“cidade”* é aquela que vai receber a informação cidade, passar para o método *“getDadosClimaticos”* e então, esses dados serão passados para a variável *“dadosClimaticos”*, que irá chamar o método *“imprimirDadosClimaticos"*, entregando os dados para realizar a formatação. 
Agora que está bem claro como o JSON chega até o método podemos ver que é criado um objeto chamado de *“dadosJson”*, ele vai servir para para receber os dados que estão no JSON. Após receber os dados, nós criamos outros objetos, um para cada tipo de dado, que irão usar o *“dadosJson”* para buscar pelos dados específicos. Fica fácil de definir qual dado cada objeto vai procurar usando nomes para cada objeto, *“velocidadeVento”*, *“pressaoAtmosferica”*, e *“condicaoTempo”* são alguns exemplos. O único que se destaca é o objeto *“informacoesMeteorologicas”*, pois ele parece servir apenas para informar o momento que da informação, seja uma previsão, informação passada ou a última atualização / situação atual daquele dado.

# Finalização

Utilizando a WeahteAPI podemos ver que é fácil para ter dados meteorológicos, é uma API que ainda tem muito mais conteúdo para fornecer e que possui uma documentação muito boa para saber o que utilizar dependendo do que for desejado. O código também é reutilizável, ele pode ser alterado para servir de backend em outros projetos ou sofrer algumas melhorias para no futuro.
