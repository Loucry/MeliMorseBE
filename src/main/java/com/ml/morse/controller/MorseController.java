package com.ml.morse.controller;

import com.ml.morse.model.BinaryResponseDTO;
import com.ml.morse.model.RequestDTO;
import com.ml.morse.service.IMorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping("/translate")
public class MorseController {

    private IMorseService morseService;

    @Autowired
    public MorseController(IMorseService morseService) {
        this.morseService = morseService;
    }

    @RequestMapping(path = "/2text", method = RequestMethod.POST)
    public @ResponseBody
    String morseToText(@RequestBody RequestDTO requestDTO) {
        return morseService.morseToText(requestDTO.getText());
    }

    @RequestMapping(path = "/2morse", method = RequestMethod.POST)
    public @ResponseBody
    String textToMorse(@RequestBody RequestDTO requestDTO) {
        return morseService.textToMorse(requestDTO.getText());
    }

    @RequestMapping(path = "/binary", method = RequestMethod.POST)
    public @ResponseBody
    BinaryResponseDTO binary(@RequestBody RequestDTO requestDTO) {
        return morseService.binary(requestDTO);
    }

}
