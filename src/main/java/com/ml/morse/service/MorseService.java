package com.ml.morse.service;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ml.morse.model.BinaryResponseDTO;
import com.ml.morse.model.BinaryStats;
import com.ml.morse.model.RequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MorseService implements IMorseService {

    private final Map<String, String> morseToTextMap = getMorseToTextMap();
    private final Map<String, String> textToMorseMap = getTextToMorseMap();

    @Override
    public BinaryResponseDTO binary(RequestDTO request) {
        BinaryResponseDTO dto = new BinaryResponseDTO();
        String text = request.getText();

        Character prevChar = text.charAt(0);
        int currentLenght = 0;
        BinaryStats pulsesStats = new BinaryStats();
        BinaryStats spacesStats;
        if (request.areSpaceStatsValid()) {
            spacesStats = request.getSpaceStats();
        } else {
            spacesStats = new BinaryStats();
        }

        for (Character c : Lists.charactersOf(text)) {
            if (c.equals('0')) {
                if (c.equals(prevChar)) {
                    currentLenght++;
                } else {
                    updateStats(pulsesStats, currentLenght);
                    prevChar = c;
                    currentLenght = 1;
                }
            } else if (c.equals('1')) {
                if (c.equals(prevChar)) {
                    currentLenght++;
                } else {
                    if (!pulsesStats.getLengths().isEmpty()) {//ignore first space lenght from stats
                        updateStats(spacesStats, currentLenght);
                    }
                    prevChar = c;
                    currentLenght = 1;
                }
            }
        }
        if (prevChar.equals('0')) {
            spacesStats.getLengths().add(++currentLenght); //ignore last space lenght from stats
        } else {
            updateStats(pulsesStats, ++currentLenght);
        }

        String morse = parseBinaryToMorse(pulsesStats, spacesStats);

        dto.setMorse(morse);
        dto.setText(morseToText(morse));
        dto.setSpaceStats(spacesStats);

        return dto;
    }

    private String parseBinaryToMorse(BinaryStats pulsesStats, BinaryStats spacesStats) {
        StringBuilder morse = new StringBuilder();
        for (int i = 0; i < pulsesStats.getLengths().size(); i++) {
            morse.append(parsePulseToDotOrDash(pulsesStats.getLengths().get(i), pulsesStats));
            morse.append(parseSpaceToEmptyOrNextLetter(spacesStats.getLengths().get(i), spacesStats));
        }
        return morse.toString();
    }

    private String parseSpaceToEmptyOrNextLetter(Integer spaceLenght, BinaryStats spacesStats) {
        int diffWithMaxLenght = Math.abs(spaceLenght - spacesStats.getMaxLenght()),
                diffWithMinLenght = Math.abs(spaceLenght - spacesStats.getMinLenght());

        if (diffWithMaxLenght <= diffWithMinLenght) {
            return " ";
        } else {
            return "";
        }
    }

    private String parsePulseToDotOrDash(Integer pulseLenght, BinaryStats pulsesStats) {
        int diffWithMaxLenght = Math.abs(pulseLenght - pulsesStats.getMaxLenght()),
            diffWithMinLenght = Math.abs(pulseLenght - pulsesStats.getMinLenght());

        if (diffWithMaxLenght < diffWithMinLenght) {
            return "-";
        } else {
            return ".";
        }
    }

    private void updateStats(BinaryStats stats, Integer currentLenght) {
        stats.getLengths().add(currentLenght);
        stats.setMinLenght(Math.min(stats.getMinLenght(), currentLenght));
        stats.setMaxLenght(Math.max(stats.getMaxLenght(), currentLenght));
    }

    @Override
    public String morseToText(String text) {
        List<String> spltText = Splitter.on(' ').splitToList(text);
        StringBuilder result = new StringBuilder();

        for (String str : spltText) {
            str = str.toUpperCase();
            if (str.equals("")) {
                result.append(" ");
            } else if (morseToTextMap.containsKey(str)) {
                result.append(morseToTextMap.get(str));
            }
        }
        return result.toString().trim();
    }

    @Override
    public String textToMorse(String text) {
        StringBuilder result = new StringBuilder();

        for(char c: text.toCharArray()) {
            String str = String.valueOf(c).toUpperCase();
            if (str.equals(" ")) {
                result.append(str);
            } else if (textToMorseMap.containsKey(str)) {
                result.append(textToMorseMap.get(str)).append(" ");
            }
        }
        return result.toString().trim();
    }

    public Map<String, String> getTextToMorseMap() {
        return new ImmutableMap.Builder<String, String>()
                .put("A", ".-").put("B", "-...")
                .put("C", "-.-.").put("D", "-..")
                .put("E", ".").put("F", "..-.")
                .put("G", "--.").put("H", "....")
                .put("I", "..").put("J", ".---")
                .put("K", "-.-").put("L", ".-..")
                .put("M", "--").put("N", "-.")
                .put("O", "---").put("P", ".--.")
                .put("Q", "--.-").put("R", ".-.")
                .put("S", "...").put("T", "-")
                .put("U", "..-").put("V", "...-")
                .put("W", ".--").put("X", "-..-")
                .put("Y", "-.--").put("Z", "--..")
                .put("0", "-----").put("1", ".----")
                .put("2", "..---").put("3", "...--")
                .put("4", "....-").put("5", ".....")
                .put("6", "-....").put("7", "--...")
                .put("8", "---..").put("9", "----.")
                .put(".", ".-.-.-")
                .build();
    }

    private Map<String, String> getMorseToTextMap() {
        return new ImmutableMap.Builder<String, String>()
                .put(".-", "A").put("-...", "B")
                .put("-.-.", "C").put("-..", "D")
                .put(".", "E").put("..-.", "F")
                .put("--.", "G").put("....", "H")
                .put("..", "I").put(".---", "J")
                .put("-.-", "K").put(".-..", "L")
                .put("--", "M").put("-.", "N")
                .put("---", "O").put(".--.", "P")
                .put("--.-", "Q").put(".-.", "R")
                .put("...", "S").put("-", "T")
                .put("..-", "U").put("...-", "V")
                .put(".--", "W").put("-..-", "X")
                .put("-.--", "Y").put("--..", "Z")
                .put("-----", "0").put(".----", "1")
                .put("..---", "2").put("...--", "3")
                .put("....-", "4").put(".....", "5")
                .put("-....", "6").put("--...", "7")
                .put("---..", "8").put("----.", "9")
                .put(".-.-.-", ".")
                .build();
    }
}
