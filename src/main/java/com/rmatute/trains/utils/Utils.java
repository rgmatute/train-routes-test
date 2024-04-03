package com.rmatute.trains.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	
	public static boolean statusCodeIs2xx(int statusCode) {
        return (statusCode >= 200 && statusCode < 300);
    }
	
	public static boolean statusCodeIs5xx(int statusCode) {
        return (statusCode >= 500 && statusCode < 600);
    }

    public static boolean statusCodeIs4xx(int statusCode) {
        return (statusCode >= 400 && statusCode < 500);
    }
    
    /**
     *Transforma un objeto ó entidad a un json string
     * @param entity entidad u objeto a transformar en json
     * @return {@link String}
     */
    public static String toJson(Object entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{}";
        try {
            json = objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            
        }
        return json;
    }
    
    /**
     * Se remplazan los valores en los patrones '{}'
     * @param template mensaje completo incluyendo patrones '{}' donde se remplazaran los valores
    * @param values valores que se remplazaran en los patrones '{}'
     * @return {@link String}
     */
    public static String replaceText(String template, Object... values) {
        Pattern pattern = Pattern.compile("\\{\\}");
        Matcher matcher = pattern.matcher(template);
        StringBuilder buffer = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            String replacement = "";
            if (i < values.length) {
                replacement = Objects.toString(values[i], "null");
                i++;
            }
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
    
    public static void debug(String title, Object message) {
        Utils.consoleTitle(" " + title + " ", message);
    }
    
    
    /**
    *
    * @param title aa
    * @param message bb
    */
   public static void console(String title, Object message) {
       consoleTitle(title, message);
   }

   /**
    *
    * @param title aa
    * @param message bb
    */
   public static void consoleTitle(String title, Object message) {
       println(characterGenerate("*", 20) + title + characterGenerate("*", 20));
       println(message);
       println(characterGenerate("*", (40 + title.length())));
   }

   /**
    *
    * @param title aa
    * @param message bb
    * @param lengthCharacter cc
    */
   public static void consoleTitle(String title, Object message, int lengthCharacter) {
       println(characterGenerate("*", (lengthCharacter / 2)) + title + characterGenerate("*", (lengthCharacter / 2)));
       println(message);
       println(characterGenerate("*", (lengthCharacter + title.length())));
   }

   /**
    *
    * @param message aaa
    */
   public static void println(Object message) {
       System.out.println(message);
   }

   /**
    *
    * @param character aaa
    * @param length bbb
    * @return {@link String}
    */
   public static String characterGenerate(String character, int length) {
       return String.valueOf(character).repeat(Math.max(0, length));
   }





}
