package io.springboot.auzmor.message.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageConstants {
	
	   public static String FROM = "from" ;
	   public static String TO = "to" ;
	   public static String TEXT = "text" ;
	   
	   public static Integer FROMMINLENGTH = 6 ;
	   public static Integer FROMMAXLENGTH = 16 ;
	   
	   public static Integer TOMINLENGTH = 6 ;
	   public static Integer TOMAXLENGTH = 16 ;
	   
	   public static Integer TEXTMINLENGTH = 1 ;
	   public static Integer TEXTMAXLENGTH = 120 ;
	   
	   public static final Set<String> INBOUND_SMS_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(FROM, TO,TEXT));

	   public static final Set<String> OUTBOUND_SMS_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(FROM, TO,TEXT));
}
