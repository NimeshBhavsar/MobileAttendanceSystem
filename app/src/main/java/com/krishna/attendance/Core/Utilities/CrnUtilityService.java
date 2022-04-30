package com.krishna.attendance.Core.Utilities;

import androidx.annotation.NonNull;

import java.text.ParseException;

public class CrnUtilityService {

    /**
     * Parses crn string and gets integer appended to the right of the string or integers occurring
     * first from last
     */
    public static CrnInteger getIntegerFromCrn(@NonNull String crn) {

        char[] array = crn.toCharArray();
        //char[] appenededNumbers = new char[array.length];
        String appenededNumbers = "";
        boolean digitFound = false;

        int endIndex = 0, startIndex = 0;
        // start from last of the string
        // 'i' tracks the loop; 'j' is to assign digits to new variable
        for (int i = array.length - 1, j = 0; i >= 0; i--) {
            if (Character.isDigit(array[i])) {
                appenededNumbers = array[i] + appenededNumbers;
                if (!digitFound) {
                    // indicate the index where the digits start occurring while going through the end
                    endIndex = i;
                    digitFound = true;
                }
            } else {
                if (digitFound) {
                    // means digits already found and now there's non-digit characters occuring; hence break
                    startIndex = i + 1;
                    break;
                }
            }
        }
        CrnInteger crnInteger = new CrnInteger();
        if (appenededNumbers.length() > 0) {

            // check if there's zero(0) prepended in the extracted string. if so then preserve it
            // TODO:: Handle cases for CRNs like 'A099C', 'A0099C' , 'A0010C', 'A010C',
            // TODO:: Handle several CRN types in the above extraction loop (NOT here)
            for(int i=0; i< appenededNumbers.length(); i++){
                if(appenededNumbers.substring(i, i+1).equals("0")){
                    startIndex++;
                } else {
                    break;
                }
            }

            try {
                crnInteger.number = Integer.parseInt(appenededNumbers);
                crnInteger.isParsable = true;
            } catch (Exception ex) {
                ex.printStackTrace();
                crnInteger.isParsable = false;
            }
        } else {
            crnInteger.isParsable = false;
        }

        crnInteger.crn = crn;
        crnInteger.endIndex = endIndex;
        crnInteger.startIndex = startIndex;
        return crnInteger;
    }

    public static String nextCrn(String crn){
        CrnInteger crnInteger = CrnUtilityService.getIntegerFromCrn(crn);
        if(crnInteger.isParsable){

            String s = crn.substring(0, crnInteger.startIndex);

            s += String.valueOf(crnInteger.number+1);

            if(crnInteger.endIndex < crn.length()-1){
                s += crn.substring(crnInteger.endIndex+1, crn.length());
            }
            return s;
        }
        return "";
    }

    public static class CrnInteger {
        public boolean isParsable;
        public int startIndex;
        public int endIndex;
        public int number;
        public String crn;
    }
}
