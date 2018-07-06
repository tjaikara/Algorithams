package bioinformatics;

import stdlib.StdIn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class SequenceReport {

    private static SortedMap<String, String> codonMap = new TreeMap<>();
    private static String Path;
    private static List<DNASequence> speciesList = new ArrayList<>();

    static {
        Path = System.getProperty("user.dir");
        generateCodonMap("/data/codonMap.txt");
    }

    public static void main(String args[]){

        String fileName = "/data/sequences.txt";

        //Reading the input file and creating species list
        try(BufferedReader fin1 = new BufferedReader(new FileReader(Path+fileName))){

            String data;
            DNASequence specie;

            while ((data = fin1.readLine()) != null){

                String [] KeyValue = data.split("\t");

                specie = new DNASequence(KeyValue[0], KeyValue[1]);
                speciesList.add(specie);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        generateSequenceReport();
    }

    //This function will generate sequence report
    private static void generateSequenceReport(){

        for(DNASequence dnaSequence : speciesList){

            System.out.println("Name of the Specie: "+dnaSequence.getName());
            System.out.println("Number of bases in the sequence: "+dnaSequence.size());
            System.out.println("Number of codons in the sequence: "+dnaSequence.size()/3);
            System.out.println("Occurence of adenine: "+ String.format("%,.2f",(dnaSequence.countA()*100.0f/dnaSequence.size()))
                    +"% Occurence of cytosine: " + String.format("%,.2f",(dnaSequence.countC()*100.0f/dnaSequence.size()))
                    +"% Occurence of guanine: " + String.format("%,.2f",(dnaSequence.countG()*100.0f/dnaSequence.size()))
                    +"% Occurence of thymine: " + String.format("%,.2f",(dnaSequence.countT()*100.0f/dnaSequence.size())) +"%");
            System.out.println("Positions in the sequence where the subsequence CCAAT occures : " + dnaSequence.subsequencePositions("CCAAT").toString());
            System.out.println("First twelve amino acids by their short name: "+dnaSequence.aminoAcidSequenceShortName(0, 36).toString());
            System.out.println("First twelve amino acids by their full name: "+dnaSequence.aminoAcidSequenceFullName(0, 36).toString());
            System.out.println("Number of Phenylalanine in the sequence: " + findPhenylalanineCount(dnaSequence));
            System.out.println("Number of Histidine in the sequence: " + findHistidineCount(dnaSequence));
            System.out.println("===============================================================================================================\n");
        }
    }

    /**
     * This method will find the count of amino acid "phenylalanine" in the sequence
     * @param dnaSequence - DNASequence
     * @return - count of phenylalanine in the sequence
     */
    private static int findPhenylalanineCount(DNASequence dnaSequence){

        int count = 0;
        Iterable<String> phenylalanine = dnaSequence.aminoAcidSequenceFullName(0, dnaSequence.getSequence().length());

        for(String value : phenylalanine ){

            if(value.equals("phenylalanine")){
                count++;
            }
        }
        return count;
    }

    /**
     * This method will find the count of amino acid "histidine" in the sequence
     * @param dnaSequence - DNASequence
     * @return - count of histidine in the sequence
     */
    private static int findHistidineCount(DNASequence dnaSequence){

        int count = 0;
        Iterable<String> histidine = dnaSequence.aminoAcidSequenceFullName(0, dnaSequence.getSequence().length());

        for(String value : histidine ){

            if(value.equals("histidine")){
                count++;
            }
        }
        return count;
    }


    /**
     * This method will generate codon map
     * @param fileName - text file name which contain codon table
     */
    private static void generateCodonMap(String fileName){

        try(BufferedReader fin1 = new BufferedReader(new FileReader(Path+fileName))){

            String data;
            while ((data = fin1.readLine()) != null){

                String [] KeyValue = data.split("\t");
                if(KeyValue.length == 2){
                    codonMap.put(KeyValue[0], KeyValue[1]);
                }
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    //This method returns codonMap
    public static SortedMap<String, String> getCodonMap(){
        return codonMap;
    }
}
