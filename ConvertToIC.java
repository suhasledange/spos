import java.io.*;
import java.util.*;

public class ConvertToIC {
    static int symidx=0;
    static LinkedHashMap<String,TableRow> SYMTAB = new LinkedHashMap<>();
    public static void main(String[] args) throws IOException {
        int lc=0;
        String inp,out;
        BufferedReader FRead = new BufferedReader(new FileReader("code.txt"));
        BufferedWriter FWrite = new BufferedWriter(new FileWriter("IC.txt"));

        INSTtable lookup = new INSTtable();
        while((inp = FRead.readLine()) != null){

            String temp[] = inp.split("\\s+");

            if(temp[1].equals("START")){
                lc = Integer.parseInt(temp[2]);
                out = "(AD,01) (C,"+ lc+")";
                FWrite.write(out+"\n");
            }
            if(temp[1].equals("END")){
                out = "(AD,02)";
                FWrite.write(out+"\n");
            }
            if(!temp[0].isEmpty()){
                if(SYMTAB.containsKey(temp[0]))
                    SYMTAB.put(temp[0],new TableRow(temp[0],lc,SYMTAB.get(temp[0]).getIdx()));
                else
                    SYMTAB.put(temp[0],new TableRow(temp[0],lc,++symidx));
            }

            if(temp[1].equals("DC")){
                lc++;
                int constant = Integer.parseInt(temp[2]);
                out = "(DL,01)\t(C,"+constant+")";
                FWrite.write(out+"\n");
            }

            else if(temp[1].equals("DS")){
                int size = Integer.parseInt(temp[2]);
                lc = lc+size;
                out = "(DL,02)\t(C,"+size+")";
                FWrite.write(out+"\n");
            }

            if(lookup.getType(temp[1]).equals("IS")){
                out = "(IS,"+lookup.getCode(temp[1])+") ";
                int j=2;
                String out1="";
                while (j< temp.length){
                    if(lookup.getType(temp[j]).equals("RG"))
                        out1 = lookup.getCode(temp[j])+" ";
                    else{
                        if(SYMTAB.containsKey(temp[j])){
                            int idx = SYMTAB.get(temp[j]).getIdx();
                            out1+= "(S,0"+idx+")";
                        }
                        else{
                            SYMTAB.put(temp[j],new TableRow(temp[j],-1,++symidx));
                            int idx = SYMTAB.get(temp[j]).getIdx();
                            out1+="(S,0"+idx+")";
                        }
                    }
                    j++;
                }
                lc++;
                out = out+out1;
                FWrite.write(out+"\n");
            }
        }
        FRead.close();
        FWrite.close();
        CreateSym();

        System.out.println("Content added with");
        Scanner sc = new Scanner(new File("IC.txt"));
        while (sc.hasNextLine()){
            System.out.println(sc.nextLine());
        }
    }
    public static void CreateSym() throws IOException {
        BufferedWriter FWrite = new BufferedWriter(new FileWriter("symtab.txt"));
        Iterator<String> it = SYMTAB.keySet().iterator();

        while (it.hasNext()){
            String key = it.next().toString();
            TableRow value = SYMTAB.get(key);

            FWrite.write(value.getIdx()+"\t"+value.getSym()+"\t"+value.getAddr()+"\n");
        }
        FWrite.close();
    }

}
