package com.ml.morse.service;

import com.ml.morse.model.BinaryResponseDTO;
import com.ml.morse.model.RequestDTO;

public interface IMorseService {

    BinaryResponseDTO binary(RequestDTO request);

    String morseToText(String text);

    String textToMorse(String text);
}
