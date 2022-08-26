import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ConvertToMachine {
   static ArrayList<TableRow> SYMTAB = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader FRead = new BufferedReader(new FileReader("symtab.txt"));
        String line,code;

        while ((line = FRead.readLine()) != null){
                String temp[] = line.split("\\s+");
                SYMTAB.add(new TableRow(temp[1],Integer.parseInt(temp[2]),Integer.parseInt(temp[0])));
        }
        FRead.close();

        FRead = new BufferedReader(new FileReader("IC.txt"));
        BufferedWriter FWrite = new BufferedWriter(new FileWriter("Machine.txt"));

        while ((line = FRead.readLine()) != null){
            String temp[] = line.split("\\s+");

            if(temp[0].contains("AD") || temp[0].contains("DL,02")){
                FWrite.write("\n");
            }
            else if(temp.length==2){

                if(temp[0].contains("DL")){
                    temp[0] = temp[0].replaceAll("[^0-9]","");
                    if(Integer.parseInt(temp[0])==1){
                        int constant = Integer.parseInt(temp[1].replaceAll("[^0-9]",""));
                        code = "00\t0\t"+String.format("%03d",constant)+"\n";
                        FWrite.write(code);
                    }
                }
                else if(temp[0].contains("IS")){
                    int opcode = Integer.parseInt(temp[0].replaceAll("[^0-9]",""));
                    if(opcode == 10){
                        if(temp[1].contains("S")){
                            int symidx = Integer.parseInt(temp[1].replaceAll("[^0-9]",""));
                            code = String.format("%02d",opcode)+"\t0\t"+String.format("%03d",SYMTAB.get(symidx-1).getAddr())+"\n";
                            FWrite.write(code);
                        }
                    }
                }
            }
            else if(temp.length==1 && temp[0].contains("IS")){
                int opcode = Integer.parseInt(temp[0].replaceAll("[^0-9]",""));
                code = String.format("%02d",opcode)+"\t0\t"+String.format("%03d",0)+"\n";
                FWrite.write(code);
            }
            else if(temp[0].contains("IS") && temp.length==3){
                int opcode = Integer.parseInt(temp[0].replaceAll("[^0-9]",""));
                int regcode = Integer.parseInt(temp[1]);

                if(temp[2].contains("S")){
                    int symidx = Integer.parseInt(temp[2].replaceAll("[^0-9]",""));
                    code = String.format("%02d",opcode)+"\t"+regcode+"\t"+String.format("%03d",SYMTAB.get(symidx-1).getAddr())+"\n";
                    FWrite.write(code);
                }
            }

        }
        FWrite.close();
        FRead.close();

        System.out.println("Content added with : ");
        Scanner sc = new Scanner(new File("Machine.txt"));
        while (sc.hasNextLine()){
            System.out.println(sc.nextLine());
        }

    }
}
