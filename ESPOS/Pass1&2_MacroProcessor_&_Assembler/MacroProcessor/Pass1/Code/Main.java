package com.muthadevs;

import java.util.*;
import java.io.*;

public class Main  {
    static String[][] mnt = new String[5][3];
    static String[][] ala = new String[10][2];
    static String[][] mdt = new String[20][2];
    static String[] actual = new String[2];

    static int mntc = 0, mdtc = 0, alac = 0, ac = 0;

    public static void main(String[] args) throws Exception {
        pass1();
        BufferedWriter f1 = new BufferedWriter(new FileWriter("D:\\IntellijWorkspace\\SEM5(EPOS)\\Pass1_MacroProcessor_A\\Files\\MNT.txt"));
        BufferedWriter f2 = new BufferedWriter(new FileWriter("D:\\IntellijWorkspace\\SEM5(EPOS)\\Pass1_MacroProcessor_A\\Files\\MDT.txt"));
        BufferedWriter f3 = new BufferedWriter(new FileWriter("D:\\IntellijWorkspace\\SEM5(EPOS)\\Pass1_MacroProcessor_A\\Files\\ALA.txt"));

        int i,j;
        f1.write("Index\t\tMacro name\t\tMDT index\n");
        for(i=0;i<mntc;i++) {
            for (j = 0; j < 3; j++)
                f1.write(mnt[i][j] + "\t\t");
            f1.write("\n");
        }
        int cnt=0;
        for(i=0;i< actual.length;i++){
            String[] arr = actual[i].split("\\s+");
            f3.write(actual[i]+"\n");
            f3.write("Index\tFormal Parameters\tActual Parameters\n");
            for (int k = 1; k < arr.length; k++) {
                f3.write(k+"\t\t\t\t\t"+ala[cnt++][0]+"\t\t\t\t\t"+arr[k]+"\n");
            }
        }

        f2.write("Index\tMDT Instruction\n");
        for(i=0;i<mdtc;i++) {
            for (j = 0; j < 2; j++)
                f2.write(mdt[i][j] + "\t\t");
            f2.write("\n");
        }

        f1.close();
        f2.close();
        f3.close();

    }

    static void pass1() {
        int  i;
        String s, prev;
        try {
            BufferedReader inp = new BufferedReader(new FileReader("D:\\IntellijWorkspace\\SEM5(EPOS)\\Pass1_MacroProcessor_A\\Files\\INPUT.asm"));

            BufferedWriter output = new BufferedWriter(new
                    FileWriter("D:\\IntellijWorkspace\\SEM5(EPOS)\\Pass1_MacroProcessor_A\\Files\\Pass1_MP_Output.txt"));
            while ((s = inp.readLine()) != null) {
                if (s.equalsIgnoreCase("MACRO")) {
                    prev = s;
                    for (; !(s = inp.readLine()).equalsIgnoreCase("MEND"); mdtc++, prev = s) {
                        if (prev.equalsIgnoreCase("MACRO")) {
                            StringTokenizer st = new StringTokenizer(s);
                            String[] str = new String[st.countTokens()];
                            for (i = 0; i < str.length; i++)
                                str[i] = st.nextToken();
                            mnt[mntc][0] = (mntc + 1) + "";
                            mnt[mntc][1] = str[0];
                            mnt[mntc++][2] = (++mdtc) + "";
                            String[] arr = s.split("\\s+");
                            if(arr.length!=0 && (arr[0].equals("INCR") || arr[0].equals("DECR"))){
                                for (int j = 1; j < arr.length; j++) {
                                    ala[alac++][0] = arr[j];
                                }
                            }
                        }
                        mdt[mdtc - 1][1] = s;
                        mdt[mdtc - 1][0] = Integer.toString(mdtc);
                    }
                    mdt[mdtc - 1][1] = s;
                    mdt[mdtc - 1][0] = Integer.toString(mdtc);
                } else {
                    output.write(s);
                    if(s.equals("INCR N1 N2 AREG")) actual[ac++] = s;
                    if(s.equals("DECR N1 N2 BREG")) actual[ac++] = s;
                    output.newLine();
                }
            }
            output.close();
        } catch (FileNotFoundException ex) {
            System.out.println("UNABLE TO END FILE ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
