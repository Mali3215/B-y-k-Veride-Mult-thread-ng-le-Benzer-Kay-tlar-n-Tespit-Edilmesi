package com.mycompany.swing;

public class RemovePunctuation {
    public static String removePunc(String row){
        row= row.replaceAll("\\p{Punct}", "");
    
       row=row.replace("            "," ");
       row=row.replace("           "," ");
       row=row.replace("          "," ");
       row=row.replace("         "," ");
       row=row.replace("        "," ");
       row=row.replace("       "," ");
       row=row.replace("      "," ");
       row=row.replace("     "," ");
       row=row.replace("    "," ");
       row=row.replace("   "," ");
       row=row.replace("  "," ");


        return row;
    }
}
