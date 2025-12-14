package com.dnc.gustavomota.projeto_integrador_dnc.services;

import com.dnc.gustavomota.projeto_integrador_dnc.dto.ViaCepResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    @Autowired
    private RestTemplate restTemplate;

    // Corrigido: Removido < e >
    private static final String URL_VIACEP = "https://viacep.com.br/ws/{cep}/json/";

    public ViaCepResponse buscarEnderecoPorCep(String cep) {
        try {
            // Substitui o placeholder {cep} na URL pela string do CEP
            String url = URL_VIACEP.replace("{cep}", cep);
            // Faz a requisição GET e converte automaticamente para o objeto ViaCepResponse
            ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);

            return response;
        } catch (Exception e) { // Pode ser refinado para capturar HttpClientErrorException, ResourceAccessException, etc.
            throw e;
        }
    }
}