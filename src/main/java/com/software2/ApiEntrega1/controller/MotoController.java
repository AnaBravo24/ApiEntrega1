package com.software2.ApiEntrega1.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software2.ApiEntrega1.model.Moto;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/motos")
public class MotoController {
    private List<Moto> motos;

    public MotoController(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Moto[] motosArray = objectMapper.readValue(new ClassPathResource("motos.json").getFile(), Moto[].class);
            motos = new ArrayList<>(Arrays.asList(motosArray));

        } catch (IOException e) {
            e.printStackTrace();
            motos = new ArrayList<>();
        }
    }

    @GetMapping
    public List<Moto> getAllMotos(){
        return motos;
    }

    @GetMapping("/{placa}")
    public Moto getMotoByplaca(@PathVariable String placa) {
        return motos.stream()
        .filter(moto -> moto.getPlaca().equals(placa))
        .findFirst()
        .orElse(null);        
    }

    @PostMapping
    public Moto createMoto(@RequestBody Moto moto) {
        motos.add(moto);
        return moto;
    }

    @PutMapping("/{placa}")
    public Moto updateMoto(@PathVariable String placa, @RequestBody Moto updatedMoto) {
        Moto moto = getMotoByplaca(placa);
        if (moto != null) {
            moto.setMarca(updatedMoto.getMarca());
            moto.setModelo(updatedMoto.getModelo());
            return moto;
        }

        return null;
    }

    @DeleteMapping("/{placa}")
    public void deleteMoto(@PathVariable String placa) {
        motos.removeIf(moto -> moto.getPlaca().equals(placa));
    }

}
