package bioinformatics;

import java.util.ArrayList;
import java.util.List;

public class DNASequence {

    private String name;
    private String sequence;


    DNASequence(String species, String sequence){

        this.name = species;
        this.sequence = sequence;
    }

    //This function will return number of adenine in the sequence
    public int countA(){
        int count = 0;

        for(int i = 0; i < sequence.length(); i++){
            if(sequence.charAt(i) == 'A'){
                count++;
            }
        }

        return count;
    }

    //This function will return number of cytosine in the sequence
    public int countC(){
        int count = 0;

        for(int i = 0; i < sequence.length(); i++){
            if(sequence.charAt(i) == 'C'){
                count++;
            }
        }

        return count;
    }

    //This function will return number of guanine in the sequence
    public int countG(){
        int count = 0;

        for(int i = 0; i < sequence.length(); i++){
            if(sequence.charAt(i) == 'G'){
                count++;
            }
        }

        return count;
    }

    //This function will return number of thymine in the sequence
    public int countT(){
        int count = 0;

        for(int i = 0; i < sequence.length(); i++){
            if(sequence.charAt(i) == 'T'){
                count++;
            }
        }

        return count;
    }

    public int size(){
        return sequence.length();
    }

    public Iterable<String> aminoAcidSequenceFullName(int start, int end){

        List<String> aminoAcidSequenceFullNameList = new ArrayList<>();

        if(sequence.length() < end){
            return null;
        }

        String subSequence = sequence.substring(start, end);

        for(int i =0; i < subSequence.length(); i += 3){

            String codon = subSequence.substring(i, i+3);
            if(SequenceReport.getCodonMap().containsKey(codon)){
                aminoAcidSequenceFullNameList.add(AminoAcid.fullName(SequenceReport.getCodonMap().get(codon)));
            }
        }
        return aminoAcidSequenceFullNameList;
    }

    public Iterable<String> aminoAcidSequenceShortName(int start, int end){

        List<String> aminoAcidSequenceShortNameList = new ArrayList<>();

        if(sequence.length() < end){
            return null;
        }

        String subSequence = sequence.substring(start, end);

        for(int i = 0; i < subSequence.length(); i += 3){

            String codon = subSequence.substring(i, i+3);
            if(SequenceReport.getCodonMap().containsKey(codon)){
                aminoAcidSequenceShortNameList.add(AminoAcid.shortName(SequenceReport.getCodonMap().get(codon)));
            }
        }
        return aminoAcidSequenceShortNameList;
    }

    public Iterable<Integer> subsequencePositions(String subsequence){

        List<Integer> positions = new ArrayList<>();
        int index = 0;

        while (index != -1){

            index = sequence.indexOf(subsequence, index);

            if(index != -1){
                positions.add(index);
                index += subsequence.length();
            }
        }
        return positions;
    }

    public String getName() {
        return name;
    }

    public String getSequence() {
        return sequence;
    }
}

