/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrew Kearney
 */
public class Lexar {
    private static final Logger log = LoggerFactory.getLogger(Lexar.class);

    public static enum TokenType {
        // Token types cannot have underscores
        NUMBER("-?[0-9]+"),
        BINARYOP("[*|/|+|-]"),
        WHITESPACE("[\t\f\r\n]+");

        public final String mPattern;

        private TokenType(String pattern) {
            mPattern = pattern;
        }
    }

    public static class Token {
        public TokenType mType;
        public String mData;

        public Token(TokenType type, String data) {
            mType = type;
            mData = data;
        }

        @Override
        public String toString() {
            return String.format("(%s %s)", mType.name(), mData);
        }

        public static List<Token> lex(String input) {
            List<Token> tokens = new ArrayList<>();

            // Lexer logic begins here
            StringBuffer patternBuffer = new StringBuffer();
            for (TokenType tokenType :  TokenType.values()) {
                patternBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.mPattern));
            }
            Pattern tokenPatterns = Pattern.compile(patternBuffer.substring(1));

            // Begin matching tokens
            Matcher matcher = tokenPatterns.matcher(input);
            while (matcher.find()) {
                if (matcher.group(TokenType.NUMBER.name()) != null) {
                    tokens.add(new Token(TokenType.NUMBER, matcher.group(TokenType.NUMBER.name())));
                } else if (matcher.group(TokenType.BINARYOP.name()) != null) {
                    tokens.add(new Token(TokenType.BINARYOP, matcher.group(TokenType.BINARYOP.name())));
                } else if (matcher.group(TokenType.WHITESPACE.name()) != null) {

                }
            }

            return tokens;
        }
    }

    public static void main(String[] args) {
        String input = "11 + 22 - 33";

        List<Token> tokens = Token.lex(input);
        for (Token token : tokens) {
            log.info("{}", token);
        }
    }
}
